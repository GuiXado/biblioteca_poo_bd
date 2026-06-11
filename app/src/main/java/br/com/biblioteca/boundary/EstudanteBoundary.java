package br.com.biblioteca.boundary;

import br.com.biblioteca.control.EstudanteControl;
import br.com.biblioteca.entity.Estudante;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class EstudanteBoundary implements Tela {

    private TextField txtNome = new TextField();
    private TextField txtCurso = new TextField();
    private TextField txtTelefone = new TextField();

    private EstudanteControl control = new EstudanteControl();

    private TableView<Estudante> table = new TableView<>();

    @Override
    @SuppressWarnings("unchecked")
    public Pane render() {

        BorderPane bp = new BorderPane();
        GridPane paneCampos = new GridPane();

        bp.setTop(paneCampos);
        bp.setCenter(table);

        paneCampos.add(new Label("Nome"), 0, 0);
        paneCampos.add(txtNome, 1, 0);

        paneCampos.add(new Label("Curso"), 0, 1);
        paneCampos.add(txtCurso, 1, 1);

        paneCampos.add(new Label("Telefone"), 0, 2);
        paneCampos.add(txtTelefone, 1, 2);

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> {

            StringBuilder erros = new StringBuilder();

            // NOME (obrigatório)
            String nome = txtNome.getText();
            if (nome == null || nome.trim().isEmpty()) {
                erros.append("Nome não pode ser vazio.\n");
            }

            // TELEFONE
            String telefone = txtTelefone.getText();

            if (telefone != null && !telefone.trim().isEmpty()) { //verfica se nao é nulo ou vazio
                if (!telefone.matches("\\d+")) { //verifica se tem só numeros
                    erros.append("Telefone deve conter apenas números.\n");
                }
            }
            // não precisa usar eu deixer ele como null
            /*if (telefone == null || telefone.trim().isEmpty()) {
                erros.append("Telefone não pode ser vazio.\n");
            } else {
                if (!telefone.matches("\\d+")) {
                    erros.append("Telefone deve conter apenas números.\n");
                }
            }*/

            // QUANDO APARECER ERRO
            if (erros.length() > 0) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erro de validação");
                alert.setHeaderText("Corrija os campos:");
                alert.setContentText(erros.toString());
                alert.show();
                return;
            }



            control.salvar();
            new Alert(AlertType.INFORMATION,"Estudante salvo com sucesso").show();
        });

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> control.pesquisar());

        paneCampos.add(btnSalvar, 0, 3);
        paneCampos.add(btnPesquisar, 1, 3);

        Button btnLimparCampos = new Button();

        try {
            Image icon = new Image(
                getClass().getResourceAsStream("/images/new.png")
            );

            ImageView imageView = new ImageView(icon);
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);

            btnLimparCampos.setGraphic(imageView);
        } catch (Exception ex) {
            btnLimparCampos.setText("Novo");
        }

        btnLimparCampos.setOnAction(e -> control.limparCampos());

        paneCampos.add(btnLimparCampos, 2, 0);

        Bindings.bindBidirectional(txtNome.textProperty(), control.nomeProperty());
        Bindings.bindBidirectional(txtCurso.textProperty(), control.cursoProperty());
        Bindings.bindBidirectional(txtTelefone.textProperty(), control.telefoneProperty());

        TableColumn<Estudante, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getNome()));

        TableColumn<Estudante, String> colCurso = new TableColumn<>("Curso");
        colCurso.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getCurso()));

        TableColumn<Estudante, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getTelefone()));

        TableColumn<Estudante, Void> colAcoes = new TableColumn<>("Ações");
        Callback<TableColumn<Estudante, Void>, TableCell<Estudante, Void>> callBack = new Callback<>() {

            @Override
            public TableCell<Estudante, Void> call(
                    TableColumn<Estudante, Void> param) {

                return new TableCell<>() {

                    private Button btnDelete = new Button("Excluir");
                    {
                        btnDelete.setOnAction(e -> {
                            try {
                                control.apagar(getIndex());

                            } catch (Exception ex) {

                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Erro");

                                if (ex.getMessage().equals("ESTUDANTE_COM_EMPRESTIMO")) {
                                    alert.setContentText(
                                        "Não é possível excluir: estudante possui empréstimo ativo."
                                    );
                                } else {
                                    alert.setContentText(ex.getMessage());
                                }

                                alert.show();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(
                            Void item,
                            boolean empty) {

                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnDelete);
                        }
                    }
                };
            }
        };

        colAcoes.setCellFactory(callBack);

        table.setItems(control.getLista());

        table.getColumns().addAll(
            colNome,
            colCurso,
            colTelefone,
            colAcoes
        );

        table.getSelectionModel()
             .selectedItemProperty()
             .addListener((obs, antigo, novo) -> control.toBoundary(novo));

        control.limparCampos();

        return bp;
    }
}