package DAO;

import Model.Amigo;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AmigoDAO {

    public static ArrayList<Amigo> MinhaLista = new ArrayList<Amigo>();
    int qtd_Emprest;

    public Connection getConexao() {

        Connection connection = null;  //instância da conexão

        try {

            // Carregamento do JDBC Driver
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);

            // Configurar a conex�o
            String server = "localhost"; //caminho do MySQL
            String database = "mysql";
            String url = "jdbc:mysql://" + server + ":3306/" + database + "?useTimezone=true&serverTimezone=UTC";
            String user = "root";
            String password = "banco";

            connection = DriverManager.getConnection(url, user, password);

            // Testando..
            if (connection != null) {
                System.out.println("\u001B[32mStatus: Conectado!\u001B[0m");
            } else {
                System.out.println("\u001B[31mStatus: NAO CONECTADO!\u001B[0m");
            }

            return connection;

        } catch (ClassNotFoundException e) {  //Driver nao encontrado
            System.out.println("O driver nao foi encontrado. " + e.getMessage() );
            return null;

        } catch (SQLException e) {
            System.out.println("Nao foi possivel conectar...");
            return null;
        }
    }

    // Retorna todos os amigos
    public ArrayList getMinhaLista() {

        MinhaLista.clear(); // Limpa nosso ArrayList

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_amigos order by `qtd_emprestimos` DESC");
            while (res.next()) {

                int id = res.getInt("id_amigo");
                String nome = res.getString("nome");
                long telefone = res.getLong("telefone");
                int quantEmprest = res.getInt("qtd_emprestimos");

                Amigo objeto = new Amigo(id, nome, telefone, quantEmprest);

                MinhaLista.add(objeto);
            }

            stmt.close();

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }

        return MinhaLista;
    }

    public int getqtd_Emprest(int id){ // calculando qtd de emprestimo de cada amigo

        ResultSet qtdEmprest;

        try {
            Statement stmt = this.getConexao().createStatement();
            qtdEmprest = stmt.executeQuery("SELECT COUNT(`fk_amigo`) AS `total_emprest` FROM `tb_emprestimos` WHERE `fk_amigo` = " + id);
            if (qtdEmprest.next()) {
                qtd_Emprest = qtdEmprest.getInt("total_emprest");
            }
            stmt.close();

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }

        return qtd_Emprest;
    }

    // Cadastra novo amigo
    public boolean InsertAmigoBD(Amigo objeto) throws SQLException {
        String sql = "INSERT INTO tb_amigos(nome,telefone,qtd_emprestimos) VALUES(?,?,?)";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setString(1, objeto.getNome());
            stmt.setLong(2, objeto.getTelefone());
            stmt.setInt(3, getqtd_Emprest(objeto.getId()));

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            if(erro.getSQLState().equals("23000")){
                throw new SQLException("Telefone já está registrado.");
            }
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }

    }

    // Deleta um amigo pelo seu ID
    public boolean DeleteAmigoBD(int id) {
        try {
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM tb_amigos WHERE id_amigo = " + id);
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }
    }

    // Edita as informações de um amigo pelo seu ID
    public boolean UpdateAmigoBD(Amigo objeto) {

        String sql = "UPDATE tb_amigos SET nome = ? ,telefone = ? WHERE id_amigo = ?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setString(1, objeto.getNome());
            stmt.setLong(2, objeto.getTelefone());
            stmt.setInt(3, objeto.getId());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }

    }

    // Atualiza quantidade de empréstimos dos amigos
    public boolean UpdateQtdEmprest(int id){

         String sql = "UPDATE tb_amigos SET qtd_emprestimos = ? WHERE id_amigo = ?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, getqtd_Emprest(id));
            stmt.setInt(2, id);

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }

    }

    public Amigo carregaAmigo(int id) throws SQLException {

        Amigo objeto = new Amigo();
        objeto.setId(id);

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_amigos WHERE id_amigo = " + id );
            res.next();

            objeto.setNome(res.getString("nome"));
            objeto.setTelefone(res.getLong("telefone"));
            objeto.setQuantEmprest(res.getInt("qtd_emprestimos"));

            stmt.close();

        } catch (SQLException erro) {
            if(erro.getSQLState().equals("S1000")){
                throw new SQLException("ID de Amigo inexistente.");
            }
            throw new RuntimeException("Erro de execução no SQL código " + erro.getSQLState());
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }

        return objeto;
    }

}
