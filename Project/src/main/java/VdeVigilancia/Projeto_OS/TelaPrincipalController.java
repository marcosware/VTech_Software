package VdeVigilancia.Projeto_OS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TelaPrincipalController {
    @FXML

    Button abrirTelaInicio;

    @FXML
    void setAbrirTelaInicio (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaInicio.fxml"));
            StackPane root = loader.load();
            Scene scene = abrirTelaInicio.getScene();
            Stage currentStage = (Stage) abrirTelaInicio.getScene().getWindow();
            currentStage.setTitle("Tela Perfil");
            scene.setRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}