package br.com.biblioteca;

public class Estudante {
    private int idEstudante;
    private String nome;
    private String curso;
    private String telefone;

    public Estudante() {
    }

    public Estudante(int idEstudante, String nome, String curso, String telefone) {
        this.idEstudante = idEstudante;
        this.nome = nome;
        this.curso = curso;
        this.telefone = telefone;
    }

    public void setIdEstudante(int idEstudante) {
        this.idEstudante = idEstudante;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
    public int getIdEstudante() {
        return idEstudante;
    }

    public String toString() {
        return nome;
    }
}