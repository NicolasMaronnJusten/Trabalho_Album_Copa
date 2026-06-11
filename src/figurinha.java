package src;

public class Figurinha {
    //Atributos
    private int id;
    private String nome;
    private TipoFigurinha tipo;
    private String seleção;
    private Boolean colada;
    private int quantidade_repetida;
    
    //Inicializador
    public Figurinha(int id, String nome, String tipo, String seleção) {
        this.id = id;
        this.nome = nome;
        this.tipo = new TipoFigurinha(Tipo.valueOf(tipo.toUpperCase())); // Converte a string para o enum Tipo, caso escrever minusculo, transforma para maisculo
        this.seleção = seleção;
        this.colada = false;
        this.quantidade_repetida = 0;
    }
}
