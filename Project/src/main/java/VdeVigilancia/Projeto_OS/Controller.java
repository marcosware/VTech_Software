 package VdeVigilancia.Projeto_OS;

import VdeVigilancia.Projeto_OS.Database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

 public class Controller {

    public Button botaoFiltrar;
    @FXML
    Button BotaoCadastrarCliente, BotaoEntrar, CadastrarUsuario;

    @FXML
    MenuItem BotaoCliente, BotaoUsuario, BotaoOS, BotaoAparelho;

    @FXML
    TextField fieldLoginEmail, fieldLoginSenha;

    @FXML
    protected void abrirTelaCliente() {
        changeScreen(BotaoCliente, "/TelaClientes.fxml");
    }

    @FXML
    protected void abrirTelaInicio() {
        changeScreen(BotaoEntrar, "/TelaInicio.fxml");
    }

    @FXML
    protected void abrirTelaOS() {
        changeScreen(BotaoOS, "/TelaOS.fxml");
    }

    @FXML
    protected void abrirTelaAparelho() {
         changeScreen(BotaoAparelho, "/TelaAparelho.fxml");
     }

    @FXML
    protected void abrirTelaUsuario() {
        changeScreen(BotaoUsuario, "/TelaUsuario.fxml");
    }

    @FXML
    protected void onEntrarButtonClicked() {
        String email = fieldLoginEmail.getText();
        String senha = fieldLoginSenha.getText();
        try {
            if (email.isEmpty() || senha.isEmpty()) {
                System.out.println("empty");
            }
            else if(DatabaseManager.checkLogin(email, senha)) {
                abrirTelaInicio();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


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

    public void idCliente(ActionEvent actionEvent) {

    }

    public void adicionarItem(ActionEvent actionEvent) {
    }
}