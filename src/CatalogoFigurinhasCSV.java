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

public class CatalogoFigurinhasCSV { // classe que representa o catálogo de figurinhas, carregado a partir de um arquivo CSV

    private static final Path CAMINHO_PADRAO = Paths.get("data", "catalogo_figurinhas.csv");

    private List<RegistroCatalogo> registros; // lista de registros do catálogo de figurinhas
    private Map<String, RegistroCatalogo> porCodigo; // mapa de registros do catálogo de figurinhas por código

    public CatalogoFigurinhasCSV() { 
        this.registros = new ArrayList<>();
        this.porCodigo = new HashMap<>();
    }

    public static CatalogoFigurinhasCSV carregarPadrao() {  // carrega o catálogo padrão de figurinhas (catalogo_figurinhas.csv)
        return carregar(CAMINHO_PADRAO);
    }

    public static CatalogoFigurinhasCSV carregar(Path caminho) { 
        CatalogoFigurinhasCSV catalogo = new CatalogoFigurinhasCSV();
        catalogo.lerArquivo(caminho);
        return catalogo;
    }

    private void lerArquivo(Path caminho) { // lê o arquivo CSV e cria os registros do catálogo de figurinhas
        try {
            if (!Files.exists(caminho)) {
                throw new IllegalArgumentException("Catálogo não encontrado em: " + caminho.toAbsolutePath());
            }

            List<String> linhas = Files.readAllLines(caminho, StandardCharsets.UTF_8);

            for (int i = 1; i < linhas.size(); i++) { //comeca pela 1 pois a primeira e o cabecalho
                String linha = linhas.get(i).trim(); 

                if (linha.isEmpty()) { 
                    continue;
                }

                String[] colunas = separarLinhaCSV(linha); // separa a linha em colunas, considerando que o separador é ";"

                if (colunas.length < 8) {
                    throw new IllegalArgumentException("Linha inválida no catálogo: " + (i + 1));
                }

                RegistroCatalogo registro = new RegistroCatalogo( // Transformar texto em em objeto java
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

    private void adicionar(RegistroCatalogo registro) { // verifica se o codigo ja existe 
        String codigo = normalizarCodigo(registro.getCodigo());

        if (porCodigo.containsKey(codigo)) {
            throw new IllegalArgumentException("Código duplicado no catálogo: " + registro.getCodigo()); 
        }

        registros.add(registro);
        porCodigo.put(codigo, registro);
    }

    public RegistroCatalogo buscarPorCodigo(String codigo) { //recebe o codigo, normaliza, e busca no mapa 
        RegistroCatalogo registro = porCodigo.get(normalizarCodigo(codigo));

        if (registro == null) {
            throw new IllegalArgumentException("Código não existe no catálogo oficial: " + codigo);
        }

        return registro;
    }

    public boolean existeCodigo(String codigo) { // verifica se o codigo existe no mapa
        return porCodigo.containsKey(normalizarCodigo(codigo));
    }

    public List<RegistroCatalogo> listarTodos() { 
        return new ArrayList<>(registros);
    }

    public Album criarAlbumBase(Proprietario proprietario) {  // transforma catalogo em um album
        Album album = new Album(proprietario);
        Map<String, Selecao> selecoes = new LinkedHashMap<>();

        for (RegistroCatalogo registro : registros) { 
            Selecao selecao = selecoes.computeIfAbsent( //computeIfAbsent para evitar criar várias seleções repetidas.
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
