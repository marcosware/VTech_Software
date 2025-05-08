package Dominio;

import javax.persistence.*;
import javax.sound.midi.Soundbank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

import static Application.Programa.sc;

@Entity

public class OS implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer id;

    @Column (nullable = false)
    private LocalDate abertura;

    private LocalDate fechamento;

    @Column (columnDefinition = "Text" , nullable = false)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)

    private StatusOS status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "cliente_id" , nullable = false)

    private Clientes cliente;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "aparelhos_id" , nullable = false)
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

    public void setAparelho(Aparelhos_Clientes aparelho) {
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


    public void criarOSAutomatica(EntityManager em, Clientes cliente, Aparelhos_Clientes aparelho, String descricao){

        /*System.out.println("CPF: ");
        String cpf = sc.nextLine();*/


        if (cliente == null || aparelho == null || descricao == null || descricao.trim().isEmpty()){
            System.out.println("Erro: Campos Obrigátorios estão vazios. Insira as informações solicitadas");
            return;
        }


        Random status = new Random();
        StatusOS [] statusOS = StatusOS.values();

        int randomEnum = status.nextInt(statusOS.length);

        try {
            em.getTransaction().begin();

            OS novaOS = new OS();

            novaOS.setAbertura(LocalDate.now());
            novaOS.setStatus(statusOS[randomEnum]);
            novaOS.setDescricao(descricao);

            novaOS.setCliente(cliente);
            novaOS.setAparelho(aparelho);

            em.merge(novaOS);

            em.getTransaction().commit();
            System.out.println("OS criada com sucesso! ID: " + novaOS.getId());
        }catch (Exception e){
            if (em != null && em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao criar OS !!");
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
