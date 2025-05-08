/*package VdeVigilancia.Projeto_OS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConnection() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/DB_Projeto_OS";
            String user = "root"; // Replace with actual username
            String password = null; // Replace with actual password

            return DriverManager.   getConnection(url, user, "");
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }
}

 */