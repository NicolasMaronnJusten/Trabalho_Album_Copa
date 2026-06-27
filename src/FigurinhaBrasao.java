package src;

public class FigurinhaBrasao extends Figurinha {

    public FigurinhaBrasao(int id,
                           String nome,
                           Selecao selecao,
                           Raridade raridade) {

        super(id,
                nome,
                Tipo.BRASAO,
                selecao,
                raridade);

    }

    @Override
    public String toString() {

        return "\nID: " + id +

                "\nBrasão: " + nome +

                "\nSeleção: " + selecao.getNome() +

                "\nTipo: " + tipo +

                "\nRaridade: " + raridade +

                "\nColada: " + colada +

                "\nRepetidas: " + quantidadeRepetida +

                "\n";

    }

}