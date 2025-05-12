package VdeVigilancia.Projeto_OS;

import VdeVigilancia.Projeto_OS.Dominio.Clientes;
import VdeVigilancia.Projeto_OS.Dominio.JPAUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCliente implements Initializable {

    @FXML
    private TextField NomeCliente, CpfCliente, TelefoneCliente, campoBusca;

    @FXML
    public TableView<Clientes> tabelaCliente;


    @FXML
    public TableColumn<Clientes, Integer> colunaID;

    @FXML
    public TableColumn<Clientes, String> colunaNome, colunaCpf, colunaTelefone;

    @FXML
    public Button BotaoCadastrar, BotaoEditar, BotaoExcluir, botaoFiltrar;

    @FXML
    public MenuItem BotaoCliente, BotaoOS;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Inicializando tela de clientes...");
        if (NomeCliente == null || CpfCliente == null || TelefoneCliente == null) {
            System.out.println("ERRO: Campo(s) não injetado(s) corretamente!");
            colunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
            colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
            atualizarTabela();
        }
    }

    public void limparCamposClientes() {
        NomeCliente.clear();
        CpfCliente.clear();
        TelefoneCliente.clear();
    }

    public void atualizarTabela() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Clientes> clientes = em.createQuery("SELECT C FROM Clientes C", Clientes.class).getResultList();
            tabelaCliente.setItems(FXCollections.observableArrayList(clientes));
        } finally {
            em.close();
        }
    }

    @FXML
    public void criarCliente(ActionEvent event) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Clientes cliente = new Clientes();
            cliente.setNome(NomeCliente.getText());
            cliente.setCpf(CpfCliente.getText());
            cliente.setTelefone(TelefoneCliente.getText());

            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        atualizarTabela();
        limparCamposClientes();
    }

    @FXML
    public void editarClientes(ActionEvent event) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Clientes selecionado = tabelaCliente.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                em.getTransaction().begin();
                Clientes cliente = em.find(Clientes.class, selecionado.getId());

                if (cliente != null) {
                    cliente.setNome(NomeCliente.getText());
                    cliente.setCpf(CpfCliente.getText());
                    cliente.setTelefone(TelefoneCliente.getText());
                }

                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao editar: " + e.getMessage());
        } finally {
            em.close();
        }

        atualizarTabela();
        limparCamposClientes();
    }

    @FXML
    public void removerClientes(ActionEvent event) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Clientes cliente = tabelaCliente.getSelectionModel().getSelectedItem();
            if (cliente != null) {
                em.getTransaction().begin();
                cliente = em.find(Clientes.class, cliente.getId());
                em.remove(cliente);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
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
        }
    }


    @FXML
    public void onBuscarClick(ActionEvent event) {
        String filtro = campoBusca.getText();
        EntityManager em = JPAUtil.getEntityManager();
        List<Clientes> resultados = em.createQuery(
                        "SELECT C FROM Clientes C WHERE CAST(C.id AS string) LIKE :filtro", Clientes.class)
                .setParameter("filtro", "%" + filtro + "%").getResultList();
        em.close();
        tabelaCliente.setItems(FXCollections.observableArrayList(resultados));
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
