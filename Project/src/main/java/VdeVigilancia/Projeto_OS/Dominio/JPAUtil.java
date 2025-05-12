package VdeVigilancia.Projeto_OS.Dominio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_V_Tech");

    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
