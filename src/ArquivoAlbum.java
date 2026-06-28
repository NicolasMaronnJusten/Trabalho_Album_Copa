package src;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArquivoAlbum {

    private static final Path ARQUIVO = Paths.get("data", "album.txt");

    private ArquivoAlbum() {
        // Classe utilitária.
    }

    public static void salvar(Album album) {
        try {
            Files.createDirectories(ARQUIVO.getParent());
            Files.writeString(ARQUIVO, montarTexto(album), StandardCharsets.UTF_8);
            System.out.println("Álbum salvo em TXT: " + ARQUIVO.toAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível salvar o arquivo TXT.", e);
        }
    }

    public static void mostrarArquivo() {
        try {
            if (!Files.exists(ARQUIVO)) {
                System.out.println("Arquivo TXT ainda não existe. Use a opção de salvar primeiro.");
                return;
            }

            String conteudo = Files.readString(ARQUIVO, StandardCharsets.UTF_8);
            System.out.println(conteudo);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível ler o arquivo TXT.", e);
        }
    }

    private static String montarTexto(Album album) {
        StringBuilder sb = new StringBuilder();

        sb.append(album.gerarResumo()).append("\n");
        sb.append("========== FIGURINHAS ==========\n");

        for (Figurinha figurinha : album.getFigurinhas()) {
            sb.append(figurinha).append("\n");
        }

        return sb.toString();
    }
}
