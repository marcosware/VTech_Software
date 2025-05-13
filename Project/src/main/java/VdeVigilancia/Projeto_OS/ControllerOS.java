package VdeVigilancia.Projeto_OS;

import VdeVigilancia.Projeto_OS.Dominio.Usuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static VdeVigilancia.Projeto_OS.Application.Programa.em;

public class ControllerOS {
    @FXML
    Button MAIS, Editar, deletarOS;

    @FXML
    TextField EquipamentosOS,
    DefeitoOS,
    ServicoOS,
    ValorTotal;


    public void limparCamposOS() {
       EquipamentosOS.clear();
       DefeitoOS.clear();
       ServicoOS.clear();
       ValorTotal.clear();
    }

}