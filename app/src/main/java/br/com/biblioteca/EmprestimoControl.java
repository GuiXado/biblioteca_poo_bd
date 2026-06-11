package br.com.biblioteca;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EmprestimoControl {

    private ObservableList<Emprestimo> lista = FXCollections.observableArrayList();

    private ObservableList<Livro> livros = FXCollections.observableArrayList();

    private ObservableList<Estudante> estudantes = FXCollections.observableArrayList();

    IntegerProperty idEmprestimo = new SimpleIntegerProperty(0);

    ObjectProperty<Livro> livro = new SimpleObjectProperty<>();

    ObjectProperty<Estudante> estudante = new SimpleObjectProperty<>();

    //ObjectProperty<LocalDate> dataEmprestimo = new SimpleObjectProperty<>(LocalDate.now());

    ObjectProperty<LocalDate> dataPrevista = new SimpleObjectProperty<>(LocalDate.now().plusDays(7));

    ObjectProperty<LocalDate> dataDevolucao = new SimpleObjectProperty<>();

    EmprestimoDAO dao = new EmprestimoDAOImpl();
    
    EstudanteDAO estudanteDAO = new EstudanteDAOImpl();

    LivroDAO livroDAO = new LivroDAOImpl();

    public EmprestimoControl() {
        carregar();
        carregarCombos();
    }

    public void limparCampos() {
        idEmprestimo.set(0);
        livro.set(null);
        estudante.set(null);
        //dataEmprestimo.set(LocalDate.now());
        dataPrevista.set(LocalDate.now().plusDays(7));
        dataDevolucao.set(null);
    }

    public void salvar() {

        Emprestimo e = toEntity();

        Livro livroSelecionado = e.getLivro();

        LivroDAO livroDAO = new LivroDAOImpl();

        // para emprestimo novo
        if (e.getIdEmprestimo() == 0) {

            if (livroSelecionado.getQuantidade() <= 0) {
                return;
            }

            livroSelecionado.setQuantidade(
                livroSelecionado.getQuantidade() - 1
            );

            livroDAO.atualizar(
                livroSelecionado.getIdLivro(),
                livroSelecionado
            );

            dao.cadastrar(e);
        }

        // atualização e devolução
        else {

            if (e.getDataDevolucao() != null) {

                livroSelecionado.setQuantidade(
                    livroSelecionado.getQuantidade() + 1
                );

                livroDAO.atualizar(
                    livroSelecionado.getIdLivro(),
                    livroSelecionado
                );
            }

            dao.atualizar(e.getIdEmprestimo(), e);
        }

        carregar();

        //limparCampos(); to chamando no botão
    } 

    public void carregar() {
        lista.clear();
        lista.addAll(dao.consultarPorEstudante(""));

        livros.clear();
        livros.addAll(livroDAO.consultarTodos());

        estudantes.clear();
        estudantes.addAll(estudanteDAO.consultarTodos());
    }

    public void apagar(int index) {
        Emprestimo e = lista.get(index);
        
        if (e.getDataDevolucao() == null) { // aqui ta a magica

            Livro livro = e.getLivro();

            if (livro != null) {
                livro.setQuantidade(livro.getQuantidade() + 1);

                LivroDAO livroDAO = new LivroDAOImpl();
                livroDAO.atualizar(livro.getIdLivro(), livro);
            }
        }

        // remove empréstimo
        dao.apagar(e.getIdEmprestimo());

        carregar();
    }

    public Emprestimo toEntity() {
        Emprestimo e = new Emprestimo();

        e.setIdEmprestimo(idEmprestimo.get());
        e.setLivro(livro.get());
        e.setEstudante(estudante.get());
        //e.setDataEmprestimo(dataEmprestimo.get());
        e.setDataPrevista(dataPrevista.get());
        e.setDataDevolucao(dataDevolucao.get());

        return e;
    }

    public void toBoundary(Emprestimo e) {
        if (e != null) {
            idEmprestimo.set(e.getIdEmprestimo());
            livro.set(e.getLivro());
            estudante.set(e.getEstudante());
            //dataEmprestimo.set(e.getDataEmprestimo());
            dataPrevista.set(e.getDataPrevista());
            dataDevolucao.set(e.getDataDevolucao());
        }
    }

    //manter os cadastro atualizados no emprestimo
    public void carregarCombos() {

        livros.clear();
        livros.addAll(livroDAO.consultarPorTitulo(""));

        estudantes.clear();
        estudantes.addAll(estudanteDAO.consultarPorNome(""));
    }

    public ObservableList<Emprestimo> getLista() {
        return lista;
    }

    public ObservableList<Livro> getLivros() {
        return livros;
    }

    public ObservableList<Estudante> getEstudantes() {
        return estudantes;
    }

    public void pesquisar() {
        carregar();
    }

    public boolean temEmprestimoPorLivro(int idLivro) {

    for (Emprestimo e : lista) {
        if (e.getLivro() != null &&
            e.getLivro().getIdLivro() == idLivro) {
            return true;
        }
    }

    return false;
}
}