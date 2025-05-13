package VdeVigilancia.Projeto_OS;

import VdeVigilancia.Projeto_OS.Database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import VdeVigilancia.Projeto_OS.Models.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerAparelho implements Initializable {

    @FXML
    private TextField txtCliente, txtMarca, txtVersao, txtSerie, campoBusca;

    @FXML
    public TableView<Aparelhos> tabelaAparelho;

    @FXML
    public TableColumn<Aparelhos, Integer> colunaID;

    @FXML
    public TableColumn<Aparelhos, String> colunaMarca, colunaVersao, colunaSerie, colunaCliente;

    @FXML
    public Button BotaoCadastrar, BotaoEditar, BotaoExcluir, botaoFiltrar;

    @FXML
    public MenuItem BotaoCliente, BotaoAparelho, BotaoOS;

    @FXML
    public Label usuarioLogado, dataAtual;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inicializando tela de aparelhos...");
        colunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colunaVersao.setCellValueFactory(new PropertyValueFactory<>("versao"));
        colunaSerie.setCellValueFactory(new PropertyValueFactory<>("serie"));
        usuarioLogado.setText(DatabaseManager.nomeLogged);
        dataAtual.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        atualizarTabela();
    }

    public void limparCampos() {
        txtCliente.clear();
        txtMarca.clear();
        txtSerie.clear();
        txtVersao.clear();
    }

    public void atualizarTabela() {
        try{
            ObservableList<Aparelhos> aparelhos = DatabaseManager.getListAparelhos("", "");
            tabelaAparelho.setItems(FXCollections.observableArrayList(aparelhos));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void cadastrarAparelho() {
        try {
            String marca = txtMarca.getText();
            String versao = txtVersao.getText();
            String serie = txtSerie.getText();
            String cliente = txtCliente.getText();
            String[] values = {marca, versao, serie, cliente};
            DatabaseManager.insertAll("Aparelhos", values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        atualizarTabela();
        limparCampos();
    }

    @FXML
    public void editarAparelho(ActionEvent event) {
        Aparelhos selecionado = tabelaAparelho.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                DatabaseManager.select("Aparelhos", "*",
                        "ID = " + selecionado.getId());
                String cliente = txtCliente.getText();
                String marca = txtMarca.getText();
                String versao = txtVersao.getText();
                String modelo = txtSerie.getText();
                String[] columns = {"Marca", "Versao", "Serie", "Cliente"};
                String[] values = {marca, versao, modelo, cliente};
                DatabaseManager.updateMany("Aparelhos", columns, values,
                        "ID = " + selecionado.getId());
            } catch (SQLException e) {
                System.out.println("Erro ao editar: " + e.getMessage());
            }
        }
        atualizarTabela();
        limparCampos();
    }

    @FXML
    public void excluirAparelho(ActionEvent event) {
        Aparelhos selecionado = tabelaAparelho.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                DatabaseManager.delete("Aparelhos", "ID = " + selecionado.getId());
            } catch(SQLException e) {
                System.out.println("Erro ao deletar: " + e.getMessage());
            }
        }
        atualizarTabela();
        limparCampos();
    }

    @FXML
    public void selecionarAparelho() {
        Aparelhos aparelho = tabelaAparelho.getSelectionModel().getSelectedItem();
        if (aparelho != null) {
            txtVersao.setText(aparelho.getVersao());
            txtMarca.setText(aparelho.getMarca());
            txtSerie.setText(aparelho.getSerie());
            txtCliente.setText(aparelho.getCliente());
        }
    }


    @FXML
    public void pesquisarAparelho() {
        String filtro = "'%" + campoBusca.getText() + "%'";
        try{
            List<Aparelhos> resultados = DatabaseManager.getListAparelhos("CAST(id as CHAR)", filtro);
            tabelaAparelho.setItems(FXCollections.observableArrayList(resultados));
        } catch(SQLException e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
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
    protected void abrirTelaAparelho() {
        changeScreen(BotaoAparelho, "/TelaAparelho.fxml");
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