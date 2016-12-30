<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:master title="Usuarios">

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/program/security/usuario.css" media="screen,projection"/>	    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/controller/ambitoController.js"></script>

    <div ng-controller="ambitoCtrl" style="background:#fff; padding: 30px 50px;" class="ambito-section"> 
        <div class="section-head animated fadeIn">
            <md-button href="${pageContext.request.contextPath}/main" class="md-icon-button btn-back">
                <md-icon class="material-icons">arrow_back</md-icon>
            </md-button>
            <h5 class="title-section">Ambitos: ([[ (ambitos | filter:searchQuery).length ]])</h5>

            <div class="input-group">
                <input type="text" ng-model="query" ng-enter="search()" placeholder="Busque por Nombre de Ambito"><button ng-click="search()"><md-icon class="material-icons">search</md-icon></button>
            </div>

            <md-button ng-click="addAmbito()" class="md-fab btn-new animated bounceInRight">
                <md-icon class="material-icons">add</md-icon>
            </md-button>
        </div>

        <div class="line" style="margin-right:74px;"></div>

        <table class="table-default" ng-cloak>
            <thead>
                <tr class="caption">
                    <th colspan="7">Ambito
            <div class="line"></div>
            </th>
            <th class="separator" colspan="3">
                Autoridad
            <div class="line"></div>
            </th>
            </tr>
            <tr>
                <th style="width:2.5%;" class="rownum">N</th>
                <th style="width:10%;">Departamento</th>
                <th style="width:10%;">Provincia</th>
                <th style="width:10%;">Distrito</th>
                <th style="width:10%;">Tipo</th>
                <th style="width:10%;">Nombre del Ambito</th>
                <th style="width:2.5%;"></th>
                <th style="width:10%;" class="separator">Cargo</th>
                <th style="width:2.5%;"></th>
                <th style="width:10%;" class="separator">Cargo</th>
                <th style="width:2.5%;"></th>
            </tr>
            </thead>
        </table>
        <div ng-show="showTable" >        
            <div class="loading-message" ng-hide="(ambitos | filter:searchQuery).length"><h3>No se encontraron datos.</h3></div> 
            <div style="overflow-y: auto; height: 400px;">
                <table class="table-default" ng-cloak>                    
                    <tbody>
                        <tr ng-repeat="ambito in ambitos| filter:searchQuery | orderBy:'-id'">
                            <td class="rownum " style="width:2.5%;"><span ng-bind="$index + 1"></span></td>
                            <td style="width:10%;"><span ng-bind="ambito.departamento | uppercase"></span></td>
                            <td style="width:10%;"><span ng-bind="ambito.provincia | uppercase"></span></td>
                            <td style="width:10%;"><span ng-bind="ambito.distrito | uppercase"></span></td>                        
                            <td style="width:10%;" class="action-col"><span ng-bind="ambito.tipoAmbito == 1 ? 'PRINCIPAL': 'ADJUNTO'"></span></td>
                            <td style="width:10%;"><span ng-bind="ambito.nombreAmbito | uppercase"></span></td>
                            <td class="space action-col" style="width:2.5%;">
                    <md-menu ng-if="!ambito.ambitoPadre">
                        <md-button ng-click="$mdOpenMenu($event)" class="md-icon-button" aria-label="Open sample menu">
                            <i class="material-icons">more_vert</i>
                        </md-button>                             
                        <md-menu-content width="4">
                            <md-menu-item><md-button ng-click="download(ambito.id)" md-no-ink >Descargar</md-button></md-menu-item>
                            <md-menu-item><md-button ng-click="ver(ambito.id)" md-no-ink >Ver</md-button></md-menu-item>
                            <md-menu-item><md-button ng-click="edit(ambito.id)" md-no-ink >Editar</md-button></md-menu-item>
                            <md-menu-item><md-button ng-click="delete(ambito)" md-no-ink >Eliminar</md-button></md-menu-item>
                            <md-menu-divider></md-menu-divider>
                            <md-menu-item><md-button md-no-ink href="${pageContext.request.contextPath}/mantenimiento/local/[[ambito.id]]">Ver Locales de Votación</md-button></md-menu-item>
                            <md-menu-item><md-button md-no-ink href="${pageContext.request.contextPath}/mantenimiento/expediente/[[ambito.id]]">Ver Expedientes</md-button></md-menu-item>
                        </md-menu-content>
                    </md-menu>

                    <md-button ng-if="ambito.ambitoPadre" ng-click="deleteAnexo(ambito.id)" class="md-icon-button">
                        <md-icon class="material-icons">delete</md-icon> 
                    </md-button>
                    </td>

                    <td style="width:10%;" class="separator">[[!ambito.ambitoPadre ? (ambito.infoParse.cargo==1?'Alcalde':'Representante') : '']]</td>                       
                    <td class="space action-col" style="width:2.5%;">
                    <md-button class="md-icon-button" ng-click="editAuth(1, ambito.id)" ng-if="!ambito.ambitoPadre">
                        <i class="fa fa-edit" style="font-size:17px;">[[]]</i>
                    </md-button>
                    </td>
                    <td style="width:10%;" class="separator">[[!ambito.ambitoPadre ? (ambito.infoParse.cargo_==1?'Alcalde':'Representante') : '']]</td>                        
                    <td class="space action-col" style="width:2.5%;">
                    <md-button class="md-icon-button" ng-click="editAuth(2, ambito.id)" ng-if="!ambito.ambitoPadre">
                        <i class="fa fa-edit" style="font-size:17px;">[[]]</i>
                    </md-button>
                    </td>                      
                    </tr>
                    </tbody>            
                </table>
            </div>                            
        </div>                         
        <div class="loading" ng-show="showLoading" layout="row" layout-sm="column" layout-align="center center">
            <md-progress-circular md-mode="indeterminate"></md-progress-circular>
        </div>    
    </div>            
</t:master>

