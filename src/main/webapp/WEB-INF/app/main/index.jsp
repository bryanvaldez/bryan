<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:master title="main">

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/program/main.css" media="screen,projection"/>	 
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/controller/mainController.js"></script>

    <div style="margin-top:40px;" ng-cloak ng-controller="mainCtrl">   
        <div layout="row" class="container-block" layout-wrap layout-fill>
            <c:forEach items="${USUARIO_AUTENTICADO.perfil.opciones}" var="item" varStatus="a">            
                <c:choose>
                    <c:when test="${a.index+1 == '1' || a.index+1 == '2' }">
                        <md-button ng-click="updateExp(${item.id})" href="${pageContext.request.contextPath}/${item.modulo.enlace}/${item.enlace}" class=" mblock md-raised animated fadeIn" md-whiteframe="${a.index+1}" flex-xs="30" flex-gt-xs="30"  layout layout-align="center center"><h2 class="title">${item.nombre}</h2></md-button>
                    </c:when>
                    <c:otherwise>
                         <md-button ng-click="updateExp(${item.id})" href="${pageContext.request.contextPath}/${item.modulo.enlace}/${item.enlace}" class="mblock md-raised animated fadeIn" md-whiteframe="${a.index+1}" flex-xs="30" flex-gt-xs="30" layout layout-align="center center"><h2 class="title">${item.nombre}</h2></md-button>
                    </c:otherwise>
                    </c:choose>
                </c:forEach>                        
        </div> 
    </div>
</t:master>