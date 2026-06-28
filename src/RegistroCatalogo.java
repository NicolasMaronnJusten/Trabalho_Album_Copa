package src;

public class RegistroCatalogo {

    private String codigo;
    private int id;
    private String nome;
    private String selecao;
    private Tipo tipo;
    private String clube;
    private String posicao;
    private Raridade raridade;

    public RegistroCatalogo(String codigo,
                            int id,
                            String nome,
                            String selecao,
                            Tipo tipo,
                            String clube,
                            String posicao,
                            Raridade raridade) {
        this.codigo = codigo;
        this.id = id;
        this.nome = nome;
        this.selecao = selecao;
        this.tipo = tipo;
        this.clube = clube;
        this.posicao = posicao;
        this.raridade = raridade;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getSelecao() {
        return selecao;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getClube() {
        return clube;
    }

    public String getPosicao() {
        return posicao;
    }

    public Raridade getRaridade() {
        return raridade;
    }

    @Override
    public String toString() {
        return "Código: " + codigo +
                " | ID: " + id +
                " | Nome: " + nome +
                " | Seleção: " + selecao +
                " | Tipo: " + tipo +
                " | Raridade: " + raridade;
    }
}
