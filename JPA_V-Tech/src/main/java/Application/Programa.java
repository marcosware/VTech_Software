package Application;

import Dominio.Clientes;
import Dominio.Usuarios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.security.SecureRandom;
import java.util.Scanner;

public class Programa {

    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Clientes services = new Clientes();
        Usuarios users = new Usuarios();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_V_Tech");
        EntityManager em = emf.createEntityManager();

        System.out.println("----------------------------- MENU OPÇÕES -----------------------------");
        System.out.println("1 - Inserir Clientes");
        System.out.println("2 - Inserir Usuário");
        System.out.println("0 - Sair");
        try {
            while (true) {
                System.out.println("Escolha: ");
                String opção = sc.nextLine();

                if (opção.equals("1")) {
                    try {
                        services.inserirCliente(em);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e);
                    }
                } else if (opção.equals("2")) {
                    users.inserirUsuarios(em);
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

