package VdeVigilancia.Projeto_OS.Dominio;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static VdeVigilancia.Projeto_OS.ProjetoOsApplication.sc;

@Entity
public class Aparelhos_Clientes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;

    private String Marca;

    private String modelo;

    private String numero_serie;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "cliente_id")

    private Clientes cliente;

    @OneToMany (mappedBy = "aparelho" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OS> ordemServico = new ArrayList<>();

    public List<OS> getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(List<OS> ordemServico) {
        this.ordemServico = ordemServico;
    }

    public void addOS (OS os){
        if (this.ordemServico == null){
            this.ordemServico = new ArrayList<>();
        }
        this.ordemServico.add(os);
        os.setAparelho(this);
    }

    public void removeOS (OS os){
        if (this.ordemServico != null){
            this.ordemServico.remove(os);
            os.setAparelho(null);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumero_serie() {
        return numero_serie;
    }

    public void setNumero_serie(String numero_serie) {
        this.numero_serie = numero_serie;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public static List<Aparelhos_Clientes> cadastrarAparelhos(){
        List<Aparelhos_Clientes> listaAparelhos = new ArrayList<>();
        char continuar = 's';

        while (continuar == 's' || continuar == 'S'){
            System.out.println("\n ---- Cadastrar Aparelhos ----");

            Aparelhos_Clientes novoAparelho = new Aparelhos_Clientes();

            System.out.println("Marca do Aparelho: ");
            novoAparelho.setMarca(sc.nextLine());

            System.out.println("Modelo do Aparelho: ");
            novoAparelho.setModelo(sc.nextLine());

            System.out.println("Número de série do aparelho: ");
            novoAparelho.setNumero_serie(sc.nextLine());

            listaAparelhos.add(novoAparelho);

            System.out.println("Aparelho adicinado à lista temporária.");

            System.out.println("Deseja cadastrar outro aparelho? (s/n)");

            continuar = sc.nextLine().charAt(0);
        }
        return listaAparelhos;
    }
    public void cadastrarAparelhos(EntityManager em){
        try {
            System.out.println("\n --- Cadastro de Aparelhos ---");
            System.out.print("Digite o ID do cliente: ");

            int clienteID = Integer.parseInt(sc.nextLine());

            Clientes cliente = em.find(Clientes.class, clienteID);

            if (cliente == null) {
                System.out.println("Cliente com ID " + clienteID + " não encontrado.");
                return;
            }
                System.out.println("Cliente encontrado: " + cliente.getNome());

            List<Aparelhos_Clientes> novosAparelhos = Aparelhos_Clientes.cadastrarAparelhos();

            if(novosAparelhos.isEmpty()){
                System.out.println("Nenhum aparelhos cadastrado.");
                return;
            }

            em.getTransaction().begin();

            for (Aparelhos_Clientes aparelho : novosAparelhos){
                cliente.addAparelhos(aparelho);
            }

            em.getTransaction().commit();
            System.out.println(novosAparelhos.size() + " aparelho(s) adicionado(s) ao cliente " + cliente.getNome() + "com sucesso");
        }catch (NumberFormatException e) {
            System.out.println("Entrada de ID inválida. Por favor, digite um número inteiro.");
            if (em != null && em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao cadastrar aparelhos: " + e.getMessage());
            e.printStackTrace();
            }finally {
        }
    }
}

