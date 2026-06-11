package br.com.biblioteca.control;

import br.com.biblioteca.dao.EstudanteDAO;
import br.com.biblioteca.dao.EstudanteDAOImpl;
import br.com.biblioteca.entity.Emprestimo;
import br.com.biblioteca.entity.Estudante;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EstudanteControl {

    private EmprestimoControl emprestimoControl = new EmprestimoControl();

    ObservableList<Estudante> lista = FXCollections.observableArrayList();

    IntegerProperty idEstudante = new SimpleIntegerProperty(0);

    StringProperty nome = new SimpleStringProperty("");

    StringProperty curso = new SimpleStringProperty("");

    StringProperty telefone = new SimpleStringProperty("");

    private EstudanteDAO dao = new EstudanteDAOImpl();

    public EstudanteControl() {
        carregar();
        emprestimoControl.carregar();
    }

    public void limparCampos() {
        idEstudante.set(0);
        nome.set("");
        curso.set("");
        telefone.set("");
    }

    public void salvar() {
        Estudante estudante = toEntity();

        if (estudante.getIdEstudante() > 0) {
            dao.atualizar(estudante.getIdEstudante(), estudante);
        } else {
            dao.cadastrar(estudante);
        }

        limparCampos();
        carregar();
    }

    public void carregar() {
        lista.clear();
        lista.addAll(
                dao.consultarPorNome("")
        );
    }

    public void pesquisar() {
        lista.clear();
        lista.addAll(
                dao.consultarPorNome(nome.get())
        );
    }

    public void apagar(int index) {

        Estudante estudante = lista.get(index);

        if (temEmprestimo(estudante.getIdEstudante())) {
            throw new RuntimeException("ESTUDANTE_COM_EMPRESTIMO");
        }

        dao.apagar(estudante.getIdEstudante());
        carregar();
    }

    public Estudante toEntity() {
        Estudante estudante = new Estudante();

        estudante.setIdEstudante(idEstudante.get());
        estudante.setNome(nome.get());
        estudante.setCurso(curso.get());
        estudante.setTelefone(telefone.get());

        return estudante;
    }

    public void toBoundary(Estudante estudante) {
        if (estudante != null) {
            idEstudante.set(estudante.getIdEstudante());
            nome.set(estudante.getNome());
            curso.set(estudante.getCurso());
            telefone.set(estudante.getTelefone());
        }
    }

    public ObservableList<Estudante> getLista() {
        return lista;
    }

    private boolean temEmprestimo(int idEstudante) {

        for (Emprestimo e : emprestimoControl.getLista()) {
            if (e.getEstudante() != null &&
                e.getEstudante().getIdEstudante() == idEstudante) {
                return true;
            }
        }

        return false;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty cursoProperty() {
        return curso;
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

}