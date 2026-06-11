package br.com.biblioteca.entity;

import java.time.LocalDate;

public class Emprestimo {
    private int idEmprestimo;
    private Livro livro;
    private Estudante estudante;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevista;
    private LocalDate dataDevolucao;

    public Emprestimo() {
    }

    public Emprestimo(int idEmprestimo, Livro livro, Estudante estudante, LocalDate dataEmprestimo, LocalDate dataPrevista, LocalDate dataDevolucao) {
        this.idEmprestimo = idEmprestimo;
        this.livro = livro;
        this.estudante = estudante;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataPrevista;
        this.dataDevolucao = dataDevolucao;
    }

    public String getStatus() {

        if (dataDevolucao != null) {
            return "Devolvido";
        }

        if (dataPrevista.isBefore(LocalDate.now())) {
            return "Atrasado";
        }

        return "Ativo";
    }
    public int getIdEmprestimo() {
        return idEmprestimo;
    }
    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Livro getLivro() {
        return livro;
    }
    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Estudante getEstudante() {
        return estudante;
    }
    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataPrevista() {
        return dataPrevista;
    }
    public void setDataPrevista(LocalDate dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
        
}