package VdeVigilancia.Projeto_OS;
import VdeVigilancia.Projeto_OS.BANCO.Clientes;
import VdeVigilancia.Projeto_OS.DAO.DAO_Cliente;
import VdeVigilancia.Projeto_OS.Dominio.Clientes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.persistence.EntityManager;


public class Controller {
    @FXML
    Button BotaoCadastrarCliente, BotaoEntrar;

    @FXML
    MenuItem BotaoCliente, BotaoUsuario, BotaoOS;

    @FXML
    protected void CadastrarCliente () {


    }
    @FXML private TextField NomeCliente;
    @FXML private TextField EmailCliente;
    @FXML private TextField CPFCliente;
    @FXML private TextField TelefoneCliente;


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
