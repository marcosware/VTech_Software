package VdeVigilancia.Projeto_OS.Dominio;

import javax.persistence.*;
import java.io.Serializable;


import static VdeVigilancia.Projeto_OS.ProjetoOsApplication.sc;

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