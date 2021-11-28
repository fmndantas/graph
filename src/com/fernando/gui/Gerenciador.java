package com.fernando.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Gerenciador extends Receptor {
    private Reacao reacao;
    private Quadro quadro;
    private List<FiguraGui> figuras;
    private Long idAtual = 1L;

    Gerenciador() {
        this.reacao = new ReacaoVazia();
        this.figuras = new ArrayList<>();
    }

    public void setQuadro(Quadro quadro) {
        this.quadro = quadro;
    }

    void estabelecerReacao() throws Exception {
        if (eventoGrafoEnum == EventoGrafoEnum.VAZIO) {
            throw new Exception("Acao de grafo nao selecionada");
        }
        if (reacao.getClass().equals(ReacaoVazia.class) &&
                eventoGui.obterTipoEvento() == EventGuiEnum.CLIQUE) {
            switch (eventoGrafoEnum) {
                case INSERIR_ARESTA -> reacao = new ReacaoAresta(this);
                case INSERIR_NO -> reacao = new ReacaoNo(this);
                case SELECIONAR -> reacao = new ReacaoSelecao(this);
            }
        }
    }

    void anularReacao() {
        reacao = new ReacaoVazia();
    }

    @Override
    void receberEGui(EventoGui e) {
        super.receberEGui(e);
        try {
            estabelecerReacao();
        } catch (Exception ex) {
            System.out.println("Não foi possível obter reação. " + ex.getMessage());
        }
        reacao.executar(e);
    }

    List<FiguraGui> obterFiguras() {
        return figuras;
    }

    void removerFiguraPeloId(Long id) {
        determinarFiguras(obterFiguras().stream().filter(x -> !x.obterId().equals(id)).collect(Collectors.toList()));
    }

    FiguraGui obterFiguraPeloId(Long id) {
        return obterFiguras().stream().filter(x -> x.obterId().equals(id)).collect(Collectors.toList()).get(0);
    }

    void adicionarAresta(FiguraGui aresta) {
        adicionarFigura(aresta);
        quadro.atualizarInterface();
    }

    void adicionarNo(FiguraGui noGrafico) {
        adicionarFigura(noGrafico);
        quadro.atualizarInterface();
    }

    void iluminarFigura(Long id) {
    }

    Long obterProximoId() {
        return idAtual++;
    }

    FiguraGui obterFiguraPeloXy(XY xy) {
        var figuras = obterFiguras().stream().filter(x -> x.XyDentro(xy)).collect(Collectors.toList());
        if (figuras.size() == 0) {
            return null;
        }
        return figuras.get(0);
    }

    private void determinarFiguras(List<FiguraGui> figuras) {
        this.figuras = figuras;
    }

    private void adicionarFigura(FiguraGui figuraGui) {
        obterFiguras().add(figuraGui);
    }
}