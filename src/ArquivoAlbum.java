package src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

                            jogador.getNome() + ";" +

                            jogador.getTipo() + ";" +

                            jogador.getRaridade() + ";" +

                            jogador.getSelecao().getNome() + ";" +

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

                            brasao.getNome() + ";" +

                            brasao.getTipo() + ";" +

                            brasao.getRaridade() + ";" +

                            brasao.getSelecao().getNome() + ";" +

                            "-" + ";" +

                            "-" + ";" +

                            "-" + ";" +

                            "-" + ";" +

                            "-" + ";" +

                            "-" + ";" +

                            brasao.isColada() + ";" +

                            brasao.getQuantidadeRepetida()

                    );

                }

                bw.newLine();

            }

            bw.close();

            System.out.println("Álbum salvo com sucesso.");

        }

        catch(IOException e){

            System.out.println("Erro ao salvar arquivo.");

        }

    }

    public static void mostrarArquivo(){

        try{

            BufferedReader br = new BufferedReader(new FileReader(ARQUIVO));

            String linha;

            while((linha = br.readLine()) != null){

                System.out.println(linha);

            }

            br.close();

        }

        catch(IOException e){

            System.out.println("Arquivo inexistente.");

        }

    }

}