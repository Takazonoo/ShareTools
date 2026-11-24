package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

final class Banco {
    private Banco() {
    }

    static Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                System.getenv().getOrDefault("SHARETOOLS_DB_URL",
                        "jdbc:mysql://127.0.0.1:3306/sharetools?connectTimeout=3000&socketTimeout=5000"),
                System.getenv().getOrDefault("SHARETOOLS_DB_USER", "develop"),
                System.getenv().getOrDefault("SHARETOOLS_DB_PASSWORD", "develop"));
    }

    static SQLException erro(SQLException causa) {
        if (causa.getSQLState() != null && causa.getSQLState().startsWith("08")) {
            return new SQLException("Não foi possível conectar ao banco. Verifique se o MySQL está em execução e confira a configuração do ShareTools.", causa);
        }
        return new SQLException("Não foi possível concluir a operação no banco de dados. Verifique a configuração e tente novamente.", causa);
    }
}
