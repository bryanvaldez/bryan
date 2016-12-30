<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<t:master title="security">

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/program/security/login.css" media="screen,projection"/>	 
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/controller/loginController.js"></script>

    <div layout="column" ng-controller="loginCtrl" ng-cloak>
        <div class="banner">
            <div class="logo2 animated fadeIn low">
                <div class="logo">
                    <span>Registro Automatizado de Electores</span>
                </div>
            </div>
            
        </div>
        <div class="content">
            <form name="form" action="${pageContext.request.contextPath}/j_spring_security_check" method="post" role="form">
                <div layout="row" layout-wrap>
                    <div class="block1" flex-xs="100" flex="50">
                        <md-content>
                            <md-input-container class="md-icon-float md-block">
                                <label>Ingrese su Usuario</label>
                                <md-icon  md-menu-align-target class="icon-top">
                                    <i style="color: #015780;" class="material-icons animated tada">&#xE853;</i>
                                </md-icon>
                                <input required class="inputhide" maxlength="8" name="j_username" type="text" is-number flex="90" autocomplete="off" focus-me="focusUser" ng-model="user" numbers-only> <!--ui-mask="[[filterDni]]"--> 
                            </md-input-container>   

                            <md-input-container class="md-icon-float md-block">
                                <label>Clave</label>
                                <md-icon  md-menu-align-target class="icon-top">
                                    <i style="color: #015780;" class="material-icons animated tada">&#xE0DA;</i>
                                </md-icon>
                                <input required name="j_password" type="password" maxlength="8" ng-maxlength="8" autocomplete="false" flex="90" ng-model="password" ng-readonly="true" ng-blur="showHint = false" ng-focus="showHint = true">
                                <div class="hint-input" >Utilice el teclado seguro en pantalla para ingresar su clave</div>
                            </md-input-container>
                            <c:choose>
                                <c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'Bad credentials'}">
                                    <font color="red">Usuario y/o Clave incorrecta</font>
                                    <c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
                                </c:when>    
                                <c:otherwise>
                                    <font color="red">
                                    <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                                    </font>
                                    <c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
                                </c:otherwise>
                            </c:choose>                                                        
                        </md-content>
                    </div>
                    <div class="block2" flex-xs="100" flex="50" >
                        <div class="keyboard">
                            <div class="numbers">
                                <c:forEach items="${numbers}" var="n">
                                    <div class="key" ng-click="virtualKey('${n}')"><span>${n}</span></div>
                                        </c:forEach>
                            </div>
                            <div class="letters">
                                <c:forEach items="${letters}" var="l">
                                    <div class="key" ng-click="virtualKey('${l}')"><span>${l}</span></div>
                                        </c:forEach>    
                                <div class="key back" ng-click="backspace()"><span><i class="material-icons">&#xE317;</i></span></div>
                            </div>
                        </div>
                    </div>
                    <div class="block3" flex-xs="100" >
                        <md-button ng-if="form.$invalid" style="background-color: #015780;color: #fff;" ng-disabled="form.$invalid || vm.dataLoading" type="submit" ng-click="focusUsers()" class="md-raised button-login"  >
                            INICIAR SESIÓN
                        </md-button>
                        <md-button ng-if="!form.$invalid" style="background-color: #12a537;color: #fff;" ng-disabled="form.$invalid || vm.dataLoading" type="submit" ng-click="focusUsers()" class="md-raised button-login"  >
                            INICIAR SESIÓN
                        </md-button>                        
                    </div>
                </div>
            </form>
        </div>
        <div class="footer-login animated fadeInLeft"></div>            
    </div> 

</t:master>