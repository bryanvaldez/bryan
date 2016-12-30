<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:master title="Expedientes">

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/program/security/usuario.css" media="screen,projection"/>	    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/controller/expedienteController.js"></script>

    <div ng-controller="expedienteCtrl">
        <div style="padding: 30px 50px;" class="users-section" ng-cloak>
            <div class="section-head animated fadeIn" >
                <md-button href="${pageContext.request.contextPath}/mantenimiento/index" class="md-icon-button btn-back" >
                    <md-icon class="material-icons">arrow_back</md-icon>
                </md-button>
                <h5 class="title-section">Expedientes</h5>
                <div class="input-group" >
                    <input type="text" ng-model="query" ng-enter="search()" placeholder="Ingrese un termino de Busqueda"><button ng-click="search()"><md-icon class="material-icons" >search</md-icon></button>
                    <!--                    <md-autocomplete flex 
                                                         md-search-text="searchText"                                     
                                                         md-items="item in querySearch(searchText)"
                                                         md-item-text="item.EXPEDIENTE"
                                                         md-selected-item-change="selectedItemSearch(item.ID)"
                                                         md-search-text-change="selectedEmptySearch()"
                                                         md-floating-label="Busque por..."
                                                         md-require-match="true"
                                                         md-min-length="1" uppercase>
                                            <div layout="row" layout-align="start center">                    
                                                <span md-highlight-text="searchText">{{item.EXPEDIENTE}}</span>  
                                            </div> 
                                            <md-not-found>
                                                No se encontraron coincidencias.
                                            </md-not-found>
                                        </md-autocomplete>-->

                </div> 
                <md-button ng-click="add(${idAmbito})" class="md-fab btn-new animated bounceInRight" >
                    <md-icon class="material-icons">add</md-icon>
                </md-button>
            </div>                                                    
            <div class="line" style="margin-right:74px;"></div>
            <div ng-show="showTable" >
                <table class="table-default">
                    <thead>
                        <tr>
                            <th style="width: 30px;">N°</th>                            
                            <th class="space">Departamento/Provincia/Distrito</th>
                            <th class="space">Ámbito Principal</th>                            
                            <th class="space">Número de Expediente</th>
                            <th class="space">Fecha de Recepción</th>
                            <th class="space"></th>
                            <th class="space"></th>
                            <th class="space"></th>                                                        
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-show="showUp">
                            <td colspan="5">
                    <md-input-container flex="30" class="md-icon-float md-block">
                        <span>HA SELECCIONADO ([[countSelectedList]]) EXPEDIENTE(S)</span>
                    </md-input-container>                                
                    </td>
                    <td colspan="3">
                    <md-button ng-click="cerrar()" class="md-primary">
                        CERRAR SELECCION
                    </md-button>
                    </td>
                    </tr>
                    <tr ng-repeat="expediente in expedientes| filter:searchQuery  | orderBy:'-id'">
                        <td class="rownum"><span ng-bind="$index + 1"></span></td>                            
                        <td class="space"><span ng-bind="expediente.ambito.departamento + '/' + expediente.ambito.provincia + '/' + expediente.ambito.distrito"></span></td>
                        <td class="space"><span ng-bind="expediente.ambito.nombreAmbito"></span></td>                            
                        <td class="space"><span ng-bind="expediente.expediente"></span></td>
                        <td class="space"><span ng-bind="expediente.fechaExpediente | date:'dd / MM / yyyy'"></span></td>                                                         

                        <td class="space action-col" style="width:20px;">                                                
                    <md-checkbox aria-label="EXPEDIENTES" ng-checked="exists(expediente.id)" ng-click="toggle(expediente.id)">
                        <span ng-if="exists(expediente.id)"></span>
                    </md-checkbox>                        
                    </td>
                    <td style="width: 20px;" class="space"><md-button ng-click="edit(expediente.id)" class="md-icon-button" ><md-icon class="material-icons">edit</md-icon></md-button></td>      
                    <td style="width: 20px;" class="space"><md-button ng-click="delete(expediente.id)" class="md-icon-button" ><md-icon class="material-icons">delete</md-icon></md-button></td>                     
                    </tr> 
                    <tr ng-show="!showUp">
                        <td colspan="5">
                    <md-input-container flex="30" class="md-icon-float md-block">
                        <span>HA SELECCIONADO ([[countSelectedList]]) EXPEDIENTE(S)</span>
                    </md-input-container>                                
                    </td>
                    <td colspan="3">
                    <md-button ng-click="ver()" class="md-primary">                                    
                        VER SELECCION
                    </md-button>
                    </td>
                    </tr>
                    <tr ng-show="showUp">
                        <td colspan="8">
                    <md-button ng-click="exportar()" class="md-primary" >
                        EXPORTAR SELECCION
                    </md-button>
                    </td>
                    </tr>
                    </tbody>
                </table>
                <div class="loading-message" ng-hide="(expedientes | filter:searchQuery).length"><h3>No se encontraron datos.</h3></div>
            </div>
            <div class="loading" ng-show="showLoading" layout="row" layout-sm="column" layout-align="center center">
                <md-progress-circular md-mode="indeterminate"></md-progress-circular>
            </div>             
        </div> 
    </div>                                  
</t:master>

