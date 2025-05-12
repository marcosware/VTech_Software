package VdeVigilancia.Projeto_OS;

import VdeVigilancia.Projeto_OS.Dominio.*;
import VdeVigilancia.Projeto_OS.Query_Banco.Querys;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerOS implements Initializable {

    @FXML private TextField txtClienteId;
    @FXML private TextField txtClienteNome;
    @FXML private TextField txtEquipamento;
    @FXML private TextField txtDefeito;
    @FXML private TextField txtServico;
    @FXML private TextField txtValorTotal;

    @FXML private CheckBox chkOrcamento;
    @FXML private CheckBox chkOrdemServico;

    @FXML private Button adicionarOS;
    @FXML private Button editar;
    @FXML private Button apagar;

    @FXML private MenuItem botaoCliente;
    @FXML private MenuItem botaoUsuario;
    @FXML private MenuItem botaoOS;

    @FXML private Label usuario;
    @FXML private Label data;

    @FXML private TreeTableView<Clientes> treeTableClientes;
    @FXML private TreeTableColumn<Clientes, Integer> colunaID;
    @FXML private TreeTableColumn<Clientes, String> colunaNome;
    @FXML private TreeTableColumn<Clientes, String> colunaFone;

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_V_Tech");
    private EntityManager em = emf.createEntityManager();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            em = Persistence.createEntityManagerFactory("JPA_V_Tech").createEntityManager();
        } catch (Exception e) {
            System.err.println("Erro ao inicializar EntityManager: " + e.getMessage());
            e.printStackTrace();
        }
        colunaID.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new TreeItemPropertyValueFactory<>("nome"));
        colunaFone.setCellValueFactory(new TreeItemPropertyValueFactory<>("telefone"));

        usuario.setText("Administrador");
        data.setText(LocalDate.now().toString());

        editar.setDisable(true);
        apagar.setDisable(true);

        TreeItem<Clientes> root = new TreeItem<>(new Clientes());
        root.setExpanded(true);
        for (Clientes cliente : em.createQuery("SELECT c FROM Clientes c", Clientes.class).getResultList()) {
            root.getChildren().add(new TreeItem<>(cliente));
        }
        treeTableClientes.setRoot(root);
        treeTableClientes.setShowRoot(false);

        treeTableClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            boolean selected = newSel != null && newSel.getValue().getId() != null;
            editar.setDisable(!selected);
            apagar.setDisable(!selected);

            if (selected) {
                txtClienteId.setText(String.valueOf(newSel.getValue().getId()));
                txtClienteNome.setText(newSel.getValue().getNome());
            }
        });
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
            novaOS.setServico(txtServico.getText());
            novaOS.setValorTotal(Double.parseDouble(txtValorTotal.getText()));

            em.persist(novaOS);
            em.getTransaction().commit();

            mostrarAlerta("Sucesso", "Ordem de Serviço criada com ID: " + novaOS.getId());
            limparCampos();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            mostrarAlerta("Erro", "Erro ao criar OS: " + e.getMessage());
        }
    }

    @FXML
    private void editarOS(ActionEvent event) {
        try {
            Integer idOS = Integer.parseInt(txtValorTotal.getText());  // Assumindo campo de ID da OS temporariamente
            OS os = em.find(OS.class, idOS);

            if (os == null) {
                mostrarAlerta("Erro", "Ordem de Serviço não encontrada.");
                return;
            }

            os.setDescricao(txtDefeito.getText());
            os.setServico(txtServico.getText());
            os.setValorTotal(Double.parseDouble(txtValorTotal.getText()));
            os.setStatus(chkOrdemServico.isSelected() ? StatusOS.EM_ANDAMENTO :
                    chkOrcamento.isSelected() ? StatusOS.ABERTA : StatusOS.CANCELADA);

            em.getTransaction().begin();
            em.merge(os);
            em.getTransaction().commit();

            mostrarAlerta("Sucesso", "Ordem de Serviço atualizada com sucesso.");
            limparCampos();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            mostrarAlerta("Erro", "Erro ao editar OS: " + e.getMessage());
        }
    }

    @FXML
    private void removerOS(ActionEvent event) {
        try {
            Integer idOS = Integer.parseInt(txtValorTotal.getText());  // Assumindo campo de ID da OS temporariamente
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

    private void limparCampos() {
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
    private void abrirTelaCliente(ActionEvent actionEvent) {
        abrirNovaTela("/VdeVigilancia/Projeto_OS/views/TelaCliente.fxml", "Tela de Clientes");
    }

    @FXML
    private void abrirTelaUsuario(ActionEvent actionEvent) {
        abrirNovaTela("/VdeVigilancia/Projeto_OS/views/TelaUsuario.fxml", "Tela de Usuários");
    }

    @FXML
    private void abrirTelaOS(ActionEvent actionEvent) {
        abrirNovaTela("/VdeVigilancia/Projeto_OS/views/TelaOS.fxml", "Ordem de Serviço");
    }

    private void abrirNovaTela(String caminhoFXML, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // <- Mostra no console!
            mostrarAlerta("Erro", "Erro ao abrir tela: " + e.getMessage());
        }
    }

}
