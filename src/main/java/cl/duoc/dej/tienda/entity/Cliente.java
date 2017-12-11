package cl.duoc.dej.tienda.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

@Entity
public class Cliente implements Serializable {

    static final long serialVersionUID = 9L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
        
      
    private int rut;        
    
    private String telefono; 
    
    private String nombre;
    
    private String email;
    

    // constructores
    public Cliente() {
    }

    public Cliente(Long id, int rut, String telefono, String nombre, String email) {
        this.id = id;
        this.rut = rut;
        this.telefono = telefono;
        this.nombre = nombre;
        this.email = email;
    }

    public Cliente(int rut, String telefono, String nombre, String email) {
        this.rut = rut;
        this.telefono = telefono;
        this.nombre = nombre;
        this.email = email;
    }

    

   
    

    
    
    

    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

   

    
}
