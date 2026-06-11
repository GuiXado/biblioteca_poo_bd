package br.com.biblioteca.dao;

import java.util.List;

import br.com.biblioteca.entity.Emprestimo;

public interface EmprestimoDAO {
    void cadastrar(Emprestimo e);
    List<Emprestimo> consultarPorEstudante( String estudante );
    void atualizar(long id, Emprestimo e);
    void apagar( long id );
}