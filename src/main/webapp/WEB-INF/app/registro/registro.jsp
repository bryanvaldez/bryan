<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="hidden" ng-init="lstDpto =<c:out value='${lstDptos}'/>" />
<div>
    <div class="cargalist-section"  ng-cloak>
        <div class="section-head animated fadeIn" >
            <md-button href="${pageContext.request.contextPath}/registro/registrar" class="md-icon-button btn-back" >
                <md-icon class="material-icons">arrow_back</md-icon>
            </md-button>
            <h5 class="title-section">Expediente <span style="font-size: 18px;">([[expediente.expediente]])</span></h5>
        </div>
        <ul class="breadcrumbs" style="margin: 0;">
            <li>[[expediente.ambito.departamento]]</li>
            <li>[[expediente.ambito.provincia]]</li>
            <li>[[expediente.ambito.distrito]]</li>
        </ul>
        <div layout-gt-xs="row">
            <div flex-gt-xs="80">
                <h3>[[expediente.ambito.nombreAmbito]]</h3>
            </div>
            <div flex-gt-xs="20">
                <md-button style="width: 100%;" class="md-raised btn-default"  ng-click="pageReimprimir()" >REIMPRIMIR</md-button>
            </div>
        </div>
        <div class="line"></div>
        <div style="margin-top: 5px; margin-bottom: 5px;" layout-gt-xs="row">
            <div >
                <p style="color: #005780;margin: 6px;font-size: 23px;font-weight: bold;">Ingrese un DNI</p>
            </div>
            <div style="margin: 6px;" class="input-group" >
                <input style="text-align: center;border-bottom: 2px solid #c6911a;" type="text" ng-model="numele" ng-enter="searchDni()" placeholder="Busque por DNI..." class="ng-pristine ng-untouched ng-valid ng-empty" aria-invalid="false">
                <button ng-click="searchDni()"><i class="fa fa-level-down" style="font-size:20px;-webkit-transform: rotate(90deg);color: #c6911a;">[[]]</i></button>
            </div>
        </div>        

        <div class="line"></div>
        <table class="table-default">
            <thead>
                <tr>
                    <th style="width: 5%;" class="rownum">N°</th>                            
                    <th style="width: 16%;" class="space">DNI</th>
                    <th style="width: 17%;">Ap. Paterno</th>                            
                    <th style="width: 17%;">Ap. Materno</th>
                    <th style="width: 20%;">Nombres</th>
                    <th style="width: 20%;">Anexo</th>
                    <!--                    <th style="width: 5%;" class="space"></th>                                                        -->
                </tr>
            </thead>
        </table>        
        <div style="overflow-y: scroll; height: 350px;">
            <table class="table-default">
                <tbody>
                    <tr ng-repeat="expElector in expElectores">
                        <td style="width: 5%;"><span ng-bind="expElectores.length - $index"></span></td>                            
                        <td style="width: 16%;" class="space">
                            <span ng-bind="expElector.C_DOCUMENTO_IDENTIDAD"></span>
                            <!--                        <md-button class="md-icon-button btn-action" ><i class="fa fa-search">[[]]</i></md-button>-->
                        </td>
                        <td style="width: 17%;"><span ng-bind="expElector.C_APELLIDO_PATERNO"></span></td>                            
                        <td style="width: 17%;"><span ng-bind="expElector.C_APELLIDO_MATERNO"></span></td>
                        <td style="width: 20%;"><span ng-bind="expElector.C_NOMBRE"></span></td>
                        <td style="width: 20%;" class="space action-col"><md-select ng-change="updateAmbito(expElector)" ng-model="expElector.N_AMBITO" ng-disabled="expElector.N_ESTADO === 1" placeholder="Ambito...">
                    <md-option ng-repeat="amb in ambitos" ng-value="amb.N_AMBITO_PK">
                        [[amb.C_NOMBRE_AMBITO]]
                    </md-option></md-select>
                </td>
                <!--                    <td style="width: 5%;" class="space"><md-button ng-click="delete()" class="md-icon-button" ><i class="fa fa-trash-o" style="font-size:17px;"></i>[[]]</md-button></td>-->
                </tr>
                </tbody>
            </table>
            <div style="text-align: center;" class="loading-message" ng-hide="expElectores.length"><h3>No se encontraron datos.</h3></div>
        </div>
    </div>
    <div layout="row">
        <div  flex-gt-sm="50" flex-gt-xs="50" class="bottom-sheet-demo inset" layout="row" layout-xs="column" layout-align="center">
            <md-button style="font-size:30px;border: 4px solid #005780;color: #005780;" flex-gt-xs="100" flex-gt-sm="70" class="md-raised" >
                <i class="fa fa-check" style="margin-right: 5%;padding: 5px;"></i>
                [[totalMismoUbigeo]]
            </md-button>
            <md-button style="font-size:30px;border: 4px solid #005780;color: #005780;" flex-gt-xs="100" flex-gt-sm="15" class="md-raised" ng-click="vistaPreviaMSelected()">
                <i class="fa fa-eye" style="margin-right: 5%;padding: 5px;"></i>
                [[totalVPMismoUbigeo]]
            </md-button>
            <md-button style="font-size:30px;border: 4px solid #005780;color: #005780;" flex-gt-xs="100" flex-gt-sm="15" class="md-raised" ng-click="generarListaImpresion(0)" >
                <i class="fa fa-print" style="margin-right: 5%;padding: 5px;"></i>
            </md-button>
        </div>
        <div flex-gt-sm="50" flex-gt-xs="50" class="bottom-sheet-demo inset" layout="row" layout-xs="column" layout-align="center">
            <md-button style="font-size:30px;border: 4px solid #4a4a49;color: #4a4a49;" flex-gt-xs="100" flex-gt-sm="70" class="md-raised" >
                <i class="fa fa-close" style="margin-right: 5%;padding: 5px;"></i>
                [[totalDiferenteUbigeo]]
            </md-button>
            <md-button style="font-size:30px;border: 4px solid #4a4a49;color: #4a4a49;" flex-gt-xs="100" flex-gt-sm="15" class="md-raised" ng-click="vistaPreviaDSelected()">
                <i class="fa fa-eye" style="margin-right: 5%;padding: 5px;"></i>
                [[totalVPDiferenteUbigeo]]
            </md-button>
            <md-button style="font-size:30px;border: 4px solid #4a4a49;color: #4a4a49;" flex-gt-xs="100" flex-gt-sm="15" class="md-raised" ng-click="generarListaImpresion(1)" >
                <i class="fa fa-print" style="margin-right: 5%;padding: 5px;"></i>
            </md-button>
        </div>        
    </div>                
    <div layout="row">
        <md-button class="md-raised" ng-click="generarFichaV()">
            GENERAR FICHA
        </md-button>
    </div>            
</div>