package DAO;

import Model.Ferramenta;
import java.sql.*;
import java.util.ArrayList;

public class FerramentaDAO {
    public ArrayList<Ferramenta> getMinhaLista() throws SQLException {
        String sql = "SELECT id_ferramenta, nome, marca, custo_aquisicao FROM tb_ferramentas ORDER BY id_ferramenta";
        ArrayList<Ferramenta> ferramentas = new ArrayList<>();
        try (Connection conexao = Banco.conectar(); PreparedStatement consulta = conexao.prepareStatement(sql);
                ResultSet dados = consulta.executeQuery()) {
            while (dados.next()) {
                ferramentas.add(new Ferramenta(dados.getInt("id_ferramenta"), dados.getString("nome"),
                        dados.getString("marca"), dados.getDouble("custo_aquisicao")));
            }
            return ferramentas;
        } catch (SQLException erro) {
            throw Banco.erro(erro);
        }
    }

    public boolean InsertFerramentaBD(Ferramenta ferramenta) throws SQLException {
        return salvar(ferramenta, "INSERT INTO tb_ferramentas (nome, marca, custo_aquisicao) VALUES (?, ?, ?)");
    }

    public boolean UpdateFerramentaBD(Ferramenta ferramenta) throws SQLException {
        return salvar(ferramenta, "UPDATE tb_ferramentas SET nome = ?, marca = ?, custo_aquisicao = ? WHERE id_ferramenta = ?");
    }

    private boolean salvar(Ferramenta ferramenta, String sql) throws SQLException {
        try (Connection conexao = Banco.conectar(); PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, ferramenta.getNome());
            comando.setString(2, ferramenta.getMarca());
            comando.setBigDecimal(3, java.math.BigDecimal.valueOf(ferramenta.getCustoAquisicao()));
            if (ferramenta.getId() > 0) {
                comando.setInt(4, ferramenta.getId());
            }
            return comando.executeUpdate() == 1;
        } catch (SQLException erro) {
            throw Banco.erro(erro);
        }
    }

    public boolean DeleteFerramentaBD(int id) throws SQLException {
        String sql = "DELETE FROM tb_ferramentas WHERE id_ferramenta = ? AND NOT EXISTS "
                + "(SELECT 1 FROM tb_emprestimos WHERE fk_ferramenta = ?)";
        try (Connection conexao = Banco.conectar(); PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);
            comando.setInt(2, id);
            return comando.executeUpdate() == 1;
        } catch (SQLException erro) {
            if (erro.getErrorCode() == 1451) {
                return false;
            }
            throw Banco.erro(erro);
        }
    }
}
