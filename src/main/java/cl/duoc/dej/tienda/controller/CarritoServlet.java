package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Cliente;
import cl.duoc.dej.tienda.entity.LineaPedido;
import cl.duoc.dej.tienda.entity.Pedido;
import cl.duoc.dej.tienda.entity.Estacionamiento;

import cl.duoc.dej.tienda.exception.EstacionamientoNoEncontradoException;
import cl.duoc.dej.tienda.service.ClienteService;
import cl.duoc.dej.tienda.service.PedidoBuilder;
import cl.duoc.dej.tienda.service.PedidoService;
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
import javax.servlet.http.HttpSession;

@WebServlet(name = "CarritoServlet", urlPatterns = {"/carrito"})
public class CarritoServlet extends HttpServlet {

    @EJB
    PedidoBuilder pedidoBuilder;
    @EJB
    PedidoService pedidoService;
    @EJB
    ClienteService clienteService;
    @EJB
    EstacionamientoService estacionamientoService;

    private final static String JSP_CARRITO = "/WEB-INF/jsp/carrito/carrito.jsp";
    private final static String JSP_COMPROBANTE = "/WEB-INF/jsp/carrito/comprobante.jsp";
    Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operacion = request.getParameter("op");

        HttpSession session = request.getSession();
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        Long estacionamientoId = 0L;

        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        if ("quitar".equals(operacion)) {
            String stringEstacionamientoId = request.getParameter("estacionamientoId");
            try {
                estacionamientoId = Long.parseLong(stringEstacionamientoId);
                pedido.quitarEstacionamiento(estacionamientoId);
                mensaje = String.format("Se quitó correctamente el estacionamiento con ID %s del carrito", estacionamientoId);
                mensajes.add(mensaje);
                logger.log(Level.INFO, mensaje);
            } catch (NumberFormatException nfe) {
                error = "ID de estacionamiento mal formateado, no se pudo quitar el estacionamiento del carrito";
                logger.log(Level.SEVERE, error);
                errores.add(error);
            }
        }

        request.setAttribute("errores", errores);
        request.setAttribute("mensajes", mensajes);
        request.setAttribute("pedido", pedido);
        if ("comprar".equals(operacion)) {

            Cliente cliente = buildCliente(request, response);
            if (cliente == null) {
                mensajes.add("Complete todos los campos ");
                request.getRequestDispatcher(JSP_CARRITO).forward(request, response);

                return;
            }

            String pago=request.getParameter("medioPago");
            String retiro=request.getParameter("opcionRetiro");
            
            pedido.setMedioPago(pago);
            pedido.setOpcionRetiro(retiro);
            pedido.setEstacionamientos(pedido.getEstacionamientosNombres());
            pedido.setTotalF(pedido.getTotal());
            pedido.setCliente(cliente);
            pedido = pedidoService.crearPedido(pedido);
            request.setAttribute("pedido", pedido);
            session.invalidate();
            request.getRequestDispatcher(JSP_COMPROBANTE).forward(request, response);
        } else {
            request.getRequestDispatcher(JSP_CARRITO).forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errores = new ArrayList<>();
        List<String> mensajes = new ArrayList<>();
        String error = "";
        String mensaje = "";

        String stringCantidad = request.getParameter("cantidad");
        String stringEstacionamientoId = request.getParameter("estacionamientoId");
        Estacionamiento estacionamiento = null;
        int cantidad = 1;

        try {
            Long estacionamientoId = Long.parseLong(stringEstacionamientoId);
            estacionamiento = estacionamientoService.getEstacionamientoById(estacionamientoId);
            if (estacionamiento == null) {
                throw new EstacionamientoNoEncontradoException();
            }
        } catch (NumberFormatException nfe) {
            error = "Estacionamiento ID mal formateado";
            logger.log(Level.SEVERE, error);
            errores.add(error);
        } catch (EstacionamientoNoEncontradoException pnee) {
            error = "Estacionamiento no encontrado";
            logger.log(Level.SEVERE, error);
            errores.add(error);
        }

        try {
            cantidad = Integer.parseInt(stringCantidad);
        } catch (NumberFormatException nfe) {
            cantidad = 1;
        }

        HttpSession session = request.getSession();
        Pedido pedido = (Pedido) session.getAttribute("pedido");
        if (pedido == null) {
            pedido = pedidoBuilder.agregarEstacionamiento(estacionamiento.getId(), cantidad).build();
        } else {
            pedido.getLineasPedido().add(new LineaPedido(estacionamiento, cantidad));
        }
        session.setAttribute("pedido", pedido);

        request.setAttribute("pedido", pedido);
        request.setAttribute("mensajes", mensajes);
        request.setAttribute("errores", errores);
        request.getRequestDispatcher(JSP_CARRITO).forward(request, response);
    }

    private Cliente buildCliente(HttpServletRequest request, HttpServletResponse response) {

        String stringRut = request.getParameter("rut");

        String nombre = request.getParameter("nombre");

        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        // conversiones
        int rut = Integer.parseInt(stringRut);

        // creación de cliente
        Cliente cliente = clienteService.getClienteByRut(rut);
        if (cliente == null) {
            cliente = new Cliente();
            cliente.setRut(rut);

            cliente.setNombre(nombre);

            cliente.setTelefono(telefono);
            cliente.setEmail(email);
            cliente = clienteService.crearCliente(cliente);
        }

        return cliente;
    }

}
