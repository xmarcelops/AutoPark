<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Pago de estacionamientos</title>

        <jsp:include page="/WEB-INF/jspf/header.jsp" />
        <style>
            fieldset {
                border: 1px solid #ccc;
                padding: 10px;
            }

            legend {
                display: inline;
                padding: 0 10px;
                width: auto;
            }
        </style>
    </head>    
    <body>
    <center>
        <div class="container" style="background: whitesmoke">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />
            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />

            <c:if test="${empty pedido.lineasPedido}">
                No hay Estacionamientos para pagar.
                </br>
                </br>
            </c:if>            

            <c:if test="${!empty pedido.lineasPedido}">
                <h2>Datos Empresa</h2>

                <form method="get" action="carrito">



                    <input type="hidden" name="id" value="0" />
                    <table class="col col-lg-8 padre">     
                        <tr>
                            <th><label for="rut">Rut</label></th>
                            <th>

                                <input type="number" class="form-control" id="rut" name="rut" placeholder="Ingrese el rut del empresa" aria-describedby="rut-help">
                            </th>
                        </tr>
                        <tr>
                            <th><label for="nombre">Nombre</label></th>
                            <th><input type="text" class="form-control" id="nombre" name="nombre" placeholder="Ingrese el nombre de su empresa" aria-describedby="nombre-help"></th>
                        </tr>
                        <tr>
                            <th><label for="telefono">Telefono</label></th>
                            <th>
                                <div class="input-group">                                
                                    <input type="text" class="form-control" id="telefono" name="telefono" placeholder="Ingrese el telefono de su empresa" aria-describedby="telefono-help">
                                </div><!-- end input-group--></th>
                        </tr>
                        <tr>
                            <th><label>E-mail</label></th>
                            <th><div class="input-group">                                
                                    <input type="text" class="form-control" id="email" name="email" placeholder="Ingrese E-mail de la empresa" aria-describedby="email-help">
                                </div><!-- end input-group--></th>
                        </tr>
                    </table>
                    <table>
                        <tr>
                            <th>Opciones de Pago</th>
                            <th><input type="radio" name="medioPago" value="transferencia" checked="true">Transferencia<br>
                                <input type="radio" name="medioPago" value="pagoEnLinea">Pago en Linea<br>
                                <input type="radio" name="medioPago" value="ordenDeCompra">Orden de Compra<br>
                            </th>
                        </tr>
                        <tr>
                            <th>Opciones de Envio Boleta</th>
                            <th><input type="radio" name="opcionRetiro" value="oficina" checked="true">Correo Electronico<br>
                                <input type="radio" name="opcionRetiro" value="envio">Direccion Particular<br>
                            </th>
                        </tr>
                    </table>

                    </br>
                    <center>

                        <button type="submit" class="btn btn-success">Pagar</button>

                    </center>
                    </br>




                    <input type="hidden" name="op" value="comprar" />

                </form>

                <!-- tabla con carreteras -->
                TOTAL: $<fmt:formatNumber value="${pedido.total}" />
                <table class="col col-lg-8 padre">
                    <thead class="thead-inverse">
                        <tr>
                            <th>Carretera</th>
                            <th>Precio</th>
                            <th>Cantidad</th>
                            <th>Subtotal</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${pedido.lineasPedido}" var="lp">
                            <tr>

                                <td>${lp.estacionamiento.nombre}</td>
                                <td>
                                    $<fmt:formatNumber value="${lp.precio}" />
                                </td>
                                <td>${lp.cantidad}</td>
                                <td>
                                    $<fmt:formatNumber value="${lp.subtotal}" />
                                </td>                                
                                <td>
                                    <form method="get" action="carrito" class="form-inline">
                                        <input type="hidden" name="op" value="quitar" />
                                        <input type="hidden" name="estacionamientoId" value="${lp.estacionamiento.id}" />
                                        <button type="submit" class="btn btn-primary">Quitar</button>
                                    </form>                                    
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                </br>
                </br>
                </br>
            </c:if>
        </div>
            </br>
            </br>
    </center>

    <jsp:include page="/WEB-INF/jspf/footer.jsp" />

    <script type="text/javascript">
        jQuery(function () {
            jQuery("#rut").blur(function () {
                var url = "${pageContext.request.contextPath}/clientes";
                var d = {op: "ws", rut: jQuery(this).val()};
                jQuery.ajax({
                    type: "GET"
                    , url: url
                    , data: d
                    , dataType: "json"
                }).done(function (res) {
                    console.log("procesando respuesta JSON");
                    jQuery("#nombre").val(res.nombre);
                    jQuery("#telefono").val(res.telefono);
                    jQuery("#email").val(res.email);
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    console.log("AJAX call failed: " + textStatus + ", " + errorThrown);
                });
            });
        });

    </script>
    <style>

        .padre {

            background-color: #fafafa;
            margin: 1rem;
            padding: 1rem;
            border: 2px solid #ccc;
            /* IMPORTANTE */

        }
    </style>
</body>
</html>