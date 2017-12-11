<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c>
    
        <body  BACKGROUND="img/fondoAuto.jpg">
    
    
</c>





<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: slategrey;">
    
    <br /><br />



    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent" >
        <ul class="navbar-nav mr-auto">
            <li class="nav-item" >
                <a class="${pageContext.request.servletPath=='/index.jsp'?'active ':''}nav-link" href="index.jsp">Inicio</a>
            </li>
            <li class="nav-item">
                <a class="${pageContext.request.servletPath=='/WEB-INF/jsp/carrito/carrito.jsp'?'active ':''}nav-link" href="carrito">Pago Estacionamientos</a>
            </li>            
            <li class="nav-item">
                <a class="${pageContext.request.servletPath=='/WEB-INF/jsp/estacionamiento/listar.jsp'?'active ':''}nav-link" href="catalogo">Estacionamiento</a>
            </li>            
                      
            <li class="nav-item">
                <a class="${pageContext.request.servletPath=='/WEB-INF/jsp/pedido/listar.jsp'?'active ':''}nav-link" href="pedidos">Ver Estacionamiento</a>
            </li>
            <li class="nav-item">
                <a class="${pageContext.request.servletPath=='/WEB-INF/jsp/cliente/listar.jsp'?'active ':''}nav-link" href="clientes">Clientes</a>
            </li>            
        </ul>


        
    </div>
</nav>
<br />