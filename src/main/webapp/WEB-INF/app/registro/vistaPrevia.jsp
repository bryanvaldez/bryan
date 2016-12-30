
<md-dialog aria-label="VISTA PREVIA" class="modal-form">
    <md-toolbar>
        <div class="md-toolbar-tools">
            <h1>Vista Previa</h1>
            <h2>REGISTRO DE ELECTORES</h2>
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
        <div layout="row" layout-xs="column">
            <span>00002_2016</span>                                        
        </div>
        <div layout="row">
            <table class="table-default">
                <thead>
                    <tr>
                        <th style="width: 30px;">N°</th>
                        <th class="space">DNI</th>                                
                        <th class="space">Nombres</th>
                        <th class="space">Apellidos</th>                                
                    </tr>
                </thead>
                <tbody>
                    <tr ng-repeat="electorPrevia in electoresPrevia">
                        <td class="space"><span ng-bind="electorPrevia.N_ORDEN_REGISTRO"></span></td>
                        <td class="space"><span ng-bind="electorPrevia.C_DOCUMENTO_IDENTIDAD"></span></td>
                        <td class="space"><span ng-bind="electorPrevia.C_NOMBRE"></span></td>                                
                        <td class="space"><span ng-bind="electorPrevia.C_APELLIDO_PATERNO + ' ' + electorPrevia.C_APELLIDO_MATERNO"></span></td>
                    </tr>                                        
                </tbody>
            </table>
        </div> 
    </md-dialog-content>    
    <md-dialog-actions layout="row">               
        <md-button ng-click="printVP()">
            Imprimir
        </md-button>
    </md-dialog-actions>
</md-dialog>