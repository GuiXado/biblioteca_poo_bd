package br.com.biblioteca;

import javafx.collections.ObservableList;

public interface RelatorioDAO {
    ObservableList<String> emprestimosAtivos();
    ObservableList<String> emprestimosAtrasados();
    ObservableList<String> consultaHistorico();
    ObservableList<String> estudanteQueMaisPegaLivro();
    ObservableList<String> livrosEmprestados();
    ObservableList<String> livrosSemEstoque();
}