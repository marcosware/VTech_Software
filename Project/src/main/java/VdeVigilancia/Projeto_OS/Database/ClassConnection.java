package VdeVigilancia.Projeto_OS.Database;

import java.sql.*;

public class ClassConnection {
    public static Connection getConnection() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/DB_VTech";
            String user = "root";
            String password = null;
            return DriverManager.getConnection(url, user, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
