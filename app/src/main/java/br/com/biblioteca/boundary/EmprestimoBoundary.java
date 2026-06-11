package br.com.biblioteca.boundary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.biblioteca.control.EmprestimoControl;
import br.com.biblioteca.entity.Emprestimo;
import br.com.biblioteca.entity.Estudante;
import br.com.biblioteca.entity.Livro;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class EmprestimoBoundary implements Tela {

    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private ComboBox<Livro> cmbLivro = new ComboBox<>();
    private ComboBox<Estudante> cmbEstudante = new ComboBox<>();

    //private DatePicker dtaEmprestimo = new DatePicker();
    private DatePicker dtaPrevista = new DatePicker();
    private DatePicker dtaDevolucao = new DatePicker();

    private EmprestimoControl control = new EmprestimoControl();
    private TableView<Emprestimo> table = new TableView<>();

    private String formatarData(LocalDate data) {
        return data == null ? "" : data.format(DATA_FORMATTER);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Pane render() {

        BorderPane bp = new BorderPane();
        GridPane paneCampos = new GridPane();

        bp.setTop(paneCampos);
        bp.setCenter(table);

        paneCampos.add(new Label("Livro"), 0, 0);
        paneCampos.add(cmbLivro, 1, 0);

        paneCampos.add(new Label("Estudante"), 0, 1);
        paneCampos.add(cmbEstudante, 1, 1);

        /*paneCampos.add(new Label("Empréstimo"), 0, 2);
        paneCampos.add(dtaEmprestimo, 1, 2);*/

        paneCampos.add(new Label("Prevista"), 0, 3);
        paneCampos.add(dtaPrevista, 1, 3);

        paneCampos.add(new Label("Devolução"), 0, 4);
        paneCampos.add(dtaDevolucao, 1, 4);

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> {

        StringBuilder erros = new StringBuilder();

        // validações...
        if (cmbLivro.getValue() == null) {
            erros.append("Selecione um livro.\n");
        }

        if (cmbEstudante.getValue() == null) {
            erros.append("Selecione um estudante.\n");
        }

        if (dtaDevolucao.getValue() != null) {

            if (dtaDevolucao.getValue().isAfter(LocalDate.now())) {
                erros.append("Data de devolução não pode ser futura.\n");
            }
        }

        if (erros.length() > 0) {
            new Alert(Alert.AlertType.ERROR, erros.toString()).show();
            return;
        }

        // agora só chama
        control.salvar();
        control.limparCampos();

        new Alert(Alert.AlertType.INFORMATION, "Empréstimo salvo com sucesso").show();
    });

        Button btnLimpar = new Button();
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/new.png"));
            ImageView iv = new ImageView(icon);
            iv.setFitHeight(20);
            iv.setFitWidth(20);
            btnLimpar.setGraphic(iv);
        } catch (Exception ex) {
            btnLimpar.setText("Novo");
        }

        btnLimpar.setOnAction(e -> control.limparCampos());

        paneCampos.add(btnSalvar, 0, 5);
        paneCampos.add(btnLimpar, 1, 5);

        Bindings.bindBidirectional(cmbLivro.valueProperty(), control.livroProperty());
        Bindings.bindBidirectional(cmbEstudante.valueProperty(), control.estudanteProperty());

        //Bindings.bindBidirectional(dtaEmprestimo.valueProperty(), control.dataEmprestimo);
        Bindings.bindBidirectional(dtaPrevista.valueProperty(), control.dataPrevistaProperty());
        Bindings.bindBidirectional(dtaDevolucao.valueProperty(), control.dataDevolucaoProperty());

        cmbLivro.setConverter(new javafx.util.StringConverter<Livro>() {

            @Override
            public String toString(Livro l) {
                return l == null ? "" : l.getTitulo();
            }

            @Override
            public Livro fromString(String s) {
                return null;
            }
        });

        cmbEstudante.setConverter(new javafx.util.StringConverter<Estudante>() {

            @Override
            public String toString(Estudante e) {
                return e == null ? "" : e.getNome();
            }

            @Override
            public Estudante fromString(String s) {
                return null;
            }
        });

        cmbLivro.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Livro item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTitulo());
            }
        });

        cmbLivro.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Livro item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getTitulo());
            }
        });

        cmbEstudante.setCellFactory(es -> new ListCell<>() {
            @Override
            protected void updateItem(Estudante item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome());
            }
        });

        cmbEstudante.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Estudante item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getNome());
            }
        });

        TableColumn<Emprestimo, String> colLivro = new TableColumn<>("Livro");
        colLivro.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getLivro().getTitulo()));

        TableColumn<Emprestimo, String> colEstudante = new TableColumn<>("Estudante");
        colEstudante.setCellValueFactory( item -> new ReadOnlyStringWrapper(item.getValue().getEstudante().getNome()));

        TableColumn<Emprestimo, String> colData = new TableColumn<>("Empréstimo");
        colData.setCellValueFactory(item -> new ReadOnlyStringWrapper(formatarData(item.getValue().getDataEmprestimo())));

        TableColumn<Emprestimo, String> colDataPrevista = new TableColumn<>("Prevista");
        colDataPrevista.setCellValueFactory(item -> new ReadOnlyStringWrapper(formatarData(item.getValue().getDataPrevista())));

        TableColumn<Emprestimo, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getStatus()));

        TableColumn<Emprestimo, Void> colAcoes = new TableColumn<>("Ações");

        colAcoes.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Emprestimo, Void> call(TableColumn<Emprestimo, Void> param) {
                return new TableCell<>() {

                    private final Button btnDelete = new Button("Cancelar");

                    {
                        btnDelete.setOnAction(e -> control.apagar(getIndex()));
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : btnDelete);
                    }
                };
            }
        });

        table.setItems(control.getLista());

        table.getColumns().addAll(
            colLivro,
            colEstudante,
            colData,
            colDataPrevista,
            colStatus,
            colAcoes
        );

        table.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> control.toBoundary(novo));

        cmbLivro.setItems(control.getLivros());
        cmbEstudante.setItems(control.getEstudantes());

        control.limparCampos();

        return bp;
    }
}