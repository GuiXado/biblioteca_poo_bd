package br.com.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstudanteDAOImpl implements EstudanteDAO {
    private static final String DB_JDBC_URI = "jdbc:sqlserver://localhost:1433;databaseName=biblioteca;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456"; 
    private Connection con;

    public EstudanteDAOImpl() { 
        System.out.println("Estudante DAO criado - com database");
        try {        
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Classe carregada...");
            con = DriverManager.getConnection(DB_JDBC_URI, DB_USER, DB_PASS);
            System.out.println("Conexao foi feita com sucesso");
        } catch (ClassNotFoundException e) { 
            System.out.println("Erro ao carregar a classe");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar");
            e.printStackTrace();
        }
    }

    @Override
    public void cadastrar(Estudante e) {
        try { 
            String sql = "INSERT INTO estudante (nome, curso, telefone) VALUES (?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, e.getNome());
            stm.setString(2, e.getCurso());
            stm.setString(3, e.getTelefone());
            stm.executeUpdate();
            System.out.println("Comando executado com sucesso"); 
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar");
            ex.printStackTrace();
        }
    }

    @Override
    public List<Estudante> consultarPorNome(String nome) {
        List<Estudante> lista = new ArrayList<>();
        try { 
            String sql = "SELECT * FROM estudante WHERE nome LIKE ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + nome + "%" );
            ResultSet rs = stm.executeQuery();
            while (rs.next()) { 
                Estudante estudante = new Estudante(
                    rs.getInt("idEstudante"),
                    rs.getString("nome"),
                    rs.getString("curso"),
                    rs.getString("telefone")
                );
                lista.add(estudante);
            }
            System.out.println("Comando executado com sucesso");   
        } catch (SQLException e) {
            System.out.println("Erro ao conectar");
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void atualizar(long id, Estudante e) {
        try { 
            String sql = "UPDATE estudante SET nome = ?, curso = ?, telefone = ? WHERE idEstudante = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, e.getNome());
            stm.setString(2, e.getCurso());
            stm.setString(3, e.getTelefone());
            stm.setLong(4, id);
            stm.executeUpdate();
            System.out.println("Estudante atualizado com sucesso"); 
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar");
            ex.printStackTrace();
        }
    }

    @Override
    public void apagar(long id) { 
        try { 
            String sql = "DELETE FROM estudante WHERE idEstudante = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setLong(1, id);
            stm.executeUpdate();
            System.out.println("Estudante apagado com sucesso"); 
        } catch (SQLException e) {
            System.out.println("Erro ao conectar");
            e.printStackTrace();
        }
    }

    @Override
    public List<Estudante> consultarTodos() {

        List<Estudante> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM estudante";
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Estudante e = new Estudante(
                    rs.getInt("idEstudante"),
                    rs.getString("nome"),
                    rs.getString("curso"),
                    rs.getString("telefone")
                );

                lista.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}