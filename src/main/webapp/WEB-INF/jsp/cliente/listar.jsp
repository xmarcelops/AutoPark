<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Clientes</title>

        <jsp:include page="/WEB-INF/jspf/header.jsp" />
    </head>
    <body>
        <div class="container" style="background: whitesmoke">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" />
            <jsp:include page="/WEB-INF/jspf/mensajes.jsp" />


           


            <c:if test="${empty clientes}">
                No hay clientes para mostrar.
                </br>
                </br>
                </br>
                </br>
                
            </c:if>            

            <c:if test="${!empty clientes}">
                <!-- tabla con categorías -->
                <table class="table table-bordered table-dark">
                    <thead class="thead-inverse">
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>RUT</th>
                            <th>Telfono</th>
                            <th>E-mail</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${clientes}" var="c">
                            <tr style="background-color: khaki">


                                <td><dt>${c.id}</dt></td>
                        <td><dt>${c.nombre}</dt></td>
                        <td><dt>${c.rut}</dt></td>
                        <td><dt>${c.telefono}</dt></td>
                        <td><dt>${c.email}</dt></td>
                        <td>
                            <form method="get" action="clientes">
                                <input type="hidden" name="op" value="eliminar" />
                                <input type="hidden" name="id" value="${c.id}" />
                                <button type="submit" class="btn btn-danger">Eliminar</button>
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