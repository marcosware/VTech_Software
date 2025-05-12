package VdeVigilancia.Projeto_OS;

import VdeVigilancia.Projeto_OS.Dominio.Clientes;
import VdeVigilancia.Projeto_OS.Dominio.Usuarios;
import com.mysql.cj.xdevapi.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javax.persistence.TypedQuery;
import java.util.List;

import static VdeVigilancia.Projeto_OS.Application.Programa.em;

public class Controller{

    private int idClienteSelecionado = -1;

    @FXML
    Button BotaoCadastrarCliente, BotaoEntrar, CadastrarUsuario;

    @FXML
    MenuItem BotaoCliente, BotaoUsuario, BotaoOS;

    @FXML
        private TextField NomeCliente,CpfCliente, TelefoneCliente;
    @FXML
    private TextField NomeUsuario, SenhaUsuario, TelefoneUsuario, EmailUsuario, Codigo;

    @FXML
    private TableView<Clientes> tabelaClientes;

    @FXML
    private TableColumn<Clientes, Integer> colID;

    @FXML
    private TableColumn<Clientes, String> colNome;

    @FXML
    private TableColumn<Clientes, String> colCpf;

    @FXML
    private TableColumn<Clientes, String> colTelefone;

    @FXML
    private TextField filtrarField;

    @FXML
    private TextField Pesquisar;


    @FXML
    public void initialize(){
        if(tabelaClientes != null){
            ObservableList<Clientes> clientes = FXCollections.observableArrayList(
                    new Clientes(1, "Caio", "15193439659", "971494707")
            );
            tabelaClientes.setItems(clientes);
        }else {
            System.out.println("tabelaClientes não foi inicializada!");
        }
        atualizaTabelasClientes();
    }

    @FXML
    protected void CadastrarCliente() {
        String nome = NomeCliente.getText();
        String cpf = CpfCliente.getText();
        String telefone = TelefoneCliente.getText();
        System.out.println(telefone.length());
        Clientes.menuClientes();

        if(nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty()){
            System.out.println("Por favor, preencha todos os campos.");
            return;
        }
        Clientes.menuClientes();
    }

    @FXML
    public void editarClientes() {
       if(idClienteSelecionado == -1){
           System.out.println("Nenhum cliente selecionado para editar.");
           return;
       }

       try{
           em.getTransaction().begin();

           Clientes clientes = em.find(Clientes.class, idClienteSelecionado);

           if(clientes != null){
               clientes.setNome(NomeCliente.getText());
               clientes.setCpf(CpfCliente.getText());
               clientes.setTelefone(TelefoneCliente.getText());

               em.merge(clientes);
           }
           em.getTransaction().commit();

           atualizaTabelasClientes();
           limparCampos();
       }catch (Exception e){
           if(em.getTransaction().isActive()){
               em.getTransaction().rollback();
           }
           e.printStackTrace();
       }

    }


    public void atualizaTabelasClientes(){
        try {
            List<Clientes> list = em.createQuery("select c from Clientes c", Clientes.class).getResultList();
            ObservableList<Clientes> dados = FXCollections.observableArrayList(list);
            tabelaClientes.setItems(dados);

            colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            colNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
            colCpf.setCellValueFactory(new PropertyValueFactory<>("CPF"));
            colTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
        }catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @FXML
    protected void removerClientes() {
        if(idClienteSelecionado == -1){
            System.out.println("Nenhum cliente selecionado para a remoção.");
            return;
        }

        try {
            em.getTransaction().begin();

            Clientes cliente = em.find(Clientes.class, idClienteSelecionado);
            if(cliente != null){
                em.remove(cliente);
            }

            em.getTransaction().commit();

            atualizaTabelasClientes();
            limparCampos();
        }catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    @FXML
    public void selecionarCliente(){
        Clientes cliente = tabelaClientes.getSelectionModel().getSelectedItem();
        if(cliente != null){
            idClienteSelecionado = cliente.getId();
            NomeCliente.setText(cliente.getNome());
            CpfCliente.setText(cliente.getCpf());
            TelefoneCliente.setText(cliente.getTelefone());
        }
    }

    @FXML
    protected void carregarCliente (String filtro){
        TypedQuery <Clientes> query = em.createQuery("Select c from Clientes c where lower (c.nome) like :filtro", Clientes.class);
        query.setParameter("filtro" , "%" + filtro.toLowerCase() + "%");

        List <Clientes> lista = query.getResultList();
        ObservableList <Clientes> dados = FXCollections.observableArrayList(lista);
        tabelaClientes.setItems(dados);
    }
    @FXML
    private TextField Filtrar;

    @FXML
    private void filtrarClientes(){
        String textoFiltro = Filtrar.getText();
        carregarCliente(textoFiltro);
    }


    @FXML
    protected void CadastrarUsuario() {
        String nome = NomeUsuario.getText();
        String email = EmailUsuario.getText();
        String telefone = TelefoneUsuario.getText();
        String senha = SenhaUsuario.getText();
        String codigoRegisto = Codigo.getText();
        Usuarios.inserirUsuarios(em, nome, email, telefone, senha, codigoRegisto);
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
