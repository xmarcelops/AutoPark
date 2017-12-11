<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Comprobante de Compra</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <jsp:include page="/WEB-INF/jspf/header.jsp" />
        <style type="text/css">
            @media print {
                #logo
                , nav.navbar
                , #btn-imprimir
                , #usuario
                {
                    display: none;
                }


            }
        </style>
    </head>

    <body>
        <div class="container" style="background: whitesmoke">


            <jsp:include page="/WEB-INF/jspf/menu.jsp" />
            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />

            <c:if test="${empty pedido.lineasPedido}">
                No se ha realizado ningun Pago.
            </c:if>            

            <c:if test="${!empty pedido.lineasPedido}">
                <div id="comprobante">
                    <a id="btn-imprimir" class="btn btn-success" href="javascript:window.print();">Imprimir</a>
                    <center><h1>VOUCHER Nº ${pedido.id}</h1></center>
                    <br /><br />
                    <div class="row">


                        <div class="col">
                            AutoPark Estacionamientos<br />                           
                            EMAIL: consultas@autopark.cl<br />
                            TELÉFONO: 228509538<br />
                        </div>
                        <div class="col">
                            R.U.T.: 88.367.254.8<br />
                            FACTURA ELECTRÓNICA<br />                            
                            S.I.I. - SANTIAGO ORIENTE<br />                            
                        </div>
                    </div>

                    <br /><br />
                    <table border="1" style="width: 100%;">
                        <thead>
                            <tr>

                                <td>Estacionamiento</td>
                                <td>Monto</td>
                                <td>N° Ticket</td>

                            </tr>
                        </thead>                        
                        <tbody>
                            <c:forEach items="${pedido.lineasPedido}" var="lp">

                                <tr>
                                    <td>${lp.estacionamiento.nombre}</td>                                    
                                    <td><fmt:formatNumber value="${lp.precio}" /></td>
                                    <td>${lp.estacionamiento.ticket}</td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>
                    </br> 
                    <div class="row">
                        <div class="col"></div>
                        <div class="col"></div>
                        <div class="col text-right">
                            <br /><br />
                            <table>
                                <tr>
                                    <td>MONTO NETO $</td>
                                    <td><fmt:formatNumber value="${pedido.total}" /></td>
                                </tr>
                                <tr>
                                    <td>I.V.A. 19% $</td>
                                    <td><fmt:formatNumber value="${pedido.iva}" /></td>
                                </tr>
                                <tr>
                                    <td>IMPUESTO ADICIONAL $</td>
                                    <td>0</td>
                                </tr>

                            </table>
                        </div>
                    </div>
                </div>
                <center>
                    <h1>Total a pagar: $<fmt:formatNumber value="${pedido.totalConIva}" /></h1>
                    </br>
                    </br>
                    </br>
                </center>
            </c:if>
            <center>
                <h3>Muchas gracias por preferirnos </h3>
            </center>
            </br>
            </br>
            </br>
        </div>

        <jsp:include page="/WEB-INF/jspf/footer.jsp" />
    </body>
</html>