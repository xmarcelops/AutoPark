package cl.duoc.dej.tienda.service;

import cl.duoc.dej.tienda.entity.Cliente;
import cl.duoc.dej.tienda.exception.ClienteNoEncontradoException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class ClienteService implements Serializable {

    static final long serialVersionUID = 33L;

    @PersistenceContext
    private EntityManager em;

    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public Cliente crearCliente(Cliente cliente) {
        if(cliente.getId() == null) {
            em.persist(cliente);    
        } else {
            em.merge(cliente);
        }
        return cliente;
    }

    public Cliente getClienteById(Long clienteId) {
        return em.find(Cliente.class, clienteId);
    }

    public Cliente getClienteByRut(int rut) {
        String jpql = "SELECT c FROM Cliente c WHERE c.rut = :rut";
        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
        query.setParameter("rut", rut);
        Cliente cliente = null;
        try {
            cliente = query.getSingleResult();
        } catch (NoResultException nre) {
            logger.log(Level.SEVERE, "No se encontró ningún cliente con el RUT especificado");
        }
        return cliente;
    }

    public List<Cliente> getClientes() {
        String jpql = "SELECT c FROM Cliente c";
        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
        return query.getResultList();
    }

    public void eliminarCliente(Long clienteId) throws ClienteNoEncontradoException {
        Cliente c = getClienteById(clienteId);
        if (c == null) {
            String mensajeException = String.format("Cliente con ID %s no encontrado para ser eliminado", clienteId);
            logger.log(Level.SEVERE, mensajeException);
            throw new ClienteNoEncontradoException(mensajeException);
        }
        em.remove(c);
    }
    
    public List<Cliente> buscarCliente(String nombreCliente, Long estacionamientoId) {
        if (nombreCliente != null && !nombreCliente.isEmpty() && estacionamientoId != null && estacionamientoId > 0) {
            String jpql = "SELECT p FROM Cliente p WHERE LOWER(p.nombre) LIKE :nombre AND p.estacionamiento.id = :estacionamientoId";
            TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
            query.setParameter("nombre", "%" + nombreCliente + "%");
            query.setParameter("estacionamientoId", estacionamientoId);
            return query.getResultList();
        }

        if (nombreCliente != null && !nombreCliente.isEmpty()) {
            String jpql = "SELECT p FROM Cliente p WHERE LOWER(p.nombre) LIKE :nombre";
            TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
            query.setParameter("nombre", "%" + nombreCliente + "%");
            return query.getResultList();
        }

        if (nombreCliente == null || nombreCliente.isEmpty()) {
            if (estacionamientoId != null && estacionamientoId > 0) {
                String jpql = "SELECT p FROM Cliente p WHERE p.estacionamiento.id = :estacionamientoId";
                TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);                
                query.setParameter("estacionamientoId", estacionamientoId);
                return query.getResultList();
            }

        }

        // sino devuelve la lista completa de clientes
        return getClientes();
    }

}
