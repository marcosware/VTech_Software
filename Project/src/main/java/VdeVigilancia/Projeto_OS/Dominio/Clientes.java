package VdeVigilancia.Projeto_OS.Dominio;

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

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aparelhos_Clientes> aparelhos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OS> ordemServico = new ArrayList<>();

    public Clientes() {
    }

    public Clientes(Integer id, String nome, String cpf, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) throws IllegalArgumentException {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido");
        }
        this.cpf = cpf;
    }

    public String getTelefone() { return telefone; }

    public void setTelefone(String telefone) throws IllegalArgumentException {
        if (telefone == null || !telefone.matches("\\d{11}")) {
            throw new IllegalArgumentException("Telefone inválido");
        }
        this.telefone = telefone;
    }

    public List<Aparelhos_Clientes> getAparelhos() { return aparelhos; }

    public void setAparelhos(List<Aparelhos_Clientes> aparelhos) {
        this.aparelhos = aparelhos;
    }

    public List<OS> getOrdemServico() { return ordemServico; }

    public void setOrdemServico(List<OS> ordemServico) {
        this.ordemServico = ordemServico;
    }

    public void addOS(OS os) {
        if (this.ordemServico == null) {
            this.ordemServico = new ArrayList<>();
        }
        this.ordemServico.add(os);
        os.setCliente(this);
    }

    public void removeOS(OS os) {
        if (this.ordemServico != null) {
            this.ordemServico.remove(os);
            os.setCliente(null);
        }
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

    // --- MENU DE CLIENTES ---
    public static void menuClientes() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU CLIENTES ---");
            System.out.println("1 - Listar todos");
            System.out.println("2 - Buscar por ID");
            System.out.println("3 - Editar cliente");
            System.out.println("4 - Remover cliente");
            System.out.println("5 - Adicionar cliente");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida.");
                continue;
            }

            switch (opcao) {
                case 1:
                    listarTodos();
                    break;
                case 2:
                    buscarPorId();
                    break;
                case 3:
                    editarCliente();
                    break;
                case 4:
                    removerCliente();
                    break;
                case 5:
                    adicionarCliente();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void listarTodos() {
        List<Clientes> clientes = em.createQuery("SELECT c FROM Clientes c", Clientes.class).getResultList();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
        } else {
            for (Clientes c : clientes) {
                System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome());
            }
        }
    }

    private static void buscarPorId() {
        System.out.print("Digite o ID do cliente: ");
        int id = Integer.parseInt(sc.nextLine());
        Clientes cliente = em.find(Clientes.class, id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
        } else {
            System.out.println("Cliente: " + cliente);
        }
    }

    private static void editarCliente() {
        System.out.print("Digite o ID do cliente a editar: ");
        int id = Integer.parseInt(sc.nextLine());
        Clientes cliente = em.find(Clientes.class, id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("Novo nome: ");
        String novoNome = sc.nextLine();

        em.getTransaction().begin();
        cliente.setNome(novoNome);
        em.merge(cliente);
        em.getTransaction().commit();

        System.out.println("Cliente atualizado.");
    }

    private static void removerCliente() {
        System.out.print("Digite o ID do cliente a remover: ");
        int id = Integer.parseInt(sc.nextLine());
        Clientes cliente = em.find(Clientes.class, id);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        em.getTransaction().begin();
        em.remove(cliente);
        em.getTransaction().commit();

        System.out.println("Cliente removido com sucesso.");
    }

    private static void adicionarCliente() {
        try {
            System.out.print("Nome: ");
            String nome = sc.nextLine();

            System.out.print("CPF (somente números, 11 dígitos): ");
            String cpf = sc.nextLine();

            System.out.print("Telefone (somente números, 11 dígitos): ");
            String telefone = sc.nextLine();

            System.out.print("Email: ");
            String email = sc.nextLine();

            Clientes cliente = new Clientes();
            cliente.setNome(nome);
            cliente.setCpf(cpf);
            cliente.setTelefone(telefone);

            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();

            System.out.println("Cliente adicionado com sucesso!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao adicionar cliente: " + e.getMessage());
        }
    }
}
