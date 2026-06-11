package br.com.biblioteca;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LivroControl {
    private EmprestimoControl emprestimoControl = new EmprestimoControl();
    // cria a lista observável que armazena os livros exibidos na tabela
    ObservableList<Livro> lista = FXCollections.observableArrayList();

    IntegerProperty idLivro = new SimpleIntegerProperty(0);
    StringProperty titulo = new SimpleStringProperty("");
    StringProperty autor = new SimpleStringProperty("");
    StringProperty editora = new SimpleStringProperty("");
    ObjectProperty<LocalDate> dataPublicacao = new SimpleObjectProperty<>(LocalDate.now());
    IntegerProperty quantidade = new SimpleIntegerProperty(0);
    
    // instância do DAO para acessar o banco de cados, insntacia o LivroDAOImpl do tipo LivroDAO que é a interface
    // por isso eu "chamo a inteface" e ela liga a implementação
    private LivroDAO dao = new LivroDAOImpl();

    // contrutor já inicia com o metodo carregar()
    public LivroControl() {
        carregar();
    }

    public void limparCampos() {
        idLivro.set(0);
        titulo.set("");
        autor.set("");
        editora.set("");
        dataPublicacao.set(LocalDate.now());
        quantidade.set(0);
    }

    public void salvar() {
        Livro livro = toEntity();

        if (livro.getIdLivro() > 0) {
            dao.atualizar(livro.getIdLivro(), livro);
        } else {
            dao.cadastrar(livro);
        }

        //limparCampos();
        carregar();
    }

    // carrega os livros do banco para a lista obervável
    public void carregar() {
        lista.clear();
        lista.addAll(dao.consultarPorTitulo(""));
    }

    public void pesquisar() {
        lista.clear();
        lista.addAll(dao.consultarPorTitulo(titulo.get()));
    }

    public void apagar(int index) {
        Livro livro = lista.get(index);

        if (emprestimoControl.temEmprestimoPorLivro(livro.getIdLivro())) {
            throw new RuntimeException("LIVRO_COM_EMPRESTIMO");
        }

        dao.apagar(livro.getIdLivro());
        carregar();
    }

    // Transforma dados da interface em um objeto do sistema
    public Livro toEntity() {
        Livro livro = new Livro();

        livro.setIdLivro(idLivro.get());
        livro.setTitulo(titulo.get());
        livro.setAutor(autor.get());
        livro.setEditora(editora.get());
        livro.setDataPublicacao(dataPublicacao.get());
        livro.setQuantidade(quantidade.get());

        return livro;
    }

    // Carrega dados de um objeto para a interface gráfica
    public void toBoundary(Livro livro) {
        if (livro != null) {
            idLivro.set(livro.getIdLivro());
            titulo.set(livro.getTitulo());
            autor.set(livro.getAutor());
            editora.set(livro.getEditora());
            dataPublicacao.set(livro.getDataPublicacao());
            quantidade.set(livro.getQuantidade());
        }
    }

    public ObservableList<Livro> getLista() {
        return lista;
    }

}