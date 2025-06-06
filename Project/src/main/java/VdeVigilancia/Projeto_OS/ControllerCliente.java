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

public class ControllerCliente implements Initializable {

    @FXML
    private TextField NomeCliente, CpfCliente, TelefoneCliente, EmailCliente, campoBusca;

    @FXML
    public TableView<Clientes> tabelaCliente;

    @FXML
    public TableColumn<Clientes, Integer> colunaID;

    @FXML
    public TableColumn<Clientes, String> colunaNome, colunaCpf, colunaTelefone, colunaEmail;

    @FXML
    public Button BotaoCadastrar, BotaoEditar, BotaoExcluir, botaoFiltrar;

    @FXML
    public MenuItem BotaoCliente, BotaoAparelho, BotaoOS;

    @FXML
    public Label usuarioLogado, dataAtual;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inicializando tela de clientes...");
        colunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        usuarioLogado.setText(DatabaseManager.nomeLogged);
        dataAtual.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        atualizarTabela();
    }

    public void limparCamposClientes() {
        NomeCliente.clear();
        CpfCliente.clear();
        TelefoneCliente.clear();
        EmailCliente.clear();
    }

    public void atualizarTabela() {
        try{
            ObservableList<Clientes> clientes = DatabaseManager.getListClientes("", "");
            tabelaCliente.setItems(FXCollections.observableArrayList(clientes));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void criarCliente() {
        try {
            String nome = NomeCliente.getText();
            String cpf = CpfCliente.getText();
            String telefone = TelefoneCliente.getText();
            String email = EmailCliente.getText();
            String[] values = {nome, cpf, telefone, email};
            DatabaseManager.insertAll("Clientes", values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        atualizarTabela();
        limparCamposClientes();
    }

    @FXML
    public void editarClientes(ActionEvent event) {
        Clientes selecionado = tabelaCliente.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                DatabaseManager.select("Clientes", "*",
                        "ID = " + selecionado.getId());
                String nome = NomeCliente.getText();
                String cpf = CpfCliente.getText();
                String telefone = TelefoneCliente.getText();
                String email = EmailCliente.getText();
                String[] columns = {"Nome", "CPF", "Telefone", "Email"};
                String[] values = {nome, cpf, telefone, email};
                DatabaseManager.updateMany("Clientes", columns, values,
                        "ID = " + selecionado.getId());
            } catch (SQLException e) {
                System.out.println("Erro ao editar: " + e.getMessage());
            }
        }
        atualizarTabela();
        limparCamposClientes();
    }

    @FXML
    public void removerClientes(ActionEvent event) {
        Clientes selecionado = tabelaCliente.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                DatabaseManager.delete("Clientes", "ID = " + selecionado.getId());
            } catch(SQLException e) {
                System.out.println("Erro ao deletar: " + e.getMessage());
            }
        }
        atualizarTabela();
        limparCamposClientes();
    }

    @FXML
    public void selecionarCliente() {
        Clientes cliente = tabelaCliente.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            NomeCliente.setText(cliente.getNome());
            CpfCliente.setText(cliente.getCpf());
            TelefoneCliente.setText(cliente.getTelefone());
            EmailCliente.setText(cliente.getEmail());
        }
    }


    @FXML
    public void onBuscarClick(ActionEvent event) {
        String filtro = "'%" + campoBusca.getText() + "%'";
        try{
            List<Clientes> resultados = DatabaseManager.getListClientes("CAST(id as CHAR)", filtro);
            tabelaCliente.setItems(FXCollections.observableArrayList(resultados));
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