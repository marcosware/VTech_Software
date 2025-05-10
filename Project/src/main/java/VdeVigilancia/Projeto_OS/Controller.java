package VdeVigilancia.Projeto_OS;
import VdeVigilancia.Projeto_OS.Dominio.Clientes;
import VdeVigilancia.Projeto_OS.Dominio.Usuarios;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.persistence.EntityManager;

import static VdeVigilancia.Projeto_OS.ProjetoOsApplication.em;


public class Controller {

    @FXML
    Button BotaoCadastrarCliente, BotaoEntrar, CadastrarUsuario;

    @FXML
    MenuItem BotaoCliente, BotaoUsuario, BotaoOS;

    @FXML
    protected void CadastrarCliente () {
        String nome = NomeCliente.getText();
        String email = EmailCliente.getText();
        String cpf = CPFCliente.getText();
        String telefone = TelefoneCliente.getText();
        System.out.println(telefone.length());

        Clientes.inserirCliente ( em , nome , cpf , telefone );


    }
    @FXML private TextField NomeCliente;
    @FXML private TextField EmailCliente;
    @FXML private TextField CPFCliente;
    @FXML private TextField TelefoneCliente;

    @FXML
    protected void CadastrarUsuario () {
        String nome = NomeUsuario.getText();
        String email  = EmailUsuario.getText();
        String telefone = TelefoneUsuario.getText();
        String senha = SenhaUsuario.getText();
        String codigoRegisto = Codigo.getText();
         Usuarios.inserirUsuarios(em,  nome,  email, telefone, senha, codigoRegisto);

    }
    @FXML private TextField NomeUsuario;
    @FXML private TextField SenhaUsuario;
    @FXML private TextField TelefoneUsuario;
    @FXML private TextField EmailUsuario;
    @FXML private TextField Codigo;


    @FXML
    protected void abrirTelaCliente () {
        changeScreen(BotaoCliente, "/TelaClientes.fxml");
    }

    @FXML
    protected void abrirTelaInicio () {
        changeScreen(BotaoEntrar, "/TelaInicio.fxml");
    }

    @FXML
    protected void abrirTelaOS () {changeScreen(BotaoOS, "/TelaOS.fxml"); }

    @FXML
    protected void abrirTelaUsuario () {changeScreen(BotaoUsuario, "/TelaUsuario.fxml"); }

    @FXML
    protected void changeScreen(Button currentButton, String screen) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(screen));
            AnchorPane root = loader.load();
            Scene scene = currentButton.getScene();
            scene.setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @FXML
        protected void changeScreen(MenuItem currentMenu, String screen) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(screen));
                AnchorPane root = loader.load();
                Scene scene = currentMenu.getParentPopup().getOwnerWindow().getScene();
                scene.setRoot(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
