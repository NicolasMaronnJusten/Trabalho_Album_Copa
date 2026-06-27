package src;

public class FigurinhaJogador extends Figurinha {

    private double altura;
    private double peso;

    private String clube;

    private String posicao;

    private int idade;

    private int numeroCamisa;

    public FigurinhaJogador(int id,
                            String nome,
                            Selecao selecao,

                            double altura,
                            double peso,

                            String clube,

                            String posicao,

                            int idade,

                            int numeroCamisa,

                            Raridade raridade) {

        super(id,
                nome,
                Tipo.JOGADOR,
                selecao,
                raridade);

        this.altura = altura;
        this.peso = peso;

        this.clube = clube;

        this.posicao = posicao;

        this.idade = idade;

        this.numeroCamisa = numeroCamisa;

    }

    public double getAltura() {
        return altura;
    }

    public double getPeso() {
        return peso;
    }

    public String getClube() {
        return clube;
    }

    public String getPosicao() {
        return posicao;
    }

    public int getIdade() {
        return idade;
    }

    public int getNumeroCamisa() {
        return numeroCamisa;
    }

    @Override
    public String toString() {

        return "\nID: " + id +

                "\nNome: " + nome +

                "\nSeleção: " + selecao.getNome() +

                "\nClube: " + clube +

                "\nPosição: " + posicao +

                "\nIdade: " + idade +

                "\nCamisa: " + numeroCamisa +

                "\nAltura: " + altura +

                "\nPeso: " + peso +

                "\nTipo: " + tipo +

                "\nRaridade: " + raridade +

                "\nColada: " + colada +

                "\nRepetidas: " + quantidadeRepetida +

                "\n";

    }

}