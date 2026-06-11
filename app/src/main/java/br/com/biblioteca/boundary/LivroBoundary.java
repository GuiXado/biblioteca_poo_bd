package br.com.biblioteca.boundary;

import br.com.biblioteca.control.LivroControl;
import br.com.biblioteca.entity.Livro;
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

    // campos de entrada para os dados do livro
    private TextField txtTitulo = new TextField();
    private TextField txtAutor = new TextField();
    private TextField txtEditora = new TextField();
    private DatePicker dtaPublicacao = new DatePicker();
    private TextField txtQuantidade = new TextField();


    private LivroControl control = new LivroControl(); //instancia o controle para manipular os dados dos livros

    private TableView<Livro> table = new TableView<>();

    @Override
    @SuppressWarnings("unchecked")
    public Pane render() {
        //cria o container, painel principal, divide por regioes (top, left, center, right, bottom)
        BorderPane bp = new BorderPane();
        // aqui crio os campo quase que como uma matriz
        GridPane paneCampos = new GridPane();

        bp.setTop(paneCampos); // logo, digo que em cima fica o paneCampo (minha matriz)
        bp.setCenter(table); // no centro a tabela


        paneCampos.add(new Label("Título"), 0, 0);
        paneCampos.add(txtTitulo, 1, 0);

        paneCampos.add(new Label("Autor"), 0, 1);
        paneCampos.add(txtAutor, 1, 1);

        paneCampos.add(new Label("Editora"), 0, 2);
        paneCampos.add(txtEditora, 1, 2);

        paneCampos.add(new Label("Data Publicação"), 0, 3); // se der dois espeços depois do texto é um jeito porco de afastar o textfiled do texto
        paneCampos.add(dtaPublicacao, 1, 3);

        paneCampos.add(new Label("Quantidade"), 0, 4);
        paneCampos.add(txtQuantidade, 1, 4);

        Button btnSalvar = new Button("Salvar");
        // todas as validações ficaram no botão de salvar
        btnSalvar.setOnAction(e -> {
            StringBuilder erros = new StringBuilder();

            // AUTOR (não pode vazio + sem números)
            String autor = txtAutor.getText();
            if (autor == null || autor.trim().isEmpty()) {
                erros.append("Autor não pode ser vazio.\n");
            } else if (!autor.matches("[A-Za-zÀ-ÿ\\s]+")) {
                erros.append("Autor não pode conter números ou caracteres inválidos.\n");
            }

            // DATA PUBLICAÇÃO (não pode ser futura)
            if (dtaPublicacao.getValue() == null) {
                erros.append("Data de publicação não pode ser vazia.\n");
            } else if (dtaPublicacao.getValue().isAfter(java.time.LocalDate.now())) {
                erros.append("Data de publicação não pode ser futura.\n");
            }

            // QUANTIDADE (somente número >= 0)
            String qtdTexto = txtQuantidade.getText();

            if (qtdTexto == null || qtdTexto.trim().isEmpty()) {
                erros.append("Quantidade não pode ser vazia.\n");
            } else {
                try {
                    int qtd = Integer.parseInt(qtdTexto);

                    if (qtd < 0) {
                        erros.append("Quantidade não pode ser negativa.\n");
                    }

                } catch (NumberFormatException ex) {
                    erros.append("Quantidade deve ser um número inteiro.\n");
                }
            }

            // SE TEM ERRO MOSTRA E NÃO SALVA
            if (erros.length() > 0) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erro de validação");
                alert.setHeaderText("Corrija os campos abaixo:");
                alert.setContentText(erros.toString());
                alert.show();
                return;
            }

            // antes das validações só tinha esse trecho, mas salvava tudo errado
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
            Image icon = new Image(getClass().getResourceAsStream("/images/new.png"));

            ImageView imageView = new ImageView(icon);
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);

            btnLimparCampos.setGraphic(imageView);
        } catch (Exception ex) {
            btnLimparCampos.setText("Novo");
        }

        btnLimparCampos.setOnAction(e -> control.limparCampos());

        paneCampos.add(btnLimparCampos, 2, 0);

        // to ligando os campos com o controler, to dando acesso para o controler ver o que é digitado, tem haver com o que fiz em cima 
        Bindings.bindBidirectional(txtTitulo.textProperty(), control.tituloProperty());
        Bindings.bindBidirectional(txtAutor.textProperty(), control.autorProperty());
        Bindings.bindBidirectional(txtEditora.textProperty(), control.editoraProperty());
        Bindings.bindBidirectional(dtaPublicacao.valueProperty(), control.dataPublicacaoProperty());
        Bindings.bindBidirectional(txtQuantidade.textProperty(), control.quantidadeProperty(), new javafx.util.converter.NumberStringConverter());

     
        // Cria a coluna "Título" da tabela e define que ela exibirá
        // o valor retornado pelo método getTitulo() de cada objeto Livro
        TableColumn<Livro, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getTitulo()));

        TableColumn<Livro, String> colAutor = new TableColumn<>("Autor");
        colAutor.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getAutor()));

        TableColumn<Livro, String> colEditora =  new TableColumn<>("Editora");
        colEditora.setCellValueFactory(itemData -> new ReadOnlyStringWrapper(itemData.getValue().getEditora()));

        TableColumn<Livro, String> colQuantidade = new TableColumn<>("Quantidade");
        colQuantidade.setCellValueFactory(
            itemData -> new ReadOnlyStringWrapper(String.valueOf(itemData.getValue().getQuantidade()))
        );

        // similar aos de cima, mas to criando um botão de acção da tabela, coluna "excluir", vou remover a linha do banco
        TableColumn<Livro, Void> colAcoes = new TableColumn<>("Ações");
        Callback<TableColumn<Livro, Void>,
                 TableCell<Livro, Void>> callBack = new Callback<>() {

                @Override
                public TableCell<Livro, Void> call(TableColumn<Livro, Void> param) {

                    return new TableCell<>() {

                        private Button btnDelete = new Button("Excluir");

                        { // isso aqui é assim mesmo, //é um bloco de inicialização do objeto, é executado quando a célula é criada, aqui eu defino a ação do botão
                           btnDelete.setOnAction(e -> {
                                try {
                                    control.apagar(getIndex());

                                } catch (Exception ex) {

                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Erro");

                                    if (ex.getMessage().equals("LIVRO_COM_EMPRESTIMO")) {
                                        alert.setContentText(
                                            "Não é possível excluir: livro possui empréstimos vinculados."
                                        );
                                    } else {
                                        alert.setContentText(ex.getMessage());
                                    }

                                    alert.show();
                                }
                            });
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

        // Associa a tabela à lista de livros do Control.
        table.setItems(control.getLista()); 

        // adiciona as colunas criadas à tabela
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