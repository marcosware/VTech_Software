package VdeVigilancia.Projeto_OS.Database;
import VdeVigilancia.Projeto_OS.Models.Aparelhos;
import VdeVigilancia.Projeto_OS.Models.Clientes;
import VdeVigilancia.Projeto_OS.Models.OS;
import VdeVigilancia.Projeto_OS.Models.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    public static String idLogged;
    public static String nomeLogged;

    public static void connectDB() throws SQLException {
        String url = "jdbc:mysql://localhost:3306";
        String user = "root";
        String password = null;
        try (Connection conn = DriverManager.getConnection(url, user, null)) {
            System.out.println("Successfully connected with server!");
            createDB("DB_VTech", conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createDB(String db_name, Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE DATABASE IF NOT EXISTS " + db_name);
        conn = ClassConnection.getConnection();
        System.out.println("Successfully created database " + db_name + "!");
        conn.close();
    }

    public static void defaultTables() throws SQLException {
        Connection conn = ClassConnection.getConnection();

        String[] clientesColumns = {"ID", "Nome", "CPF", "Telefone", "Email"};
        String[] clientesDataTypes = {"INT AUTO_INCREMENT UNIQUE",
                "VARCHAR(255) NOT NULL",
                "CHAR(11) NOT NULL PRIMARY KEY",
                "VARCHAR(15)",
                "VARCHAR(255)"
        };
        DatabaseManager.createTable("Clientes", clientesColumns, clientesDataTypes);

        String[] aparelhosColumns = {"ID", "Marca", "Versao", "Serie", "Cliente"};
        String[] aparelhosDataTypes = {"INT AUTO_INCREMENT PRIMARY KEY",
                "VARCHAR(255)",
                "VARCHAR(255)",
                "VARCHAR(255)",
                "CHAR(11) NOT NULL, FOREIGN KEY (Cliente) REFERENCES CLIENTES (CPF)"
        };
        DatabaseManager.createTable("Aparelhos", aparelhosColumns, aparelhosDataTypes);

        String[] OSColumns = {"ID", "Cliente", "Aparelho", "Serviço", "ValorTotal", "Status"};
        String[] OSDataTypes = {"INT AUTO_INCREMENT PRIMARY KEY",
                "CHAR(11) NOT NULL",
                "VARCHAR(255)",
                "VARCHAR(255)",
                "VARCHAR(255)",
                "VARCHAR(30)",
                "FOREIGN KEY (Cliente) REFERENCES CLIENTES (CPF)",
                "FOREIGN KEY (Aparelho) REFERENCES APARELHOS (Nome)"
        };
        DatabaseManager.createTable("OS", OSColumns, OSDataTypes);

        String[] UsuariosColumns = {"ID", "Nome", "Telefone", "Email", "Senha"};
        String[] UsuariosDataTypes = {"INT AUTO_INCREMENT PRIMARY KEY",
                "VARCHAR(255)",
                "VARCHAR(255)",
                "VARCHAR(255)",
                "VARCHAR(255)"
        };
        DatabaseManager.createTable("Usuarios", UsuariosColumns, UsuariosDataTypes);

        String[] loggedColumns = {"ID", "CPF"};
        String[] loggedDataTypes = {"INT AUTO_INCREMENT PRIMARY KEY", "CHAR(11) NOT NULL, FOREIGN KEY (CPF) REFERENCES CLIENTES (CPF)"};
        DatabaseManager.createTable("Logged", loggedColumns, loggedDataTypes);
        String[] nullCPF = {"0"};
        DatabaseManager.insertAll("Logged", nullCPF);
    }

    public static void createTable(String table, String[] columns, String[] datatypes) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "CREATE TABLE IF NOT EXISTS " + table + " (";
        for(int i = 0; i < columns.length - 1; i++) {
            query += columns[i];
            query += " ";
            query += datatypes[i];
            query += ", ";
        }
        query += columns[columns.length - 1];
        query += " ";
        query += datatypes[columns.length - 1];
        query += ");";
        System.out.println(query);
        stmt.execute(query);
    }

    public static void insertColumns(String table, String[] columns, String[] values) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "INSERT INTO " + table + " (";
        for (int i = 0 ; i < columns.length - 1 ; i++){
            query += columns[i];
            query += ", ";
        }
        query += columns[columns.length - 1];
        query += ")\nVALUES (";

        for(int i = 0; i < values.length - 1; i++){
            query += "'";
            query += values[i];
            query += "', ";
        }
        query += "'";
        query += values[values.length - 1];
        query += "'";
        query += ");";
        System.out.println(query);
        stmt.execute(query);
    }

    public static void insertAll(String table, String [] values) throws SQLException{
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();
        String lastIdQuery = "SELECT COUNT(ID) FROM " + table + ";";
        ResultSet lastId = stmt.executeQuery(lastIdQuery);
        lastId.next();
        int id = Integer.parseInt(lastId.getString(1));
        id += 1;
        System.out.println(id);
        String query = "INSERT INTO " + table + " VALUES (" + id + ", ";
        for (int i = 0; i < values.length - 1; i++ ) {
            query += "'";
            query += values[i];
            query += "', ";
        }
        query += "'";
        query += values[values.length - 1];
        query += "'";
        query += ");";
        System.out.println(query);
        stmt.execute(query);
    }
    public static boolean checkLogin(String email, String senha) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM Usuarios WHERE Email = '" + email + "'";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        boolean result = false;
        if (!rs.getString("Senha").isBlank() && senha.equals(rs.getString("Senha"))){
            result = true;
            idLogged = rs.getString("ID");
            nomeLogged = rs.getString("Nome");

            // query = "UPDATE Logged SET CPF = '" + cpf + "'";
            // stmt.execute(query);
        }
        return result;
    }

    public static ObservableList<Clientes> getListClientes(String where, String like) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM Clientes";
        if(!where.isEmpty()) query += " WHERE " + where;
        if(!like.isEmpty()) query += " LIKE " + like;
        ResultSet rs = stmt.executeQuery(query);
        ObservableList<Clientes> clientes = FXCollections.observableArrayList();
        while(rs.next()) {
            Clientes cliente = new Clientes();
            cliente.setId(rs.getInt("id"));
            cliente.setNome(rs.getString("nome"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setTelefone(rs.getString("telefone"));
            cliente.setEmail(rs.getString("email"));
            clientes.add(cliente);
        }
        return clientes;
    }

    public static ObservableList<Aparelhos> getListAparelhos(String where, String like) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM Aparelhos";
        if(!where.isEmpty()) query += " WHERE " + where;
        if(!like.isEmpty()) query += " LIKE " + like;
        System.out.println(query);
        ResultSet rs = stmt.executeQuery(query);
        ObservableList<Aparelhos> aparelhos = FXCollections.observableArrayList();
        while(rs.next()) {
            Aparelhos aparelho = new Aparelhos();
            aparelho.setId(rs.getInt("id"));
            aparelho.setMarca(rs.getString("marca"));
            aparelho.setVersao(rs.getString("versao"));
            aparelho.setSerie(rs.getString("serie"));
            aparelho.setCliente(rs.getString("cliente"));
            aparelhos.add(aparelho);
        }
        return aparelhos;
    }

    public static ObservableList<OS> getListOS(String where, String like) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM OS";
        if(!where.isEmpty()) query += " WHERE " + where;
        if(!like.isEmpty()) query += " LIKE " + like;
        ResultSet rs = stmt.executeQuery(query);
        ObservableList<OS> OSs = FXCollections.observableArrayList();
        while(rs.next()) {
            OS OS_single = new OS();
            OS_single.setId(rs.getInt("id"));
            OS_single.setCliente(rs.getInt("cliente"));
            OS_single.setAparelho(rs.getString("aparelho"));
            OS_single.setServico(rs.getString("serviço"));
            OS_single.setValor_total(rs.getDouble("valortotal"));
            OS_single.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
            OSs.add(OS_single);
        }
        return OSs;
    }

    public static String select(String table, String column, String where) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "SELECT " + column + " FROM " + table + " WHERE " + where;
        System.out.println(query);
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        return rs.getString(1);
    }

    public static void update(String table, String column, String value, String where) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "UPDATE " + table + " SET " + column + " = " + value + " WHERE " + where;
        System.out.println(query);
        stmt.execute(query);
    }

    public static void updateMany(String table, String[] columns, String[] values, String where) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "UPDATE " + table + " SET ";
        for(int i = 0; i < columns.length - 1; i++) {
            query += columns[i];
            query += " = '";
            if(values[i].isEmpty()) values[i] = "NULL";
            query += values[i];
            query += "', ";
        }
        query += columns[columns.length - 1];
        query += " = '";
        query += values[values.length - 1];
        query += "' WHERE ";
        query += where;
        System.out.println(query);
        stmt.execute(query);
    }

    public static void delete(String table, String where) throws SQLException {
        Connection conn = ClassConnection.getConnection();
        Statement stmt = conn.createStatement();
        String query = "DELETE FROM " + table + " WHERE " + where;
        System.out.println(query);
        stmt.execute(query);
    }
}