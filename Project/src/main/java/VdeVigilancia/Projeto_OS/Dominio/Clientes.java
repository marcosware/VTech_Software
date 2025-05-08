package VdeVigilancia.Projeto_OS.Dominio;

import VdeVigilancia.Projeto_OS.Dominio.OS;
import VdeVigilancia.Projeto_OS.Dominio.Aparelhos_Clientes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static VdeVigilancia.Projeto_OS.Application.Programa.sc;

@Entity
public class Clientes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aparelhos_Clientes> aparelhos;

    @OneToMany (mappedBy = "cliente" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List <OS> ordemServico = new ArrayList<>();

    public List<OS> getOrdemServico(){
        return  ordemServico;
    }

    public void setOrdemServico(List<OS> ordemServico) {
        this.ordemServico = ordemServico;
    }

    public void addOS (OS os){
        if (this.ordemServico == null){
            this.ordemServico = new ArrayList<>();
        }
        this.ordemServico.add(os);
        os.setCliente(this);
    }

    public void removeOS (OS os) {
        if (this.ordemServico != null){
            this.ordemServico.remove(os);
            os.setCliente(null);
        }
    }

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
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido");
        }
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) throws IllegalArgumentException {
        if (telefone == null || telefone.matches("\\d{11}")) {
            throw new IllegalArgumentException("Telefone inválido");
        }
        this.telefone = telefone;
    }

    public List<Aparelhos_Clientes> getAparelhos() {
        return aparelhos;
    }

    public void setAparelhos(List<Aparelhos_Clientes> aparelhos) {
        this.aparelhos = aparelhos;
    }

    public void inserirCliente(EntityManager em) {

        System.out.println("Nome: ");
        System.out.println("Cpf: ");
        System.out.println("Telefone: ");
        Clientes clientes = new Clientes();
        clientes.setNome(nome);
        clientes.setCpf(cpf);
        clientes.setTelefone(telefone);

        em.getTransaction().begin();

        em.merge(clientes);

        em.getTransaction().commit();

    }

    public void addAparelhos(Aparelhos_Clientes aparelho) {
        if(this.aparelhos == null){
            this.aparelhos = new ArrayList<>();
        }
        this.aparelhos.add(aparelho);
        aparelho.setCliente(this);
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
