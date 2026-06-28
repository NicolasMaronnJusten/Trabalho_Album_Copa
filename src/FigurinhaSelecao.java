package src;

public class FigurinhaSelecao extends Figurinha {

    public FigurinhaSelecao(int id,
                            String codigo,
                            Selecao selecao,
                            Raridade raridade) {

        super(id, codigo, "Seleção - " + selecao.getNome(), Tipo.SELECAO, selecao, raridade);
    }

    @Override
    public String toString() {
        return dadosBaseComoTexto() +
                "\nPaís: " + selecao.getPais() +
                "\n";
    }
}
