package src;

public class figurinha {
    //Atributos
    private int id;
    private String nome;
    private String tipo;
    private String seleção;
    private Boolean colada;
    private int quantidade_repetida;
    
    //Inicializador
    public figurinha(int id, String nome, String tipo, String seleção) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.seleção = seleção;
        this.colada = false;
        this.quantidade_repetida = 0;
    }
}
