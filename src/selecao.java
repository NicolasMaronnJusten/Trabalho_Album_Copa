package src;

public class Selecao {
    private String nome;
    private String pais;

    public Selecao(String nome, String pais) {
        this.nome = nome;
        this.pais = pais;
    }

    public String getNome() {
        return nome;
    }

    public String getPais() {
        return pais;
    }

    @Override
    public String toString() {
        return nome + " - " + pais;
    }
}