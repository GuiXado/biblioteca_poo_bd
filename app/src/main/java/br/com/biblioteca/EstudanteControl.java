package br.com.biblioteca;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EstudanteControl {

    ObservableList<Estudante> lista = FXCollections.observableArrayList();

    IntegerProperty idEstudante = new SimpleIntegerProperty(0);

    StringProperty nome = new SimpleStringProperty("");

    StringProperty curso = new SimpleStringProperty("");

    StringProperty telefone = new SimpleStringProperty("");

    private EstudanteDAO dao = new EstudanteDAOImpl();

    public EstudanteControl() {
        carregar();
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

}