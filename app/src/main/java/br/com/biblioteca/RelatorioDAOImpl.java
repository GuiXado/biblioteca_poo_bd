package br.com.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RelatorioDAOImpl implements RelatorioDAO {
    private static final String DB_JDBC_URI = "jdbc:sqlserver://localhost:1433;databaseName=biblioteca;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456"; 
    private Connection con;

    public RelatorioDAOImpl() {
        System.out.println("Relatorio DAO criado - com database");
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
    public ObservableList<String> emprestimosAtivos() {

        ObservableList<String> lista = FXCollections.observableArrayList();

        String sql = """
            SELECT e.idEmprestimo, l.titulo, s.nome
            FROM Emprestimo e
            JOIN Livro l ON e.idLivro = l.idLivro
            JOIN Estudante s ON e.idEstudante = s.idEstudante
            WHERE e.dataDevolucao IS NULL
        """;
            //AND e.dataPrevista >= GETDATE()

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(
                    "ID: " + rs.getInt("idEmprestimo")
                    + " | Livro: " + rs.getString("titulo")
                    + " | Estudante: " + rs.getString("nome")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public ObservableList<String> emprestimosAtrasados() {

        ObservableList<String> lista = FXCollections.observableArrayList();

        String sql = """
            SELECT 
                e.idEmprestimo, 
                l.titulo, 
                s.nome, 
                CONVERT(VARCHAR, e.dataPrevista, 103) as dataPrevista
            FROM Emprestimo e
            JOIN Livro l ON e.idLivro = l.idLivro
            JOIN Estudante s ON e.idEstudante = s.idEstudante
            WHERE e.dataDevolucao IS NULL
              AND e.dataPrevista < GETDATE()
        """;

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(
                    "Livro: " + rs.getString("titulo")
                    + " | Estudante: " + rs.getString("nome")
                    + " | Previsto: " + rs.getString("dataPrevista")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public ObservableList<String> consultaHistorico() {

        ObservableList<String> lista = FXCollections.observableArrayList();

        String sql = """
            SELECT 
                e.idEmprestimo,
                l.titulo,
                s.nome,
                CONVERT(VARCHAR, e.dataEmprestimo, 103) as dataEmprestimo,
                CONVERT(VARCHAR, e.dataDevolucao, 103) as dataDevolucao
            FROM emprestimo e
            JOIN livro l ON l.idLivro = e.idLivro
            JOIN estudante s ON s.idEstudante = e.idEstudante
            WHERE e.dataDevolucao IS NOT NULL
            ORDER BY e.dataDevolucao DESC
        """;

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(
                    "ID: " + rs.getInt("idEmprestimo")
                    + " | Livro: " + rs.getString("titulo")
                    + " | Estudante: " + rs.getString("nome")
                    + " | Empréstimo: " + rs.getString("dataEmprestimo")
                    + " | Devolução: " + rs.getString("dataDevolucao")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public ObservableList<String> estudanteQueMaisPegaLivro() {

        ObservableList<String> lista = FXCollections.observableArrayList();

        String sql = """
            SELECT 
                s.idEstudante,
                s.nome,
                COUNT(*) AS total_emprestimos
            FROM emprestimo e
            JOIN estudante s ON s.idEstudante = e.idEstudante
            GROUP BY s.idEstudante, s.nome
            ORDER BY total_emprestimos DESC
        """;

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(
                    "ID: " + rs.getInt("idEstudante")
                    + " | Estudante: " + rs.getString("nome")
                    + " | Total de empréstimos: " + rs.getInt("total_emprestimos")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public ObservableList<String> livrosEmprestados() {

        ObservableList<String> lista = FXCollections.observableArrayList();

        String sql = """
            SELECT l.titulo, COUNT(e.idEmprestimo) as total
            FROM Livro l
            JOIN Emprestimo e ON l.idLivro = e.idLivro
            GROUP BY l.titulo
            ORDER BY total DESC;
        """;

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add("Livro: " + rs.getString("titulo") + " | Emprestimos: " + rs.getInt("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public ObservableList<String> livrosSemEstoque() {

        ObservableList<String> lista = FXCollections.observableArrayList();

        String sql = """
            SELECT titulo, autor
            FROM Livro
            WHERE quantidade = 0
        """;

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {lista.add("Livro: " + rs.getString("titulo") + " | Autor: " + rs.getString("autor"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}