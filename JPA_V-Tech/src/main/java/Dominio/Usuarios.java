package Dominio;

import javax.persistence.*;
import java.io.Serializable;

import static Application.Programa.sc;

@Entity

public class Usuarios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    private String email;
    private String codigo_registro;
    private String perfil;

    public Usuarios() {
    }

    public Usuarios(Integer id, String perfil, String codigo_registro, String email, String nome) {
        this.id = id;
        this.perfil = perfil;
        this.codigo_registro = codigo_registro;
        this.email = email;
        this.nome = nome;
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

    public String getCodigo_registro() {
        return codigo_registro;
    }

    public void setCodigo_registro(String codigo_registro) {
        this.codigo_registro = codigo_registro;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void inserirUsuarios (EntityManager em ){
        System.out.println("Nome : ");
        String nome = sc.nextLine();

        System.out.println("Email: ");
        String email = sc.nextLine();

        System.out.println("Código ");
        String codigo = sc.nextLine();

        Usuarios users = new Usuarios();

        users.setNome(nome);
        users.setEmail(email);
        users.setCodigo_registro(codigo);

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
                ", codigo_registro='" + codigo_registro + '\'' +
                ", perfil='" + perfil + '\'' +
                '}';
    }
}
