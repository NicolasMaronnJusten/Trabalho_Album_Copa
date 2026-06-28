package src;

public class FigurinhaBrasao extends Figurinha {

    public FigurinhaBrasao(int id,
                           String codigo,
                           String nome,
                           Selecao selecao,
                           Raridade raridade) {

        super(id, codigo, nome, Tipo.BRASAO, selecao, raridade);
    }

    @Override
    public String toString() {
        return dadosBaseComoTexto() + "\n";
    }
}
