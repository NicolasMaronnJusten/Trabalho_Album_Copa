package src;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private Album album;
    private CardLayout cardLayout;
    private JPanel painelPrincipal;
    private JTextArea areaResultado;

    // Depois tu coloca o caminho das imagens aqui.
    private static final String CAMINHO_CAPA_ALBUM = "";
    private static final String CAMINHO_ALBUM_ABERTO = "";

    public TelaPrincipal(Album album) {
        this.album = album;

        setTitle("Álbum da Copa");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        painelPrincipal.add(criarTelaInicial(), "inicio");
        painelPrincipal.add(criarTelaAlbumAberto(), "album");

        add(painelPrincipal);
        cardLayout.show(painelPrincipal, "inicio");
    }

    private JPanel criarTelaInicial() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(240, 235, 220));

        JLabel titulo = new JLabel("Álbum da Copa", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        JLabel imagemAlbum = criarImagemOuTexto(CAMINHO_CAPA_ALBUM, "CAPA DO ÁLBUM", 420, 320);

        JButton botaoAbrir = new JButton("Abrir álbum");
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

    private JPanel criarTelaAlbumAberto() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(245, 240, 225));

        JLabel imagemAlbumAberto = criarImagemOuTexto(CAMINHO_ALBUM_ABERTO, "ÁLBUM ABERTO", 700, 300);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
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

    private JPanel criarPainelOpcoes() {
        JPanel painel = new JPanel(new FlowLayout());

        JButton botaoResumo = new JButton("Resumo");
        JButton botaoFaltantes = new JButton("Faltantes");
        JButton botaoRepetidas = new JButton("Repetidas");
        JButton botaoJogadores = new JButton("Jogadores");
        JButton botaoBuscar = new JButton("Buscar");
        JButton botaoConsultar = new JButton("Consultar código");
        JButton botaoColar = new JButton("Colar código");
        JButton botaoRepetida = new JButton("Repetida código");
        JButton botaoSalvar = new JButton("Salvar");
        JButton botaoVoltar = new JButton("Voltar");

        botaoResumo.addActionListener(e -> areaResultado.setText(album.gerarResumo()));
        botaoFaltantes.addActionListener(e -> areaResultado.setText(album.listarFaltantesComoTexto()));
        botaoRepetidas.addActionListener(e -> areaResultado.setText(album.listarRepetidasComoTexto()));
        botaoJogadores.addActionListener(e -> areaResultado.setText(album.listarJogadoresComoTexto()));
        botaoBuscar.addActionListener(e -> buscarJogador());
        botaoConsultar.addActionListener(e -> consultarCodigo());
        botaoColar.addActionListener(e -> colarFigurinha());
        botaoRepetida.addActionListener(e -> marcarRepetida());
        botaoSalvar.addActionListener(e -> salvarAlbum());
        botaoVoltar.addActionListener(e -> cardLayout.show(painelPrincipal, "inicio"));

        painel.add(botaoResumo);
        painel.add(botaoFaltantes);
        painel.add(botaoRepetidas);
        painel.add(botaoJogadores);
        painel.add(botaoBuscar);
        painel.add(botaoConsultar);
        painel.add(botaoColar);
        painel.add(botaoRepetida);
        painel.add(botaoSalvar);
        painel.add(botaoVoltar);

        return painel;
    }

    private void buscarJogador() {
        String busca = JOptionPane.showInputDialog(this, "Digite nome, seleção, posição, código ou clube:");

        if (busca == null || busca.trim().isEmpty()) {
            return;
        }

        areaResultado.setText(album.buscarJogadoresGeralComoTexto(busca));
    }

    private void consultarCodigo() {
        String codigo = JOptionPane.showInputDialog(this, "Digite o código oficial da figurinha:");

        if (codigo == null || codigo.trim().isEmpty()) {
            return;
        }

        try {
            areaResultado.setText(album.consultarFigurinhaComoTexto(codigo));
        } catch (Exception e) {
            areaResultado.setText("Erro: " + e.getMessage());
        }
    }

    private void colarFigurinha() {
        String codigo = JOptionPane.showInputDialog(this, "Digite o código oficial da figurinha para colar:");

        if (codigo == null || codigo.trim().isEmpty()) {
            return;
        }

        try {
            Figurinha figurinha = album.buscarFigurinhaPorCodigo(codigo);
            int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Figurinha encontrada:\n" + figurinha + "\nDeseja colar?",
                    "Confirmar figurinha",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                album.registrarFigurinhaColada(codigo);
                ArquivoAlbumJson.salvar(album);
                areaResultado.setText("Figurinha " + figurinha.getCodigo() + " colada com sucesso!\n\n" + figurinha);
            }
        } catch (Exception e) {
            areaResultado.setText("Erro: " + e.getMessage());
        }
    }

    private void marcarRepetida() {
        String codigo = JOptionPane.showInputDialog(this, "Digite o código oficial da figurinha repetida:");

        if (codigo == null || codigo.trim().isEmpty()) {
            return;
        }

        try {
            Figurinha figurinha = album.buscarFigurinhaPorCodigo(codigo);
            int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Figurinha encontrada:\n" + figurinha + "\nDeseja marcar como repetida?",
                    "Confirmar repetida",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {
                album.registrarFigurinhaRepetida(codigo);
                ArquivoAlbumJson.salvar(album);
                areaResultado.setText("Figurinha " + figurinha.getCodigo() + " registrada como repetida!\n\n" + figurinha);
            }
        } catch (Exception e) {
            areaResultado.setText("Erro: " + e.getMessage());
        }
    }

    private void salvarAlbum() {
        ArquivoAlbumJson.salvar(album);
        areaResultado.setText("Álbum salvo com sucesso!");
    }

    private JLabel criarImagemOuTexto(String caminho, String textoAlternativo, int largura, int altura) {
        JLabel label = new JLabel(textoAlternativo, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(largura, altura));
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        label.setOpaque(true);
        label.setBackground(new Color(230, 220, 200));

        if (caminho != null && !caminho.isEmpty()) {
            ImageIcon icon = new ImageIcon(caminho);
            Image imagemRedimensionada = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(imagemRedimensionada));
            label.setText("");
        }

        return label;
    }
}
