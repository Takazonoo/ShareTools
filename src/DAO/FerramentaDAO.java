package DAO;

import Model.Ferramenta;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FerramentaDAO {
    public static ArrayList<Ferramenta> MinhaLista = new ArrayList<>();
    double contadorCusto;
    
    public Connection getConexao() {

        Connection connection = null;  //instância da conexão

        try {

            // Carregamento do JDBC Driver
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);

            // Configurar a conexão
            String server = "localhost";
            String database = "mysql";
            String url = "jdbc:mysql://" + server + ":3306/" + database + "?useTimezone=true&serverTimezone=UTC";
            String user = "develop";
            String password = "develop";

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

    public ArrayList getMinhaLista() {
        
        MinhaLista.clear();

        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramentas");
            while (res.next()) {

                int id = res.getInt("id_ferramenta");
                String nome = res.getString("nome");
                String marca = res.getString("marca");
                double custoAquisicao = res.getDouble("custo_aquisicao");

                Ferramenta objeto = new Ferramenta(id, nome, marca, custoAquisicao);

                MinhaLista.add(objeto);
            }

            stmt.close();

        } catch (SQLException ex) {
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }

        return MinhaLista;
    }

    public boolean InsertFerramentaBD(Ferramenta objeto) throws SQLException{
        String sql = "INSERT INTO tb_ferramentas(nome,marca,custo_aquisicao) VALUES(?,?,?)";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getMarca());
            stmt.setDouble(3, objeto.getCustoAquisicao());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }

    }

    public boolean DeleteFerramentaBD(int id) {
        try {
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM tb_ferramentas WHERE id_ferramenta = " + id);
            stmt.close();            
            
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }
        
        return true;
    }

    // Edita uma ferramenta específica
    public boolean UpdateFerramentaBD(Ferramenta objeto) {

        String sql = "UPDATE tb_ferramentas SET nome = ? ,marca = ? ,custo_aquisicao = ? WHERE id_ferramenta = ?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);
            
            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getMarca());
            stmt.setDouble(3, objeto.getCustoAquisicao());
            stmt.setInt(4, objeto.getId());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }

    }

    public Ferramenta carregaFerramenta(int id) throws SQLException {
        
        Ferramenta objeto = new Ferramenta();
        
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_ferramentas WHERE id_ferramenta = " + id);
            res.next();

            objeto.setId(id);
            objeto.setNome(res.getString("nome"));
            objeto.setMarca(res.getString("marca"));
            objeto.setCustoAquisicao(res.getDouble("custo_aquisicao"));

            stmt.close();            
        } catch (SQLException erro) {
            if(erro.getSQLState().equals("S1000")){
                throw new SQLException("ID de Ferramenta inexistente.");
            }
            throw new RuntimeException("Erro de execução no SQL código " + erro.getSQLState());
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }
        
        return objeto;
    }
    
    public double getContadorCusto() {
        ResultSet resCusto;
        
        try {
            Statement stmt = this.getConexao().createStatement();
            resCusto = stmt.executeQuery("SELECT SUM(custo_aquisicao) FROM tb_ferramentas");
            if (resCusto.next()) {
                contadorCusto = resCusto.getDouble("SUM(custo_aquisicao)");  
            }
            stmt.close();            
            
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }
        
        return contadorCusto;
    }
    
    public boolean FerramentaExistenteDB(String nome) throws SQLException {
        
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nome FROM tb_ferramentas WHERE nome = '" + nome + "'");

            return rs.next();
            
        } catch (SQLException erro) {
            throw new SQLException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }
        
    }
    
    public int amigoComFerramenta(int id) { // disponibilidade da ferramenta
        int idEmprestimo = 0;
        
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_emprestimo FROM tb_emprestimos WHERE fk_ferramenta = " + id + " AND status = false");
            
            if(rs.next()){
                idEmprestimo = rs.getInt("id_emprestimo"); // retorna id do amigo com ferramenta pendente
            }
            
            stmt.close();
            
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }
        
        return idEmprestimo;
    }
}
