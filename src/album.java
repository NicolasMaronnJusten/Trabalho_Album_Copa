package src;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private Proprietario proprietario;
    private ArrayList<Figurinha> figurinhas;

    public Album(Proprietario proprietario) {
        this.proprietario = proprietario;
        this.figurinhas = new ArrayList<>();
    }
    
    public Proprietario getProprietario() {
        return proprietario;
    }

    public void adicionarFigurinha(Figurinha figurinha) {
        figurinhas.add(figurinha);
    }

    public Figurinha buscarFigurinhaPorId(int id) {
        for (Figurinha figurinha : figurinhas) {
            if (figurinha.getId() == id) {
                return figurinha;
            }
        }

        throw new IllegalArgumentException("Figurinha não encontrada: " + id);
    }

    public void registrarFigurinhaColada(int id) {
        Figurinha figurinha = buscarFigurinhaPorId(id);
        figurinha.colar();
    }

    public void registrarFigurinhaRepetida(int id) {
        Figurinha figurinha = buscarFigurinhaPorId(id);
        figurinha.adicionarRepetida();
    }

    public int calcularTotalColadas() {
        int total = 0;

        for (Figurinha figurinha : figurinhas) {
            if (figurinha.isColada()) {
                total++;
            }
        }

        return total;
    }

    public int calcularTotalRepetidas() {
        int total = 0;

        for (Figurinha figurinha : figurinhas) {
            total += figurinha.getQuantidadeRepetida();
        }

        return total;
    }

    public List<Figurinha> listarFaltantes() {
        List<Figurinha> faltantes = new ArrayList<>();

        for (Figurinha figurinha : figurinhas) {
            if (!figurinha.isColada()) {
                faltantes.add(figurinha);
            }
        }

        return faltantes;
    }

    public List<Figurinha> listarRepetidas() {
        List<Figurinha> repetidas = new ArrayList<>();

        for (Figurinha figurinha : figurinhas) {
            if (figurinha.getQuantidadeRepetida() > 0) {
                repetidas.add(figurinha);
            }
        }

        return repetidas;
    }

    public List<Figurinha> listarFaltantesPorSelecao(String nomeSelecao) {
        List<Figurinha> faltantes = new ArrayList<>();

        for (Figurinha figurinha : figurinhas) {
            if (!figurinha.isColada() && figurinha.pertenceASelecao(nomeSelecao)) {
                faltantes.add(figurinha);
            }
        }

        return faltantes;
    }
}