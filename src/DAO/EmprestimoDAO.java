package DAO;

import Model.Amigo;
import Model.Emprestimo;
import Model.Ferramenta;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmprestimoDAO {
    AmigoDAO amigoDAO = new AmigoDAO(); // dao para transformar o id em objeto
    FerramentaDAO ferramentaDAO = new FerramentaDAO();
    public static ArrayList<Emprestimo> MinhaLista = new ArrayList<>();
    
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
            System.out.println("Nao foi possivel conectar..." + e.getSQLState());
            return null;
        }
    }
        
    public ArrayList getMinhaLista() {
        
        MinhaLista.clear();
        
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_emprestimos");
            
            while (res.next()) {
                
                int id = res.getInt("id_emprestimo");
                Amigo amigo = amigoDAO.carregaAmigo(res.getInt("fk_amigo"));
                Ferramenta ferramenta = ferramentaDAO.carregaFerramenta(res.getInt("fk_ferramenta"));
                java.sql.Date dataEmprestimo = res.getDate("data_emprestimo");
                java.sql.Date dataDevolucao = res.getDate("data_devolucao");
                
                Emprestimo objeto = new Emprestimo(id, amigo, ferramenta, dataEmprestimo, dataDevolucao);
                
                MinhaLista.add(objeto);
            }
            
            stmt.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }
        
        return MinhaLista;
    }
    
    // Cadastra novo empréstimo
    public boolean InsertEmprestimoBD(Emprestimo objeto) throws SQLException {
        String sql = "INSERT INTO tb_emprestimos(fk_amigo,fk_ferramenta,data_emprestimo,data_devolucao,status) VALUES(?,?,?,?,?)";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);

            stmt.setInt(1, objeto.getAmigo().getId());
            stmt.setInt(2, objeto.getFerramenta().getId());
            stmt.setDate(3, objeto.getDataEmprestimo());
            stmt.setDate(4, objeto.getDataDevolucao());
            stmt.setBoolean(5, objeto.isStatus());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }

    }
    
    public boolean DeleteEmprestimoBD(int id) {
        try {
            Statement stmt = this.getConexao().createStatement();
            stmt.executeUpdate("DELETE FROM tb_emprestimos WHERE id_emprestimo = " + id);
            stmt.close();            
            
            return true;
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }
    }
    
    public boolean UpdateEmprestimoBD(Emprestimo objeto) throws SQLException {
        String sql = "UPDATE `tb_emprestimos` SET `fk_amigo` = ? ,`fk_ferramenta` = ? ,`data_emprestimo` = ?, `data_devolucao` = ? WHERE `id_emprestimo` = ?";

        try {
            PreparedStatement stmt = this.getConexao().prepareStatement(sql);
            
            stmt.setInt(1, objeto.getAmigo().getId());
            stmt.setInt(2, objeto.getFerramenta().getId());
            stmt.setDate(3, objeto.getDataEmprestimo());
            stmt.setDate(4, objeto.getDataDevolucao());
            stmt.setInt(5, objeto.getId());

            stmt.execute();
            stmt.close();

            return true;

        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }

    }
    
    public Emprestimo carregaEmprestimo(int id) throws SQLException{
        Emprestimo objeto = new Emprestimo();
        objeto.setId(id);
        
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet res = stmt.executeQuery("SELECT * FROM tb_emprestimos WHERE id_emprestimo = " + id );
            res.next();

            objeto.setAmigo(amigoDAO.carregaAmigo(res.getInt("fk_amigo")));
            objeto.setFerramenta(ferramentaDAO.carregaFerramenta(res.getInt("fk_ferramenta")));
            objeto.setDataEmprestimo(res.getDate("data_emprestimo"));
            objeto.setDataDevolucao(res.getDate("data_devolucao"));
            objeto.setStatus(res.getBoolean("status"));

            stmt.close();            
            
        } catch (SQLException erro) {
            if(erro.getSQLState().equals("S1000")){
                throw new SQLException("ID de Emprestimo inexistente.");
            }
            throw new RuntimeException("Erro de execução no SQL código " + erro.getSQLState());
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
        }
        
        return objeto;
    }
    
    public int amigoPendente(int idAmigo){ // verifica se o amigo não devolveu uma ferramenta
        int idFerramenta = 0;
                
        try {
            Statement stmt = this.getConexao().createStatement();
            ResultSet pdt = stmt.executeQuery("SELECT fk_ferramenta FROM `tb_emprestimos` WHERE `fk_amigo` = " + idAmigo + " and `status` = false;");
            
            if(pdt.next()){
                idFerramenta = pdt.getInt("fk_ferramenta");
            }
            
            stmt.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try { this.getConexao().close(); } catch (Exception e) { }
            try { this.getConexao().close(); } catch (Exception e) { }
        }
        
        return idFerramenta;
    }

}


