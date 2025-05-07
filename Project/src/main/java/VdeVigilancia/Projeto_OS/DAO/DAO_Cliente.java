package VdeVigilancia.Projeto_OS.DAO;

import VdeVigilancia.Projeto_OS.BANCO.Clientes;
import VdeVigilancia.Projeto_OS.Conexao;
import java.sql.Connection;
import java.sql.SQLException;


public class DAO_Cliente {
    public void inserirCliente(Clientes cliente) {
        String sql = "INSERT INTO Clientes (nome, Email, CPF , Telefone)  VALUES(?,?,?,?)";

        try { Connection conn = VdeVigilancia.Projeto_OS.Conexao.getConecction();





        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
