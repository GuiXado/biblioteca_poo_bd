package br.com.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LivroDAOImpl implements LivroDAO {
    private static final String DB_JDBC_URI = "jdbc:sqlserver://localhost:1433;databaseName=biblioteca;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456"; 
    private Connection con;

    public LivroDAOImpl() { 
        System.out.println("Livro DAO criado - com database");
        try {        
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Classe carregada...");
            con = DriverManager.getConnection(DB_JDBC_URI, DB_USER, DB_PASS);
            System.out.println("Conexao foi feita com sucesso (livro DAOImpl)");
        } catch (ClassNotFoundException e) { 
            System.out.println("Erro ao carregar a classe (livro DAOImpl)");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar (livro DAOImpl)");
            e.printStackTrace();
        }
    }

    @Override
    public void cadastrar(Livro l) {
        try { 
            String sql = "INSERT INTO livro (titulo, autor, editora, dataPublicacao, quantidade) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, l.getTitulo());
            stm.setString(2, l.getAutor());
            stm.setString(3, l.getEditora());
            stm.setDate(4, java.sql.Date.valueOf(l.getDataPublicacao()));
            stm.setInt(5, l.getQuantidade());
            stm.executeUpdate();
            System.out.println("Comando executado com sucesso (livro DAOImpl)"); 
        } catch (SQLException e) {
            System.out.println("Erro ao conectar (livro DAOImpl)");
            e.printStackTrace();
        }
    }

    @Override
    public List<Livro> consultarPorTitulo(String titulo) {
        List<Livro> lista = new ArrayList<>();
        try { 
            String sql = "SELECT * FROM livro WHERE titulo LIKE ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + titulo + "%" );
            ResultSet rs = stm.executeQuery();
            while (rs.next()) { 
                Livro livro = new Livro(
                    rs.getInt("idLivro"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("editora"),
                    rs.getDate("dataPublicacao").toLocalDate(),
                    rs.getInt("quantidade")
                );
                lista.add(livro);
            }
            System.out.println("Comando executado com sucesso (livro DAOImpl)");   
        } catch (SQLException e) {
            System.out.println("Erro ao conectar (livro DAOImpl)");
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void atualizar(long id, Livro l) {
        try { 
            String sql = "UPDATE livro SET titulo = ?, autor = ?, editora = ?, dataPublicacao = ?, quantidade = ? WHERE idLivro = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, l.getTitulo());
            stm.setString(2, l.getAutor());
            stm.setString(3, l.getEditora());
            stm.setDate(4, java.sql.Date.valueOf(l.getDataPublicacao()));
            stm.setInt(5, l.getQuantidade());
            stm.setLong(6, id);
            stm.executeUpdate();
            System.out.println("Livro atualizado com sucesso (livro DAOImpl)"); 
        } catch (SQLException e) {
            System.out.println("Erro ao conectar (livro DAOImpl)");
            e.printStackTrace();
        }
    }

    @Override
    public void apagar( long id ) { 
        try { 
            String sql = "DELETE FROM livro WHERE idLivro = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setLong(1, id);
            stm.executeUpdate();
            System.out.println("Livro apagado com sucesso (livro DAOImpl)"); 
        } catch (SQLException e) {
            System.out.println("Erro ao conectar (livro DAOImpl)");
            e.printStackTrace();
        }
    }

    @Override
    public List<Livro> consultarTodos() {

        List<Livro> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM livro";
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Livro l = new Livro(
                    rs.getInt("idLivro"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("editora"),
                    rs.getDate("dataPublicacao").toLocalDate(),
                    rs.getInt("quantidade")
                );

                lista.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
