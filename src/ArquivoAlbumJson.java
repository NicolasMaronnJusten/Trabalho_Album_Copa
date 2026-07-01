package src;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
    
public class ArquivoAlbumJson { // responsável por salvar e carregar o progresso do álbum em JSON.

    private static final Path ARQUIVO = Paths.get("data", "album.json");

    private ArquivoAlbumJson() {
        
    }

    public static Album carregarOuCriarPadrao() {
        return GerenciadorAlbumCatalogo.carregarAlbum();
    }

    public static Album carregar() {
        return GerenciadorAlbumCatalogo.carregarAlbum();
    }

    public static void salvar(Album album) {
        try {
            Files.createDirectories(ARQUIVO.getParent());  //garante q a pasta data existe
            Files.writeString(ARQUIVO, montarJson(album), StandardCharsets.UTF_8); // salva o album em json
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível salvar o álbum em JSON.", e);
        }
    }
    public static boolean precisaNomeProprietario() { // verifica se o arquivo existe e se o nome do proprietário está vazio
    try {
        if (!Files.exists(ARQUIVO)) {
            return true;
        }

        String conteudo = Files.readString(ARQUIVO, StandardCharsets.UTF_8);
        String proprietario = extrairString(conteudo, "proprietario");

        return proprietario == null || proprietario.trim().isEmpty();
        } catch (IOException e) {
        return true; // se nao existe, ou nao tem proprietario, precisa pedir o nome do proprietario 
        }
    }

    public static void carregarProgresso(Album album) {
        try {
            if (!Files.exists(ARQUIVO)) { // se existe, salva
                salvar(album);
                return;
            }

            String conteudo = Files.readString(ARQUIVO, StandardCharsets.UTF_8); // le o arquivo e aplica 
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

    private static String montarJson(Album album) { // transforma o objeto album em uma string json
    StringBuilder sb = new StringBuilder();
//salva o proprietario e as figurinhas coladas e repetidas, ignorando as que não estão coladas e não tem repetidas
    sb.append("{\n");
    sb.append("  \"proprietario\": \"")
      .append(escapar(album.getProprietario().getNome()))
      .append("\",\n");

    sb.append("  \"figurinhas\": [\n");

    boolean primeiro = true;

    for (Figurinha figurinha : album.getFigurinhas()) {
        if (!figurinha.isColada() && figurinha.getQuantidadeRepetida() == 0) {
            continue;
        }

        if (!primeiro) {
            sb.append(",\n");
        }

            sb.append("    {\n");
            sb.append("      \"codigo\": \"").append(escapar(figurinha.getCodigo())).append("\",\n");
            sb.append("      \"colada\": ").append(figurinha.isColada()).append(",\n");
            sb.append("      \"repetidas\": ").append(figurinha.getQuantidadeRepetida()).append("\n");
            sb.append("    }");

            primeiro = false;
        }

        sb.append("\n  ]\n");
        sb.append("}\n");

        return sb.toString();
    }
    

    private static void aplicarJsonNoAlbum(Album album, String json) {  //Esse método lê o JSON e restaura o estado do álbum
    String nomeProprietario = extrairString(json, "proprietario"); // pega o proprietario 

    if (nomeProprietario != null && !nomeProprietario.trim().isEmpty()) {
        album.getProprietario().setNome(nomeProprietario); // atualiza o nome
    }

    Pattern objetoPattern = Pattern.compile("\\{([^}]*)\\}", Pattern.DOTALL);  //usando o regex, ele procura os objetos dentro do JSON
    Matcher objetoMatcher = objetoPattern.matcher(json);

    while (objetoMatcher.find()) { //extrai os valores de cada objeto encontrado
        String objeto = objetoMatcher.group(1);
        String codigo = extrairString(objeto, "codigo");
        boolean colada = extrairBoolean(objeto, "colada");
        int repetidas = extrairInt(objeto, "repetidas");

        if (codigo == null || codigo.trim().isEmpty()) {
            continue;
        }

        try { 
            Figurinha figurinha = album.buscarFigurinhaPorCodigo(codigo); // E aplica o progresso
            figurinha.setColada(colada);
            figurinha.setQuantidadeRepetida(repetidas);
        } catch (IllegalArgumentException e) {
            System.err.println("Aviso: Figurinha com código '" + codigo + "' não encontrada no álbum. Ignorando.");
        }
    }
}
// metodos de extracao de valores do json, usando regex para pegar os valores de cada campo
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
