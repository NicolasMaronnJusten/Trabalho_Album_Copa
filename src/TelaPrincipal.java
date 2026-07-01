package src;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame { // usa 2 janelas, uma para a tela inicial e outra para o álbum aberto, com cardlayout para alternar entre elas
 
    private Album album;
    private CardLayout cardLayout;
    private JPanel painelPrincipal;
    private JTextArea areaResultado; // onde aparece as opcoes de resumo, faltantes, etc

    
    private static final String CAMINHO_CAPA_ALBUM = "data/capa_album.jpg"; // capa do album usada na tela inicial
    private static final String CAMINHO_ALBUM_ABERTO = "data/album_aberto.jpg"; // imagem do album aberto usada na tela do album aberto

    public TelaPrincipal(Album album) { // construtor da tela principal, recebe o album carregado do arquivo json
        this.album = album; //recebe o o album ja carregado do arquivo json, com o progresso do usuario

        setTitle("Álbum da Copa"); //configuracao da janela
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout); // troca da janela do inicio e a do album aberto

        painelPrincipal.add(criarTelaInicial(), "inicio");
        painelPrincipal.add(criarTelaAlbumAberto(), "album");

        add(painelPrincipal);
        cardLayout.show(painelPrincipal, "inicio");
    }

    private JPanel criarTelaInicial() {   // cria a tela inicial com a capa do album e o botão para abrir o album
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(240, 235, 220));

        JLabel titulo = new JLabel("Álbum da Copa", SwingConstants.CENTER); 
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        JLabel imagemAlbum = criarImagemOuTexto(CAMINHO_CAPA_ALBUM, "CAPA DO ÁLBUM", 420, 320);

        JButton botaoAbrir = new JButton("Abrir álbum"); // quando clica nesse botão, troca para a tela do album aberto
        botaoAbrir.setFont(new Font("Arial", Font.BOLD, 20));
        botaoAbrir.setFocusPainted(false);
        botaoAbrir.addActionListener(e -> cardLayout.show(painelPrincipal, "album"));

        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        centro.add(imagemAlbum, BorderLayout.CENTER);

        JPanel painelBotao = new JPanel();
        painelBotao.setOpaque(false);
        painelBotao.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        painelBotao.add(botaoAbrir);

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(centro, BorderLayout.CENTER);
        painel.add(painelBotao, BorderLayout.SOUTH);

        return painel;
    }
 
    private JPanel criarTelaAlbumAberto() { // cria a tela do album aberto, com a imagem do album aberto e as opcoes de resumo, faltantes, etc
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(245, 240, 225));

        JLabel imagemAlbumAberto = criarImagemOuTexto(CAMINHO_ALBUM_ABERTO, "ÁLBUM ABERTO", 700, 300);

        areaResultado = new JTextArea(); // area de texto onde aparecem as informações do álbum, como resumo
        areaResultado.setEditable(false); // n pode editar o texto, apenas ver
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollResultado = new JScrollPane(areaResultado);
        scrollResultado.setPreferredSize(new Dimension(1000, 190));

        JPanel parteDeBaixo = new JPanel(new BorderLayout());
        parteDeBaixo.add(criarPainelOpcoes(), BorderLayout.NORTH);
        parteDeBaixo.add(scrollResultado, BorderLayout.CENTER);

        painel.add(imagemAlbumAberto, BorderLayout.CENTER);
        painel.add(parteDeBaixo, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelOpcoes() { // opcoes do sistema
        JPanel painel = new JPanel(new FlowLayout());

        JButton botaoResumo = new JButton("Resumo");
        JButton botaoFaltantes = new JButton("Faltantes");
        JButton botaoRepetidas = new JButton("Repetidas");
        JButton botaoBuscar = new JButton("Buscar");
        JButton botaoColar = new JButton("Colar");
        JButton botaoRepetida = new JButton("Repetida");
        JButton botaoVoltar = new JButton("Voltar");

        botaoResumo.addActionListener(e -> areaResultado.setText(album.gerarResumo())); // chama os metodos do album para as opcoes
        botaoFaltantes.addActionListener(e -> areaResultado.setText(album.listarFaltantesComoTexto()));
        botaoRepetidas.addActionListener(e -> areaResultado.setText(album.listarRepetidasComoTexto()));
        botaoBuscar.addActionListener(e -> buscarFigurinha());
        botaoColar.addActionListener(e -> colarFigurinha());
        botaoRepetida.addActionListener(e -> marcarRepetida());
        botaoVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, "inicio"));

        painel.add(botaoResumo); // adiciona os botoes ao painel
        painel.add(botaoFaltantes);
        painel.add(botaoRepetidas);
        painel.add(botaoBuscar);
        painel.add(botaoColar);
        painel.add(botaoRepetida);
        painel.add(botaoVoltar);

        return painel;
    }

    private void buscarFigurinha() { 
        String busca = JOptionPane.showInputDialog(this, "Digite nome, código ou ID da figurinha:");

        if (busca == null || busca.trim().isEmpty()) {
            return;
        }

        areaResultado.setText(album.consultarFigurinhaComoTexto(busca));
    }



    private void colarFigurinha() {
        String entrada = JOptionPane.showInputDialog(this, "Digite o código, ID ou nome da figurinha para colar:");

        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        try {
            Figurinha figurinha = escolherFigurinhaPorEntrada(entrada, "Escolha a figurinha para colar");

            if (figurinha == null) {
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(  // confirma se quer colar a figurinha
                    this,
                    "Figurinha encontrada:\n" + figurinha + "\nDeseja colar?",
                    "Confirmar figurinha",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) { // se confirma, registra a figurinha como colada e salva o progresso no arquivo json
                album.registrarFigurinhaColada(figurinha.getCodigo());
                ArquivoAlbumJson.salvar(album);
                areaResultado.setText("Figurinha " + figurinha.getCodigo() + " colada com sucesso!\n\n" + figurinha);
            }
        } catch (Exception e) {
            areaResultado.setText("Erro: " + e.getMessage());
        }
    }

    private void marcarRepetida() { // pede o codigo ou nome da figurinha, busca no album e marca como repetida
        String entrada = JOptionPane.showInputDialog(this, "Digite o código, ID ou nome da figurinha repetida:");

        if (entrada == null || entrada.trim().isEmpty()) {
            return;
        }

        try {
            Figurinha figurinha = escolherFigurinhaPorEntrada(entrada, "Escolha a figurinha repetida");

            if (figurinha == null) {
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog( // confirma se quer marcar a figurinha como repetida
                    this,
                    "Figurinha encontrada:\n" + figurinha + "\nDeseja marcar como repetida?",
                    "Confirmar repetida",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                album.registrarFigurinhaRepetida(figurinha.getCodigo());
                ArquivoAlbumJson.salvar(album);
                areaResultado.setText("Figurinha " + figurinha.getCodigo() + " registrada como repetida!\n\n" + figurinha);
            }
        } catch (Exception e) {
            areaResultado.setText("Erro: " + e.getMessage());
        }
    }

    private Figurinha escolherFigurinhaPorEntrada(String entrada, String titulo) { // busca a figurinha pelo codigo, id ou nome
        java.util.List<Figurinha> encontradas = album.buscarFigurinhasPorEntrada(entrada);

        if (encontradas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma figurinha encontrada para: " + entrada);
        }

        if (encontradas.size() == 1) { // se encontrou apenas uma figurinha, retorna ela
            return encontradas.get(0);
        }

        Object escolhida = JOptionPane.showInputDialog( // se encontrou mais de uma figurinha, mostra uma lista para o usuario escolher
                this,
                "Encontrei mais de uma figurinha. Selecione a correta:",
                titulo,
                JOptionPane.QUESTION_MESSAGE,
                null,
                encontradas.toArray(),
                encontradas.get(0)
        );

        return (Figurinha) escolhida;
    }

// usado para imagem do album e capa do album, caso nao exista a imagem, mostra um texto alternativo
    private JLabel criarImagemOuTexto(String caminho, String textoAlternativo, int largura, int altura) {  // cria um JLabel com a imagem do caminho informado, ou com o texto alternativo caso a imagem nao exista
        JLabel label = new JLabel(textoAlternativo, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(largura, altura));
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        label.setOpaque(true);
        label.setBackground(new Color(230, 220, 200));

        if (caminho != null && !caminho.isEmpty()) { // se o caminho da imagem for informado, tenta carregar a imagem
            ImageIcon icon = new ImageIcon(caminho);
            Image imagemRedimensionada = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(imagemRedimensionada));
            label.setText("");
        }

        return label; 
    }
}
