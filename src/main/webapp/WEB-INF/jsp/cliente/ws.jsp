<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
{
    "id": ${cliente.id}
    , "nombre": "${cliente.nombre}"
    , "rut": ${cliente.rut}
    , "telefono": "${cliente.telefono}"
    , "email": "${cliente.email}"
}
                       
                                