<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:master title="Registro Elector">
    <jsp:body>
        
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/program/carga.css" media="screen,projection"/>	    
        <script charset="UTF-8" type="text/javascript" src="${pageContext.request.contextPath}/js/controller/registroController.js"></script>
        <div  style="background:#fff; padding: 30px 50px 0px 50px;" ng-controller="registroCtrl" id="popupContainer">            
            <div class="carga-section" ng-show="showLista">   
                <jsp:include page="/WEB-INF/app/registro/lista.jsp"/>
            </div>   

            <div class="carga-section" ng-show="showRegistro">   
                <jsp:include page="/WEB-INF/app/registro/registro.jsp"/>
            </div>                        
            
            <div class="carga-section" ng-show="showReimprimir">   
                <jsp:include page="/WEB-INF/app/registro/reimprimir.jsp"/>
            </div>
        </div>
    </jsp:body>        

</t:master>