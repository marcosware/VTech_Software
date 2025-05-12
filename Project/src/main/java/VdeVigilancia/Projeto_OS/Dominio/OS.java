package VdeVigilancia.Projeto_OS.Dominio;

import VdeVigilancia.Projeto_OS.Dominio.Aparelhos_Clientes;
import VdeVigilancia.Projeto_OS.Dominio.Clientes;
import VdeVigilancia.Projeto_OS.Dominio.StatusOS;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class OS implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private LocalDate abertura;

    private LocalDate fechamento;

    @Column(columnDefinition = "TEXT", nullable = false)
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

    private String servico;

    private double valorTotal;

    public OS() {}

    public OS(Aparelhos_Clientes aparelho, Clientes cliente, StatusOS status, String descricao, LocalDate fechamento, LocalDate abertura) {
        this.aparelho = aparelho;
        this.cliente = cliente;
        this.status = status;
        this.descricao = descricao;
        this.fechamento = fechamento;
        this.abertura = abertura;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getAbertura() { return abertura; }
    public void setAbertura(LocalDate abertura) { this.abertura = abertura; }

    public LocalDate getFechamento() { return fechamento; }
    public void setFechamento(LocalDate fechamento) { this.fechamento = fechamento; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public StatusOS getStatus() { return status; }
    public void setStatus(StatusOS status) { this.status = status; }

    public Clientes getCliente() { return cliente; }
    public void setCliente(Clientes cliente) { this.cliente = cliente; }

    public Aparelhos_Clientes getAparelho() { return aparelho; }
    public void setAparelho(Aparelhos_Clientes aparelho) { this.aparelho = aparelho; }

    public String getServico() { return servico; }
    public void setServico(String servico) { this.servico = servico; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

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
                ", servico='" + servico + '\'' +
                ", valorTotal=" + valorTotal +
                '}';
    }

    public void criarOSAutomatica(EntityManager em) {
    }
}
