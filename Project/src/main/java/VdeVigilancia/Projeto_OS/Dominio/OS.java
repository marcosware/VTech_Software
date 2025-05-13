package VdeVigilancia.Projeto_OS.Dominio;

import VdeVigilancia.Projeto_OS.Query_Banco.Querys;

import javax.persistence.*;
import javax.sound.midi.Soundbank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

import static VdeVigilancia.Projeto_OS.Application.Programa.sc;

@Entity

public class  OS implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private LocalDate abertura;

    private LocalDate fechamento;

    @Column(columnDefinition = "Text", nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)

    private StatusOS status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)

    private Clientes cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aparelhos_id", nullable = false)
    private Aparelhos_Clientes aparelho;

    public OS() {
    }

    public OS(Aparelhos_Clientes aparelho, Clientes cliente, StatusOS status, String descricao, LocalDate fechamento, LocalDate abertura) {
        this.aparelho = aparelho;
        this.cliente = cliente;
        this.status = status;
        this.descricao = descricao;
        this.fechamento = fechamento;
        this.abertura = abertura;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aparelhos_Clientes getAparelho() {
        return aparelho;
    }

    public void setAparelho() {
        this.aparelho = aparelho;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public StatusOS getStatus() {
        return status;
    }

    public void setStatus(StatusOS status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getFechamento() {
        return fechamento;
    }

    public void setFechamento(LocalDate fechamento) {
        this.fechamento = fechamento;
    }

    public LocalDate getAbertura() {
        return abertura;
    }

    public void setAbertura(LocalDate abertura) {
        this.abertura = abertura;
    }

    public void criarOSAutomatica(EntityManager em) {
        try {
            Querys query = new Querys();

            System.out.print("Insira o ID do cliente: ");
            int idCliente = Integer.parseInt(sc.nextLine());
            Clientes cliente = em.find(Clientes.class, idCliente);

            if (cliente == null) {
                System.out.println("Cliente não encontrado.");
                return;
            }

            Aparelhos_Clientes aparelho = query.selectWhereAparelhos();
            if (aparelho == null) {
                System.out.println("Aparelho não encontrado.");
                return;
            }

            System.out.print("Descrição da Ordem de Serviço: ");
            String descricao = sc.nextLine();

            if (descricao == null || descricao.trim().isEmpty()) {
                System.out.println("Descrição não pode ser vazia.");
                return;
            }

            StatusOS[] statusOS = StatusOS.values();
            StatusOS status = statusOS[new Random().nextInt(statusOS.length)];

            em.getTransaction().begin();

            OS novaOS = new OS();
            novaOS.setAbertura(LocalDate.now());
            novaOS.setStatus(status);
            novaOS.setDescricao(descricao);
            novaOS.setCliente(cliente);
            novaOS.setAparelho();

            em.persist(novaOS);  // use persist em vez de merge para novo registro

            em.getTransaction().commit();
            System.out.println("OS criada com sucesso! ID: " + novaOS.getId());

        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Use apenas números.");
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao criar OS: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "OS{" +
                "id=" + id +
                ", abertura=" + abertura +
                ", fechamento=" + fechamento +
                ", descricao='" + descricao + '\'' +
                ", status=" + status +
                ", cliente=" + cliente +
                ", aparelho=" + aparelho +
                '}';
    }
}