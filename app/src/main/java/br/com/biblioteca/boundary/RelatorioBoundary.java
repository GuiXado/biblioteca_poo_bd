package br.com.biblioteca.boundary;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import br.com.biblioteca.control.RelatorioControl;
import javafx.collections.FXCollections;

public class RelatorioBoundary implements Tela {

    private RelatorioControl control = new RelatorioControl();

    private TableView<String> table = new TableView<>();

    private ComboBox<String> cmbTipo = new ComboBox<>();

    @Override
    public Pane render() {

        BorderPane bp = new BorderPane();
        GridPane top = new GridPane();

        bp.setTop(top);
        bp.setCenter(table);
        
        //para tentar deixar a tabela maior
        //bp.setPrefSize(1000, 700);
        //deprecated
        //table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //deprecated
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); 

        cmbTipo.setItems(FXCollections.observableArrayList(
            "Empréstimos ativos",
            "Empréstimos atrasados",
            "Consulta histórico",
            "Ranking de empréstimos por estudante",
            "Livros mais emprestados",
            "Livros sem estoque"
        ));

        top.add(new Label("Relatório"), 0, 0);
        top.add(cmbTipo, 1, 0);


        Button btnExecutar = new Button("Executar");
        btnExecutar.setOnAction(e -> {
            String tipo = cmbTipo.getValue();

            if (tipo == null) return;

            table.setItems(control.executarRelatorio(tipo));
        });

        top.add(btnExecutar, 2, 0);

        TableColumn<String, String> col = new TableColumn<>("Resultado");

        col.setCellValueFactory(data ->
            new javafx.beans.property.ReadOnlyStringWrapper(data.getValue())
        );

        table.getColumns().add(col);

        return bp;
    }
}