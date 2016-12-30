<md-dialog aria-label="REGISTRAR NUEVO ELECTOR" class="modal-form">
    <md-toolbar>
        <div class="md-toolbar-tools">
            <h1>Elector</h1>            
            <h2>REGISTRAR NUEVO ELECTOR</h2>
            <md-button class="md-icon-button btn-close" ng-click="cancel()">
                <md-icon aria-label="Close dialog" class="material-icons" ng-click="close()">close</md-icon>
            </md-button>
        </div>
    </md-toolbar>
    <form name="userForm" ng-submit="submit()"> 
        <md-dialog-content>                        
            <div layout="row" layout-align="start">
                <Label>DNI</Label>
                <md-input-container>
                    <label>Ingrese número de DNI</label>
                    <input ng-model="numele" ng-enter="searchDni()" ng-change="changeNumEle()">
                </md-input-container>
            </div>
            <div layout="row" layout-align="start">
                <md-input-container class="md-block" flex-gt-sm>
                    <label>Ambito</label>
                    <md-select ng-model="ambito.codigo" placeholder="Ambito...">
                        <md-option ng-repeat="amb in ambitos" ng-value="amb.N_AMBITO_PK">
                            [[amb.C_NOMBRE_AMBITO]]
                        </md-option>
                    </md-select> 
                </md-input-container>
            </div>
            <div layout="row" layout-align="start">
                <md-input-container>
                    <label>Apellido Paterno</label>
                    <input ng-model="elector.apellidoPaterno">
                </md-input-container>
                <md-input-container>
                    <label>Apellido Materno</label>
                    <input ng-model="elector.apellidoMaterno">
                </md-input-container>
            </div>
            <div layout="row" layout-align="start">
                <md-input-container>
                    <label>Nombres</label>
                    <input ng-model="elector.nombre">
                </md-input-container>
            </div>
            <div layout="row" layout-align="start">
                <md-input-container class="md-block" flex-gt-sm>
                    <label>Departamento</label>
                    <md-select ng-change="listarProv()" ng-model="ubigeoElectorDpto.ubigeo" placeholder="Departamento...">
                        <md-option ng-repeat="dpto in lstDpto" ng-value="dpto.UBIGEO">
                            [[dpto.DESCRIPCION]]
                        </md-option>
                    </md-select> 
                </md-input-container>
            </div>
            <div layout="row" layout-align="start">
                <div ng-repeat="control in controlsProv">          
                    <md-input-container class="md-block" flex-gt-sm>
                        <label>Provincia</label>
                        <md-select ng-change="listarDist()" ng-model="ubigeoElectorProv.ubigeo" placeholder="Provincia...">
                            <md-option ng-repeat="prov in lstProv" ng-value="prov.ubigeo">
                                [[prov.descripcion]]
                            </md-option>
                        </md-select> 
                    </md-input-container>
                </div>
            </div>
            <div layout="row" layout-align="start">
                <div ng-repeat="control in controlsDist">
                    <md-input-container class="md-block" flex-gt-sm>
                        <label>Distrito</label>
                        <md-select ng-model="ubigeoElectorDist.ubigeo" placeholder="Distrito...">
                            <md-option ng-repeat="dist in lstDist" ng-value="dist.ubigeo">
                                [[dist.descripcion]]
                            </md-option>
                        </md-select> 
                    </md-input-container>
                </div>
            </div>                                    
        </md-dialog-content>
        <md-dialog-actions layout="row" layout-align="left">        
            <md-button ng-click="close()" class="btn-cancel">CANCELAR</md-button>
            <md-button type="submit" class="btn-confirm">GUARDAR</md-button> 
        </md-dialog-actions>
    </form>          
</md-dialog>