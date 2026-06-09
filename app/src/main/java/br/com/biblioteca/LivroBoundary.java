package br.com.biblioteca;

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
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class LivroBoundary implements Tela {

    private TextField txtTitulo = new TextField();
    private TextField txtAutor = new TextField();
    private TextField txtEditora = new TextField();
    private TextField txtQuantidade = new TextField();

    private DatePicker dtaPublicacao = new DatePicker();

    private LivroControl control = new LivroControl();

    private TableView<Livro> table = new TableView<>();

    @Override
    public Pane render() {

        BorderPane bp = new BorderPane();
        GridPane paneCampos = new GridPane();

        bp.setTop(paneCampos);
        bp.setCenter(table);

        paneCampos.add(new Label("Título"), 0, 0);
        paneCampos.add(txtTitulo, 1, 0);

        paneCampos.add(new Label("Autor"), 0, 1);
        paneCampos.add(txtAutor, 1, 1);

        paneCampos.add(new Label("Editora"), 0, 2);
        paneCampos.add(txtEditora, 1, 2);

        paneCampos.add(new Label("Data Publicação"), 0, 3);
        paneCampos.add(dtaPublicacao, 1, 3);

        paneCampos.add(new Label("Quantidade"), 0, 4);
        paneCampos.add(txtQuantidade, 1, 4);

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> {
            control.salvar();
            control.limparCampos();

            new Alert(AlertType.INFORMATION,"Livro salvo com sucesso").show();
        });

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> control.pesquisar());

        paneCampos.add(btnSalvar, 0, 5);
        paneCampos.add(btnPesquisar, 1, 5);

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

        Bindings.bindBidirectional(txtTitulo.textProperty(), control.titulo);

        Bindings.bindBidirectional(txtAutor.textProperty(), control.autor);

        Bindings.bindBidirectional(txtEditora.textProperty(), control.editora);

        Bindings.bindBidirectional(dtaPublicacao.valueProperty(), control.dataPublicacao);

        Bindings.bindBidirectional(txtQuantidade.textProperty(), control.quantidade, new javafx.util.converter.NumberStringConverter());

        /*txtQuantidade.textProperty().addListener(
            (obs, antigo, novo) -> {
                try {
                    control.quantidade.set(Integer.parseInt(novo));
                } catch (Exception ex) {

                }
            }
        );*/

        TableColumn<Livro, String> colTitulo = new TableColumn<>("Título");

        colTitulo.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(itemData.getValue().getTitulo())
        );

        TableColumn<Livro, String> colAutor = new TableColumn<>("Autor");

        colAutor.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(itemData.getValue().getAutor())
        );

        TableColumn<Livro, String> colEditora =  new TableColumn<>("Editora");

        colEditora.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(itemData.getValue().getEditora())
        );

        TableColumn<Livro, String> colQuantidade = new TableColumn<>("Quantidade");

        colQuantidade.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(
                String.valueOf(itemData.getValue().getQuantidade())
            )
        );

        TableColumn<Livro, Void> colAcoes = new TableColumn<>("Ações");

        Callback<TableColumn<Livro, Void>,
                 TableCell<Livro, Void>> callBack = new Callback<>() {

                @Override
                public TableCell<Livro, Void> call(TableColumn<Livro, Void> param) {

                    return new TableCell<>() {

                        private Button btnDelete = new Button("Excluir");

                        {
                            btnDelete.setOnAction(
                                e -> control.apagar(getIndex())
                            );
                        }

                        @Override
                        public void updateItem(
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
            colTitulo,
            colAutor,
            colEditora,
            colQuantidade,
            colAcoes
        );

        table.getSelectionModel()
             .selectedItemProperty()
             .addListener( (obj, antigo, novo) -> control.toBoundary(novo) );

        control.limparCampos();

        return bp;
    }
}