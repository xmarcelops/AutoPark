package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Estacionamiento;

import cl.duoc.dej.tienda.exception.EstacionamientoNoEncontradoException;

import cl.duoc.dej.tienda.service.EstacionamientoService;
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

@WebServlet(name = "EstacionamientoServlet", urlPatterns = {"/catalogo"})
public class EstacionamientoServlet extends HttpServlet {

    @EJB
    EstacionamientoService estacionamientoService;

    private final String JSP_LISTA_PRODUCTOS = "/WEB-INF/jsp/estacionamiento/listar.jsp";
    private final String JSP_CREAR = "/WEB-INF/jsp/estacionamiento/crear.jsp";
    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("op");
        operacion = operacion != null ? operacion : "";
        switch (operacion) {

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

    private void listar(HttpServletRequest request, HttpServletResponse response, List<Estacionamiento> estacionamientos) throws ServletException, IOException {

        request.setAttribute("estacionamientos", estacionamientos);

        request.getRequestDispatcher(JSP_LISTA_PRODUCTOS).forward(request, response);
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Estacionamiento> estacionamientos = estacionamientoService.getEstacionamientos();
        listar(request, response, estacionamientos);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String stringId = request.getParameter("id");
        Long estacionamientoId = 0L;
        try {
            estacionamientoId = Long.parseLong(stringId);
            estacionamientoService.eliminarEstacionamiento(estacionamientoId);
            mensaje = String.format("Se ha eliminado correctamente el estacionamiento con ID %s", estacionamientoId);
            logger.log(Level.INFO, mensaje);
            request.setAttribute("mensajes", mensajes);
            mensajes.add(mensaje);
        } catch (NumberFormatException nfe) {
            error = String.format("Formato de ID inválido");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        } catch (EstacionamientoNoEncontradoException ex) {
            error = String.format("No se pudo encontrar el estacionamiento con el ID especificado");
            logger.log(Level.SEVERE, error);
            errores.add(error);
        }

        listar(request, response);
    }

    private void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String estacionamientoBuscada = request.getParameter("estacionamiento");

        List<Estacionamiento> estacionamientos = estacionamientoService.buscarEstacionamiento(estacionamientoBuscada);
        listar(request, response, estacionamientos);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String nombre = request.getParameter("estacionamiento");

        String stringPrecio = request.getParameter("precio");

        Long precio = 0L;
        
        Long ticket= 0L;

        try {

            precio = Long.parseLong(stringPrecio);
            Estacionamiento estacionamiento = new Estacionamiento(nombre, precio, ticket);
            estacionamiento = estacionamientoService.crearEstacionamiento(estacionamiento);
            mensaje = String.format("Estacionamiento %s creada correctamente con ID %s", estacionamiento.getNombre(), estacionamiento.getId());
            mensajes.add(mensaje);

        } catch (NumberFormatException nfe) {
            errores.add("Formato numérico incompatible");

            request.setAttribute("errores", errores);
            request.setAttribute("mensajes", mensajes);
            request.getRequestDispatcher(JSP_CREAR).forward(request, response);
        }
    }

}
