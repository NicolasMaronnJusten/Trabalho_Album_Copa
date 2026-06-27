package src;

public class Figurinha {
    private int id;
    private String nome;
    private TipoFigurinha tipo;
    private Selecao selecao;
    private boolean colada;
    private int quantidadeRepetida;

    public Figurinha(int id, String nome, String tipo, Selecao selecao) {
        this.id = id;
        this.nome = nome;
        this.tipo = new TipoFigurinha(Tipo.valueOf(tipo.toUpperCase()));
        this.selecao = selecao;
        this.colada = false;
        this.quantidadeRepetida = 0;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoFigurinha getTipo() {
        return tipo;
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
        this.colada = true;
    }

    public void adicionarRepetida() {
        this.quantidadeRepetida++;
    }

    public boolean pertenceASelecao(String nomeSelecao) {
        return selecao.getNome().equalsIgnoreCase(nomeSelecao);
    }

    @Override
    public String toString() {
        return id + " - " + nome + " | " + tipo + " | " + selecao.getNome()
                + " | Colada: " + colada
                + " | Repetidas: " + quantidadeRepetida;
    }
}