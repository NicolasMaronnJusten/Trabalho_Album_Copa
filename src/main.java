package src;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Proprietario proprietario =
                new Proprietario("Maicon-Amelio", "maiconenriqueamelio@gmail.com");

        Album album = new Album(proprietario);

        // ===========================
        // Seleções
        // ===========================

        Selecao brasil = new Selecao("Brasil", "Brasil");
        Selecao argentina = new Selecao("Argentina", "Argentina");
        Selecao franca = new Selecao("França", "França");
        Selecao portugal = new Selecao("Portugal", "Portugal");

        // ===========================
        // Cadastro das Figurinhas
        // ===========================

        album.adicionarFigurinha(

                new FigurinhaJogador(

                        1,
                        "Vinicius Jr",
                        brasil,
                        1.76,
                        73,
                        "Real Madrid",
                        "Atacante",
                        25,
                        7,
                        Raridade.RARA

                )

        );

        album.adicionarFigurinha(

                new FigurinhaJogador(

                        2,
                        "Rodrygo",
                        brasil,
                        1.74,
                        65,
                        "Real Madrid",
                        "Atacante",
                        24,
                        11,
                        Raridade.COMUM

                )

        );

        album.adicionarFigurinha(

                new FigurinhaJogador(

                        3,
                        "Messi",
                        argentina,
                        1.70,
                        72,
                        "Inter Miami",
                        "Atacante",
                        39,
                        10,
                        Raridade.LENDARIA

                )

        );

        album.adicionarFigurinha(

                new FigurinhaJogador(

                        4,
                        "Mbappé",
                        franca,
                        1.78,
                        75,
                        "Real Madrid",
                        "Atacante",
                        27,
                        9,
                        Raridade.EPICA

                )

        );

        album.adicionarFigurinha(

                new FigurinhaJogador(

                        5,
                        "Cristiano Ronaldo",
                        portugal,
                        1.87,
                        83,
                        "Al-Nassr",
                        "Atacante",
                        41,
                        7,
                        Raridade.LENDARIA

                )

        );

        album.adicionarFigurinha(

                new FigurinhaBrasao(

                        100,
                        "Escudo Brasil",
                        brasil,
                        Raridade.COMUM

                )

        );

        album.adicionarFigurinha(

                new FigurinhaBrasao(

                        101,
                        "Escudo Argentina",
                        argentina,
                        Raridade.BRILHANTE

                )

        );

        album.adicionarFigurinha(

                new FigurinhaBrasao(

                        102,
                        "Escudo França",
                        franca,
                        Raridade.COMUM

                )

        );

        album.adicionarFigurinha(

                new FigurinhaBrasao(

                        103,
                        "Escudo Portugal",
                        portugal,
                        Raridade.BRILHANTE

                )

        );

        album.adicionarFigurinha(

                new FigurinhaSelecao(

                        200,
                        brasil,
                        Raridade.RARA,
                        "Carlo Ancelotti",
                        5
                )

    );

        album.adicionarFigurinha(

                new FigurinhaSelecao(

                        201,
                        argentina,
                        Raridade.COMUM,
                        "Lionel Scaloni",
                        3
                )

    );

        album.adicionarFigurinha(

                new FigurinhaSelecao(

                        202,
                        portugal,
                        Raridade.COMUM,
                        "Roberto Martínez",
                        0

    )

);

        int opcao = -1;

        while(opcao != 0){

            System.out.println("\n==============================");
            System.out.println("ÁLBUM COPA DO MUNDO 2026");
            System.out.println("==============================");

            System.out.println("1 - Colar figurinha");
            System.out.println("2 - Registrar repetida");
            System.out.println("3 - Total de coladas");
            System.out.println("4 - Total de repetidas");
            System.out.println("5 - Listar faltantes");
            System.out.println("6 - Faltantes por seleção");
            System.out.println("7 - Listar por tipo");
            System.out.println("8 - Listar por raridade");
            System.out.println("9 - Buscar figurinha pelo nome");
            System.out.println("10 - Mostrar resumo");
            System.out.println("11 - Salvar álbum em TXT");
            System.out.println("12 - Mostrar arquivo TXT");
            System.out.println("13 - Salvar álbum em JSON");
            System.out.println("14 - Mostrar arquivo JSON");
            System.out.println("0 - Sair");

            System.out.print("\nEscolha: ");

            try{

                opcao = sc.nextInt();
                sc.nextLine();

                switch(opcao){
                    case 1:

                        System.out.print("Digite o ID da figurinha: ");

                        int id = sc.nextInt();

                        album.registrarFigurinhaColada(id);

                        System.out.println("Figurinha colada com sucesso!");

                        break;

                    case 2:

                        System.out.print("Digite o ID da figurinha: ");

                        id = sc.nextInt();

                        album.registrarFigurinhaRepetida(id);

                        System.out.println("Figurinha repetida registrada!");

                        break;

                    case 3:

                        System.out.println();

                        System.out.println("Total de figurinhas coladas: "
                                + album.calcularTotalColadas());

                        break;

                    case 4:

                        System.out.println();

                        System.out.println("Total de figurinhas repetidas: "
                                + album.calcularTotalRepetidas());

                        break;

                    case 5:

                        System.out.println();

                        List<Figurinha> faltantes =
                                album.listarFaltantes();

                        if(faltantes.isEmpty()){

                            System.out.println("Nenhuma figurinha faltando.");

                        }else{

                            for(Figurinha f : faltantes){

                                System.out.println(f);

                            }

                        }

                        break;

                    case 6:

                        System.out.print("Digite a seleção: ");

                        String selecao = sc.nextLine();

                        List<Figurinha> listaSelecao =
                                album.listarFaltantesPorSelecao(selecao);

                        if(listaSelecao.isEmpty()){

                            System.out.println("Nenhuma figurinha encontrada.");

                        }else{

                            for(Figurinha f : listaSelecao){

                                System.out.println(f);

                            }

                        }

                        break;

                    case 7:

                        System.out.println("Tipos disponíveis:");

                        for(Tipo t : Tipo.values()){

                            System.out.println("- " + t);

                        }

                        System.out.print("Digite o tipo: ");

                        Tipo tipo =
                                Tipo.valueOf(sc.nextLine().toUpperCase());

                        List<Figurinha> listaTipo =
                                album.listarPorTipo(tipo);

                        for(Figurinha f : listaTipo){

                            System.out.println(f);

                        }

                        break;

                    case 8:

                        System.out.println("Raridades disponíveis:");

                        for(Raridade r : Raridade.values()){

                            System.out.println("- " + r);

                        }

                        System.out.print("Digite a raridade: ");

                        Raridade raridade =
                                Raridade.valueOf(sc.nextLine().toUpperCase());

                        List<Figurinha> listaRaridade =
                                album.listarPorRaridade(raridade);

                        for(Figurinha f : listaRaridade){

                            System.out.println(f);

                        }

                        break;

                    case 9:

                        System.out.print("Digite o nome da figurinha: ");

                        String nome = sc.nextLine();

                        System.out.println(album.buscarPorNome(nome));

                        break;

                    case 10:

                        album.mostrarResumo();

                        break;

                    case 11:

                        ArquivoAlbum.salvar(album);

                        break;

                    case 12:

                        ArquivoAlbum.mostrarArquivo();

                        break;

                    case 13:

                        ArquivoAlbumJson.salvar(album);

                        break;

                    case 14:

                        ArquivoAlbumJson.mostrarArquivo();

                        break;

                    case 0:

                        System.out.println("Programa encerrado.");

                        break;

                    default:

                        System.out.println("Opção inválida.");

                }

            }

            catch(InputMismatchException e){

                System.out.println("Digite apenas números.");

                sc.nextLine();

            }

            catch(IllegalArgumentException e){

                System.out.println(e.getMessage());

            }

            catch(Exception e){

                System.out.println("Erro: " + e.getMessage());

            }

        }

        sc.close();

    }

}