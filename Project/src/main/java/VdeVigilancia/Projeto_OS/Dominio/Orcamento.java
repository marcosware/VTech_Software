package VdeVigilancia.Projeto_OS.Dominio;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static VdeVigilancia.Projeto_OS.ProjetoOsApplication.em;
import static VdeVigilancia.Projeto_OS.ProjetoOsApplication.sc;

@Entity

public class Orcamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDateTime data_Orcamento;

    private Double valor;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "cliente_id" , nullable = false)

    private Clientes cliente;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "OS" , nullable = false)

    private OS ordemServico;

    public Orcamento() {
    }

    public Orcamento(Integer id, LocalDateTime data_Orcamento, Double valor, OS ordemServico) {
        this.id = id;
        this.data_Orcamento = data_Orcamento;
        this.valor = valor;
        this.ordemServico = ordemServico;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getData_Orcamento() {
        return data_Orcamento;
    }

    public void setData_Orcamento(LocalDateTime data_Orcamento) {
        this.data_Orcamento = data_Orcamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public OS getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OS ordemServico) {
        this.ordemServico = ordemServico;
    }

    public void criarOrcamento(){

        System.out.println("--- Criar Orçamento ---");
        System.out.println("Digite o id do cliente: ");

        int clienteID;

        try {
            clienteID = Integer.parseInt(sc.nextLine());
        }catch (NumberFormatException e){
            System.out.println("ID de cliente inválido.");
            return;
        }

        Clientes cliente = em.find(Clientes.class, clienteID);

        if(cliente == null){
            System.out.println("Cliente com ID: " + clienteID + " não encontrado");
            return;
        }
        System.out.println("Cliente encontrado: " + cliente.getNome());

        System.out.print("Valor: ");

        try{
            valor = Double.parseDouble(sc.nextLine());
        }catch (NumberFormatException e){
            System.out.println("Valor inválido");
            return;
        }

        OS osAssociada = null;

        Orcamento novoOrcamento = new Orcamento();

        em.getTransaction().begin();

        try{
            em.merge(novoOrcamento);
            em.getTransaction().commit();
            System.out.println("Orçamento criado com sucesso para o cliente: " + cliente.getNome() + " ID: " + novoOrcamento.getId() + " )");
        }catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao criar orçamento: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "Orcamento{" +
                "id=" + id +
                ", data_Orcamento=" + data_Orcamento +
                ", valor=" + valor +
                ", cliente=" + cliente +
                ", ordemServico=" + ordemServico +
                '}';
    }
}
