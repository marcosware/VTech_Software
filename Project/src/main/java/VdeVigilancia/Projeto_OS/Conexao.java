package VdeVigilancia.Projeto_OS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConecction() throws SQLException {

        try {
            String url = "jdbc:mysql://localhost:3306/DB_Projeto_OS";
            String user = "root";
            String password = "";
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
