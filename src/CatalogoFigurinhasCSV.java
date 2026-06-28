package src;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CatalogoFigurinhasCSV {

    private static final Path CAMINHO_PADRAO = Paths.get("data", "catalogo_figurinhas.csv");

    private List<RegistroCatalogo> registros;
    private Map<String, RegistroCatalogo> porCodigo;

    public CatalogoFigurinhasCSV() {
        this.registros = new ArrayList<>();
        this.porCodigo = new HashMap<>();
    }

    public static CatalogoFigurinhasCSV carregarPadrao() {
        return carregar(CAMINHO_PADRAO);
    }

    public static CatalogoFigurinhasCSV carregar(Path caminho) {
        CatalogoFigurinhasCSV catalogo = new CatalogoFigurinhasCSV();
        catalogo.lerArquivo(caminho);
        return catalogo;
    }

    private void lerArquivo(Path caminho) {
        try {
            if (!Files.exists(caminho)) {
                throw new IllegalArgumentException("Catálogo não encontrado em: " + caminho.toAbsolutePath());
            }

            List<String> linhas = Files.readAllLines(caminho, StandardCharsets.UTF_8);

            for (int i = 1; i < linhas.size(); i++) {
                String linha = linhas.get(i).trim();

                if (linha.isEmpty()) {
                    continue;
                }

                String[] colunas = separarLinhaCSV(linha);

                if (colunas.length < 8) {
                    throw new IllegalArgumentException("Linha inválida no catálogo: " + (i + 1));
                }

                RegistroCatalogo registro = new RegistroCatalogo(
                        colunas[0].trim().toUpperCase(),
                        Integer.parseInt(colunas[1].trim()),
                        colunas[2].trim(),
                        colunas[3].trim(),
                        Tipo.valueOf(colunas[4].trim().toUpperCase()),
                        colunas[5].trim(),
                        colunas[6].trim(),
                        Raridade.valueOf(colunas[7].trim().toUpperCase())
                );

                adicionar(registro);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o catálogo de figurinhas.", e);
        }
    }

    private void adicionar(RegistroCatalogo registro) {
        String codigo = normalizarCodigo(registro.getCodigo());

        if (porCodigo.containsKey(codigo)) {
            throw new IllegalArgumentException("Código duplicado no catálogo: " + registro.getCodigo());
        }

        registros.add(registro);
        porCodigo.put(codigo, registro);
    }

    public RegistroCatalogo buscarPorCodigo(String codigo) {
        RegistroCatalogo registro = porCodigo.get(normalizarCodigo(codigo));

        if (registro == null) {
            throw new IllegalArgumentException("Código não existe no catálogo oficial: " + codigo);
        }

        return registro;
    }

    public boolean existeCodigo(String codigo) {
        return porCodigo.containsKey(normalizarCodigo(codigo));
    }

    public List<RegistroCatalogo> listarTodos() {
        return new ArrayList<>(registros);
    }

    public Album criarAlbumBase(Proprietario proprietario) {
        Album album = new Album(proprietario);
        Map<String, Selecao> selecoes = new LinkedHashMap<>();

        for (RegistroCatalogo registro : registros) {
            Selecao selecao = selecoes.computeIfAbsent(
                    normalizarTexto(registro.getSelecao()),
                    chave -> new Selecao(registro.getSelecao(), registro.getSelecao())
            );

            Figurinha figurinha;

            if (registro.getTipo() == Tipo.JOGADOR) {
                figurinha = new FigurinhaJogador(
                        registro.getId(),
                        registro.getCodigo(),
                        registro.getNome(),
                        selecao,
                        registro.getClube(),
                        registro.getPosicao(),
                        registro.getRaridade()
                );
            } else if (registro.getTipo() == Tipo.BRASAO) {
                figurinha = new FigurinhaBrasao(
                        registro.getId(),
                        registro.getCodigo(),
                        registro.getNome(),
                        selecao,
                        registro.getRaridade()
                );
            } else if (registro.getTipo() == Tipo.SELECAO) {
                figurinha = new FigurinhaSelecao(
                        registro.getId(),
                        registro.getCodigo(),
                        selecao,
                        registro.getRaridade()
                );
            } else if (registro.getTipo() == Tipo.ESTADIO) {
                figurinha = new FigurinhaEstadio(
                        registro.getId(),
                        registro.getCodigo(),
                        registro.getNome(),
                        selecao,
                        registro.getRaridade()
                );
            } else {
                figurinha = new FigurinhaEstadio(
                        registro.getId(),
                        registro.getCodigo(),
                        registro.getNome(),
                        selecao,
                        registro.getRaridade()
                );
            }

            album.adicionarFigurinha(figurinha);
        }

        return album;
    }

    private String[] separarLinhaCSV(String linha) {
        List<String> partes = new ArrayList<>();
        StringBuilder atual = new StringBuilder();
        boolean dentroDeAspas = false;

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);

            if (c == '"') {
                dentroDeAspas = !dentroDeAspas;
            } else if (c == ';' && !dentroDeAspas) {
                partes.add(atual.toString());
                atual.setLength(0);
            } else {
                atual.append(c);
            }
        }

        partes.add(atual.toString());
        return partes.toArray(new String[0]);
    }

    private String normalizarCodigo(String codigo) {
        if (codigo == null) {
            return "";
        }

        return codigo.trim().toUpperCase();
    }

    private String normalizarTexto(String texto) {
        if (texto == null) {
            return "";
        }

        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        normalizado = normalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return normalizado.trim().toLowerCase();
    }
}
