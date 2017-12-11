<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>

        <title>Bienvenido a la Plataforma de Gestión de Empresas</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <jsp:include page="/WEB-INF/jspf/header.jsp" />
    </head>
    <body>


        <div class="container" style="background: whitesmoke">
            <jsp:include page="/WEB-INF/jspf/menu.jsp" /> 
            
        </div>
        <div class="container" style="background: whitesmoke">

            <%-- mensajes --%>
            <c:if test="${!empty mensajes}">
                <div class="alert alert-primary" role="alert">
                    <ul>
                        <c:forEach items="${mensajes}" var="mensaje">
                            <li>${mensaje}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>

            <%-- errores --%>
            <c:if test="${!empty errores}">
                <div class="alert alert-danger" role="alert">
                    <ul>
                        <c:forEach items="${errores}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </div>
            </c:if>
            <jsp:include page="clientes?op=iniciar" />
            <center>
                <c:if test="${empty clientes}">
                    <a class="btn btn-warning" href="setup">Instalar</a>
                    </br>
                </c:if>
            </center>
            </br>
            </br>
        </div>

        <jsp:include page="/WEB-INF/jspf/footer.jsp" />
    </body>
</html>