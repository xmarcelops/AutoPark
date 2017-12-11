<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Estacionamientos</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <jsp:include page="/WEB-INF/jspf/header.jsp" />
    </head>
    <body>
        <div class="container" style="background: whitesmoke">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />

            <!-- formulario de búsqueda -->
            <form method="get" action="catalogo">
                <input type="hidden" name="op" value="buscar" />
                <div class="form-row align-items-center">
                    <div class="col-6">
                        <label class="sr-only" for="estacionamiento">Estacionamiento</label>
                        <input type="text" name="estacionamiento" id="estacionamiento" value="${fn:escapeXml(estacionamientoBuscado)}" class="form-control form-control-lg mb-2 mb-sm-0" placeholder="Ingrese el nombre del estacionamiento a buscar">
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-warning">Buscar</button>
                    </div>
                </div>
            </form>
            <!-- END formulario de búsqueda -->


            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />



            <c:if test="${empty estacionamientos}">
                No hay estacionamientos para mostrar.
                </br>
                </br>
                </br>
                </br>
            </c:if>            

            <c:if test="${!empty estacionamientos}">
                <!-- tabla con estacionamientos -->

                <table class="table table-bordered table-dark">

                    <thead class="thead-inverse">
                        <tr>

                            <th scope="col">ID</th>
                            <th scope="col">Estacionamiento</th>
                            <th scope="col">Precio</th>
                            <th scope="col">Acciones</th>      
                        </tr>
                    </thead>             


                    <tbody>
                        <c:forEach items="${estacionamientos}" var="p">
                            <tr style="background-color: khaki">
                                <th>${p.id}</th>
                                <td><dt>${p.nombre}<dt></td>
                        <td><dt>
                            $<fmt:formatNumber value="${p.precio}" />
                        </dt></td>
                        <td>
                            <!-- agregar al carro -->
                            <form method="post" action="carrito" class="form-inline">
                                <div class="form-group">
                                    <input name="cantidad" value="1" type="number" min="1" max="10" />
                                    <input type="hidden" name="estacionamientoId" value="${p.id}" />
                                </div>
                                <button type="submit" class="btn btn-primary">Agregar</button>
                            </form>
                        </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </br>
                </br>
            </c:if>
        </div>
        <jsp:include page="/WEB-INF/jspf/footer.jsp" />
    </body>
</html>