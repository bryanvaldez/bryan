<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:master title="Reportes">

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/program/security/usuario.css" media="screen,projection"/>	    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/controller/reporteController.js"></script>

    <div ng-controller="reporteCtrl">
        <input type="hidden" ng-init="departamentos =<c:out value='${lstDptos}'/>" />
        
        <div style="padding: 30px 50px;" class="users-section" ng-cloak>
            <div class="section-head animated fadeIn" >                
                <md-button href="${pageContext.request.contextPath}/main" class="md-icon-button btn-back" >
                    <md-icon class="material-icons">arrow_back</md-icon>
                </md-button>
                <h5 class="title-section">Exportar Reportes</h5>                
            </div>                                                    
<!--            <div class="line" style="margin-right:74px;"></div>-->
            <div layout="row" layout-padding layout-wrap class="index-cont" ng-cloak >            
                <div flex-sm="100" flex-gt-sm="50" flex-gt-md="33" flex-gt-lg="25" ng-repeat="reporte in reportes">                                     

                    <div flex  md-colors="" class="block card-reporte" layout="column">                    
                        <div flex>
                            <md-icon  md-menu-align-target class="icon-top">
<!--                                <i class="material-icons" md-colors="{color: 'background-500'}">&#xE01D;</i>-->
                                <i class="fa fa-bar-chart" style="font-size:23px;">[[]]</i>
                            </md-icon>
                        </div>
                        <div flex class="title" layout="row">
                            <div flex="100">
                                [[reporte.etiqueta]]
                            </div>
<!--                            <div flex="30" layout-align="end center" layout="row" >
                                <md-button class="md-fab btn-block" aria-label="" ng-click="generarReportes(reporte.id)">
                                    <md-tooltip>
                                        Descargar
                                    </md-tooltip>
                                    <md-icon class="btn-icon">
                                        <i class="material-icons">&#xE2C4;</i>
                                    </md-icon>
                                </md-button>
                            </div>-->
                        </div>
                        <div flex layout-align="start center" layout="row" class="subtitle"> 
                            [[reporte.descripcion]]                            
                        </div>
                        <div class="pleca">
                            <md-button  aria-label="" ng-click="generarReportes(reporte.id)">
                            <i class="fa fa-download">[[]]</i>
                            </md-button>
                        </div>
                    </div> 
                </div>
            </div>                             
        </div> 
    </div>                                  
</t:master>

