package br.com.biblioteca;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PrincipalBoundary extends Application {

    // to tirando o livro da memoria pra conseguir atualizar a quantidade
    //private Pane livroBoundary = new LivroBoundary().render();

    // aqui nao tem problema subir e deixar em memoria
    private Pane estudanteBoundary = new EstudanteBoundary().render();

    // mesma coisa aqui se não me engano era sobre o status
    //private Pane emprestimoBoundary = new EmprestimoBoundary().render();

    // sobre pra memoria
    private Pane relatorioBoundary = new RelatorioBoundary().render();

    @Override
    public void start(Stage stage) {

        BorderPane panPrincipal = new BorderPane();

        Scene scn = new Scene(panPrincipal, 1000, 700);

        MenuBar menuBar = new MenuBar();

        Menu mnuCadastro = new Menu("Cadastros");
        Menu mnuEmprestimo = new Menu("Empréstimos");
        Menu mnuRelatorio = new Menu("Relatórios");
        Menu mnuAjuda = new Menu("Ajuda"); // talvez eu ainda coloque o ajuda
        
        menuBar.getMenus().addAll(mnuCadastro, mnuEmprestimo, mnuRelatorio);

        //menuBar.getMenus().addAll(mnuCadastro, mnuEmprestimo, mnuAjuda); 

        MenuItem mnuLivro = new MenuItem("Livros");
        MenuItem mnuEstudante = new MenuItem("Estudantes");
        MenuItem mnuEmprestimoItem = new MenuItem("Gerenciar Empréstimos");
        MenuItem mnuRelatorioItem = new MenuItem("Consultar Relatórios");

        mnuLivro.setOnAction(e -> panPrincipal.setCenter(new LivroBoundary().render()));

        mnuEstudante.setOnAction(e -> panPrincipal.setCenter(estudanteBoundary));

        mnuEmprestimoItem.setOnAction(e -> panPrincipal.setCenter(new EmprestimoBoundary().render()));

        mnuRelatorioItem.setOnAction(e -> panPrincipal.setCenter(relatorioBoundary));
        
        mnuCadastro.getItems().addAll(mnuLivro, mnuEstudante);

        mnuEmprestimo.getItems().add(mnuEmprestimoItem);

        mnuRelatorio.getItems().add(mnuRelatorioItem);

        panPrincipal.setTop(menuBar);

        stage.setTitle("Sistema de Biblioteca");
        stage.setScene(scn);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}