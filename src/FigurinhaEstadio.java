package src;

public class FigurinhaEstadio extends Figurinha {

    public FigurinhaEstadio(int id,
                            String codigo,
                            String nome,
                            Selecao selecao,
                            Raridade raridade) {

        super(id, codigo, nome, Tipo.ESTADIO, selecao, raridade);
    }

    @Override
    public String toString() {
        return dadosBaseComoTexto() + "\n";
    }
}
