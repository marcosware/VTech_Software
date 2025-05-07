package Dominio;

import javax.persistence.*;
import java.io.Serializable;

import static Application.Programa.sc;

@Entity
public class Clientes implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;

    public Clientes() {
    }

    public Clientes(Integer id, String nome, String cpf, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) throws IllegalArgumentException {
        if (cpf == null || !cpf.matches("\\d{11}")){
            throw new IllegalArgumentException("CPF inválido");
        }
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) throws IllegalArgumentException{
        if (telefone == null || telefone.matches("\\d{11}")){
            throw new IllegalArgumentException("Telefone inválido");
        }
        this.telefone = telefone;
    }

    public void inserirCliente (EntityManager em){

        System.out.println("Nome: ");
        String nome = sc.nextLine();

        System.out.println("Cpf: ");
        String cpf = sc.nextLine();

        System.out.println("Telefone: ");
        String telefone = sc.nextLine();

        Clientes clientes = new Clientes();

        clientes.setNome(nome);
        clientes.setCpf(cpf);
        clientes.setTelefone(telefone);

        em.getTransaction().begin();

        em.merge(clientes);

        em.getTransaction().commit();

    }




    @Override
    public String toString() {
        return "Clientes{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
