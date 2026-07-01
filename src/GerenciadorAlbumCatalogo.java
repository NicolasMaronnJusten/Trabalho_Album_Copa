package src;

public class GerenciadorAlbumCatalogo {

    private GerenciadorAlbumCatalogo() { // monta o álbum inteiro
        // Classe utilitária.
    }

    public static Album carregarAlbum() { //caso nao tenha nome informado, usa o nome padrão "Proprietário"
        return carregarAlbum("Proprietário");
    }

    public static Album carregarAlbum(String nomeProprietario) { 
        boolean precisaNome = ArquivoAlbumJson.precisaNomeProprietario(); // verifica se precisa pedir o nome do proprietário

        Proprietario proprietario = new Proprietario( 
                prepararNome(nomeProprietario),
                "email@exemplo.com"
        );

        CatalogoFigurinhasCSV catalogo = CatalogoFigurinhasCSV.carregarPadrao(); //carrega o catálogo padrão de figurinhas (catalogo_figurinhas.csv)
        Album album = catalogo.criarAlbumBase(proprietario); //cria o album com todas as figurinhas, mas sem estarem coladas

        ArquivoAlbumJson.carregarProgresso(album); //carrega o progresso salvo

        if (precisaNome) { // se precisa pedir o nome do proprietário, atualiza o nome no álbum e salva
            album.getProprietario().setNome(prepararNome(nomeProprietario));
            ArquivoAlbumJson.salvar(album);
        }

        return album; // retorna o álbum carregado com o progresso salvo e o nome do proprietário atualizado para o main ou interface
    }

    private static String prepararNome(String nome) { //evita nome vazio
        if (nome == null || nome.trim().isEmpty()) {
            return "Proprietário";
        }

        return nome.trim();
    }
}   