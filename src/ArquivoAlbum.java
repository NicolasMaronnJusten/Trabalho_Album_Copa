package src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ArquivoAlbum {

    private static final String ARQUIVO = "album.txt";

    public static void salvar(Album album) {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO));

            for (Figurinha f : album.getFigurinhas()) {

                if (f instanceof FigurinhaJogador) {

                    FigurinhaJogador jogador = (FigurinhaJogador) f;

                    bw.write(

                            jogador.getId() + ";" +

                            jogador.getTipo() + ";" +

                            jogador.getNome() + ";" +

                            jogador.getSelecao().getNome() + ";" +

                            jogador.getRaridade() + ";" +

                            jogador.getClube() + ";" +

                            jogador.getPosicao() + ";" +

                            jogador.getIdade() + ";" +

                            jogador.getNumeroCamisa() + ";" +

                            jogador.getAltura() + ";" +

                            jogador.getPeso() + ";" +

                            jogador.isColada() + ";" +

                            jogador.getQuantidadeRepetida()

                    );

                }

                else if (f instanceof FigurinhaBrasao) {

                    FigurinhaBrasao brasao = (FigurinhaBrasao) f;

                    bw.write(

                            brasao.getId() + ";" +

                            brasao.getTipo() + ";" +

                            brasao.getNome() + ";" +

                            brasao.getSelecao().getNome() + ";" +

                            brasao.getRaridade() + ";" +

                            brasao.isColada() + ";" +

                            brasao.getQuantidadeRepetida()

                    );

                }

                else if (f instanceof FigurinhaSelecao) {

                    FigurinhaSelecao selecao = (FigurinhaSelecao) f;

                    bw.write(

                            selecao.getId() + ";" +

                            selecao.getTipo() + ";" +

                            selecao.getNome() + ";" +

                            selecao.getSelecao().getNome() + ";" +

                            selecao.getRaridade() + ";" +

                            selecao.getTecnico() + ";" +

                            selecao.getTitulos() + ";" +

                            selecao.isColada() + ";" +

                            selecao.getQuantidadeRepetida()

                    );

                }

                bw.newLine();

            }

            bw.close();

            System.out.println("Álbum salvo com sucesso!");

        }

        catch (IOException e) {

            System.out.println("Erro ao salvar arquivo.");

        }

    }

    public static void mostrarArquivo() {

        try {

            BufferedReader br = new BufferedReader(new FileReader(ARQUIVO));

            String linha;

            while ((linha = br.readLine()) != null) {

                System.out.println(linha);

            }

            br.close();

        }

        catch (IOException e) {

            System.out.println("Arquivo não encontrado.");

        }

    }

}