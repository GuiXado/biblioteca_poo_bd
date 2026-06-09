package br.com.biblioteca;

import java.util.List;

public interface EmprestimoDAO {
    void cadastrar(Emprestimo e);
    List<Emprestimo> consultarPorEstudante( String estudante );
    void atualizar(long id, Emprestimo e);
    void apagar( long id );
}