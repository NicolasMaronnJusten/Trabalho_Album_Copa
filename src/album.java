package src;

import java.util.ArrayList;
import java.util.List;
//album da copa
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

    public ArrayList<Figurinha> getFigurinhas() {
        return figurinhas;
    }

    public void adicionarFigurinha(Figurinha figurinha) {

        figurinhas.add(figurinha);

    }

    public Figurinha buscarFigurinhaPorId(int id) {

        for (Figurinha f : figurinhas) {

            if (f.getId() == id) {

                return f;

            }

        }

        throw new IllegalArgumentException("Figurinha não encontrada.");

    }

    public Figurinha buscarPorNome(String nome) {

        for (Figurinha f : figurinhas) {

            if (f.getNome().equalsIgnoreCase(nome)) {

                return f;

            }

        }

        throw new IllegalArgumentException("Figurinha não encontrada.");

    }

    public void registrarFigurinhaColada(int id) {

        buscarFigurinhaPorId(id).colar();

    }

    public void registrarFigurinhaRepetida(int id) {

        buscarFigurinhaPorId(id).adicionarRepetida();

    }

    public int calcularTotalColadas() {

        int total = 0;

        for (Figurinha f : figurinhas) {

            if (f.isColada()) {

                total++;

            }

        }

        return total;

    }

    public int calcularTotalRepetidas() {

        int total = 0;

        for (Figurinha f : figurinhas) {

            total += f.getQuantidadeRepetida();

        }

        return total;

    }

    public List<Figurinha> listarFaltantes() {

        List<Figurinha> lista = new ArrayList<>();

        for (Figurinha f : figurinhas) {

            if (!f.isColada()) {

                lista.add(f);

            }

        }

        return lista;

    }

    public List<Figurinha> listarRepetidas() {

        List<Figurinha> lista = new ArrayList<>();

        for (Figurinha f : figurinhas) {

            if (f.getQuantidadeRepetida() > 0) {

                lista.add(f);

            }

        }

        return lista;

    }

    public List<Figurinha> listarFaltantesPorSelecao(String selecao) {

        List<Figurinha> lista = new ArrayList<>();

        for (Figurinha f : figurinhas) {

            if (!f.isColada()
                    && f.getSelecao().getNome().equalsIgnoreCase(selecao)) {

                lista.add(f);

            }

        }

        return lista;

    }

    public List<Figurinha> listarPorTipo(Tipo tipo) {

        List<Figurinha> lista = new ArrayList<>();

        for (Figurinha f : figurinhas) {

            if (f.getTipo() == tipo) {

                lista.add(f);

            }

        }

        return lista;

    }

    public List<Figurinha> listarPorRaridade(Raridade raridade) {

        List<Figurinha> lista = new ArrayList<>();

        for (Figurinha f : figurinhas) {

            if (f.getRaridade() == raridade) {

                lista.add(f);

            }

        }

        return lista;

    }

    public int totalPorRaridade(Raridade raridade) {

        int total = 0;

        for (Figurinha f : figurinhas) {

            if (f.getRaridade() == raridade) {

                total++;

            }

        }

        return total;

    }

    public int porcentagemCompleta() {

        if (figurinhas.isEmpty()) {

            return 0;

        }

        return (calcularTotalColadas() * 100) / figurinhas.size();

    }

    public void mostrarResumo() {

        System.out.println();

        System.out.println("========== RESUMO ==========");

        System.out.println("Proprietário: "
                + proprietario.getNome());

        System.out.println();

        System.out.println("Total de Figurinhas: "
                + figurinhas.size());

        System.out.println("Coladas: "
                + calcularTotalColadas());

        System.out.println("Faltantes: "
                + listarFaltantes().size());

        System.out.println("Repetidas: "
                + calcularTotalRepetidas());

        System.out.println("Álbum Completo: "
                + porcentagemCompleta() + "%");

        System.out.println();

        System.out.println("Comuns: "
                + totalPorRaridade(Raridade.COMUM));

        System.out.println("Raras: "
                + totalPorRaridade(Raridade.RARA));

        System.out.println("Épicas: "
                + totalPorRaridade(Raridade.EPICA));

        System.out.println("Lendárias: "
                + totalPorRaridade(Raridade.LENDARIA));

    }

}