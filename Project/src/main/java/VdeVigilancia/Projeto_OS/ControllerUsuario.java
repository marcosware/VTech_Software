package VdeVigilancia.Projeto_OS;

import VdeVigilancia.Projeto_OS.Dominio.JPAUtil;
import VdeVigilancia.Projeto_OS.Dominio.Usuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javax.persistence.EntityManager;

public class ControllerUsuario {

    public MenuItem BotaoCliente;
    public MenuItem BotaoUsuario;
    public MenuItem BotaoOS;
    public Button adicionarUsuario;
    public Button removerUsuario;
    public Label Usuario;
    public Label Data;


    @FXML
    private TextField campoID;
    @FXML
    private TextField NomeUsuarios;
    @FXML
    private TextField EmailUsuario;
    @FXML
    private TextField TelefoneUsuario;
    @FXML
    private TextField SenhaUsuario;

    @FXML
    protected void editarUsuario() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Integer id = Integer.parseInt(campoID.getText()); // usamos o código como identificador único
            Usuarios usuario = em.find(Usuarios.class, id);

            if (usuario != null) {
                em.getTransaction().begin();
                usuario.setNome(NomeUsuarios.getText());
                usuario.setEmail(EmailUsuario.getText());
                usuario.setTelefone(TelefoneUsuario.getText());
                usuario.setSenha(SenhaUsuario.getText());
                em.getTransaction().commit();

                mostrarAlerta("Sucesso", "Usuário editado com sucesso!", AlertType.INFORMATION);
            } else {
                mostrarAlerta("Aviso", "Usuário com código não encontrado.", AlertType.WARNING);
            }

        } catch (Exception e) {
            em.getTransaction().rollback();
            mostrarAlerta("Erro", "Erro ao editar usuário: " + e.getMessage(), AlertType.ERROR);
        } finally {
            em.close();
        }
    }

    private void limparCampos() {
        NomeUsuarios.clear();
        EmailUsuario.clear();
        TelefoneUsuario.clear();
        SenhaUsuario.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public void CadastrarUsuario(ActionEvent actionEvent) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Usuarios usuario = new Usuarios();
            usuario.setNome(NomeUsuarios.getText());
            usuario.setEmail(EmailUsuario.getText());
            usuario.setTelefone(TelefoneUsuario.getText());
            usuario.setSenha(SenhaUsuario.getText());

            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();

            mostrarAlerta("Sucesso", "Usuário cadastrado com sucesso!", AlertType.INFORMATION);
            limparCampos();
        } catch (Exception e) {
            em.getTransaction().rollback();
            mostrarAlerta("Erro", "Erro ao cadastrar usuário: " + e.getMessage(), AlertType.ERROR);
        } finally {
            em.close();
        }
    }

    private void deletarUsuario(){
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Integer id = Integer.parseInt(campoID.getText());
            Usuarios usuario = em.find(Usuarios.class, id);

            if (usuario != null) {
                em.getTransaction().begin();
                em.remove(usuario);
                em.getTransaction().commit();

                mostrarAlerta("Sucesso", "Usuário removido com sucesso!", AlertType.INFORMATION);
                limparCampos();
            } else {
                mostrarAlerta("Aviso", "Usuário com código não encontrado.", AlertType.WARNING);
            }

        } catch (Exception e) {
            em.getTransaction().rollback();
            mostrarAlerta("Erro", "Erro ao remover usuário: " + e.getMessage(), AlertType.ERROR);
        } finally {
            em.close();
        }
    }

    public void abrirTelaCliente(ActionEvent actionEvent) {
    }

    public void abrirTelaOS(ActionEvent actionEvent) {
    }

    public void abrirTelaUsuario(ActionEvent actionEvent) {
    }

    public void deletarUser(ActionEvent actionEvent) {
        deletarUsuario();
    }

    public void editarUser(ActionEvent actionEvent) {
        editarUsuario();
    }

    public void pesquisarCliente(ActionEvent actionEvent) {
        EntityManager em = JPAUtil.getEntityManager();
        try{
            String textoID = campoID.getText();
            if(textoID == null || textoID.isEmpty()){
                mostrarAlerta("Aviso", "Informe um codigo de usuário para pesquisar.", AlertType.WARNING);
                return;
            }

            Integer id = Integer.parseInt(textoID);
            Usuarios usuario = em.find(Usuarios.class, id);

            if(usuario != null){
            NomeUsuarios.setText(usuario.getNome());
            EmailUsuario.setText(usuario.getEmail());
            TelefoneUsuario.setText(usuario.getTelefone());
            SenhaUsuario.setText(usuario.getSenha());
        }else {
                mostrarAlerta("Aviso", "Usuário com código não encontrado", AlertType.WARNING);
                limparCampos();
            }
    }catch (Exception e){
            mostrarAlerta("Erro", "Erro ao pesquisar usuário: " + e.getMessage(), AlertType.ERROR);
        }finally {
            em.close();
        }
        }
}

