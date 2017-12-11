package cl.duoc.dej.tienda.service;

import cl.duoc.dej.tienda.entity.Cliente;
import cl.duoc.dej.tienda.entity.LineaPedido;
import cl.duoc.dej.tienda.entity.Pedido;
import cl.duoc.dej.tienda.entity.Estacionamiento;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class PedidoBuilder implements Serializable {

    static final long serialVersionUID = 52L;
    
    Pedido pedido;
    
    @EJB
    EstacionamientoService estacionamientoService;
    @EJB
    ClienteService clienteService;
    
    
    public PedidoBuilder() {
        pedido = new Pedido();
    }
    
    public PedidoBuilder setCliente(Long clienteId) {
        Cliente cliente = clienteService.getClienteById(clienteId);
        pedido.setCliente(cliente);
        return this;
    }
    
    
    
    public PedidoBuilder agregarEstacionamiento(Long estacionamientoId, int cantidad) {
        Estacionamiento estacionamiento = estacionamientoService.getEstacionamientoById(estacionamientoId);
        LineaPedido lineaPedido = new LineaPedido(estacionamiento, cantidad, estacionamiento.getPrecio());
        pedido.getLineasPedido().add(lineaPedido);
        return this;
    }

    public Pedido build() {
        Pedido p = pedido;
        pedido = new Pedido();
        return p;
    }

    
    
    
}
