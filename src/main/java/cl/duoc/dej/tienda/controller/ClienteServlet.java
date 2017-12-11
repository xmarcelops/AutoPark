package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Estacionamiento;
import cl.duoc.dej.tienda.entity.Cliente;
import cl.duoc.dej.tienda.exception.EstacionamientoNoEncontradoException;
import cl.duoc.dej.tienda.exception.ClienteNoEncontradoException;
import cl.duoc.dej.tienda.service.EstacionamientoService;
import cl.duoc.dej.tienda.service.ClienteService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ClienteServlet", urlPatterns = {"/clientes"})
public class ClienteServlet extends HttpServlet {

    @EJB
    ClienteService clienteService;
    @EJB
    EstacionamientoService estacionamientoService;

    private final String JSP_LISTA_PRODUCTOS = "/WEB-INF/jsp/cliente/listar.jsp";
    private final String JSP_CREAR = "index.jsp";
    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("op");
        operacion = operacion != null ? operacion : "";
        switch (operacion) {
            case "iniciar":
                iniciar(request,response);
                break;
            case "crear":
                request.setAttribute("estacionamientos", estacionamientoService.getEstacionamientos());
                request.getRequestDispatcher(JSP_CREAR).forward(request, response);
                break;
            case "buscar":
                buscar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            case "listar":
            default:
                listar(request, response);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response, List<Cliente> clientes) throws ServletException, IOException {
        List<Estacionamiento> estacionamientos = estacionamientoService.getEstacionamientos();

        request.setAttribute("clientes", clientes);
        request.setAttribute("estacionamientos", estacionamientos);
        request.getRequestDispatcher(JSP_LISTA_PRODUCTOS).forward(request, response);
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cliente> clientes = clienteService.getClientes();
        listar(request, response, clientes);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String stringId = request.getParameter("id");
        Long clienteId = 0L;
        try {
            clienteId = Long.parseLong(stringId);
            clienteService.eliminarCliente(clienteId);
            mensaje = String.format("Se ha eliminado correctamente el cliente con ID %s", clienteId);
            logger.log(Level.INFO, mensaje);
            request.setAttribute("mensajes", mensajes);
            mensajes.add(mensaje);
        } catch (NumberFormatException nfe) {
            error = String.format("Formato de ID inválido");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        } catch (ClienteNoEncontradoException ex) {
            error = String.format("No se pudo encontrar el cliente con el ID especificado");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        }

        listar(request, response);
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clienteBuscado = request.getParameter("cliente");
        String stringRut = request.getParameter("stringRut");
        Long clienteId = null;
        try {
            if (stringRut != null) {
                clienteId = Long.parseLong(stringRut);
            }
        } catch (NumberFormatException nfe) {
            logger.log(Level.INFO, "El Estacionamiento ID entregada no corresponde a un ID válido");
        }
        List<Cliente> clientes = clienteService.buscarCliente(clienteBuscado, clienteId);
        listar(request, response, clientes);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";
        
        String nombre = request.getParameter("nombre");
        
        int stringRut = Integer.parseInt(request.getParameter("rut"));
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");
        
        
        try {
            
            
                        
            Cliente cliente = new Cliente(stringRut, telefono, nombre, email);
            cliente = clienteService.crearCliente(cliente);
            mensaje = String.format("Cliente %s creada correctamente con ID %s", cliente.getNombre(), cliente.getId());
            mensajes.add(mensaje);
        } catch(NumberFormatException nfe) {
            errores.add("Formato numérico incompatible");
        } 
        
        request.setAttribute("errores", errores);
        request.setAttribute("mensajes", mensajes);
        request.getRequestDispatcher(JSP_CREAR).forward(request, response);
    }
    
    private void iniciar(HttpServletRequest request, HttpServletResponse response) {
        List<Cliente> clientes = clienteService.getClientes();
        request.setAttribute("clientes", clientes);
    }

}
