package src;

public class GerenciadorAlbumCatalogo {

    private GerenciadorAlbumCatalogo() {
        // Classe utilitária.
    }

    public static Album carregarAlbum() {
        return carregarAlbum("Proprietário");
    }

    public static Album carregarAlbum(String nomeProprietario) {
        boolean precisaNome = ArquivoAlbumJson.precisaNomeProprietario();

        Proprietario proprietario = new Proprietario(
                prepararNome(nomeProprietario),
                "email@exemplo.com"
        );

        CatalogoFigurinhasCSV catalogo = CatalogoFigurinhasCSV.carregarPadrao();
        Album album = catalogo.criarAlbumBase(proprietario);

        ArquivoAlbumJson.carregarProgresso(album);

        if (precisaNome) {
            album.getProprietario().setNome(prepararNome(nomeProprietario));
            ArquivoAlbumJson.salvar(album);
        }

        return album;
    }

    private static String prepararNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return "Proprietário";
        }

        return nome.trim();
    }
}   