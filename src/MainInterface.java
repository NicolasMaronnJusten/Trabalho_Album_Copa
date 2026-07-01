package src;

import javax.swing.SwingUtilities;

public class MainInterface { // inicia a interface 

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Album album = GerenciadorAlbumCatalogo.carregarAlbum();
            TelaPrincipal tela = new TelaPrincipal(album);
            tela.setVisible(true);
        });
    }
}
