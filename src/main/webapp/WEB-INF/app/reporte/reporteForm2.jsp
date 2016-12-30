<md-dialog aria-label="FILTROS" class="modal-form">
    <md-toolbar>
        <div class="md-toolbar-tools">
            <h1>Filtros</h1>            
            <h2>SELECCIONE FILTROS</h2>
            <md-button class="md-icon-button btn-close" ng-click="cancel()">
                <md-icon aria-label="Close dialog" class="material-icons" ng-click="close()">close</md-icon>
            </md-button>
        </div>
    </md-toolbar>
    <form name="userForm" ng-submit="submit(2)"> 
        <md-dialog-content>            
            <div layout="row" layout-align="start">
                <md-input-container flex="30" class="md-icon-float md-block">
                    <span>Departamento</span>
                </md-input-container>
                <md-input-container flex="70" class="md-icon-float md-block">
                    <label>Seleccione Departamento...</label>
                    <md-select ng-change="listarProvincias()" ng-model="ubigeoDepartamento.codigo">
                        <md-option ng-value="0">TODOS</md-option>
                        <md-option ng-repeat="departamento in departamentos" ng-value="departamento.UBIGEO">
                            [[departamento.DESCRIPCION]]
                        </md-option>
                    </md-select>
                </md-input-container>
            </div>
            <div layout="row" layout-align="start" ng-repeat="control in controlsProvincia">
                <md-input-container flex="30" class="md-icon-float md-block">
                    <span>Provincia</span>
                </md-input-container>
                <div ng-repeat="control in controlsProvincia" flex="70">   
                    <md-input-container class="md-icon-float md-block">
                        <label>Seleccione Provincia...</label>
                        <md-select ng-change="listarDistritos()" ng-model="ubigeoProvincia.codigo">                        
                            <md-option ng-value="0">TODOS</md-option>
                            <md-option ng-repeat="provincia in provincias" ng-value="provincia.ubigeo">
                                [[provincia.descripcion]]
                            </md-option>
                        </md-select>
                    </md-input-container>
                </div>
            </div>                                                    
            <div layout="row" layout-align="start" ng-repeat="control in controlsDistrito">
                <md-input-container flex="30" class="md-icon-float md-block">
                    <span>Distrito</span>
                </md-input-container>
                <div ng-repeat="control in controlsDistrito" flex="70">   
                    <md-input-container class="md-icon-float md-block" >
                        <label>Seleccione Distrito...</label>
                        <md-select ng-change="listarCCPP()" ng-model="ubigeoDistrito.codigo">
                            <md-option ng-value="0">TODOS</md-option>
                            <md-option ng-repeat="distrito in distritos" ng-value="distrito.ubigeo">
                                [[distrito.descripcion]]
                            </md-option>
                        </md-select>
                    </md-input-container>
                </div>
            </div>              
            <div layout="row" layout-align="start">
                <md-input-container flex="100" class="md-icon-float md-block">
                    <span>Seleccione Fecha:</span>
                </md-input-container>
            </div>    
            <div layout="row" layout-align="start">                
                <md-input-container flex="50" class="md-icon-float md-block">                    
                    <md-datepicker required ng-model="fecha.inicio" md-placeholder="Desde" md-open-on-focus></md-datepicker>
                </md-input-container>                
                <md-input-container flex="50" class="md-icon-float md-block">                    
                    <md-datepicker required ng-model="fecha.fin" md-placeholder="Hasta" md-open-on-focus></md-datepicker>
                </md-input-container>
            </div>            
        </md-dialog-content>
        <md-dialog-actions layout="row" layout-align="left">        
            <md-button ng-click="close()" class="btn-cancel">CANCELAR</md-button>
            <md-button type="submit" class="btn-confirm">EXPORTAR</md-button>
        </md-dialog-actions>
    </form>          
</md-dialog>