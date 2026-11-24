package DAO;

import Model.Amigo;
import Model.Emprestimo;
import Model.Ferramenta;
import java.sql.*;
import java.util.ArrayList;

public class EmprestimoDAO {
    public ArrayList<Emprestimo> getMinhaLista() throws SQLException {
        String sql = "SELECT e.id_emprestimo, e.data_emprestimo, e.data_devolucao, "
                + "a.id_amigo, a.nome AS amigo, a.telefone, "
                + "f.id_ferramenta, f.nome AS ferramenta, f.marca, f.custo_aquisicao "
                + "FROM tb_emprestimos e JOIN tb_amigos a ON a.id_amigo = e.fk_amigo "
                + "JOIN tb_ferramentas f ON f.id_ferramenta = e.fk_ferramenta ORDER BY e.id_emprestimo DESC";
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();
        try (Connection conexao = Banco.conectar(); PreparedStatement consulta = conexao.prepareStatement(sql);
                ResultSet dados = consulta.executeQuery()) {
            while (dados.next()) {
                Amigo amigo = new Amigo(dados.getInt("id_amigo"), dados.getString("amigo"), dados.getLong("telefone"));
                Ferramenta ferramenta = new Ferramenta(dados.getInt("id_ferramenta"), dados.getString("ferramenta"),
                        dados.getString("marca"), dados.getDouble("custo_aquisicao"));
                emprestimos.add(new Emprestimo(dados.getInt("id_emprestimo"), amigo, ferramenta,
                        dados.getDate("data_emprestimo"), dados.getDate("data_devolucao")));
            }
            return emprestimos;
        } catch (SQLException erro) {
            throw Banco.erro(erro);
        }
    }

    public boolean InsertEmprestimoBD(Emprestimo emprestimo) throws SQLException {
        return salvar(emprestimo, "INSERT INTO tb_emprestimos "
                + "(fk_amigo, fk_ferramenta, data_emprestimo, data_devolucao, status) VALUES (?, ?, ?, ?, ?)");
    }

    public boolean UpdateEmprestimoBD(Emprestimo emprestimo) throws SQLException {
        return salvar(emprestimo, "UPDATE tb_emprestimos SET fk_amigo = ?, fk_ferramenta = ?, "
                + "data_emprestimo = ?, data_devolucao = ?, status = ? WHERE id_emprestimo = ?");
    }

    private boolean salvar(Emprestimo emprestimo, String sql) throws SQLException {
        Date inicio = emprestimo.getDataEmprestimo();
        Date fim = emprestimo.getDataDevolucao();
        if (inicio == null || (fim != null && fim.before(inicio))) {
            throw new SQLException("Informe uma data de empréstimo válida e uma devolução na mesma data ou depois.");
        }
        try (Connection conexao = Banco.conectar()) {
            conexao.setAutoCommit(false);
            try {
                bloquearFerramenta(conexao, emprestimo.getFerramenta().getId());
                verificarDisponibilidade(conexao, emprestimo);
                try (PreparedStatement comando = conexao.prepareStatement(sql)) {
                    comando.setInt(1, emprestimo.getAmigo().getId());
                    comando.setInt(2, emprestimo.getFerramenta().getId());
                    comando.setDate(3, inicio);
                    comando.setDate(4, fim);
                    comando.setBoolean(5, fim != null);
                    if (emprestimo.getId() > 0) {
                        comando.setInt(6, emprestimo.getId());
                    }
                    boolean salvo = comando.executeUpdate() == 1;
                    conexao.commit();
                    return salvo;
                }
            } catch (SQLException | RuntimeException erro) {
                try {
                    conexao.rollback();
                } catch (SQLException falha) {
                    erro.addSuppressed(falha);
                }
                throw erro;
            }
        } catch (SQLException erro) {
            if ("45000".equals(erro.getSQLState())) {
                throw erro;
            }
            if (erro.getErrorCode() == 1452) {
                throw new SQLException("O amigo ou a ferramenta selecionada já não existe.", erro);
            }
            throw Banco.erro(erro);
        }
    }

    private void bloquearFerramenta(Connection conexao, int id) throws SQLException {
        try (PreparedStatement consulta = conexao.prepareStatement(
                "SELECT id_ferramenta FROM tb_ferramentas WHERE id_ferramenta = ? FOR UPDATE")) {
            consulta.setInt(1, id);
            try (ResultSet dados = consulta.executeQuery()) {
                if (!dados.next()) {
                    throw new SQLException("ID de ferramenta inexistente.", "45000");
                }
            }
        }
    }

    private void verificarDisponibilidade(Connection conexao, Emprestimo emprestimo) throws SQLException {
        String sql = "SELECT id_emprestimo FROM tb_emprestimos WHERE fk_ferramenta = ? AND id_emprestimo <> ? "
                + "AND (data_devolucao IS NULL OR data_devolucao >= ?) "
                + "AND (? IS NULL OR data_emprestimo <= ?) LIMIT 1 FOR UPDATE";
        try (PreparedStatement consulta = conexao.prepareStatement(sql)) {
            consulta.setInt(1, emprestimo.getFerramenta().getId());
            consulta.setInt(2, emprestimo.getId());
            consulta.setDate(3, emprestimo.getDataEmprestimo());
            consulta.setDate(4, emprestimo.getDataDevolucao());
            consulta.setDate(5, emprestimo.getDataDevolucao());
            try (ResultSet dados = consulta.executeQuery()) {
                if (dados.next()) {
                    throw new SQLException("A ferramenta já está emprestada durante o período informado.", "45000");
                }
            }
        }
    }

    public boolean DeleteEmprestimoBD(int id) throws SQLException {
        try (Connection conexao = Banco.conectar(); PreparedStatement comando = conexao.prepareStatement(
                "DELETE FROM tb_emprestimos WHERE id_emprestimo = ?")) {
            comando.setInt(1, id);
            return comando.executeUpdate() == 1;
        } catch (SQLException erro) {
            throw Banco.erro(erro);
        }
    }
}
