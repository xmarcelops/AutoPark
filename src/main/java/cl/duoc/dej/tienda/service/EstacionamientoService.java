package cl.duoc.dej.tienda.service;

import cl.duoc.dej.tienda.entity.Estacionamiento;
import cl.duoc.dej.tienda.exception.EstacionamientoNoEncontradoException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class EstacionamientoService {

    static final long serialVersionUID = 11L;
    
    @PersistenceContext
    EntityManager em;

    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public EstacionamientoService() {
    }

    public Estacionamiento crearEstacionamiento(Estacionamiento estacionamiento) {
        em.persist(estacionamiento);
        return estacionamiento;
    }

    public List<Estacionamiento> getEstacionamientos() {
        TypedQuery<Estacionamiento> query = em.createQuery("SELECT p FROM Estacionamiento p", Estacionamiento.class);
        return query.getResultList();
    }

    /**
     *
     * @param id
     * @return retorna el estacionamiento o nulo en caso de no ser encontrado
     */
    public Estacionamiento getEstacionamientoById(Long id) {
        return em.find(Estacionamiento.class, id);
    }

    public List<Estacionamiento> buscarEstacionamiento(String nombreEstacionamiento) {
        if (nombreEstacionamiento != null && !nombreEstacionamiento.isEmpty() ) {
            String jpql = "SELECT p FROM Estacionamiento p WHERE LOWER(p.nombre) LIKE :nombre ";
            TypedQuery<Estacionamiento> query = em.createQuery(jpql, Estacionamiento.class);
            query.setParameter("nombre", "%" + nombreEstacionamiento + "%");
           
            return query.getResultList();
        }

        if (nombreEstacionamiento != null && !nombreEstacionamiento.isEmpty()) {
            String jpql = "SELECT p FROM Estacionamiento p WHERE LOWER(p.nombre) LIKE :nombre";
            TypedQuery<Estacionamiento> query = em.createQuery(jpql, Estacionamiento.class);
            query.setParameter("nombre", "%" + nombreEstacionamiento + "%");
            return query.getResultList();
        }

       

        // sino devuelve la lista completa de estacionamientos
        return getEstacionamientos();
    }

    public void eliminarEstacionamiento(Long estacionamientoId) throws EstacionamientoNoEncontradoException {
        Estacionamiento p = getEstacionamientoById(estacionamientoId);
        if (p == null) {
            String mensajeException = String.format("Estacionamiento con ID %s no encontrado para ser eliminado", estacionamientoId);
            logger.log(Level.SEVERE, mensajeException);
            throw new EstacionamientoNoEncontradoException(mensajeException);
        }
        em.remove(p);
    }

}
