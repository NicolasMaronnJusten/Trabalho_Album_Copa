package src;

public class GerenciadorAlbumCatalogo {

    private GerenciadorAlbumCatalogo() {
        // Classe utilitária.
    }

    public static Album carregarAlbum() {
        Proprietario proprietario = new Proprietario("Proprietário", "email@exemplo.com");
        CatalogoFigurinhasCSV catalogo = CatalogoFigurinhasCSV.carregarPadrao();
        Album album = catalogo.criarAlbumBase(proprietario);
        ArquivoAlbumJson.carregarProgresso(album);
        return album;
    }
}
