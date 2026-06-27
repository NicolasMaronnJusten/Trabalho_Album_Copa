package src;

public class FigurinhaSelecao extends Figurinha {

    private String tecnico;

    private int titulos;

    public FigurinhaSelecao(int id,
                            Selecao selecao,
                            Raridade raridade,
                            String tecnico,
                            int titulos) {

        super(id,
              selecao.getNome(),
              Tipo.SELECAO,
              selecao,
              raridade);

        this.tecnico = tecnico;
        this.titulos = titulos;

    }

    public String getTecnico() {
        return tecnico;
    }

    public int getTitulos() {
        return titulos;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public void setTitulos(int titulos) {
        this.titulos = titulos;
    }

    @Override
    public String toString() {

        return "\nID: " + id +

                "\nSeleção: " + selecao.getNome() +

                "\nPaís: " + selecao.getPais() +

                "\nTécnico: " + tecnico +

                "\nTítulos: " + titulos +

                "\nTipo: " + tipo +

                "\nRaridade: " + raridade +

                "\nColada: " + colada +

                "\nRepetidas: " + quantidadeRepetida +

                "\n";

    }

}