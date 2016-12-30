<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header">
    <md-toolbar  class="header md-primary"  ng-cloak>
        <div class="md-toolbar-tools">
            <c:if test="${USUARIO_AUTENTICADO.perfil.id == '1'}">            
<!--                <md-menu>
                    <md-button class="md-icon-button" aria-label="Settings" ng-click="ctrl.openMenu($mdOpenMenu, $event)">
                        <md-tooltip>
                            Opciones
                        </md-tooltip>
                        <i class="material-icons">&#xE5D4;</i>
                    </md-button>
                    <md-menu-content width="4">
                        <md-menu-item>
                            <md-button aria-label="Descarga de Materiales" ng-click="showPopupDownloads(false)" >
                                <md-icon  md-menu-align-target>
                                    <i class="material-icons">&#xE2C4;</i>
                                </md-icon>
                                Descarga de Materiales
                            </md-button>
                        </md-menu-item>
                        <md-menu-divider></md-menu-divider>
                        <md-menu-item>
                            <md-button aria-label="Configuración de mi Colegio" ng-click="showPopupConfiguracion()">
                                <md-icon  md-menu-align-target>
                                    <i class="material-icons">&#xE0AF;</i>
                                </md-icon>
                                Configuración de mi Colegio
                            </md-button>
                        </md-menu-item>
                    </md-menu-content>
                </md-menu>-->
            </c:if>            
            <h1 flex="50">
                <span style="font-size: 35px;font-family: Times, serif;">RAE | </span><span>${USUARIO_AUTENTICADO.perfil.nombre}<span>
            </h1>
            <div flex layout="row" layout-align="end center">
                <div>
                    ${sessionScope.USUARIO_AUTENTICADO.nombreCompleto}
                </div>
                <div class="right" >
                    <a href="<c:url value='/logout'/>">
                        <md-tooltip>
                            Cerrar Sesión
                        </md-tooltip>
                        <i class="material-icons">&#xE879;</i>
                    </a>
                </div>
                <div >
                    <img class="animated bounceIn" src="<c:url value='/img/logo-onpe.png'/>" alt="onpe" height="40"/>
                </div>
            </div>
        </div>
    </md-toolbar>
</div>            