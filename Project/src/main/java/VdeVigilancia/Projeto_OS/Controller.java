package VdeVigilancia.Projeto_OS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;


public class Controller {

    @FXML
    Button BotaoCadastro, BotaoEntrar;

    @FXML
    MenuItem BotaoCliente, BotaoUsuario, BotaoOS;

    @FXML
    protected void abrirTelaCliente () {
        changeScreen(BotaoCliente, "/TelaCliente.fxml");
    }



    @FXML

    protected void abrirTelaCadastro (){
        changeScreen(BotaoCadastro, "/TelaCadastro.fxml");
    }

    @FXML
    protected void abrirTelaInicio () {
        changeScreen(BotaoEntrar, "/TelaInicio.fxml");
    }

    @FXML
    protected void abrirTelaCliente () {
        changeScreen(BotaoCliente, "/TelaCliente.flxml");
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
}
