package cl.duoc.dej.tienda.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

@Entity
public class Estacionamiento implements Serializable {

    static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true, nullable = false)
    private String nombre;
  
    
    @NotNull
    @Column(nullable = false)
    private Long precio;
    
    @NotNull
    @Column(nullable = false)
    private Long ticket;
   

    // Constructores
    public Estacionamiento() {
    }

    public Estacionamiento(Long id) {
        this.id = id;
    }

    public Estacionamiento(Long id, String nombre, Long precio, Long ticket) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.ticket = ticket;
    }

    public Estacionamiento(String nombre, Long precio, Long ticket) {
        this.nombre = nombre;
        this.precio = precio;
        this.ticket = ticket;
    }
    
    
   

    

    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public Long getTicket() {
        return ticket;
    }

    public void setTicket(Long ticket) {
        this.ticket = ticket;
    }
    

   

}
