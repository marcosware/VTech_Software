package VdeVigilancia.Projeto_OS;
import VdeVigilancia.Projeto_OS.Dominio.Clientes;
import VdeVigilancia.Projeto_OS.Dominio.Usuarios;
import VdeVigilancia.Projeto_OS.Query_Banco.Querys;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.util.List;
import static VdeVigilancia.Projeto_OS.ProjetoOsApplication.em;



public class Controller {

    @FXML
    Button BotaoCadastrarCliente, BotaoEntrar, CadastrarUsuario;

    @FXML
    MenuItem BotaoCliente, BotaoUsuario, BotaoOS;

    @FXML
    protected void CadastrarCliente () {
        String nome = NomeCliente.getText();
        String cpf = CpfCliente.getText();
        String telefone = TelefoneCliente.getText();
        System.out.println(telefone.length());

        Clientes.inserirCliente ( em , nome , cpf , telefone );

    }

    @FXML
    protected void editarClientes (){
        int id = Integer.parseInt(idCliente.getText());
        String nome = NomeCliente.getText();
        String Cpf = CpfCliente.getText();
        String fone = TelefoneCliente.getText();

        boolean sucesso = Clientes.editarClientes(em, id, nome, Cpf,fone);
    }

    @FXML
    protected void removerClientes(){
        int id = Integer.parseInt(idCliente.getText());
        Clientes.removerCliente(id);
    }

    @FXML private TextField NomeCliente;
    @FXML private TextField idCliente;
    @FXML private TextField CpfCliente;
    @FXML private TextField TelefoneCliente;

    @FXML
    protected void CadastrarUsuario () {
        String nome = NomeUsuario.getText();
        String email  = EmailUsuario.getText();
        String telefone = TelefoneUsuario.getText();
        String senha = SenhaUsuario.getText();
        String codigoRegisto = Codigo.getText();
         Usuarios.inserirUsuarios(em,  nome,  email, telefone, senha, codigoRegisto);

    }
    @FXML
    protected void editarUsuario (){
        int id = Integer.parseInt(idUsuario.getText());
        String nome = NomeUsuario.getText(),
                email = EmailUsuario.getText(),
                telefone = TelefoneUsuario.getText(),
                senha = SenhaUsuario.getText(),
                codigo = Codigo.getText();

        Usuarios.editarUsuarios(id, nome,email, telefone,senha,codigo);
    }

    @FXML
    protected void removerUsuarios(){
        int id = Integer.parseInt(idUsuario.getText());
        Usuarios.removerUsuarios(id);
    }

    private void limparCampos (){
        idUsuario.clear();
        NomeUsuario.clear();
        EmailUsuario.clear();
        TelefoneUsuario.clear();
        SenhaUsuario.clear();
        Codigo.clear();
    }


    @FXML private TextField idUsuario;
    @FXML private TextField NomeUsuario;
    @FXML private TextField SenhaUsuario;
    @FXML private TextField TelefoneUsuario;
    @FXML private TextField EmailUsuario;
    @FXML private TextField Codigo;

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


    public void atualizarTabela() {
        List<Clientes> clientes = Querys.selectClientes(ProjetoOsApplication.em);
        if (clientes != null) {
            tabelaClientes.setItems(FXCollections.observableArrayList(clientes));
        } else {
            tabelaClientes.setItems(FXCollections.observableArrayList());
        }
    }


    @FXML
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        atualizarTabela();
    }


    @FXML
    protected void filtrarClientes() {
        String filtro = filtrarField.getText();
        if (filtro == null || filtro.trim().isEmpty()) {
            // Se filtro vazio, carrega todos
            atualizarTabela();
            return;
        }

        List<Clientes> clientesFiltrados = Clientes.filtrarClientes(ProjetoOsApplication.em, filtro.trim());
        if (clientesFiltrados != null) {
            tabelaClientes.setItems(FXCollections.observableArrayList(clientesFiltrados));
        }
    }

    @FXML
    protected void abrirTelaCliente () {
        changeScreen(BotaoCliente, "/TelaClientes.fxml");
    }

    @FXML
    protected void abrirTelaInicio () {
        changeScreen(BotaoEntrar, "/TelaInicio.fxml");
    }

    @FXML
    protected void abrirTelaOS () {changeScreen(BotaoOS, "/TelaOS.fxml"); }

    @FXML
    protected void abrirTelaUsuario () {changeScreen(BotaoUsuario, "/TelaUsuario.fxml"); }

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
