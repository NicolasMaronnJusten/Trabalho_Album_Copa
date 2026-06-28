package src;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArquivoAlbumJson {

    private static final Path ARQUIVO = Paths.get("data", "album.json");

    private ArquivoAlbumJson() {
        // Classe utilitária.
    }

    public static Album carregarOuCriarPadrao() {
        return GerenciadorAlbumCatalogo.carregarAlbum();
    }

    public static Album carregar() {
        return GerenciadorAlbumCatalogo.carregarAlbum();
    }

    public static void salvar(Album album) {
        try {
            Files.createDirectories(ARQUIVO.getParent());
            Files.writeString(ARQUIVO, montarJson(album), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível salvar o álbum em JSON.", e);
        }
    }

    public static void carregarProgresso(Album album) {
        try {
            if (!Files.exists(ARQUIVO)) {
                salvar(album);
                return;
            }

            String conteudo = Files.readString(ARQUIVO, StandardCharsets.UTF_8);
            aplicarJsonNoAlbum(album, conteudo);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível carregar o progresso do álbum.", e);
        }
    }

    public static void mostrarArquivo() {
        try {
            if (!Files.exists(ARQUIVO)) {
                System.out.println("Arquivo JSON ainda não existe. Use a opção de salvar primeiro.");
                return;
            }

            String conteudo = Files.readString(ARQUIVO, StandardCharsets.UTF_8);
            System.out.println(conteudo);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível ler o arquivo JSON.", e);
        }
    }

    private static String montarJson(Album album) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");

        boolean primeiro = true;

        for (Figurinha figurinha : album.getFigurinhas()) {
            if (!figurinha.isColada() && figurinha.getQuantidadeRepetida() == 0) {
                continue;
            }

            if (!primeiro) {
                sb.append(",\n");
            }

            sb.append("  {\n");
            sb.append("    \"codigo\": \"").append(escapar(figurinha.getCodigo())).append("\",\n");
            sb.append("    \"colada\": ").append(figurinha.isColada()).append(",\n");
            sb.append("    \"repetidas\": ").append(figurinha.getQuantidadeRepetida()).append("\n");
            sb.append("  }");

            primeiro = false;
        }

        sb.append("\n]\n");
        return sb.toString();
    }

    private static void aplicarJsonNoAlbum(Album album, String json) {
        Pattern objetoPattern = Pattern.compile("\\{([^}]*)\\}", Pattern.DOTALL);
        Matcher objetoMatcher = objetoPattern.matcher(json);

        while (objetoMatcher.find()) {
            String objeto = objetoMatcher.group(1);
            String codigo = extrairString(objeto, "codigo");
            boolean colada = extrairBoolean(objeto, "colada");
            int repetidas = extrairInt(objeto, "repetidas");

            if (codigo == null || codigo.trim().isEmpty()) {
                continue;
            }

            try {
                Figurinha figurinha = album.buscarFigurinhaPorCodigo(codigo);
                figurinha.setColada(colada);
                figurinha.setQuantidadeRepetida(repetidas);
            } catch (IllegalArgumentException e) {
                // Ignora progresso antigo de código que não existe mais no catálogo.
            }
        }
    }

    private static String extrairString(String objeto, String campo) {
        Pattern pattern = Pattern.compile("\\\"" + campo + "\\\"\\s*:\\s*\\\"([^\\\"]*)\\\"");
        Matcher matcher = pattern.matcher(objeto);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static boolean extrairBoolean(String objeto, String campo) {
        Pattern pattern = Pattern.compile("\\\"" + campo + "\\\"\\s*:\\s*(true|false)");
        Matcher matcher = pattern.matcher(objeto);
        return matcher.find() && Boolean.parseBoolean(matcher.group(1));
    }

    private static int extrairInt(String objeto, String campo) {
        Pattern pattern = Pattern.compile("\\\"" + campo + "\\\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(objeto);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
    }

    private static String escapar(String texto) {
        return texto.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
