package VdeVigilancia.Projeto_OS;

import org.springframework.data.relational.core.sql.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    public  static void connectDB() throws SQLException{
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = "";
        try(Connection conn = DriverManager.getConnection(url, user, password)){
            System.out.println("Conexão bem sucedida!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
