package src;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Album album = GerenciadorAlbumCatalogo.carregarAlbum();
        int opcao = -1;

        while (opcao != 0) {

            mostrarMenu();

            try {
                System.out.print("\nEscolha: ");
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {

                    case 1:
                        buscarJogadorPorNome(sc, album);
                        break;

                    case 2:
                        colarFigurinha(sc, album);
                        break;

                    case 3:
                        registrarRepetida(sc, album);
                        break;

                    case 4:
                        consultarPorCodigo(sc, album);
                        break;

                    case 5:
                        System.out.println("Total de figurinhas coladas: "
                                + album.calcularTotalColadas());
                        break;

                    case 6:
                        System.out.println("Total de figurinhas repetidas: "
                                + album.calcularTotalRepetidas());
                        break;

                    case 7:
                        imprimirLista(
                                album.listarFaltantes(),
                                "Nenhuma figurinha faltando."
                        );
                        break;

                    case 8:
                        listarFaltantesPorSelecao(sc, album);
                        break;

                    case 9:
                        listarPorTipo(sc, album);
                        break;

                    case 10:
                        listarPorRaridade(sc, album);
                        break;

                    case 11:
                        album.mostrarResumo();
                        break;

                    case 12:
                        ArquivoAlbum.salvar(album);
                        break;

                    case 13:
                        ArquivoAlbum.mostrarArquivo();
                        break;

                    case 14:
                        ArquivoAlbumJson.salvar(album);
                        System.out.println("Álbum salvo em JSON com sucesso!");
                        break;

                    case 15:
                        ArquivoAlbumJson.mostrarArquivo();
                        break;

                    case 16:
                        imprimirLista(
                                album.listarJogadores(),
                                "Nenhum jogador cadastrado."
                        );
                        break;

                    case 17:
                        buscarJogadores(sc, album);
                        break;

                    case 18:
                        album = GerenciadorAlbumCatalogo.carregarAlbum();
                        System.out.println(
                                "Álbum recarregado do catálogo CSV e do JSON com sucesso!"
                        );
                        break;

                    case 19:
                        abrirInterfaceGrafica(album);
                        break;

                    case 20:
                        sobreSistema();
                        break;

                    case 0:
                        System.out.println("Programa encerrado.");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Digite apenas números.");
                sc.nextLine();

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        sc.close();
    }private static void mostrarMenu() {

    System.out.println("\n==============================");
    System.out.println("ÁLBUM COPA DO MUNDO 2026");
    System.out.println("==============================");

    System.out.println("1 - Buscar jogador pelo nome");
    System.out.println("2 - Colar figurinha por código");
    System.out.println("3 - Registrar repetida por código");
    System.out.println("4 - Consultar figurinha por código");
    System.out.println("5 - Total de coladas");
    System.out.println("6 - Total de repetidas");
    System.out.println("7 - Listar faltantes");
    System.out.println("8 - Faltantes por seleção");
    System.out.println("9 - Listar por tipo");
    System.out.println("10 - Listar por raridade");
    System.out.println("11 - Mostrar resumo");
    System.out.println("12 - Salvar álbum em TXT");
    System.out.println("13 - Mostrar arquivo TXT");
    System.out.println("14 - Salvar álbum em JSON");
    System.out.println("15 - Mostrar arquivo JSON");
    System.out.println("16 - Listar jogadores");
    System.out.println("17 - Buscar jogadores");
    System.out.println("18 - Recarregar álbum");
    System.out.println("19 - Abrir interface gráfica");
    System.out.println("20 - Sobre o sistema");
    System.out.println("0 - Sair");

}

private static void buscarJogadorPorNome(Scanner sc, Album album) {
    System.out.println("\n========== BUSCAR JOGADOR ==========");
    System.out.print("Digite o nome ou parte do nome: ");
    String nome = sc.nextLine();
    List<FigurinhaJogador> jogadores = album.buscarJogadoresPorNome(nome);

    if (jogadores.isEmpty()) {
        System.out.println("\nNenhum jogador encontrado.");
        return;

    }
    System.out.println("\nJogadores encontrados:\n");
    for (FigurinhaJogador jogador : jogadores) {

        System.out.println(jogador);
    }
}

private static void colarFigurinha(Scanner sc, Album album) {
    System.out.print("Digite o código oficial da figurinha: ");
    String codigo = sc.nextLine();
    Figurinha figurinha = album.buscarFigurinhaPorCodigo(codigo);
    System.out.println("\nFigurinha encontrada:");
    System.out.println(figurinha);
    System.out.print("\nDeseja colar essa figurinha? (S/N): ");
    String resposta = sc.nextLine();

    if (resposta.equalsIgnoreCase("S")) {
        album.registrarFigurinhaColada(codigo);
        ArquivoAlbumJson.salvar(album);
        System.out.println("Figurinha colada com sucesso!");

    } else {
        System.out.println("Operação cancelada.");
    }

}

private static void registrarRepetida(Scanner sc, Album album) {
    System.out.print("Digite o código oficial da figurinha repetida: ");
    String codigo = sc.nextLine();
    Figurinha figurinha = album.buscarFigurinhaPorCodigo(codigo);
    System.out.println("\nFigurinha encontrada:");
    System.out.println(figurinha);
    System.out.print("\nDeseja registrar como repetida? (S/N): ");
    String resposta = sc.nextLine();

    if (resposta.equalsIgnoreCase("S")) {
        album.registrarFigurinhaRepetida(codigo);
        ArquivoAlbumJson.salvar(album);
        System.out.println("Figurinha repetida registrada!");

    } else {
        System.out.println("Operação cancelada.");
    }
}

private static void consultarPorCodigo(Scanner sc, Album album) {
    System.out.print("Digite o código oficial da figurinha: ");
    String codigo = sc.nextLine();
    System.out.println(album.consultarFigurinhaComoTexto(codigo));

}private static void listarFaltantesPorSelecao(Scanner sc, Album album) {
    System.out.print("Digite a seleção: ");
    String selecao = sc.nextLine();
    imprimirLista(
            album.listarFaltantesPorSelecao(selecao),
            "Nenhuma figurinha encontrada."
    );
}

private static void listarPorTipo(Scanner sc, Album album) {
    System.out.println("\nTipos disponíveis:");
    for (Tipo t : Tipo.values()) {
        System.out.println("- " + t);
    }

    System.out.print("\nDigite o tipo: ");
    Tipo tipo = Tipo.valueOf(sc.nextLine().toUpperCase());
    imprimirLista(
            album.listarPorTipo(tipo),
            "Nenhuma figurinha encontrada para esse tipo."
    );
}

private static void listarPorRaridade(Scanner sc, Album album) {
    System.out.println("\nRaridades disponíveis:");
    for (Raridade r : Raridade.values()) {
        System.out.println("- " + r);
    }

    System.out.print("\nDigite a raridade: ");
    Raridade raridade = Raridade.valueOf(sc.nextLine().toUpperCase());
    imprimirLista(
            album.listarPorRaridade(raridade),
            "Nenhuma figurinha encontrada para essa raridade."
    );
}

private static void buscarJogadores(Scanner sc, Album album) {

    System.out.println("\n========== BUSCAR JOGADORES ==========");
    System.out.println("1 - Nome");
    System.out.println("2 - Seleção");
    System.out.println("3 - Clube");
    System.out.println("4 - Posição");
    System.out.println("5 - Busca geral");
    System.out.print("\nEscolha: ");

    int opcaoBusca = sc.nextInt();

    sc.nextLine();

    switch (opcaoBusca) {

        case 1:
            System.out.print("Nome: ");
            imprimirLista(
                    album.buscarJogadoresPorNome(sc.nextLine()),
                    "Nenhum jogador encontrado."
            );
            break;

        case 2:
            System.out.print("Seleção: ");
            imprimirLista(
                    album.buscarJogadoresPorSelecao(sc.nextLine()),
                    "Nenhum jogador encontrado."
            );
            break;

        case 3:
            System.out.print("Clube: ");
            imprimirLista(
                    album.buscarJogadoresPorClube(sc.nextLine()),
                    "Nenhum jogador encontrado."
            );
            break;

        case 4:
            System.out.print("Posição: ");
            imprimirLista(
                    album.buscarJogadoresPorPosicao(sc.nextLine()),
                    "Nenhum jogador encontrado."
            );
            break;

        case 5:
            System.out.print("Texto da busca: ");
            imprimirLista(
                    album.buscarJogadoresGeral(sc.nextLine()),
                    "Nenhum jogador encontrado."
            );
            break;

        default:
            System.out.println("Opção inválida.");
            break;
    }

} private static void abrirInterfaceGrafica(Album album) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal(album);
            tela.setVisible(true);
        });
    }

    private static void sobreSistema() {
        System.out.println("\n====================================");
        System.out.println("      ÁLBUM COPA DO MUNDO 2026");
        System.out.println("====================================");
        System.out.println("Sistema de gerenciamento de");
        System.out.println("figurinhas da Copa do Mundo 2026.");
        System.out.println();
        System.out.println("Recursos disponíveis:");
        System.out.println("- Cadastro de jogadores");
        System.out.println("- Cadastro de seleções");
        System.out.println("- Cadastro de brasões");
        System.out.println("- Controle de figurinhas coladas");
        System.out.println("- Controle de repetidas");
        System.out.println("- Busca por jogador");
        System.out.println("- Busca por seleção");
        System.out.println("- Busca por clube");
        System.out.println("- Busca por posição");
        System.out.println("- Relatórios");
        System.out.println("- Persistência em TXT");
        System.out.println("- Persistência em JSON");
        System.out.println();
        System.out.println("Disciplina:");
        System.out.println("Programação Orientada a Objetos");
        System.out.println("====================================");
    }

    private static void imprimirLista(List<?> lista, String mensagemVazia) {
        if (lista == null || lista.isEmpty()) {
            System.out.println(mensagemVazia);
            return;
        }
        for (Object item : lista) {
            System.out.println(item);
        }
    }
}