<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-3 col-md-2 sidebar">
    <ul class="nav nav-sidebar">
    <c:forEach items="${USUARIO_AUTENTICADO.perfil.opciones}" var="item" >        
        <li><a href="${pageContext.request.contextPath}/${item.modulo.enlace}/${item.enlace}">${item.nombre}</a></li>
    </c:forEach>
    </ul>    
</div>