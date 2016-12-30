<md-dialog aria-label="CREAR LOCAL" class="modal-form">     
    <md-toolbar>
        <div class="md-toolbar-tools">
            <h1>Local de Votación</h1>
            <h2>ASIGNAR NUEVO AMBITO</h2>
            <md-button class="md-icon-button btn-close" ng-click="cancel()">
                <md-icon aria-label="Close dialog" class="material-icons" ng-click="close()">close</md-icon>
            </md-button>
        </div>
    </md-toolbar>
    <form name="userForm" ng-submit="submitChange()"> 
        <md-dialog-content>
            <div layout="row" layout-align="start">
                <md-input-container flex="30" class="md-icon-float md-block">
                    <span>Seleccione</span>
                </md-input-container>
                <md-input-container flex="70" class="md-icon-float md-block">
                    <label>Ambito</label>
                    
                    <md-select ng-model="ambitoChange.id" placeholder="Seleccione un Ambito..." required>                        
                        <md-option ng-repeat="ambitoChange in ambitos | orderBy:'nombreAmbito'" ng-value="ambitoChange.id">
                            [[ambitoChange.nombreAmbito]]
                        </md-option>                                    
                    </md-select>                                        
                </md-input-container>              
            </div>                                                    
        </md-dialog-content>
        <md-dialog-actions layout="row" layout-align="left">        
            <md-button ng-click="close()" class="btn-cancel">CANCELAR</md-button>
            <md-button type="submit" class="btn-confirm">ASIGNAR</md-button> 
        </md-dialog-actions>
    </form>   
</md-dialog>