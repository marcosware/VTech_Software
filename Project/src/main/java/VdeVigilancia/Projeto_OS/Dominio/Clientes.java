package VdeVigilancia.Projeto_OS.Dominio;

import VdeVigilancia.Projeto_OS.Dominio.OS;
import VdeVigilancia.Projeto_OS.Dominio.Aparelhos_Clientes;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static VdeVigilancia.Projeto_OS.Application.Programa.em;
import static VdeVigilancia.Projeto_OS.Application.Programa.sc;

@Entity
public class Clientes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

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



    public Clientes(Integer id, String nome, String cpf, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

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
        if (telefone == null || !telefone.matches("\\d{11}")) {
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

    public static void inserirCliente(EntityManager em, String nome/*String email*/, String cpf, String telefone) {

        Clientes clientes = new Clientes();
        System.out.println("Nome: ");
        clientes.setNome(nome);
        System.out.println("CPF: ");
        clientes.setCpf(cpf);
        System.out.println("Telefone: ");
        clientes.setTelefone(telefone);

        em.getTransaction().begin();
        em.persist(clientes);
        em.getTransaction().commit();

    }

    public static boolean editarClientes (EntityManager em, Integer id, String nome, String cpf, String Telefone){
        System.out.print("Digite o ID do cliente que deseja alterar: \n");
        try {
            id = Integer.parseInt(sc.nextLine());
        }catch (NumberFormatException e){
            System.out.println("ID inválido");
        }

        Clientes cliente = em.find(Clientes.class, id);
        if (cliente == null){
            System.out.println("Cliente com id " + id + " não encontrado.");
        }

        System.out.println("Cliente atual: " + cliente.getNome());

        System.out.println("Novo nome (deixe em branco para não alterar.");

        String newName = sc.nextLine();

        if(!newName.trim().isEmpty()){
            cliente.setNome(newName);
        }

        /*System.out.println("Novo email (deixe em branco para não alterar): ");
        String newEmail = sc.nextLine();
        if(!newEmail.trim().isEmpty()){
            cliente.setEmail(newEmail);
        }*/

        System.out.println("Novo telefone (deixe em branco para não alterar): ");
        String newTelefone = sc.nextLine();
        if(!newTelefone.trim().isEmpty()){
            cliente.setTelefone(newTelefone);
        }

        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
            System.out.println("Cliente atualizado com sucesso!");
        }catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return editarClientes(em, id, nome, cpf,Telefone);
    }

    public static void removerCliente(Integer id){
        System.out.print("Digite o ID do cliente que deseja excluir: \n");
        try {
            id = Integer.parseInt(sc.nextLine());
        }catch (NumberFormatException e){
            System.out.println("ID inválido");
            return;
        }

        Clientes cliente = em.find(Clientes.class, id);
        if (cliente == null){
            System.out.println("Cliente com ID " + id + " não encontrado.");
            return;
        }

        System.out.println("Cliente encontrado: " + cliente.getNome());
        System.out.println("Deseja realmente excluir? (s/n): ");
        String confirmcao = sc.nextLine();

        if(!confirmcao.equalsIgnoreCase("s")){
            System.out.println("Exclusão cancelada");
            return;
        }

        try {
            em.getTransaction().begin();
            em.remove(cliente);
            em.getTransaction().commit();
            System.out.println("Cliente excluído com sucesso! ");
        }catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
            e.printStackTrace();
        }
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
