package VdeVigilancia.Projeto_OS;

import VdeVigilancia.Projeto_OS.Dominio.*;
import VdeVigilancia.Projeto_OS.Query_Banco.Querys;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class ControllerOS {

    @FXML
    private TextField txtClienteId;
    @FXML
    private TextField txtClienteNome;
    @FXML
    private TextField txtEquipamento;
    @FXML
    private TextField txtDefeito;
    @FXML
    private TextField txtServico;
    @FXML
    private TextField txtValorTotal;
    @FXML
    private CheckBox chkOrcamento;
    @FXML
    private CheckBox chkOrdemServico;
    @FXML
    private Button AdicionarOS;
    @FXML
    private Button Editar;
    @FXML
    private Button APAGAR;
    @FXML
    private MenuItem BotaoCliente;
    @FXML
    private MenuItem BotaoUsuario;
    @FXML
    private MenuItem BotaoOS;
    @FXML
    private Label Usuario;
    @FXML
    private Label Data;

    @FXML
    private TreeTableView<Clientes> treeTableClientes;
    @FXML
    private TreeTableColumn<Clientes, Integer> colunaID;
    @FXML
    private TreeTableColumn<Clientes, String> colunaNome;
    @FXML
    private TreeTableColumn<Clientes, String> colunaFone;

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_V_Tech");
    private final EntityManager em = emf.createEntityManager();

    @FXML
    public void initialize() {
        // Configuração das colunas da TreeTable
        colunaID.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new TreeItemPropertyValueFactory<>("nome"));
        colunaFone.setCellValueFactory(new TreeItemPropertyValueFactory<>("telefone"));

        // Exibe o usuário e a data atual
        Usuario.setText("Administrador");
        Data.setText(LocalDate.now().toString());

        // Carregar os clientes da base de dados
        TreeItem<Clientes> root = new TreeItem<>(new Clientes()); // item raiz invisível
        root.setExpanded(true);
        for (Clientes cliente : em.createQuery("SELECT c FROM Clientes c", Clientes.class).getResultList()) {
            root.getChildren().add(new TreeItem<>(cliente));
        }
        treeTableClientes.setRoot(root);
        treeTableClientes.setShowRoot(false); // opcional
    }

    @FXML
    private void criarOS() {
        try {
            Integer idCliente = Integer.parseInt(txtClienteId.getText());
            Clientes cliente = em.find(Clientes.class, idCliente);

            if (cliente == null) {
                mostrarAlerta("Erro", "Cliente não encontrado.");
                return;
            }

            Querys query = new Querys();
            Aparelhos_Clientes aparelho = query.selectWhereAparelhos();

            if (aparelho == null) {
                mostrarAlerta("Erro", "Aparelho não encontrado.");
                return;
            }

            String descricao = txtDefeito.getText();
            if (descricao == null || descricao.trim().isEmpty()) {
                mostrarAlerta("Erro", "Descrição não pode ser vazia.");
                return;
            }

            StatusOS status = chkOrdemServico.isSelected() ? StatusOS.EM_ANDAMENTO :
                    chkOrcamento.isSelected() ? StatusOS.ABERTA : StatusOS.CANCELADA;

            em.getTransaction().begin();

            OS novaOS = new OS(aparelho, cliente, status, descricao, null, LocalDate.now());
            em.persist(novaOS);

            em.getTransaction().commit();

            mostrarAlerta("Sucesso", "Ordem de Serviço criada com ID: " + novaOS.getId());
            limparCampos();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            mostrarAlerta("Erro", "Erro ao criar OS: " + e.getMessage());
        }
    }

    private void limparCampos() {
        // Limpa os campos após a operação
        txtClienteId.clear();
        txtClienteNome.clear();
        txtEquipamento.clear();
        txtDefeito.clear();
        txtServico.clear();
        txtValorTotal.clear();
        chkOrcamento.setSelected(false);
        chkOrdemServico.setSelected(false);
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void editarOS(ActionEvent actionEvent) {
        try {
            Integer idOS = Integer.parseInt(txtValorTotal.getText());  // Assumindo que o valor total seja o ID da OS
            OS os = em.find(OS.class, idOS);

            if (os == null) {
                mostrarAlerta("Erro", "Ordem de Serviço não encontrada.");
                return;
            }

            // Atualiza os dados da OS com base nos campos
            os.setAparelho();
            os.setDescricao(txtDefeito.getText());
            os.setServico(txtServico.getText());
            os.setValorTotal(Double.parseDouble(txtValorTotal.getText()));
            os.setStatus(chkOrdemServico.isSelected() ? StatusOS.EM_ANDAMENTO :
                    chkOrcamento.isSelected() ? StatusOS.ABERTA :
                            StatusOS.CANCELADA);

            em.getTransaction().begin();
            em.merge(os);
            em.getTransaction().commit();

            mostrarAlerta("Sucesso", "Ordem de Serviço atualizada com sucesso.");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            mostrarAlerta("Erro", "Erro ao editar OS: " + e.getMessage());
        }
    }

    @FXML
    private void removerOS(ActionEvent actionEvent) {
        try {
            Integer idOS = Integer.parseInt(txtValorTotal.getText());  // Assumindo que o valor total seja o ID da OS
            OS os = em.find(OS.class, idOS);

            if (os == null) {
                mostrarAlerta("Erro", "Ordem de Serviço não encontrada.");
                return;
            }

            em.getTransaction().begin();
            em.remove(os);
            em.getTransaction().commit();

            mostrarAlerta("Sucesso", "Ordem de Serviço removida com sucesso.");
            limparCampos();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            mostrarAlerta("Erro", "Erro ao remover OS: " + e.getMessage());
        }
    }

    @FXML
    public void abrirTelaCliente(ActionEvent actionEvent) {
        try {
            // Carrega a nova tela de cliente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VdeVigilancia/Projeto_OS/views/TelaCliente.fxml"));
            Parent root = loader.load();

            // Cria uma nova cena com a tela de cliente
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Tela de Clientes");
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Erro", "Erro ao abrir a tela de clientes: " + e.getMessage());
        }
    }

    @FXML
    public void abrirTelaUsuario(ActionEvent actionEvent) {
        // Código para abrir tela de usuário
    }

    @FXML
    public void abrirTelaOS(ActionEvent actionEvent) {
        // Código para abrir tela de Ordem de Serviço
    }
}
