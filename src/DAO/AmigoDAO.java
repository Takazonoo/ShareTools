package DAO;

import Model.Amigo;
import java.sql.*;
import java.util.ArrayList;

public class AmigoDAO {
    public ArrayList<Amigo> getMinhaLista() throws SQLException {
        String sql = "SELECT a.id_amigo, a.nome, a.telefone, COUNT(e.id_emprestimo) AS total "
                + "FROM tb_amigos a LEFT JOIN tb_emprestimos e ON e.fk_amigo = a.id_amigo "
                + "GROUP BY a.id_amigo, a.nome, a.telefone ORDER BY total DESC, a.nome, a.id_amigo";
        ArrayList<Amigo> amigos = new ArrayList<>();
        try (Connection conexao = Banco.conectar(); PreparedStatement consulta = conexao.prepareStatement(sql);
                ResultSet dados = consulta.executeQuery()) {
            while (dados.next()) {
                amigos.add(new Amigo(dados.getInt("id_amigo"), dados.getString("nome"),
                        dados.getLong("telefone"), dados.getInt("total")));
            }
            return amigos;
        } catch (SQLException erro) {
            throw Banco.erro(erro);
        }
    }

    public boolean InsertAmigoBD(Amigo amigo) throws SQLException {
        return salvar(amigo, "INSERT INTO tb_amigos (nome, telefone, qtd_emprestimos) VALUES (?, ?, 0)");
    }

    public boolean UpdateAmigoBD(Amigo amigo) throws SQLException {
        return salvar(amigo, "UPDATE tb_amigos SET nome = ?, telefone = ? WHERE id_amigo = ?");
    }

    private boolean salvar(Amigo amigo, String sql) throws SQLException {
        try (Connection conexao = Banco.conectar(); PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, amigo.getNome());
            comando.setLong(2, amigo.getTelefone());
            if (amigo.getId() > 0) {
                comando.setInt(3, amigo.getId());
            }
            return comando.executeUpdate() == 1;
        } catch (SQLException erro) {
            if (erro.getErrorCode() == 1062) {
                throw new SQLException("Esse telefone já está cadastrado.", erro);
            }
            throw Banco.erro(erro);
        }
    }

    public boolean DeleteAmigoBD(int id) throws SQLException {
        String sql = "DELETE FROM tb_amigos WHERE id_amigo = ? AND NOT EXISTS "
                + "(SELECT 1 FROM tb_emprestimos WHERE fk_amigo = ?)";
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
