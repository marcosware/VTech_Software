package VdeVigilancia.Projeto_OS.Dominio;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static VdeVigilancia.Projeto_OS.Application.Programa.em;
import static VdeVigilancia.Projeto_OS.Application.Programa.sc;

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

    public void criarOrcamento() {
        System.out.println("--- Criar Orçamento ---");
        System.out.print("Digite o ID do cliente: ");

        int clienteID;
        try {
            clienteID = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID de cliente inválido.");
            return;
        }

        Clientes cliente = em.find(Clientes.class, clienteID);
        if (cliente == null) {
            System.out.println("Cliente com ID " + clienteID + " não encontrado.");
            return;
        }

        System.out.println("Cliente encontrado: " + cliente.getNome());

        System.out.print("Digite o valor do orçamento: ");
        double valor;
        try {
            valor = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido.");
            return;
        }

        System.out.print("Digite o ID da Ordem de Serviço: ");
        int osID;
        try {
            osID = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID da OS inválido.");
            return;
        }

        OS osAssociada = em.find(OS.class, osID);
        if (osAssociada == null) {
            System.out.println("Ordem de Serviço com ID " + osID + " não encontrada.");
            return;
        }

        // Se quiser associar a uma OS existente, você pode fazer isso aqui.
        // Por enquanto vamos deixar sem OS associada (mas com o cliente e valor)

        Orcamento novoOrcamento = new Orcamento();
        novoOrcamento.setCliente(cliente);
        novoOrcamento.setValor(valor);
        novoOrcamento.setOrdemServico(osAssociada);
        novoOrcamento.setData_Orcamento(LocalDateTime.now()); // se existir esse campo

        try {
            em.getTransaction().begin();
            em.persist(novoOrcamento); // persist para novo objeto
            em.getTransaction().commit();

            System.out.println("Orçamento criado com sucesso! ID: " + novoOrcamento.getId());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao criar orçamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removerOrcamento() {
        System.out.println("--- Remover Orçamento ---");
        System.out.print("Digite o ID do orçamento a ser removido: ");

        int orcamentoID;
        try {
            orcamentoID = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Orcamento orcamento = em.find(Orcamento.class, orcamentoID);
        if (orcamento == null) {
            System.out.println("Orçamento com ID " + orcamentoID + " não encontrado.");
            return;
        }

        try {
            em.getTransaction().begin();
            em.remove(orcamento);
            em.getTransaction().commit();
            System.out.println("Orçamento removido com sucesso!");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao remover orçamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void editarOrcamento() {
        System.out.println("--- Editar Orçamento ---");
        System.out.print("Digite o ID do orçamento a ser editado: ");

        int orcamentoID;
        try {
            orcamentoID = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        Orcamento orcamento = em.find(Orcamento.class, orcamentoID);
        if (orcamento == null) {
            System.out.println("Orçamento com ID " + orcamentoID + " não encontrado.");
            return;
        }

        System.out.println("Orçamento encontrado: " + orcamento);

        System.out.print("Digite o novo valor do orçamento (atual: " + orcamento.getValor() + "): ");
        double novoValor;
        try {
            novoValor = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido.");
            return;
        }

        // Atualizar a data para o momento atual
        LocalDateTime novaData = LocalDateTime.now();

        try {
            em.getTransaction().begin();
            orcamento.setValor(novoValor);
            orcamento.setData_Orcamento(novaData);
            em.merge(orcamento);
            em.getTransaction().commit();

            System.out.println("Orçamento atualizado com sucesso!");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao atualizar orçamento: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String gerarComprovante() {
        System.out.println("--- Gerar Comprovante de Orçamento ---");

        System.out.print("Digite o ID do Cliente: ");
        int clienteID;
        try {
            clienteID = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return null;
        }

        Clientes cliente = em.find(Clientes.class, clienteID);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return null;
        }

        System.out.print("Digite o ID da Ordem de Serviço: ");
        int osID;
        try {
            osID = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return null;
        }

        OS os = em.find(OS.class, osID);
        if (os == null) {
            System.out.println("Ordem de Serviço não encontrada.");
            return null;
        }

        System.out.print("Digite o valor do orçamento: ");
        double valor;
        try {
            valor = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido.");
            return null;
        }

        Orcamento orcamento = new Orcamento();
        orcamento.setCliente(cliente);
        orcamento.setOrdemServico(os);
        orcamento.setValor(valor);
        orcamento.setData_Orcamento(LocalDateTime.now());

        try {
            em.getTransaction().begin();
            em.persist(orcamento);
            em.getTransaction().commit();
            System.out.println("Orçamento registrado com ID: " + orcamento.getId());

            // Após salvar, gera o comprovante
            orcamento.gerarComprovante();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao salvar orçamento: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
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
