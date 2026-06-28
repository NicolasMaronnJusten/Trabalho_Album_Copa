package src;

public abstract class Figurinha {

    protected int id;
    protected String codigo;
    protected String nome;
    protected Tipo tipo;
    protected Raridade raridade;
    protected Selecao selecao;
    protected boolean colada;
    protected int quantidadeRepetida;

    public Figurinha(int id,
                     String codigo,
                     String nome,
                     Tipo tipo,
                     Selecao selecao,
                     Raridade raridade) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("O código da figurinha é obrigatório.");
        }

        this.id = id;
        this.codigo = codigo.trim().toUpperCase();
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

    public String getCodigo() {
        return codigo;
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

    public void descolar() {
        colada = false;
    }

    public void setColada(boolean colada) {
        this.colada = colada;
    }

    public void adicionarRepetida() {
        quantidadeRepetida++;
    }

    public void setQuantidadeRepetida(int quantidadeRepetida) {
        if (quantidadeRepetida < 0) {
            throw new IllegalArgumentException("A quantidade repetida não pode ser negativa.");
        }

        this.quantidadeRepetida = quantidadeRepetida;
    }

    public boolean pertenceASelecao(String nomeSelecao) {
        return selecao.getNome().equalsIgnoreCase(nomeSelecao);
    }

    protected String dadosBaseComoTexto() {
        return "Código: " + codigo +
                "\nID: " + id +
                "\nNome: " + nome +
                "\nSeleção: " + selecao.getNome() +
                "\nTipo: " + tipo +
                "\nRaridade: " + raridade +
                "\nColada: " + (colada ? "Sim" : "Não") +
                "\nRepetidas: " + quantidadeRepetida;
    }

    @Override
    public abstract String toString();
}
