/*package VdeVigilancia.Projeto_OS.DAO;

import VdeVigilancia.Projeto_OS.BANCO.Clientes;
import VdeVigilancia.Projeto_OS.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DAO_Cliente {

    public void inserirCliente(Clientes cliente) {
        String sql = "INSERT INTO Clientes (nome, Email, CPF , Telefone)  VALUES(?,?,?,?)";

        try ( Connection conn = VdeVigilancia.Projeto_OS.Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
                 stmt.setString( 1,cliente.getNome());
                 stmt.setString(2, cliente.getEmail());
                 stmt.setString (3, cliente.getCPF());
                 stmt.setString(4, cliente.getTelefone());

                 stmt.executeUpdate();
                 System.out.println("Cliente inserido com sucesso");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

 */
