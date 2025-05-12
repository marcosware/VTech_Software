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

public class Controller implements Initializable{

    public Button botaoFiltrar;
    @FXML
    Button BotaoCadastrarCliente, BotaoEntrar, CadastrarUsuario;

    @FXML
    MenuItem BotaoCliente, BotaoUsuario, BotaoOS;

    @FXML
        private TextField NomeCliente,CpfCliente, TelefoneCliente;
    @FXML
    private TextField NomeUsuario, SenhaUsuario, TelefoneUsuario, EmailUsuario, Codigo;

    @FXML
    private TextField campoBusca;

    @FXML
    public TableView<Clientes> tabelaClientes;

    @FXML
    public TableColumn<Clientes, Integer> colunaID;

    @FXML
    public TableColumn<Clientes, String> colunaNome;

    @FXML
    public TableColumn<Clientes, String> colunaCpf;

    @FXML
    public TableColumn<Clientes, String> colunaTelefone;

    @FXML
    private TextField Filtrar;

    public void initialize (URL location, ResourceBundle resource) {
        // Verificar se a tabela e as colunas foram corretamente injetadas
        if (tabelaClientes != null && colunaID != null && colunaNome != null && colunaCpf != null && colunaTelefone != null) {
            System.out.println("Tabela e Colunas Iniciadas corretamente.");

            // Definir as colunas
            colunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
            colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

            // Atualizar a tabela com dados
            atualizarTabela();
        } else {
            System.out.println("Falha na injeção de colunas ou tabela.");
        }
    }


    public void limparCamposClientes(){
        NomeCliente.clear();
        CpfCliente.clear();
        TelefoneCliente.clear();
    }

    public void atualizarTabela(){
        EntityManager em = JPAUtil.getEntityManager();
        try{
        List<Clientes> clientes = em.createQuery("SELECT C FROM Clientes C" , Clientes.class).getResultList();
        tabelaClientes.setItems(FXCollections.observableArrayList(clientes));
    }finally {
            em.close();
        }
        }

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

    public void criarCliente(javafx.event.ActionEvent actionEvent) {

        EntityManager em = JPAUtil.getEntityManager();

        try{
        Clientes clientes = new Clientes();
        clientes.setNome(NomeCliente.getText());
        clientes.setCpf(CpfCliente.getText());
        clientes.setTelefone(TelefoneCliente.getText());

        em.getTransaction().begin();
        em.persist(clientes);
        em.getTransaction().commit();

    }finally {
            em.close();
        }
        atualizarTabela();
        limparCamposClientes();
        }

    public void editarClientes(javafx.event.ActionEvent actionEvent) {
        EntityManager em = JPAUtil.getEntityManager();
        try{
        Clientes clientes = tabelaClientes.getSelectionModel().getSelectedItem();
        if(clientes != null){
            clientes.setNome(NomeCliente.getText());
            clientes.setCpf(CpfCliente.getText());
            clientes.setTelefone(TelefoneCliente.getText());

            em.getTransaction().begin();
            em.merge(clientes);
            em.getTransaction().commit();


        }
    }finally {
            em.close();
        }
        atualizarTabela();
        limparCamposClientes();
        }

    public void removerClientes(javafx.event.ActionEvent actionEvent) {
        EntityManager em = JPAUtil.getEntityManager();
        try{
        Clientes clientes = tabelaClientes.getSelectionModel().getSelectedItem();
        if(clientes != null){
            em.getTransaction().begin();
            clientes = em.find(Clientes.class, clientes.getId());
            em.remove(clientes);
            em.getTransaction().commit();

        }
    }finally {
            em.close();
        }
        atualizarTabela();
        limparCamposClientes();
        }

    public void filtrarClientes(javafx.event.ActionEvent actionEvent) {
        String textoBuscar = campoBusca.getText();

        EntityManager em = JPAUtil.getEntityManager();
        String filtro =botaoFiltrar.getText();
        List<Clientes> resultados = em.createQuery("SELECT C FROM Clientes C WHERE C. nome LIKE :filtro", Clientes.class).setParameter ("filtro", "%" + filtro + "%").getResultList();

        em.close();

        tabelaClientes.setItems(FXCollections.observableArrayList(resultados));
    }

    public void onBuscarClick(ActionEvent actionEvent) {
        atualizarTabela();
    }
}
