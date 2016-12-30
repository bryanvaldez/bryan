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
    <form name="userForm" ng-submit="submit(3)"> 
        <md-dialog-content>                        
            <md-subheader style="padding-top: 0; margin-top: 0; font-size: 17px;color: #4A4A4A;background-color: #fff;" class="md-no-sticky">[[ubigeo]]</md-subheader>
            <div layout="row" layout-align="start">
                <md-input-container flex="30" class="md-icon-float md-block">
                    <span>CCPP</span>                    
                </md-input-container>
                <md-autocomplete flex 
                                md-search-text="searchCCPP"                                     
                                md-items="item in querySearch(searchCCPP)"
                                md-item-text="item.DESCRIPCION"
                                md-selected-item-change="selectedItemSearch(item)"                                     
                                md-floating-label="Ingrese CCPP..."
                                md-require-match="true"
                                md-min-length="1">
                   <div layout="row" layout-align="start center">                    
                       <span md-highlight-text="searchCCPP">{{item.DESCRIPCION}}</span>  
                   </div> 
                   <md-not-found>
                       No se encontraron coincidencias.
                   </md-not-found>
               </md-autocomplete>
            </div>  
            <div layout="row" layout-align="start">
                <md-input-container flex="30" class="md-icon-float md-block">
                    <span>Estado</span>
                </md-input-container>
                <md-input-container flex="70" class="md-icon-float md-block"> 
                    <md-checkbox ng-model="filtro.habilitado" ng-false-value="0" ng-true-value="1">
                        Habilitado
                    </md-checkbox>
                    <md-checkbox ng-model="filtro.pendiente" ng-false-value="0" ng-true-value="1">
                        Pendiente
                    </md-checkbox>
                    <md-checkbox ng-model="filtro.rechazado" ng-false-value="0" ng-true-value="1">
                        Rechazado
                    </md-checkbox>
                </md-input-container>
            </div>  
        </md-dialog-content>
        <md-dialog-actions layout="row" layout-align="left">        
            <md-button ng-click="close()" class="btn-cancel">CANCELAR</md-button>
            <md-button ng-disabled="searchCCPP == ''" type="submit" class="btn-confirm">EXPORTAR</md-button>
        </md-dialog-actions>
    </form>          
</md-dialog>