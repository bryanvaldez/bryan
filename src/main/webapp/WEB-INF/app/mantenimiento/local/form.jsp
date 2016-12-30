<md-dialog aria-label="CREAR LOCAL" class="modal-form">
    <md-toolbar>
        <div class="md-toolbar-tools">
            <h1>Locales de Votación</h1>
            <h2>[[local.id == null?'CREAR':'EDITAR']] LOCAL DE VOTACIÓN</h2>
            <md-button class="md-icon-button btn-close" ng-click="cancel()">
                <md-icon aria-label="Close dialog" class="material-icons" ng-click="close()">close</md-icon>
            </md-button>
        </div>
    </md-toolbar>
    <form name="localForm" ng-submit="localForm.$valid && submit()"> 
        <md-dialog-content>
            <div layout="row" layout-align="start">
                <md-input-container flex="30" class="md-icon-float md-block">
                    <span>Codigo</span>
                </md-input-container>
                <md-input-container flex="70" class="md-icon-float md-block">
                    <label>Codigo</label>
                    <input  class="inputhide" ng-model="local.codigo" type="text" ng-disabled="local.id != null" ui-mask="[[9999]]" required>
                    <div class="hint" >Ingrese los 4 digitos del código.</div>
                </md-input-container>              
            </div>
            <div layout="row" layout-align="start">
                <md-input-container flex="30" class="md-icon-float md-block">
                    <span>Nombre</span>
                </md-input-container>
                <md-input-container flex="70" class="md-icon-float md-block">
                    <label>Nombre</label>
                    <input ng-model="local.nombre" type="text" required>
                </md-input-container>              
            </div> 
            <div layout="row" layout-align="start">
                <md-input-container flex="30" class="md-icon-float md-block">
                    <span>Dirección</span>
                </md-input-container>
                <md-input-container flex="70" class="md-icon-float md-block">
                    <label>Dirección</label>
                    <input ng-model="local.direccion" type="text" required>
                </md-input-container>              
            </div>              
            <div layout="row" layout-align="start">
                <md-input-container flex="30" class="md-icon-float md-block">
                    <span>Referencia</span>
                </md-input-container>
                <md-input-container flex="70" class="md-icon-float md-block">
                    <label>Referencia</label>
                    <input ng-model="local.referencia" type="text" required>
                </md-input-container>              
            </div>
            <div layout="row" layout-align="start">
                <md-input-container flex="30" class="md-icon-float md-block">
                    <span>Seleccione Estado</span>
                </md-input-container>                
                <md-input-container flex="70" class="md-block">   
                    <md-radio-group layout="row"  ng-model="local.estado" required>
                        <md-radio-button flex ="25" ng-value="0" class="md-primary" style="color:#259b24;font-weight: bold;"> Habilitado </md-radio-button>
                        <md-radio-button flex ="25" ng-value="1" class="md-primary" style="color:#e51c23;font-weight: bold;"> Deshabilitado </md-radio-button>
                    </md-radio-group>
                </md-input-container>                 
            </div>           
        </md-dialog-content>
        <md-dialog-actions layout="row" layout-align="left">        
            <md-button ng-click="close()" class="btn-cancel">CANCELAR</md-button>
            <md-button type="submit" class="btn-confirm">[[local.id == null?'CREAR':'GUARDAR']]</md-button> 
        </md-dialog-actions>
    </form>   
</md-dialog>