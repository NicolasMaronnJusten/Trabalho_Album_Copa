package src;

import java.text.Normalizer;
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

    public ArrayList<Figurinha> getFigurinhas() {
        return figurinhas;
    }

    public void adicionarFigurinha(Figurinha figurinha) {
        if (figurinha == null) {
            throw new IllegalArgumentException("A figurinha não pode ser nula.");
        }

        for (Figurinha f : figurinhas) {
            if (f.getId() == figurinha.getId()) {
                throw new IllegalArgumentException("Já existe uma figurinha com esse ID.");
            }

            if (f.getCodigo().equalsIgnoreCase(figurinha.getCodigo())) {
                throw new IllegalArgumentException("Já existe uma figurinha com esse código.");
            }
        }

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

    public Figurinha buscarFigurinhaPorCodigo(String codigo) {
        String busca = normalizarCodigo(codigo);

        for (Figurinha f : figurinhas) {
            if (normalizarCodigo(f.getCodigo()).equals(busca)) {
                return f;
            }
        }

        throw new IllegalArgumentException("Código não encontrado no álbum: " + codigo);
    }

    public Figurinha buscarPorNome(String nome) {
        for (Figurinha f : figurinhas) {
            if (f.getNome().equalsIgnoreCase(nome)) {
                return f;
            }
        }

        throw new IllegalArgumentException("Figurinha não encontrada.");
    }

    public List<Figurinha> buscarPorTrechoDoNome(String trecho) {
        List<Figurinha> lista = new ArrayList<>();
        String busca = normalizarBusca(trecho);

        for (Figurinha f : figurinhas) {
            if (normalizarBusca(f.getNome()).contains(busca)) {
                lista.add(f);
            }
        }

        return lista;
    }

    public void registrarFigurinhaColada(int id) {
        buscarFigurinhaPorId(id).colar();
    }

    public void registrarFigurinhaColada(String codigo) {
        buscarFigurinhaPorCodigo(codigo).colar();
    }

    public void registrarFigurinhaRepetida(int id) {
        buscarFigurinhaPorId(id).adicionarRepetida();
    }

    public void registrarFigurinhaRepetida(String codigo) {
        Figurinha figurinha = buscarFigurinhaPorCodigo(codigo);

        if (!figurinha.isColada()) {
            figurinha.colar();
        }

        figurinha.adicionarRepetida();
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
        String busca = normalizarBusca(selecao);

        for (Figurinha f : figurinhas) {
            if (!f.isColada()
                    && normalizarBusca(f.getSelecao().getNome()).contains(busca)) {
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

    public int totalPorTipo(Tipo tipo) {
        int total = 0;

        for (Figurinha f : figurinhas) {
            if (f.getTipo() == tipo) {
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

    public List<FigurinhaJogador> listarJogadores() {
        List<FigurinhaJogador> jogadores = new ArrayList<>();

        for (Figurinha f : figurinhas) {
            if (f instanceof FigurinhaJogador) {
                jogadores.add((FigurinhaJogador) f);
            }
        }

        return jogadores;
    }

    public List<FigurinhaJogador> buscarJogadoresPorNome(String nome) {
        List<FigurinhaJogador> jogadores = new ArrayList<>();
        String busca = normalizarBusca(nome);

        for (FigurinhaJogador jogador : listarJogadores()) {
            if (normalizarBusca(jogador.getNome()).contains(busca)) {
                jogadores.add(jogador);
            }
        }

        return jogadores;
    }

    public List<FigurinhaJogador> buscarJogadoresPorSelecao(String selecao) {
        List<FigurinhaJogador> jogadores = new ArrayList<>();
        String busca = normalizarBusca(selecao);

        for (FigurinhaJogador jogador : listarJogadores()) {
            if (normalizarBusca(jogador.getSelecao().getNome()).contains(busca)) {
                jogadores.add(jogador);
            }
        }

        return jogadores;
    }

    public List<FigurinhaJogador> buscarJogadoresPorClube(String clube) {
        List<FigurinhaJogador> jogadores = new ArrayList<>();
        String busca = normalizarBusca(clube);

        for (FigurinhaJogador jogador : listarJogadores()) {
            if (normalizarBusca(jogador.getClube()).contains(busca)) {
                jogadores.add(jogador);
            }
        }

        return jogadores;
    }

    public List<FigurinhaJogador> buscarJogadoresPorPosicao(String posicao) {
        List<FigurinhaJogador> jogadores = new ArrayList<>();
        String busca = normalizarBusca(posicao);

        for (FigurinhaJogador jogador : listarJogadores()) {
            if (normalizarBusca(jogador.getPosicao()).contains(busca)) {
                jogadores.add(jogador);
            }
        }

        return jogadores;
    }

    public List<FigurinhaJogador> buscarJogadoresGeral(String texto) {
        List<FigurinhaJogador> jogadores = new ArrayList<>();
        String busca = normalizarBusca(texto);

        for (FigurinhaJogador jogador : listarJogadores()) {
            boolean encontrouNoCodigo = normalizarBusca(jogador.getCodigo()).contains(busca);
            boolean encontrouNoNome = normalizarBusca(jogador.getNome()).contains(busca);
            boolean encontrouNaSelecao = normalizarBusca(jogador.getSelecao().getNome()).contains(busca);
            boolean encontrouNoClube = normalizarBusca(jogador.getClube()).contains(busca);
            boolean encontrouNaPosicao = normalizarBusca(jogador.getPosicao()).contains(busca);

            if (encontrouNoCodigo || encontrouNoNome || encontrouNaSelecao || encontrouNoClube || encontrouNaPosicao) {
                jogadores.add(jogador);
            }
        }

        return jogadores;
    }

    public String gerarResumo() {
        StringBuilder sb = new StringBuilder();

        sb.append("========== RESUMO ==========\n");
        sb.append("Proprietário: ").append(proprietario.getNome()).append("\n\n");
        sb.append("Total de Figurinhas: ").append(figurinhas.size()).append("\n");
        sb.append("Jogadores: ").append(totalPorTipo(Tipo.JOGADOR)).append("\n");
        sb.append("Brasões: ").append(totalPorTipo(Tipo.BRASAO)).append("\n");
        sb.append("Seleções: ").append(totalPorTipo(Tipo.SELECAO)).append("\n");
        sb.append("Estádios: ").append(totalPorTipo(Tipo.ESTADIO)).append("\n\n");
        sb.append("Coladas: ").append(calcularTotalColadas()).append("\n");
        sb.append("Faltantes: ").append(listarFaltantes().size()).append("\n");
        sb.append("Repetidas: ").append(calcularTotalRepetidas()).append("\n");
        sb.append("Álbum Completo: ").append(porcentagemCompleta()).append("%\n\n");
        sb.append("Comuns: ").append(totalPorRaridade(Raridade.COMUM)).append("\n");
        sb.append("Raras: ").append(totalPorRaridade(Raridade.RARA)).append("\n");
        sb.append("Brilhantes: ").append(totalPorRaridade(Raridade.BRILHANTE)).append("\n");
        sb.append("Épicas: ").append(totalPorRaridade(Raridade.EPICA)).append("\n");
        sb.append("Lendárias: ").append(totalPorRaridade(Raridade.LENDARIA)).append("\n");

        return sb.toString();
    }

    public void mostrarResumo() {
        System.out.println();
        System.out.println(gerarResumo());
    }

    public String listarFaltantesComoTexto() {
        return converterListaParaTexto(listarFaltantes(), "Nenhuma figurinha faltando.");
    }

    public String listarRepetidasComoTexto() {
        return converterListaParaTexto(listarRepetidas(), "Nenhuma figurinha repetida.");
    }

    public String listarJogadoresComoTexto() {
        return converterListaParaTexto(listarJogadores(), "Nenhum jogador encontrado.");
    }

    public String buscarJogadoresGeralComoTexto(String busca) {
        return converterListaParaTexto(buscarJogadoresGeral(busca), "Nenhum jogador encontrado para: " + busca);
    }
    public List<Figurinha> BuscarPorNome(String nome) {
    List<Figurinha> resultado = new ArrayList<>();
    for (Figurinha f : figurinhas) {
        if (f.getNome().toLowerCase().contains(nome.toLowerCase())) {

            resultado.add(f);
        }
    }
    return resultado;
    }
    public String consultarFigurinhaComoTexto(String codigo) {
        return buscarFigurinhaPorCodigo(codigo).toString();
    }

    private String converterListaParaTexto(List<?> lista, String mensagemVazia) {
        if (lista.isEmpty()) {
            return mensagemVazia;
        }

        StringBuilder sb = new StringBuilder();

        for (Object item : lista) {
            sb.append(item).append("\n");
        }

        return sb.toString();
    }

    private String normalizarCodigo(String codigo) {
        if (codigo == null) {
            return "";
        }

        return codigo.trim().toUpperCase();
    }

    private String normalizarBusca(String texto) {
        if (texto == null) {
            return "";
        }

        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        normalizado = normalizado.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        return normalizado.trim().toLowerCase();
    }

}

