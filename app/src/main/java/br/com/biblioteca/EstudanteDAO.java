package br.com.biblioteca;

import java.util.List;

public interface EstudanteDAO {
    void cadastrar(Estudante e);
    List<Estudante> consultarPorNome( String nome );
    void atualizar(long id, Estudante e);
    void apagar( long id );
    List<Estudante> consultarTodos();
}