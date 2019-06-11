package facade;

import entity.History;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import utils.PuSelector;


/**
 *
 * @author Mads
 */
public class Facade {

    public List<History> getHistory() {
        EntityManager em = PuSelector.getEntityManagerFactory("pu").createEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM history AS a");
            List<History> history = (List<History>) query.getResultList();

            return history;
        } finally {
            em.close();
        }

    }
    
    

}
