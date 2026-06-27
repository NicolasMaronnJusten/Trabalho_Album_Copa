package src;

public class TipoFigurinha {
    private Tipo tipo;

    public TipoFigurinha(Tipo tipo) {
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return tipo.toString();
    }
}