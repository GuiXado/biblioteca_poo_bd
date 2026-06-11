package br.com.biblioteca.dao;

import java.util.List;

import br.com.biblioteca.entity.Estudante;

public interface EstudanteDAO {
    void cadastrar(Estudante e);
    List<Estudante> consultarPorNome( String nome );
    void atualizar(long id, Estudante e);
    void apagar( long id );
    List<Estudante> consultarTodos();
}