package br.com.biblioteca.dao;

import java.util.List;

import br.com.biblioteca.entity.Livro;

public interface LivroDAO {
    void cadastrar(Livro l);
    List<Livro> consultarPorTitulo( String titulo );
    void atualizar(long id, Livro l);
    void apagar( long id );
    List<Livro> consultarTodos();
}