package cl.duoc.dej.tienda.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class LineaPedido implements Serializable {

    static final long serialVersionUID = 564654L;
    
    @ManyToOne
    Estacionamiento estacionamiento;
    @Column(nullable = false)
    int cantidad;
    @Column(nullable = false)
    Long precio;

    // Constructores
    public LineaPedido() {
    }

    public LineaPedido(Estacionamiento estacionamiento, int cantidad) {
        this.estacionamiento = estacionamiento;
        this.cantidad = cantidad;
        this.precio = estacionamiento.getPrecio();
    }
    
    public LineaPedido(Estacionamiento estacionamiento, int cantidad, Long precio) {
        this.estacionamiento = estacionamiento;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    
    // Cálculos
    public Long getSubtotal() {
        return cantidad * precio;
    }
    
    
    // getters y setters
    public Estacionamiento getEstacionamiento() {
        return estacionamiento;
    }

    public void setEstacionamiento(Estacionamiento estacionamiento) {
        this.estacionamiento = estacionamiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

}
