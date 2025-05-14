package VdeVigilancia.Projeto_OS;

import VdeVigilancia.Projeto_OS.Database.DatabaseManager;
import VdeVigilancia.Projeto_OS.Models.Aparelhos;
import VdeVigilancia.Projeto_OS.Models.OS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ControllerOS {

    @FXML
    private TextField txtCliente, txtServico, txtValorTotal, campoBusca;

    @FXML
    private ChoiceBox menuAparelhos;

    @FXML
    private Button BotaoCadastrar, BotaoEditar, BotaoExcluir;

    @FXML
    private MenuItem BotaoCliente, BotaoOS, BotaoAparelho;

    @FXML
    private RadioButton radioAberta, radioAndamento, radioFechada, radioCancelada;

    @FXML
    private TableView<OS> tabelaOS;

    @FXML
    public TableColumn<OS, Integer> colunaID, colunaCliente;

    @FXML
    public TableColumn<OS, Double> colunaValorTotal;

    @FXML
    public TableColumn<OS, String> colunaServico, colunaStatus, colunaAparelho;

    @FXML
    public Label usuarioLogado, dataAtual;

    @FXML
    public ToggleGroup statusGroup;

    @FXML
    public void initialize() {
        // Configuração das colunas da TreeTable
        colunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colunaAparelho.setCellValueFactory(new PropertyValueFactory<>("aparelho"));
        colunaValorTotal.setCellValueFactory(new PropertyValueFactory<>("valor_total"));
        colunaServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
        colunaStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        usuarioLogado.setText(DatabaseManager.nomeLogged);
        dataAtual.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        atualizarTabela();
    }

    public void atualizarAparelho() {
        try{
            String where = "Cliente = '" + txtCliente.getText() + "'";
            ObservableList<Aparelhos> aparelhos = DatabaseManager.getListAparelhos(where, "");
            for(int i = 0; i < aparelhos.size(); i++) {
                menuAparelhos.getItems().add(aparelhos.get(i).getMarca() + " " + aparelhos.get(i).getVersao());
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarTabela() {
        try{
            ObservableList<OS> OS = DatabaseManager.getListOS("", "");
            tabelaOS.setItems(FXCollections.observableArrayList(OS));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cadastrarOS() {
        try {
            String cliente = txtCliente.getText();
            String aparelho = (String) menuAparelhos.getValue();
            String servico = txtServico.getText();
            String valorTotal = txtValorTotal.getText();
            RadioButton selectedRadioButton = (RadioButton) statusGroup.getSelectedToggle();
            String status = selectedRadioButton.getText();
            String[] values = {cliente, aparelho, servico, valorTotal, status};
            DatabaseManager.insertAll("OS", values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        atualizarTabela();
        limparCampos();
    }

    private void limparCampos() {
        // Limpa os campos após a operação
        txtCliente.clear();
        txtServico.clear();
        txtValorTotal.clear();
        radioAberta.setSelected(false);
        radioAndamento.setSelected(false);
        radioFechada.setSelected(false);
        radioCancelada.setSelected(false);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void editarOS() {
        OS selecionado = tabelaOS.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                DatabaseManager.select("OS", "*",
                        "ID = " + selecionado.getId());
                String cliente = txtCliente.getText();
                String aparelho = (String)menuAparelhos.getValue();
                String servico = txtServico.getText();
                String valorTotal = txtValorTotal.getText();
                RadioButton selectedRadioButton = (RadioButton) statusGroup.getSelectedToggle();
                String status = selectedRadioButton.getText();
                String[] columns = {"Cliente", "Aparelho", "Servico", "ValorTotal", "Status"};
                String[] values = {cliente, aparelho, servico, valorTotal, status};
                DatabaseManager.updateMany("OS", columns, values,
                        "ID = " + selecionado.getId());
            } catch (SQLException e) {
                System.out.println("Erro ao editar: " + e.getMessage());
            }
        }
        atualizarTabela();
        limparCampos();
    }

    @FXML
    private void removerOS(ActionEvent actionEvent) {
        OS selecionado = tabelaOS.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                DatabaseManager.delete("OS", "ID = " + selecionado.getId());
            } catch(SQLException e) {
                System.out.println("Erro ao deletar: " + e.getMessage());
            }
        }
        atualizarTabela();
        limparCampos();
    }

    @FXML
    public void onBuscarClick(ActionEvent event) {
        String filtro = "'%" + campoBusca.getText() + "%'";
        try{
            List<OS> resultados = DatabaseManager.getListOS("CAST(id as CHAR)", filtro);
            tabelaOS.setItems(FXCollections.observableArrayList(resultados));
        } catch(SQLException e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
        }
    }

    @FXML
    public void selecionarOS() {
        OS osSingle = tabelaOS.getSelectionModel().getSelectedItem();
        if (osSingle != null) {
            txtCliente.setText(String.valueOf(osSingle.getCliente()));
            menuAparelhos.getItems().add(osSingle.getAparelho());
            txtServico.setText(osSingle.getServico());
            txtValorTotal.setText(String.valueOf(osSingle.getValor_total()));
        }
    }

    @FXML
    protected void abrirTelaCliente() {
        changeScreen(BotaoCliente, "/TelaClientes.fxml");
    }

    @FXML
    protected void abrirTelaOS() {
        changeScreen(BotaoOS, "/TelaOS.fxml");
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

    @FXML
    protected void abrirTelaAparelho() {
        changeScreen(BotaoAparelho, "/TelaAparelho.fxml");
    }
}