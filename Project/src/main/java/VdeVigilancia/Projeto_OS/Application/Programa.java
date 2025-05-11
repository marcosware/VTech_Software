package VdeVigilancia.Projeto_OS.Application;

import VdeVigilancia.Projeto_OS.Dominio.*;
import VdeVigilancia.Projeto_OS.Query_Banco.Querys;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Programa {

    public static final Scanner sc = new Scanner(System.in);
    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_V_Tech");
    public static final EntityManager em = emf.createEntityManager();
    public static void main(String[] args) {

        Clientes services = new Clientes();
        Usuarios users = new Usuarios();
        Aparelhos_Clientes aparelhos = new Aparelhos_Clientes();
        OS os = new OS();
        Querys querys = new Querys();
        Orcamento orcamento = new Orcamento();

        /*querys.selectClientes();*/

        System.out.println("----------------------------- MENU OPÇÕES -----------------------------");
        System.out.println("1 - Inserir Clientes");
        System.out.println("2 - Inserir Usuário");
        System.out.println("3 - Cadastrar Aparelhos");
        System.out.println("4 - Criar OS");
        System.out.println("5 - Criar Orçamento");
        System.out.println("0 - Sair");

        try {
            while (true) {
                System.out.println("Escolha: ");
                String opção = sc.nextLine();

                if (opção.equals("1")) {
                    try {
                        services.inserirCliente(em, sc.nextLine(), sc.nextLine(), sc.nextLine());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e);
                    }
                } else if (opção.equals("2")) {
                    users.inserirUsuarios(em, sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine());
                }else if (opção.equals("3")) {
                    aparelhos.cadastrarAparelhos(em);
                }else if (opção.equals("4")) {
                    os.criarOSAutomatica(em);
                }else if(opção.equals("5")){
                    orcamento.criarOrcamento();
                } else if (opção.equals("0")) {
                    break;
                } else {
                    System.out.println("Opção Inválida");
                }
            }
        }finally {
            em.close();
            emf.close();
            sc.close();
        }
    }
}

