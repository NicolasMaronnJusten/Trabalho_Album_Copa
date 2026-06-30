package src;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Album album = GerenciadorAlbumCatalogo.carregarAlbum();
        String opcao = "";

        while (!opcao.equals("0")) {
            mostrarMenu();
            System.out.print("\nEscolha: ");
            opcao = sc.nextLine().trim();

            try {
                switch (opcao) {
                    case "1":
                        buscarFigurinhaPorNome(sc, album);
                        break;

                    case "2":
                        colarFigurinha(sc, album);
                        break;

                    case "3":
                        registrarRepetida(sc, album);
                        break;

                    case "4":
                        consultarFigurinha(sc, album);
                        break;

                    case "5":
                        System.out.println("Total de figurinhas coladas: " + album.calcularTotalColadas());
                        break;

                    case "6":
                        System.out.println("Total de figurinhas repetidas: " + album.calcularTotalRepetidas());
                        break;

                    case "7":
                        imprimirLista(album.listarFaltantes(), "Nenhuma figurinha faltando.");
                        break;

                    case "8":
                        listarFaltantesPorSelecao(sc, album);
                        break;

                    case "9":
                        listarPorTipo(sc, album);
                        break;

                    case "10":
                        listarPorRaridade(sc, album);
                        break;

                    case "11":
                        album.mostrarResumo();
                        break;

                    case "12":
                        album = GerenciadorAlbumCatalogo.carregarAlbum();
                        System.out.println("Álbum recarregado com sucesso.");
                        break;

                    case "13":
                        resetarAlbum(sc, album);
                        break;

                    case "14":
                        abrirInterfaceGrafica(album);
                        break;
                    case "0":
                        System.out.println("Programa encerrado.");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n==============================");
        System.out.println("ÁLBUM COPA DO MUNDO 2026");
        System.out.println("==============================");
        System.out.println("1 - Buscar figurinha por nome");
        System.out.println("2 - Colar figurinha por código, ID ou nome");
        System.out.println("3 - Registrar repetida por código, ID ou nome");
        System.out.println("4 - Consultar figurinha por código, ID ou nome");
        System.out.println("5 - Total de coladas");
        System.out.println("6 - Total de repetidas");
        System.out.println("7 - Listar faltantes");
        System.out.println("8 - Faltantes por seleção");
        System.out.println("9 - Listar por tipo");
        System.out.println("10 - Listar por raridade");
        System.out.println("11 - Mostrar resumo");
        System.out.println("12 - Recarregar álbum");
        System.out.println("13 - Resetar álbum");
        System.out.println("14 - Abrir interface gráfica");
        System.out.println("0 - Sair");
    }

    private static void buscarFigurinhaPorNome(Scanner sc, Album album) {
        System.out.println("\n========== BUSCAR FIGURINHA ==========");
        System.out.print("Digite o nome ou parte do nome: ");
        String nome = sc.nextLine();

        imprimirLista(
                album.buscarPorTrechoDoNome(nome),
                "Nenhuma figurinha encontrada."
        );
    }

    private static void colarFigurinha(Scanner sc, Album album) {
        System.out.print("Digite o código, ID ou nome da figurinha: ");
        String entrada = sc.nextLine();

        Figurinha figurinha = escolherFigurinha(sc, album, entrada);
        System.out.println("\nFigurinha encontrada:");
        System.out.println(figurinha);
        System.out.print("Deseja colar essa figurinha? (S/N): ");
        String resposta = sc.nextLine();

        if (resposta.equalsIgnoreCase("S")) {
            album.registrarFigurinhaColada(figurinha.getCodigo());
            ArquivoAlbumJson.salvar(album);
            System.out.println("Figurinha colada com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private static void registrarRepetida(Scanner sc, Album album) {
        System.out.print("Digite o código, ID ou nome da figurinha repetida: ");
        String entrada = sc.nextLine();

        Figurinha figurinha = escolherFigurinha(sc, album, entrada);
        System.out.println("\nFigurinha encontrada:");
        System.out.println(figurinha);
        System.out.print("Deseja registrar como repetida? (S/N): ");
        String resposta = sc.nextLine();

        if (resposta.equalsIgnoreCase("S")) {
            album.registrarFigurinhaRepetida(figurinha.getCodigo());
            ArquivoAlbumJson.salvar(album);
            System.out.println("Figurinha repetida registrada com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private static void consultarFigurinha(Scanner sc, Album album) {
        System.out.print("Digite o código, ID ou nome da figurinha: ");
        String entrada = sc.nextLine();
        System.out.println(album.consultarFigurinhaComoTexto(entrada));
    }

    private static void listarFaltantesPorSelecao(Scanner sc, Album album) {
        System.out.print("Digite a seleção: ");
        String selecao = sc.nextLine();
        imprimirLista(album.listarFaltantesPorSelecao(selecao), "Nenhuma figurinha encontrada.");
    }

    private static void listarPorTipo(Scanner sc, Album album) {
        System.out.println("\nTipos disponíveis:");
        for (Tipo t : Tipo.values()) {
            System.out.println("- " + t);
        }

        System.out.print("\nDigite o tipo: ");
        Tipo tipo = Tipo.valueOf(sc.nextLine().trim().toUpperCase());
        imprimirLista(album.listarPorTipo(tipo), "Nenhuma figurinha encontrada para esse tipo.");
    }

    private static void listarPorRaridade(Scanner sc, Album album) {
        System.out.println("\nRaridades disponíveis:");
        for (Raridade r : Raridade.values()) {
            System.out.println("- " + r);
        }

        System.out.print("\nDigite a raridade: ");
        Raridade raridade = Raridade.valueOf(sc.nextLine().trim().toUpperCase());
        imprimirLista(album.listarPorRaridade(raridade), "Nenhuma figurinha encontrada para essa raridade.");
    }

    private static Figurinha escolherFigurinha(Scanner sc, Album album, String entrada) {
        List<Figurinha> encontradas = album.buscarFigurinhasPorEntrada(entrada);

        if (encontradas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma figurinha encontrada para: " + entrada);
        }

        if (encontradas.size() == 1) {
            return encontradas.get(0);
        }

        System.out.println("\nEncontrei mais de uma figurinha. Escolha a correta:");
        for (int i = 0; i < encontradas.size(); i++) {
            Figurinha f = encontradas.get(i);
            System.out.println((i + 1) + " - " + f.getCodigo() + " | " + f.getNome() + " | "
                    + f.getSelecao().getNome() + " | " + f.getTipo());
        }

        System.out.print("\nNúmero da opção: ");
        String textoOpcao = sc.nextLine().trim();

        try {
            int indice = Integer.parseInt(textoOpcao) - 1;
            if (indice < 0 || indice >= encontradas.size()) {
                throw new IllegalArgumentException("Opção fora da lista.");
            }
            return encontradas.get(indice);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Digite um número válido para escolher a figurinha.");
        }
    }
    private static void resetarAlbum(Scanner sc, Album album) {
        System.out.println("\nATENÇÃO: isso vai apagar todas as figurinhas coladas e repetidas.");
        System.out.print("Tem certeza que deseja resetar o álbum? (S/N): ");
        String resposta = sc.nextLine();

        if (resposta.equalsIgnoreCase("S")) {
            album.resetarAlbum();
            ArquivoAlbumJson.salvar(album);
            System.out.println("Álbum resetado com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private static void abrirInterfaceGrafica(Album album) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal(album);
            tela.setVisible(true);
        });
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
