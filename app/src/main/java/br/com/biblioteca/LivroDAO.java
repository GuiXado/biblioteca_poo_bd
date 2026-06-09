package br.com.biblioteca;

import java.util.List;

public interface LivroDAO {
    void cadastrar(Livro l);
    List<Livro> consultarPorTitulo( String titulo );
    void atualizar(long id, Livro l);
    void apagar( long id );
    List<Livro> consultarTodos();
}