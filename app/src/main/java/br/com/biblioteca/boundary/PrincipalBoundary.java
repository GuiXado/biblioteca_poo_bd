package br.com.biblioteca.boundary;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// tela principal do sistema, onde tem o menu para acessar as outras telas, cada tela é carregada no centro do BorderPane quando o menu correspondente é clicado
public class PrincipalBoundary extends Application {

    // to tirando o livro da memoria pra conseguir atualizar a quantidade, nem quando tem emprestimo muda a qtd 
    //private Pane livroBoundary = new LivroBoundary().render(); 

    // aqui nao tem problema subir e deixar em memoria
    private Pane estudanteBoundary = new EstudanteBoundary().render();

    // mesma coisa aqui se não me engano era sobre o status
    private Pane emprestimoBoundary = new EmprestimoBoundary().render(); //esse tava comentado

    // sobre pra memoria
    private Pane relatorioBoundary = new RelatorioBoundary().render();

    @Override
    public void start(Stage stage) {
        // BorderPane principal para organizar a tela
        BorderPane panPrincipal = new BorderPane();

        // criando a cena de 1000px por 700px
        Scene scn = new Scene(panPrincipal, 1000, 700);

        MenuBar menuBar = new MenuBar();

        Menu mnuCadastro = new Menu("Cadastros");
        Menu mnuEmprestimo = new Menu("Empréstimos");
        Menu mnuRelatorio = new Menu("Relatórios");
        //Menu mnuAjuda = new Menu("Ajuda"); // talvez eu ainda coloque o ajuda

        // adicionando os menus na barra de menu
        menuBar.getMenus().addAll(mnuCadastro, mnuEmprestimo, mnuRelatorio);
        //menuBar.getMenus().addAll(mnuCadastro, mnuEmprestimo, mnuAjuda); 

        // criando os itens de menu para cada menu
        MenuItem mnuLivro = new MenuItem("Livros");
        MenuItem mnuEstudante = new MenuItem("Estudantes");
        MenuItem mnuEmprestimoItem = new MenuItem("Gerenciar Empréstimos");
        MenuItem mnuRelatorioItem = new MenuItem("Consultar Relatórios");

        // definindo as ações para cada item de menu, quando clicado, o centro do BorderPane é atualizado com a tela correspondente
        mnuLivro.setOnAction(e -> panPrincipal.setCenter(new LivroBoundary().render())); // faz a instancia toda vez para poder atualizar
        //mnuLivro.setOnAction(e -> panPrincipal.setCenter(livroBoundary));
        mnuEstudante.setOnAction(e -> panPrincipal.setCenter(estudanteBoundary));
        //mnuEmprestimoItem.setOnAction(e -> panPrincipal.setCenter(new EmprestimoBoundary().render()));
        mnuEmprestimoItem.setOnAction(e -> panPrincipal.setCenter(emprestimoBoundary));
        mnuRelatorioItem.setOnAction(e -> panPrincipal.setCenter(relatorioBoundary));
        
        // adicionando os itens de menu aos menus correspondentes
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