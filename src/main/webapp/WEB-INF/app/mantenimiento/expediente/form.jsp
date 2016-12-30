<md-dialog aria-label="CREAR LOCAL" class="modal-form" id="popupContainer">
    <md-toolbar>
        <div class="md-toolbar-tools">
            <h1>Expediente</h1>            
            <h2>[[expediente.id == null?'CREAR':'EDITAR']] EXPEDIENTE</h2>
            <md-button class="md-icon-button btn-close" ng-click="cancel()">
                <md-icon aria-label="Close dialog" class="material-icons" ng-click="close()">close</md-icon>
            </md-button>
        </div>
    </md-toolbar>
    <form name="expForm" ng-submit="expForm.$valid && submit()"> 
        <md-dialog-content>            
            <div layout="row" layout-align="start">
                <md-input-container flex="30" class="md-icon-float md-block">
                    <span>Expediente:</span>
                </md-input-container>
                <md-input-container flex="35" class="md-icon-float md-block">
                    <label>Expediente</label>
                    <input class="inputhide" required name="name" ng-model="name" type="text" ui-mask="[[999999]]">                    
                    <div class="hint" >Ingrese 6 digitos.</div>
                </md-input-container>
                <md-input-container flex="35">
                    <label>Año</label>
                    <md-select required ng-model="year" required>
                        <md-option value="2016">2016</md-option>
                        <md-option value="2017">2017</md-option>
                        <md-option value="2018">2018</md-option>
                        <md-option value="2019">2019</md-option>
                        <md-option value="2020">2020</md-option>
                    </md-select>
                </md-input-container>               
            </div>
            <div layout="row" layout-align="start">
                <md-input-container flex="30" class="md-icon-float md-block" >
                    <span>Fecha:</span>
                </md-input-container>
                <md-datepicker required ng-model="expediente.fechaExpediente" md-placeholder="Seleccione Fecha" required></md-datepicker>              
            </div>  
            <div>
                <font color="red">[[info]]</font>
            </div>
        </md-dialog-content>
        <md-dialog-actions layout="row" layout-align="left">        
            <md-button ng-click="close()" class="btn-cancel">CANCELAR</md-button>
            <md-button type="submit" class="btn-confirm">[[expediente.id == null?'CREAR':'GUARDAR']]</md-button> 
        </md-dialog-actions>
    </form>          
</md-dialog>