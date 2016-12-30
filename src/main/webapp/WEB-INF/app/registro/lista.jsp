<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <input type="hidden" ng-init="departamentos =<c:out value='${dptos}'/>" /> 

    <div class="section-head animated fadeIn">
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
            
            <md-input-container class="md-block">
                <label>Departamento...</label>
                <md-select ng-change="listarProvincias()" ng-model="ubigeoDepartamento.codigo">
                    <md-option ng-repeat="departamento in departamentos" ng-value="departamento.UBIGEO">
                        [[departamento.DEPARTAMENTO]]
                    </md-option>
                </md-select> 
            </md-input-container> 

            <md-input-container ng-repeat="control in controlsProvincia" class="md-block">
                <label>Provincia...</label>
                <md-select ng-change="listarDistritos()" ng-model="ubigeoProvincia.codigo">                        
                    <md-option ng-repeat="provincia in provincias" ng-value="provincia.UBIGEO">
                        [[provincia.PROVINCIA]]
                    </md-option>
                </md-select> 
            </md-input-container> 

            <md-input-container ng-repeat="control in controlsDistrito" class="md-block">
                <label>Distrito...</label>
                <md-select ng-change="listarCCPP()" ng-model="ubigeoDistrito.codigo">
                    <md-option ng-repeat="distrito in distritos" ng-value="distrito.UBIGEO">
                        [[distrito.DISTRITO]]
                    </md-option>
                </md-select> 
            </md-input-container>            
        </div>
        <div class="line-vertical"></div>
        <div flex-gt-xs="50" layout="column" style="margin-left:20px;">
            <md-autocomplete flex 
                             md-search-text="searchTextLocal"
                             md-items="item in querySearchCCPP(searchTextLocal)"
                             md-item-text="item.nombreAmbito"  
                             md-selected-item-change="selectedCCPP(item)"
                             md-floating-label="Ingrese un Centro Poblado..."
                             md-min-length="1">
                <div layout="row" layout-align="start center">
                    <span md-highlight-text="searchTextLocal">{{item.nombreAmbito}}</span>  
                </div>                    
            </md-autocomplete>
            <md-input-container  ng-repeat="control in controlsCCPP" class="md-block">  
                <label>Centro Poblado...</label>
                <md-select ng-change="listarExpedientes()" ng-model="ubigeoCCPP.codigo">
                    <md-option ng-repeat="cp in ccpps" ng-value="cp.ID">
                        [[cp.CCPP]]
                    </md-option>
                </md-select> 
            </md-input-container> 
            <md-input-container ng-repeat="control in controlsExpediente" class="md-block">  
                <label>Expediente...</label>
                <md-select ng-model="ubigeoExpediente.codigo">
                    <md-option ng-repeat="expediente in expedientes" ng-value="expediente.ID">
                        [[expediente.EXPEDIENTE]]
                    </md-option>
                </md-select> 
            </md-input-container>            
            
        </div> 
    </div>

    <div class="layout action-btn" layout-align="center center" style="margin-top: 20px;">     
        <md-button style="width: 250px;background-color: #2baf2b;border-color: #0a8f08;color: #fff;    box-shadow: 0 0 6px #aaaaaa;" class="md-raised" ng-disabled="ubigeoExpediente.codigo === ''" ng-click="listaSelected()">Seleccionar Expediente</md-button>
        <md-button style="width: 250px;background-color: #005780;border-color: #0a8f08;color: #fff;    box-shadow: 0 0 6px #aaaaaa;" class="md-raised" ng-disabled="ubigeoExpediente.codigo === ''" ng-click="exportarSelected()">Exportar Expediente</md-button>
    </div>
</div>