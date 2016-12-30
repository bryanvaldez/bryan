<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:master title="Local">

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/program/security/usuario.css" media="screen,projection"/>	    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/controller/localController.js"></script>

    <div ng-controller="localCtrl">
        <div style="padding: 30px 50px;" class="users-section" ng-cloak>
            <div class="section-head animated fadeIn" >
                <md-button href="${pageContext.request.contextPath}/mantenimiento/index" class="md-icon-button btn-back" >
                    <md-icon class="material-icons">arrow_back</md-icon>
                </md-button>
                <h5 class="title-section">Locales de Votacion</h5>
                <div class="input-group" >
                    <input type="text" ng-model="query" ng-enter="search()" placeholder="Busque por nombre de Ambito"><button ng-click="search()"><md-icon class="material-icons" >search</md-icon></button>
<!--                    <md-autocomplete flex 
                                     md-search-text="searchText"                                     
                                     md-items="item in querySearch(searchText)"
                                     md-item-text="item.LOCAL"
                                     md-selected-item-change="selectedItemSearch(item.ID)"
                                     md-search-text-change="selectedEmptySearch()"
                                     md-floating-label="Busque por..."
                                     md-require-match="true"
                                     md-min-length="1" uppercase>
                        <div layout="row" layout-align="start center">                    
                            <span md-highlight-text="searchText">{{item.LOCAL}}</span>  
                        </div> 
                        <md-not-found>
                            No se encontraron coincidencias.
                        </md-not-found>
                    </md-autocomplete>-->
                </div> 
                <md-button ng-click="add()" class="md-fab btn-new animated bounceInRight" >
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
                            <th class="space"></th>
                            <th class="space">Local de Votacion</th>
                            <th class="space" style="width: 20px;"></th>
                            <th class="space"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-repeat="local in locales | filter:searchQuery | orderBy:'-id'">
                            <td class="rownum"><span ng-bind="$index + 1"></span></td>
                            <td class="space"><span ng-bind="local.departamento+'/'+local.provincia+'/'+local.distrito"></span></td>
                            <td class="space"><span ng-bind="local.ambito.nombreAmbito"></span></td>                        
                            <td style="width: 20px;" class="space action-col"><md-button ng-click="change(local.id)" class="md-icon-button" ><i class="fa fa-refresh" style="font-size:20px;">[[]]</i></md-button></td>
                            <td class="space"><span ng-bind="local.nombre"></span></td>                            
                            <td class="space action-col"><i ng-class="{positivo: local.estado == 0, negativo: local.estado == 1}" class="fa fa-circle" style="font-size:18px;"></i></td>                            
                            <td class="space action-col" style="width:20px;">
                                <md-menu md-position-mode="target-right target" >
                                 <md-button ng-click="$mdOpenMenu($event)" class="md-icon-button" aria-label="Open sample menu">
                                   <i class="material-icons">more_vert</i>
                                 </md-button>                             
                                 <md-menu-content width="4">
                                   <md-menu-item><md-button ng-click="edit(local.id)" md-no-ink >Editar</md-button></md-menu-item>
                                   <md-menu-item><md-button ng-click="delete(local.id)" md-no-ink >Eliminar</md-button></md-menu-item>
                                 </md-menu-content>
                                </md-menu>

                                <md-button ng-if="ambito.ambitoPadre" ng-click="deleteAnexo(ambito.id)" class="md-icon-button">
                                    <md-icon class="material-icons">delete</md-icon> 
                                </md-button>
                            </td>
                        </tr>                            
                    </tbody>
                </table>
                <div class="loading-message" ng-hide="(locales | filter:searchQuery).length"><h3>No se encontraron datos.</h3></div>
            </div> 
            <div class="loading" ng-show="showLoading" layout="row" layout-sm="column" layout-align="center center">
                <md-progress-circular md-mode="indeterminate"></md-progress-circular>
            </div>         
        </div>
    </div>           
</t:master>

