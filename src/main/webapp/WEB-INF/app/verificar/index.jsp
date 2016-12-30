<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:master title="Seleccionar Lista">

    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/program/carga.css" media="screen,projection"/>	    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/controller/verificarController.js"></script>

    <div style="background:#fff;padding: 30px 50px 10px 50px;" ng-controller="verificarCtrl">
        <input type="hidden" ng-init="departamentos =<c:out value='${dptos}'/>" />
        
        <div class="carga-section" ng-show="validar">
            <div class="section-head animated fadeIn" >
                <md-button href="${pageContext.request.contextPath}/main" class="md-icon-button btn-back" >
                    <md-icon class="material-icons">arrow_back</md-icon>
                </md-button>
                <h2 class="title-section">Seleccionar Expediente</h2>                                
            </div>
                    
            <div layout="row">
                <div flex-gt-xs="50" layout="column" style="margin-right:20px;">
                    <md-autocomplete flex 
                                     md-search-text="registroCtrl.searchTextUbigeo"
                                     md-items="item in querySearchUbigeo(registroCtrl.searchTextUbigeo)"
                                     md-item-text="item.DESCRIPCION"
                                     md-selected-item-change="selectedUbigeo(item)"
                                     md-floating-label="Ingrese un Departamento, Provincia o Distrito..."
                                     md-min-length="1">
                        <div layout="row" layout-align="start center">                    
                            <span md-highlight-text="registroCtrl.searchTextUbigeo">{{item.DESCRIPCION}}</span>  
                        </div>                    
                    </md-autocomplete>
                    <div>
                        <md-input-container class="md-block" flex-gt-sm>
                            <label>Departamento...</label>
                            <md-select ng-change="listarProvincias()" ng-model="ubigeoDepartamento.codigo">
                                <md-option ng-repeat="departamento in departamentos" ng-value="departamento.UBIGEO">
                                    [[departamento.DEPARTAMENTO]]
                                </md-option>
                            </md-select> 
                        </md-input-container>
                    </div>            
                    <div ng-repeat="control in controlsProvincia">                    
                        <md-input-container class="md-block" flex-gt-sm>
                            <label>Provincia...</label>
                            <md-select ng-change="listarDistritos()" ng-model="ubigeoProvincia.codigo">                        
                                <md-option ng-repeat="provincia in provincias" ng-value="provincia.UBIGEO">
                                    [[provincia.PROVINCIA]]
                                </md-option>
                            </md-select> 
                        </md-input-container>
                    </div> 
                    <div ng-repeat="control in controlsDistrito">                    
                        <md-input-container class="md-block" flex-gt-sm>
                            <label>Distrito...</label>
                            <md-select ng-change="listarCCPP()" ng-model="ubigeoDistrito.codigo">
                                <md-option ng-repeat="distrito in distritos" ng-value="distrito.UBIGEO">
                                    [[distrito.DISTRITO]]
                                </md-option>
                            </md-select> 
                        </md-input-container>
                    </div>                    
                    
                </div>
                <div class="line-vertical"></div>                
                <div flex-gt-xs="50" layout="column" style="margin-left:20px;">
                    <md-autocomplete flex 
                                     md-search-text="registroCtrl.searchTextLocal"
                                     md-items="item in querySearchCCPP(registroCtrl.searchTextLocal)"
                                     md-item-text="item.nombreAmbito"  
                                     md-selected-item-change="selectedCCPP(item)"
                                     md-floating-label="Ingrese un Centro Poblado..."
                                     md-min-length="1">
                        <div layout="row" layout-align="start center">
                            <span md-highlight-text="registroCtrl.searchTextLocal">{{item.nombreAmbito}}</span>  
                        </div>                    
                    </md-autocomplete>
                    <div ng-repeat="control in controlsCCPP">                    
                        <md-input-container class="md-block" flex-gt-sm>  
                            <label>Centro Poblado...</label>
                            <md-select ng-change="listarExpedientes()" ng-model="ubigeoCCPP.codigo">
                                <md-option ng-repeat="cp in ccpps" ng-value="cp.ID">
                                    [[cp.CCPP]]
                                </md-option>
                            </md-select> 
                        </md-input-container>
                    </div>
                    <div ng-repeat="control in controlsExpediente">                    
                        <md-input-container class="md-block" flex-gt-sm>  
                            <label>Expediente...</label>
                            <md-select ng-model="ubigeoExpediente.codigo">
                                <md-option ng-repeat="expediente in expedientes" ng-value="expediente.ID">
                                    [[expediente.EXPEDIENTE]]
                                </md-option>
                            </md-select> 
                        </md-input-container>
                    </div>                    
                </div> 
            </div>
            <div layout="row" layout-xs="column">
                <div flex>            
                    <md-button md-no-ink class="md-primary" ng-disabled="ubigeoExpediente.codigo === ''" ng-click="search()">
                        SELECCIONAR LISTA
                    </md-button> 
                </div>                                                                                   
            </div>                           
        </div> 
                    
        <div class="cargalist-section" ng-cloak ng-show="!validar">
            <div class="section-head animated fadeIn" >
                <md-button ng-click="return()" class="md-icon-button btn-back" >
                    <md-icon class="material-icons">arrow_back</md-icon>
                </md-button>
                <h5 class="title-section">Verificar Listas</h5>                                
            </div>
            <ul class="breadcrumbs" style="margin: 0;">
                <li>[[expediente.ambito.departamento]]</li>
                <li>[[expediente.ambito.provincia]]</li>
                <li>[[expediente.ambito.distrito]]</li>
            </ul>            
            <div layout-gt-xs="row">
                <div flex-gt-xs="100">
                    <h3>[[expediente.ambito.nombreAmbito]]</h3>
                </div>
            </div>
            <div class="line"></div>            

            
            <table class="table-default">
                <thead>
                    <tr>
                        <th style="width: 5%;">N°</th>                            
                        <th style="width: 10%;" class="space">DNI</th>
                        <th style="width: 15%;" class="space">Ape. Paterno</th>                            
                        <th style="width: 15%;" class="space">Ape. Materno</th>
                        <th style="width: 15%;" class="space">Nombres</th>
                        <th style="width: 10%;" class="space">Observaciones</th>
                        <th style="width: 15%;" class="space">Ámbito</th>
                        <th style="width: 15%;" class="space">Seleccione</th>                                                      
                    </tr>
                </thead>
            </table>             
            
            <div style="overflow-y: auto; height: 350px;">
                <table class="table-default">
                    <tbody>                        
                        <tr ng-repeat="elector in electores">
                            <td style="width: 5%;" class="rownum"><span ng-bind="$index + 1"></span></td>                        
                            <td style="width: 10%;" class="space"><span ng-bind="elector.dni"></span></td>                            
                            <td style="width: 15%;" class="space"><span ng-bind="elector.apellidoPaterno"></span></td>
                            <td style="width: 15%;" class="space"><span ng-bind="elector.apellidoMaterno"></span></td>
                            <td style="width: 15%;" class="space"><span ng-bind="elector.nombre"></span></td>
                            <td style="width: 10%;" class="space"><span ng-bind="elector.observacion"></span></td>
                            <td style="width: 15%;" class="space"><span ng-bind="elector.ambito.nombreAmbito"></span></td>
                            <td style="width: 15%;" class="space action-col" style="width:200px;">
                                <md-select style="text-align: left;" ng-model="elector.observacionUser" placeholder="Seleccione...">
                                    <md-option ng-value="0">Seleccione</md-option>
                                    <md-option ng-repeat="opcion in opciones" ng-value="opcion.codigo">
                                        [[opcion.descripcion]]
                                    </md-option>                                    
                                </md-select>
                            </td>                        
                            <!--<td style="width: 20px;" class="space"><md-button ng-click="delete(elector.id)" class="md-icon-button" ><md-icon class="material-icons">delete</md-icon></md-button></td>-->
                        </tr>                        
                    </tbody>
                </table>
            </div>
            <div class="layout action-btn" layout-align="center center">
                <md-button ng-click="save()" class="md-raised btn-default">
                    Guardar
                </md-button>
                <md-button ng-click="searchElector()" class="md-raised btn-default" >
                    Buscar
                </md-button>
            </div>

            
        </div>
    </div>                                  
</t:master>

