package br.com.biblioteca;

public class RelatorioItem {

    private String livro;
    private String estudante;
    private String autor;
    private String dataEmprestimo;
    private String dataPrevista;
    private String status;
    private String quantidade;

    public RelatorioItem() {
    }

    public RelatorioItem(String livro, String estudante, String autor, String dataEmprestimo, String dataPrevista, String status, String quantidade) {

        this.livro = livro;
        this.estudante = estudante;
        this.autor = autor;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataPrevista;
        this.status = status;
        this.quantidade = quantidade;
    }

    public String getLivro() {
        return livro;
    }

    public String getEstudante() {
        return estudante;
    }

    public String getAutor() {
        return autor;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public String getDataPrevista() {
        return dataPrevista;
    }

    public String getStatus() {
        return status;
    }

    public String getQuantidade() {
        return quantidade;
    }
}