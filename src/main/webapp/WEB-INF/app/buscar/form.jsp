


<div class="users-section" ng-cloak>
    <form name="userForm" ng-submit="submitSearch()">
    <div class="modal-form" style="max-width: 100%">
        <md-toolbar>
            <div class="md-toolbar-tools">            
                <h2>BÚSQUEDA</h2>
                <md-button class="md-icon-button btn-close" ng-click="cancel()">
                    <md-icon aria-label="Close dialog" class="material-icons" ng-click="close()">close</md-icon>
                </md-button>
            </div>
        </md-toolbar>         
    </div>     
            
    <div layout="row" layout-align="start" style="width: 790px; margin: 10px 30px;">
        <div flex="30">
            <md-input-container flex="100" class="md-icon-float md-block">
                 <label>Número de DNI...</label>
                 <input name="dni" ng-model="padron.numele" type="text">                    
             </md-input-container>

             <md-input-container flex="100" class="md-icon-float md-block">
                 <label>Apellido Paterno...</label>
                 <input name="paterno" ng-model="padron.apePat" type="text">                    
             </md-input-container>

             <md-input-container flex="100" class="md-icon-float md-block">
                 <label>Apellido Materno...</label>
                 <input name="materno" ng-model="padron.apMat" type="text">                    
             </md-input-container>

             <md-input-container flex="100" class="md-icon-float md-block">
                 <label>Nombres...</label>
                 <input name="nombre" ng-model="padron.nombres" type="text">                    
             </md-input-container>
        </div>        
        <table class="table-default" flex="70">
            <thead>
                <tr>
                    <th colspan="5">RESULTADOS DE BUSQUEDA</th>                                
                </tr>                
            </thead>
            <tbody>
                <tr ng-repeat="padron in padrones">
                    <td class="rownum"><span ng-bind="$index + 1"></span></td>                            
                    <td class="space"><span ng-bind="padron.numEle"></span></td>
                    <td class="space"><span ng-bind="padron.apePat"></span></td>                            
                    <td class="space"><span ng-bind="padron.apMat"></span></td>
                    <td class="space"><span ng-bind="padron.nombres"></span></td>                    
            </tr> 
            </tbody>
        </table>         
    </div>
        <md-dialog-actions layout="row" layout-align="left">            
            <md-button type="submit" class="btn-confirm">BUSCAR</md-button> 
        </md-dialog-actions>        
</form>         
</div>                          
