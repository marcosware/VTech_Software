package VdeVigilancia.Projeto_OS.Query_Banco;

import VdeVigilancia.Projeto_OS.Dominio.*;

import javax.persistence.*;
import java.util.List;

public class Querys {

    EntityManager em = null;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_V_Tech");
    public void createDB (){
        try {
            em = emf.createEntityManager();

            String createDB = "create database if not exists banco_Vtech";

            em.getTransaction().begin();

            Query create = em.createNativeQuery(createDB);
            create.executeUpdate();

            em.getTransaction().commit();

            System.out.println("Banco criado com sucesso");

        } catch (Exception e){
            if (em!= null && em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            System.out.println("Erro ao executar a query: " + e.getMessage());
            e.printStackTrace();
    }finally {
            if(em != null);
            em.close();
        }


    }

    public void selectClientes (){
        em = emf.createEntityManager();

        TypedQuery<Clientes> queryClientes = em.createQuery("select c from Clientes c", Clientes.class);
        List <Clientes> todosClientes =  queryClientes.getResultList();

        if (todosClientes.isEmpty()){
            System.out.println("Nenhum cliente encontrado");
        }else {
             for (Clientes cliente : todosClientes){
                 System.out.println("ID: " + cliente.getId() + " , nome: " + cliente.getNome());
             }
        }
    }


}
