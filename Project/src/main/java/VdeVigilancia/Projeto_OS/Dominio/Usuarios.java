package VdeVigilancia.Projeto_OS.Dominio;

import javax.persistence.*;
import java.io.Serializable;
import static VdeVigilancia.Projeto_OS.Application.Programa.em;
import static VdeVigilancia.Projeto_OS.Application.Programa.sc;

@Entity

public class Usuarios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private String codigo_usuario;

    public String getCodigo_usuario() {
        return codigo_usuario;
    }

    public void setCodigo_usuario(String codigo_usuario) {
        this.codigo_usuario = codigo_usuario;
    }

    public Usuarios() {
    }

    public Usuarios(Integer id, String email, String nome, String telefone, String senha) {
        this.id = id;
        this.telefone = telefone;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static void inserirUsuarios (EntityManager em, String nome, String email, String telefone, String senha, String codigo_usuario) {

        Usuarios users = new Usuarios();

        users.setNome(nome);
        users.setEmail(email);
        users.setTelefone(telefone);
        users.setSenha(senha);
        users.setCodigo_usuario(codigo_usuario);
        em.getTransaction().begin();
        em.merge(users);
        em.getTransaction().commit();
    }

    public static boolean removerUsuarios(Integer id){
        Usuarios user = em.find(Usuarios.class, id);
        if(user == null){
            System.out.println("Usuário com ID " + id + " não encontrado");
            return false;
        }

        try {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
            System.out.println("Deseja realmente excluir? (s/n): ");
            String confirmcao = sc.nextLine();

            if(!confirmcao.equalsIgnoreCase("s") || !confirmcao.equalsIgnoreCase("S")){
                System.out.println("Exclusão cancelada");
            }
            System.out.println("Usuário removido com sucesso.");
            return true;
        }catch (Exception e) {
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao remover usuário: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean editarUsuarios (Integer id, String newName, String newEmail, String newTelefone, String newSenha, String newCodigo){
        Usuarios user = em.find(Usuarios.class, id);

        if(user == null){
            System.out.println("Usuários com ID " + id + " não encontrado");
            return false;
        }

        if(newName != null && !newName.trim().isEmpty()){
            user.setNome(newName);
        }
        if (newEmail != null && !newEmail.trim().isEmpty()){
            user.setEmail(newEmail);
        }
        if (newSenha != null && !newSenha.trim().isEmpty()){
            user.setSenha(newSenha);
        }
        if(newCodigo != null && !newCodigo.trim().isEmpty()){
            user.setCodigo_usuario(newCodigo);
        }
        if (newTelefone != null && !newTelefone.trim().isEmpty()){
            user.setTelefone(newTelefone);
        }

        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
            System.out.println("Usuário atualizado com sucesso");
            return true;
        }catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao atualizar usuário: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

}