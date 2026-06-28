package src;

public class FigurinhaJogador extends Figurinha {

    private String clube;
    private String posicao;

    public FigurinhaJogador(int id,
                            String codigo,
                            String nome,
                            Selecao selecao,
                            String clube,
                            String posicao,
                            Raridade raridade) {

        super(id, codigo, nome, Tipo.JOGADOR, selecao, raridade);
        this.clube = clube;
        this.posicao = posicao;
    }

    public String getClube() {
        return clube;
    }

    public String getPosicao() {
        return posicao;
    }

    @Override
    public String toString() {
        return dadosBaseComoTexto() +
                "\nClube: " + clube +
                "\nPosição: " + posicao +
                "\n";
    }
}
