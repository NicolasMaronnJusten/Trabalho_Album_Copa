package src;

public abstract class Figurinha {

    protected int id;
    protected String nome;

    protected Tipo tipo;
    protected Raridade raridade;

    protected Selecao selecao;

    protected boolean colada;

    protected int quantidadeRepetida;

    public Figurinha(int id,
                     String nome,
                     Tipo tipo,
                     Selecao selecao,
                     Raridade raridade) {

        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.selecao = selecao;
        this.raridade = raridade;

        this.colada = false;
        this.quantidadeRepetida = 0;

    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Raridade getRaridade() {
        return raridade;
    }

    public Selecao getSelecao() {
        return selecao;
    }

    public boolean isColada() {
        return colada;
    }

    public int getQuantidadeRepetida() {
        return quantidadeRepetida;
    }

    public void colar() {

        colada = true;

    }

    public void adicionarRepetida() {

        quantidadeRepetida++;

    }

    public boolean pertenceASelecao(String nomeSelecao) {

        return selecao.getNome().equalsIgnoreCase(nomeSelecao);

    }

    @Override
    public abstract String toString();

}