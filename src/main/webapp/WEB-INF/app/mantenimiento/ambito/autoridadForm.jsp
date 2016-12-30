
<md-dialog aria-label="CREAR USUARIO" class="modal-form">
    <form name="ambitoForm" ng-submit="submitAutoridad()"> 
    <md-toolbar>
        <div class="md-toolbar-tools">
            <h1>Ambitos</h1>
            <h2>Datos de Autoridad</h2>
            <md-button class="md-icon-button btn-close" ng-click="cancel()">
                <md-icon aria-label="Close dialog" class="material-icons" ng-click="close()">close</md-icon>
            </md-button>              
        </div>
    </md-toolbar>

    <md-dialog-content>
            
<!----------------------------  DATOS AUTORIDAD   ------------------------------>   
        <div ng-show="indexAuth">
            <h3 style="margin-top: 0;color: #173456;">Autoridad 1</h3>
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Cargo</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Selecciones el tipo de Cargo</label>
                    <md-select ng-model="observacion.cargo" placeholder="Cargo" >
                      <md-option value="1">Alcalde</md-option>
                      <md-option value="2">Representante</md-option>
                    </md-select>                    
                </md-input-container>              
            </div>
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Apellido Paterno</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Apellido Paterno</label>
                    <input ng-model="observacion.apellidoPaterno" type="text">
                </md-input-container>              
            </div> 
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Apellido Materno</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Apellido Materno</label>
                    <input ng-model="observacion.apellidoMaterno" type="text">
                </md-input-container>              
            </div>              
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Nombres</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Nombres</label>
                    <input ng-model="observacion.nombres" type="text">
                </md-input-container>              
            </div>
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Teléfonos</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Teléfonos</label>
                    <input ng-model="observacion.telefono" type="text">
                </md-input-container>              
            </div>
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Dirección</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Dirección</label>
                    <input ng-model="observacion.direccion" type="text">
                </md-input-container>              
            </div>
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Correo Electrónico</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Correo Electrónico</label>
                    <input ng-model="observacion.email" type="text">
                </md-input-container>              
            </div>             
        </div>

<!----------------------------  DATOS AUTORIDAD 2   ------------------------------>   
        <div ng-show="!indexAuth">
            <h3 style="margin-top: 0;color: #173456;">Autoridad 2</h3>
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Cargo</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Selecciones el tipo de Cargo</label>
                    <md-select ng-model="observacion.cargo_" placeholder="Cargo" >
                      <md-option value="1">Alcalde</md-option>
                      <md-option value="2">Representante</md-option>
                    </md-select>                    
                </md-input-container>              
            </div>
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Apellido Paterno</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Apellido Paterno</label>
                    <input ng-model="observacion.apellidoPaterno_" type="text">
                </md-input-container>              
            </div> 
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Apellido Materno</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Apellido Materno</label>
                    <input ng-model="observacion.apellidoMaterno_" type="text">
                </md-input-container>              
            </div>              
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Nombres</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Nombres</label>
                    <input ng-model="observacion.nombres_" type="text">
                </md-input-container>              
            </div>
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Teléfonos</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Teléfonos</label>
                    <input ng-model="observacion.telefono_" type="text">
                </md-input-container>              
            </div>
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Dirección</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Dirección</label>
                    <input ng-model="observacion.direccion_" type="text">
                </md-input-container>              
            </div>
            <div layout="row" layout-align="start">
                <md-input-container flex="40" class="md-icon-float md-block">
                    <span>Correo Electrónico</span>
                </md-input-container>
                <md-input-container flex="60" class="md-icon-float md-block">
                    <label>Correo Electrónico</label>
                    <input ng-model="observacion.email_" type="text">
                </md-input-container>              
            </div>             
        </div>


    </md-dialog-content>
            
    <md-dialog-actions layout="row" layout-align="left">
            <md-button class="btn-cancel" ng-click="close()">CANCELAR</md-button>            
            <md-button type="submit" class="btn-confirm">GUARDAR</md-button>          
    </md-dialog-actions>
    </form>
</md-dialog>