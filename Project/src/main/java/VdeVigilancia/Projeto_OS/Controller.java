 package VdeVigilancia.Projeto_OS;

import VdeVigilancia.Projeto_OS.Dominio.Clientes;
import VdeVigilancia.Projeto_OS.Dominio.JPAUtil;
import VdeVigilancia.Projeto_OS.Dominio.Usuarios;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.bouncycastle.crypto.agreement.jpake.JPAKEUtil;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {

    public Button botaoFiltrar;
    @FXML
    Button BotaoCadastrarCliente, BotaoEntrar, CadastrarUsuario;

    @FXML
    MenuItem BotaoCliente, BotaoUsuario, BotaoOS;

    @FXML
    private TextField NomeUsuario, SenhaUsuario, TelefoneUsuario, EmailUsuario, Codigo;


    @FXML
    protected void CadastrarUsuario() {
        String nome = NomeUsuario.getText();
        String email = EmailUsuario.getText();
        String telefone = TelefoneUsuario.getText();
        String senha = SenhaUsuario.getText();
        String codigoRegisto = Codigo.getText();
    }

    @FXML
    protected void editarUsuario() {
        String nome = NomeUsuario.getText(),
                email = EmailUsuario.getText(),
                telefone = TelefoneUsuario.getText(),
                senha = SenhaUsuario.getText(),
                codigo = Codigo.getText();

    }


    private void limparCampos() {
        NomeUsuario.clear();
        EmailUsuario.clear();
        TelefoneUsuario.clear();
        SenhaUsuario.clear();
        Codigo.clear();
    }

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
    protected void abrirTelaUsuario() {
        changeScreen(BotaoUsuario, "/TelaUsuario.fxml");
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
}