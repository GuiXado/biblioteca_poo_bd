package br.com.biblioteca;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RelatorioControl {

    private RelatorioDAO dao = new RelatorioDAOImpl();

    public ObservableList<String> executarRelatorio(String tipo) {

        switch (tipo) {

            case "Empréstimos ativos": return dao.emprestimosAtivos();

            case "Empréstimos atrasados": return dao.emprestimosAtrasados();

            case "Consulta histórico": return dao.consultaHistorico();

            case "Ranking de empréstimos por estudante": return dao.estudanteQueMaisPegaLivro();

            case "Livros mais emprestados": return dao.livrosEmprestados();

            case "Livros sem estoque": return dao.livrosSemEstoque();
        }

        return FXCollections.observableArrayList();
    }
}