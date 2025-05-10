package VdeVigilancia.Projeto_OS.Query_Banco;

import VdeVigilancia.Projeto_OS.Dominio.*;

import javax.persistence.*;
import java.util.List;

import static VdeVigilancia.Projeto_OS.Application.Programa.sc;

public class Querys {

    EntityManager em = null;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_V_Tech");

    public void createDB() {
        try {
            em = emf.createEntityManager();

            String createDB = "create database if not exists banco_Vtech";

            em.getTransaction().begin();

            Query create = em.createNativeQuery(createDB);
            create.executeUpdate();

            em.getTransaction().commit();

            System.out.println("Executado com Sucesso");

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao executar a query: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null) ;
            em.close();
        }


    }

    public void selectClientes() {
        EntityManager em = null;

        try {
            em = emf.createEntityManager();  // abre o EntityManager

            TypedQuery<Clientes> queryClientes = em.createQuery("SELECT c FROM Clientes c", Clientes.class);
            List<Clientes> todosClientes = queryClientes.getResultList();

            if (todosClientes.isEmpty()) {
                System.out.println("Nenhum cliente encontrado");
            } else {
                for (Clientes cliente : todosClientes) {
                    System.out.println("ID: " + cliente.getId() + " , nome: " + cliente.getNome());
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar clientes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();  // fecha o EntityManager corretamente
            }
        }
    }


    public void selectWhereClientes() {
        EntityManager em = null;

        try {
            System.out.print("Digite o ID do cliente: ");
            String idStr = sc.nextLine();

            if (idStr == null || idStr.trim().isEmpty()) {
                System.out.println("ID não pode ser vazio.");
                return;
            }

            int id = Integer.parseInt(idStr.trim()); // agora seguro para converter

            em = emf.createEntityManager();

            TypedQuery<Clientes> clientesID = em.createQuery(
                    "SELECT c FROM Clientes c WHERE c.id = :id", Clientes.class
            );
            clientesID.setParameter("id", id);

            List<Clientes> clientesComEsseID = clientesID.getResultList();

            if (clientesComEsseID.isEmpty()) {
                System.out.println("Nenhum cliente encontrado com este ID: " + id);
            } else {
                for (Clientes cliente : clientesComEsseID) {
                    System.out.println("ID: " + cliente.getId() + ", Nome: " + cliente.getNome());
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("ID inválido. Por favor, digite apenas números.");
        } catch (Exception e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void selectAparelhos() {
        try {
            em = emf.createEntityManager();  // abre o EntityManager

            TypedQuery<Aparelhos_Clientes> queryAparelhos = em.createQuery("SELECT c FROM Aparelhos_Clientes c", Aparelhos_Clientes.class);
            List<Aparelhos_Clientes> todosAparelhos = queryAparelhos.getResultList();

            if (todosAparelhos.isEmpty()) {
                System.out.println("Nenhum cliente encontrado");
            } else {
                for (Aparelhos_Clientes aparelhos : todosAparelhos) {
                    System.out.println("ID: " + aparelhos.getId());
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar clientes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();  // fecha o EntityManager corretamente
            }
        }
    }

    public Aparelhos_Clientes selectWhereAparelhos(EntityManager em) {
        try {
            System.out.print("Digite o ID do aparelho: ");
            String idAparelho = sc.nextLine();

            if (idAparelho == null || idAparelho.trim().isEmpty()) {
                System.out.println("ID não pode ser vazio.");
                return null;
            }

            int id = Integer.parseInt(idAparelho.trim());

            Aparelhos_Clientes aparelho = em.find(Aparelhos_Clientes.class, id);

            if (aparelho == null) {
                System.out.println("Nenhum aparelho encontrado com este ID: " + id);
            } else {
                System.out.println("ID encontrado: " + aparelho.getId());
            }

            return aparelho;

        } catch (NumberFormatException e) {
            System.err.println("ID inválido. Por favor, digite apenas números.");
            return null;
        } catch (Exception e) {
            System.err.println("Erro ao buscar aparelho por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public OS selectOS() {
        try {
            em = emf.createEntityManager();  // abre o EntityManager

            TypedQuery<OS> queryOS = em.createQuery("SELECT c FROM OS c", OS.class);
            List<OS> ordemServico = queryOS.getResultList();

            if (ordemServico.isEmpty()) {
                System.out.println("Nenhum cliente encontrado");
            } else {
                for (OS ordem : ordemServico) {
                    System.out.println("ID: " + ordem.getId());
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar clientes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();  // fecha o EntityManager corretamente
            }
        }
        return selectOS();
    }
    public OS selectWhereOS() {
        try {
            System.out.print("Digite o ID do aparelho: ");
            String idOS = sc.nextLine();

            if (idOS == null || idOS.trim().isEmpty()) {
                System.out.println("ID não pode ser vazio.");
                return null;
            }

            int id = Integer.parseInt(idOS.trim());

            OS ordem = em.find(OS.class, id);

            if (ordem == null) {
                System.out.println("Nenhum aparelho encontrado com este ID: " + id);
            } else {
                System.out.println("ID encontrado: " + ordem.getId());
            }

        } catch (NumberFormatException e) {
            System.err.println("ID inválido. Por favor, digite apenas números.");
            return null;
        } catch (Exception e) {
            System.err.println("Erro ao buscar aparelho por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return selectOS();
    }
    }






