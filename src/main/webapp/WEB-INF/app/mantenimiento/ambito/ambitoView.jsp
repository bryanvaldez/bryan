
<md-dialog aria-label="CREAR USUARIO" class="modal-form">
    <md-toolbar>
        <div class="md-toolbar-tools">
            <h1>Ambito:</h1>
            <h2><span ng-bind="ambito.nombreAmbito"></span></h2>
            <md-button class="md-icon-button btn-close" ng-click="cancel()">
                <md-icon aria-label="Close dialog" class="material-icons" ng-click="close()">close</md-icon>
            </md-button>
<!--            <div class="steps-edit">   
                <md-switch ng-model="switchAmbito" aria-label="Editar">Editar([[switchAmbito ? 'NO':'SI']])
                </md-switch>                            
            </div>-->
        </div>
    </md-toolbar>

    <md-dialog-content>
        <form name="userForm"> 
            <div layout="row" layout-align="start">
                <md-input-container flex="50" class="md-icon-float md-block">
                    <label>Departamento:</label>
                    <md-icon class="name">location_city</md-icon>
                    <input ng-model="ambito.departamento" type="text" disabled>
                </md-input-container>
                <md-input-container flex="50" class="md-icon-float md-block">
                    <label>Provincia</label>
                    <md-icon class="name">location_city</md-icon>
                    <input ng-model="ambito.provincia" type="text" disabled>
                </md-input-container>              
            </div>
            <div layout="row" layout-align="start">
                <md-input-container flex="50" class="md-icon-float md-block">
                    <label>Distrito</label>
                    <md-icon class="name">location_city</md-icon>
                    <input ng-model="ambito.distrito" type="text" disabled>
                </md-input-container>
                <md-input-container flex="50" class="md-icon-float md-block">
                    <label>Tipo de Ambito</label>
                    <md-icon class="name">location_city</md-icon>
                    <md-select ng-change="getAnexos()" ng-model="ambito.tipoAmbito"  ng-disabled="true">
                        <md-option value="1">Principal</md-option>
                        <md-option value="2">Anexo</md-option>
                    </md-select>
                </md-input-container>                 
            </div>               
            <div layout="row" ng-hide="ambito.tipoAmbito == constantAmbito.tipo.principal" layout-align="start">
                <md-input-container flex="50" class="md-icon-float md-block">
                    <label>Ambito Padre</label>
                    <md-icon class="name">location_city</md-icon>                  
                    <md-select  ng-model="ambito.ambitoPadre"  ng-disabled="switchAmbito">
                        <md-option ng-repeat="ambito in lstAmbitosPadre| filter:{ambitoPadre:null}  | orderBy:'nombreAmbito'" ng-value="ambito.id">[[ambito.nombreAmbito]]</md-option>
                    </md-select>                  
                </md-input-container>                

                <md-input-container flex="50" class="md-icon-float md-block">
                    <label>Nombre de Anexo</label>
                    <md-icon class="name">location_city</md-icon>
                    <input ng-model="ambito.nombreAmbito" type="text" ng-disabled="switchAmbito">
                </md-input-container>                  
            </div>

            <div ng-hide="ambito.tipoAmbito == constantAmbito.tipo.anexo">
                <div layout="row"> 
                    <md-input-container flex="100" class="md-icon-float md-block">
                        <md-icon class="name">location_city</md-icon>
                        <label>Nombre del Ambito Principal</label>
                        <input ng-model="ambito.nombreAmbito" required name="nombre" ng-disabled="switchAmbito"/>
                    </md-input-container>
                </div>   

                <div layout="row" ng-repeat="anexo in anexos">
                    <span ng-bind="$index + 1 + '°'" flex="5" style="margin-top: 20px;"></span>
                    <md-input-container flex="45">
                        <label>Ingrese nombre del adjunto</label>
                        <input required name="name" ng-model="anexo.nombreAmbito" ng-disabled="true"/>
                    </md-input-container>

                    <md-input-container flex="45">
                        <label>Selecciones el tipo de adjunto</label>
                        <md-select name="type" ng-model="anexo.categoria" required style="margin:0;" ng-disabled="true">
                            <md-option ng-repeat=" categoria in constantUbigeo.categorias" ng-value="categoria.id">[[categoria.nombre]]</md-option>
                        </md-select>
                    </md-input-container>
                    <span flex="5" style="margin-top: 10px;">
                        <md-button ng-click="deleteAnexo($index)" class="md-icon-button" ng-disabled="true">
                            <md-icon class="material-icons">delete</md-icon> 
                        </md-button>
                    </span>
                </div>
            </div>            
        </form>
    </md-dialog-content>
    <md-dialog-actions layout="row" layout-align="left">
        <md-button ng-click="close()" class="btn-cancel">CANCELAR</md-button>
        <md-button ng-hide="switchAmbito" ng-click="submit()" class="btn-confirm">GUARDAR</md-button> 
    </md-dialog-actions>
</md-dialog>