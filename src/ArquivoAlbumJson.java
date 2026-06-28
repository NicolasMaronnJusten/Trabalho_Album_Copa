package src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArquivoAlbumJson {

    private static final String ARQUIVO = "album.json";

    public static void salvar(Album album) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {

            writer.write("{\n");
            writer.write("  \"proprietario\": {\n");
            writer.write("    \"nome\": \"" + escaparJson(album.getProprietario().getNome()) + "\",\n");
            writer.write("    \"email\": \"" + escaparJson(album.getProprietario().getEmail()) + "\"\n");
            writer.write("  },\n");
            writer.write("  \"figurinhas\": [\n");

            for (int i = 0; i < album.getFigurinhas().size(); i++) {

                Figurinha figurinha = album.getFigurinhas().get(i);

                writer.write("    {\n");
                writer.write("      \"tipo\": \"" + figurinha.getTipo() + "\",\n");
                writer.write("      \"id\": " + figurinha.getId() + ",\n");
                writer.write("      \"nome\": \"" + escaparJson(figurinha.getNome()) + "\",\n");
                writer.write("      \"selecao\": {\n");
                writer.write("        \"nome\": \"" + escaparJson(figurinha.getSelecao().getNome()) + "\",\n");
                writer.write("        \"pais\": \"" + escaparJson(figurinha.getSelecao().getPais()) + "\"\n");
                writer.write("      },\n");
                writer.write("      \"raridade\": \"" + figurinha.getRaridade() + "\",\n");
                writer.write("      \"colada\": " + figurinha.isColada() + ",\n");
                writer.write("      \"quantidadeRepetida\": " + figurinha.getQuantidadeRepetida());

                if (figurinha instanceof FigurinhaJogador) {

                    FigurinhaJogador jogador = (FigurinhaJogador) figurinha;

                    writer.write(",\n");
                    writer.write("      \"altura\": " + jogador.getAltura() + ",\n");
                    writer.write("      \"peso\": " + jogador.getPeso() + ",\n");
                    writer.write("      \"clube\": \"" + escaparJson(jogador.getClube()) + "\",\n");
                    writer.write("      \"posicao\": \"" + escaparJson(jogador.getPosicao()) + "\",\n");
                    writer.write("      \"idade\": " + jogador.getIdade() + ",\n");
                    writer.write("      \"numeroCamisa\": " + jogador.getNumeroCamisa());

                } else if (figurinha instanceof FigurinhaSelecao) {

                    FigurinhaSelecao selecao = (FigurinhaSelecao) figurinha;

                    writer.write(",\n");
                    writer.write("      \"tecnico\": \"" + escaparJson(selecao.getTecnico()) + "\",\n");
                    writer.write("      \"titulos\": " + selecao.getTitulos());

                }

                writer.write("\n    }");

                if (i < album.getFigurinhas().size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");

            }

            writer.write("  ]\n");
            writer.write("}\n");

            System.out.println("Álbum salvo em JSON com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo JSON.");
        }

    }

    public static void mostrarArquivo() {

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {

            String linha;

            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }

        } catch (IOException e) {
            System.out.println("Arquivo JSON não encontrado.");
        }

    }

    private static String escaparJson(String valor) {

        if (valor == null) {
            return "";
        }

        return valor.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");

    }

}
