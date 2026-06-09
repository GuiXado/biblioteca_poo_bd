package br.com.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class EmprestimoDAOImpl implements EmprestimoDAO {
    private static final String DB_JDBC_URI = "jdbc:sqlserver://localhost:1433;databaseName=biblioteca;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "123456"; 
    private Connection con;

    public EmprestimoDAOImpl() { 
        System.out.println("Emprestimo DAO criado - com database");
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
    public void cadastrar(Emprestimo e) {
        System.out.println("ENTROU NO CADASTRAR EMPRESTIMO");
        try { 
            String sql = "INSERT INTO emprestimo (idEstudante, idLivro, dataEmprestimo, dataPrevista, dataDevolucao) VALUES (?, ?, GETDATE(), ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, e.getEstudante().getIdEstudante());
            stm.setInt(2, e.getLivro().getIdLivro());
            //stm.setDate(3, java.sql.Date.valueOf(e.getDataEmprestimo()));
            stm.setDate(3, java.sql.Date.valueOf(e.getDataPrevista()));
            if (e.getDataDevolucao() != null) {
                stm.setDate(4, java.sql.Date.valueOf(e.getDataDevolucao()));
            } else {
                stm.setNull(4, java.sql.Types.DATE);
            }
            stm.executeUpdate();
            System.out.println("Comando executado com sucesso"); 
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar");
            ex.printStackTrace();
        }
    }
    @Override
    public List<Emprestimo> consultarPorEstudante(String estudante) {
        List<Emprestimo> lista = new ArrayList<>();
        try {
            String sql = """
                    SELECT e.idEmprestimo,
                           e.dataEmprestimo,
                       e.dataPrevista,
                       e.dataDevolucao,
                           es.idEstudante,
                           es.nome,
                           es.curso,
                           es.telefone,
                           l.idLivro,
                           l.titulo,
                           l.autor,
                           l.editora,
                           l.dataPublicacao,
                           l.quantidade
                    FROM emprestimo e
                    INNER JOIN estudante es ON es.idEstudante = e.idEstudante
                    INNER JOIN livro l ON l.idLivro = e.idLivro
                    WHERE es.nome LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + estudante + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Estudante aluno = new Estudante(
                        rs.getInt("idEstudante"),
                        rs.getString("nome"),
                        rs.getString("curso"),
                        rs.getString("telefone")
                );

                Livro livro = new Livro(
                        rs.getInt("idLivro"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("editora"),
                        rs.getDate("dataPublicacao").toLocalDate(),
                        rs.getInt("quantidade")
                );

                LocalDate dataEmprestimo = rs.getDate("dataEmprestimo").toLocalDate();
                LocalDate dataPrevista = rs.getDate("dataPrevista").toLocalDate();
                LocalDate dataDevolucao = rs.getDate("dataDevolucao") != null
                    ? rs.getDate("dataDevolucao").toLocalDate()
                    : null;

                Emprestimo emprestimo = new Emprestimo(
                        rs.getInt("idEmprestimo"),
                        livro,
                        aluno,
                        dataEmprestimo,
                        dataPrevista,
                        dataDevolucao
                );
                lista.add(emprestimo);
            }
            System.out.println("Comando executado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar");
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void atualizar(long id, Emprestimo e) {
    try {

        String sql = "UPDATE emprestimo SET idEstudante = ?, idLivro = ?, dataPrevista = ?, dataDevolucao = ? WHERE idEmprestimo = ?";

        PreparedStatement stm = con.prepareStatement(sql);

        stm.setInt(1, e.getEstudante().getIdEstudante());
        stm.setInt(2, e.getLivro().getIdLivro());

        stm.setDate(3, java.sql.Date.valueOf(e.getDataPrevista()));

        if (e.getDataDevolucao() != null) {
            stm.setDate(4,java.sql.Date.valueOf(e.getDataDevolucao())
            );
        } else {
            stm.setNull(4, java.sql.Types.DATE);
        }

        stm.setLong(5, id);

        stm.executeUpdate();

        System.out.println("Emprestimo atualizado com sucesso");

    } catch (SQLException ex) {
        System.out.println("Erro ao conectar");
        ex.printStackTrace();
    }
}

    @Override
    public void apagar(long id) {
        try {
            String sql = "DELETE FROM emprestimo WHERE idEmprestimo = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setLong(1, id);
            stm.executeUpdate();
            System.out.println("Emprestimo apagado com sucesso");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar");
            e.printStackTrace();
        }
    }
}
