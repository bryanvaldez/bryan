<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:master title="security">
    
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/program/security/login.css" media="screen,projection"/>	 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/controller/loginController.js"></script>
  

        
    <div layout="column" ng-controller="claveCtrl" ng-cloak>
        <div class="content">
            <form name="changeForm" action="${pageContext.request.contextPath}/cambiar-clave" method="post">
                <input type="hidden" name="id" value="${USUARIO_AUTENTICADO.usuario}"/>
                <div layout="row" layout-wrap>
                    <div class="block1" flex-xs="100" flex="50">
                        <md-content>
                            <md-input-container class="md-icon-float md-block">
                                <label>Nueva Clave</label>
                                <md-icon  md-menu-align-target class="icon-top">
                                    <i class="material-icons animated tada">&#xE0DA;</i>
                                </md-icon>
                                <input focus-me="focusClave" ng-readonly="true" autocomplete="off"  ng-model="passwordA" maxlength="8" ng-maxlength="8" ng-focus="focusA()"type="password" name="clave" id="password" class="form-control"  required />
                            </md-input-container>   

                            <md-input-container class="md-icon-float md-block">
                                <label>Ingrese su nueva clave</label>
                                <md-icon  md-menu-align-target class="icon-top">
                                    <i class="material-icons animated tada">&#xE0DA;</i>
                                </md-icon>
                                <input ng-readonly="true" autocomplete="off"  ng-model="passwordB" maxlength="8" ng-maxlength="8" ng-focus="focusB()" type="password" name="claverepetida" id="password" class="form-control"  required />
                                <span ng-show="showMessage" class="help-block">${message}</span>
                                <c:remove var = "message" scope = "session" />
                            </md-input-container> 
                        </md-content>
                    </div>
                    <div class="block2" flex-xs="100" flex="50" >
                        <div class="keyboard">
                            <div class="numbers">
                                <c:forEach items="${numbers}" var="n">   
                                    <div class="key" ng-click="virtualKeyAB('${n}')"><span>${n}</span></div>
                                        </c:forEach>
                            </div>
                            <div class="letters">
                                <c:forEach items="${letters}" var="l">
                                    <div class="key" ng-click="virtualKeyAB('${l}')"><span>${l}</span></div>
                                        </c:forEach>    
                                <div class="key back" ng-click="backspaceAB()"><span><i class="material-icons">&#xE317;</i></span></div>
                            </div>
                        </div>
                    </div>
                    <div class="block3" flex-xs="100" >
                        <md-button style="background-color: #005780;color: #fff;" ng-disabled="changeForm.$invalid" type="submit" ng-click="focusUsers()" class="md-raised button-login"  >
                            CAMBIAR CLAVE
                        </md-button>
                    </div>
                </div>
            </form>
        </div>   
    </div>         
            
            
</t:master>