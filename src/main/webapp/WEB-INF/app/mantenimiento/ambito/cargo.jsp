
<md-dialog aria-label="CREAR USUARIO" class="modal-form">
    <md-toolbar>
        <div class="md-toolbar-tools">
            <h1>Ambitos</h1>
            <h2>CREAR AMBITO</h2>
            <md-button class="md-icon-button btn-close" ng-click="cancel()">
                <md-icon aria-label="Close dialog" class="material-icons" ng-click="close()">close</md-icon>
            </md-button>
            <div class="steps-legend">
                <span class="active"></span>
                <span></span>                
            </div>
        </div>
    </md-toolbar>

    <md-dialog-content>
        <form name="userForm"> 
            <div layout="row" layout-align="space-between center">
                <span class="inline-label">Departamento</span>
                <div class="custom-select">
                <select required ng-change="getProvincias(departamento)" ng-model="departamento" ng-options="dep.CODIGO as dep.DESCRIPCION for dep in departamentos | orderBy:'DESCRIPCION'"><option value="">-- SELECCIONAR --</option></select>
                </div>
            </div>

            <div layout="row" layout-align="space-between center">
                <span class="inline-label">Provincia</span>
                <div class="custom-select">
                <select required ng-change="getDistritos(provincia)" ng-disabled="departamento==''  " ng-model="provincia" ng-options="prov.CODIGO as prov.DESCRIPCION for prov in provincias | orderBy:'DESCRIPCION'"><option value="">-- SELECCIONAR --</option></select>
                </div>
            </div>

             <div layout="row" layout-align="space-between center">
                <span class="inline-label">Distrito</span>
                <div class="custom-select">
                <select required ng-change="getAmbitoPadre(ambito.ubigeo)"  ng-disabled="provincia==''" ng-model="ambito.ubigeo" ng-options="dist.CODIGO as dist.DESCRIPCION for dist in distritos | orderBy:'DESCRIPCION'"><option value="">-- SELECCIONAR --</option></select>
                </div>
            </div>

            <div layout="row">
                <md-input-container flex="100">

                    <md-select placeholder="Assign to user" name="type" ng-model="ambito.tipoAmbito" >
                        <md-option value="1" selected>Principal</md-option>
                        <md-option value="2">Anexo</md-option>
                    </md-select>
                </md-input-container>
            </div>
            
            <div ng-hide="ambito.tipoAmbito == constantAmbito.tipo.anexo">
                <div layout="row">
                    <md-button ng-disabled="ambito.ubigeo == null || ambito.nombreAmbito == ''" ng-click="addAnexo()" class="md-fab btn-new">
                        <md-icon class="material-icons">add</md-icon>
                    </md-button>                    
                    <md-input-container flex="100">
                        <label>Ingrese el nombre del Ambito Principal</label>
                        <input ng-disabled="ambito.ubigeo == null" ng-model="ambito.nombreAmbito" required name="nombre"/>
                    </md-input-container>
                </div>   

                <div layout="row" ng-repeat="anexo in anexos">
                    <span ng-bind="$index + 1+'°'" flex="5" style="margin-top: 20px;"></span>
                    <md-input-container flex="45">
                        <label>Ingrese nombre del adjunto</label>
                        <input required name="name" ng-model="anexo.nombreAmbito" />
                    </md-input-container>

                    <md-input-container flex="45">
                        <label>Selecciones el tipo de adjunto</label>
                        <md-select name="type" ng-model="anexo.categoria" required style="margin:0;">
                            <md-option ng-repeat=" categoria in constantUbigeo.categorias" ng-value="categoria.id"><span ng-bind="categoria.nombre"></span></md-option>
                        </md-select>
                    </md-input-container>
                    <span flex="5" style="margin-top: 10px;">
                        <md-button ng-click="deleteAnexo($index)" class="md-icon-button" >
                            <md-icon class="material-icons">delete</md-icon> 
                        </md-button>
                    </span>
                </div>
            </div>
            <div ng-hide="ambito.tipoAmbito == constantAmbito.tipo.principal">

             <div layout="row" layout-align="space-between center">
                <span class="inline-label">Seleccione el Ambito Principal</span>
                    <div class="custom-select">
                    <select ng-disabled="ambito.ubigeo == null" required  ng-model="anexo.ambitoPadre.id" ng-options="ambito.id as ambito.nombreAmbito for ambito in lstAmbitosPadre | filter:{ambitoPadre:null}  | orderBy:'nombreAmbito'"><option value="">-- Seleccionar --</option></select>                    
                    </div>
            </div>                
                <md-input-container flex="100" style="width:100%">
                    <label>Ingrese el nombre del Anexo...</label>
                    <input ng-model="anexo.nombreAmbito" name="nombre" />
                </md-input-container>
                <md-input-container flex="100" style="width:100%">
                    <label>Selecciones el tipo de anexo</label>
                    <md-select ng-disabled="anexo.nombreAmbito == null" name="type" ng-model="anexo.categoria" required style="margin:0;">
                        <md-option ng-repeat=" categoria in constantUbigeo.categorias" ng-value="categoria.id"><span ng-bind="categoria.nombre"></span></md-option>
                    </md-select>
                </md-input-container>                
            </div>
        </form>
    </md-dialog-content>
    <md-dialog-actions layout="row" layout-align="left">
        <md-button class="btn-cancel">CANCELAR</md-button>
        <md-button ng-disabled="ambito.ubigeo == null" ng-click="submit()" class="btn-confirm">CREAR</md-button> 
    </md-dialog-actions>
</md-dialog>