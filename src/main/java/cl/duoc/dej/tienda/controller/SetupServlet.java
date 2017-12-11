package cl.duoc.dej.tienda.controller;

import cl.duoc.dej.tienda.entity.Cliente;
import cl.duoc.dej.tienda.entity.Pedido;
import cl.duoc.dej.tienda.entity.Estacionamiento;

import cl.duoc.dej.tienda.service.ClienteService;
import cl.duoc.dej.tienda.service.PedidoBuilder;
import cl.duoc.dej.tienda.service.PedidoService;
import cl.duoc.dej.tienda.service.EstacionamientoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SetupServlet", urlPatterns = {"/setup"})
public class SetupServlet extends HttpServlet {

    @EJB
    EstacionamientoService estacionamientoService;

    @EJB
    ClienteService clienteService;

    @EJB
    PedidoService pedidoService;

    @EJB
    PedidoBuilder pedidoBuilder;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> mensajes = new ArrayList<>();
        List<String> errores = new ArrayList<>();

        try {

            Calendar fechaNacimiento = Calendar.getInstance();
            fechaNacimiento.add(Calendar.YEAR, -20);
            Cliente cliente1 = new Cliente(181889470, "56912345678", "Entel", "entel@gmail.com");
            
            clienteService.crearCliente(cliente1);
            
            mensajes.add(String.format("Cliente %s creado con ID %s", cliente1.getNombre(), cliente1.getId()));
            

            Estacionamiento es1 = new Estacionamiento("Plaza Civica - Valparaiso", 2000L, 12345L);
            es1 = estacionamientoService.crearEstacionamiento(es1);
            Estacionamiento plazaCivica = es1;
            mensajes.add(String.format("Estacionamiento %s creado con ID %s", es1.getNombre(), es1.getId()));
            
            Estacionamiento es2 = new Estacionamiento("Mirador el Sol - Quilpue", 3000L, 23127L);
            es2 = estacionamientoService.crearEstacionamiento(es2);
            Estacionamiento miradorElSol = es2;
            mensajes.add(String.format("Estacionamiento %s creado con ID %s", es2.getNombre(), es2.getId()));
            
            Estacionamiento es3 = new Estacionamiento("Plaza de Armas - Santiago", 500L, 35343L);
            es1 = estacionamientoService.crearEstacionamiento(es3);
            Estacionamiento plazaDeArmasS = es3;
            mensajes.add(String.format("Estacionamiento %s creado con ID %s", es3.getNombre(), es3.getId()));
            
            Estacionamiento es4 = new Estacionamiento("Plaza de Armas - Concepcion", 1500L, 25543L);
            es1 = estacionamientoService.crearEstacionamiento(es4);
            Estacionamiento plazaDeArmasC = es4;
            mensajes.add(String.format("Estacionamiento %s creado con ID %s", es4.getNombre(), es4.getId()));

            

            

            Pedido pedido = pedidoBuilder.setCliente(cliente1.getId())
                    .agregarEstacionamiento(plazaCivica.getId(), 1)
                    .agregarEstacionamiento(miradorElSol.getId(), 2)
                    .agregarEstacionamiento(plazaDeArmasS.getId(), 3)
                    .agregarEstacionamiento(plazaDeArmasC.getId(), 4)
                    
                    .build();
            pedido.setMedioPago("transferencia");
            pedido.setOpcionRetiro("oficina");
            pedido.setTotalF(pedido.getTotal());
            pedido.setEstacionamientos(pedido.getEstacionamientosNombres());
            pedidoService.crearPedido(pedido);

        } catch (Exception e) {
            errores.add(e.getMessage());
        }

        request.setAttribute("mensajes", mensajes);
        request.setAttribute("errores", errores);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

}
