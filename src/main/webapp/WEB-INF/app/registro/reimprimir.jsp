<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div style="padding: 30px 50px;" class="users-section" ng-cloak>

        
        
        <div class="section-head animated fadeIn" >
            <md-button ng-click="pageRegistro()" class="md-icon-button btn-back" >
                <md-icon class="material-icons">arrow_back</md-icon>
            </md-button>
                <h5 class="title-section">Reimpresión</h5>
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
                <md-button ng-disabled="auxInicial==='' || auxFinal ===''" style="width: 100%;" class="md-raised btn-default"  ng-click="reImprimir()" >REIMPRIMIR</md-button>
            </div>
        </div>
        <div class="line"></div>        
            
    <div layout="row" layout-align="start"> 
        <table class="table-default" flex="60">
            <thead>
                <tr>
                    <th style="width: 30px;">N°</th>                            
                    <th class="space">DNI</th>
                    <th class="space">Ap. Paterno</th>                            
                    <th class="space">Ap. Materno</th>
                    <th class="space">Nombres</th>                                                   
                </tr>
            </thead>
            <tbody>
                <tr ng-repeat="eImp in electoresImp">
                    <td class="rownum"><span ng-bind="eImp.ordenRegistro"></span></td>                            
                    <td class="space"><span ng-bind="eImp.dni"></span></td>
                    <td class="space"><span ng-bind="eImp.apellidoPaterno"></span></td>                            
                    <td class="space"><span ng-bind="eImp.apellidoMaterno"></span></td>
                    <td class="space"><span ng-bind="eImp.nombre"></span></td>                    
            </tr> 
            </tbody>
        </table>
        <div flex="40">
            <md-card>
                <section style="margin-top: 20px;" layout="row" layout-sm="column" layout-align="center center" layout-wrap> 
                    <md-radio-group ng-change="getCompaginados()" layout="row"  flex="80" ng-model="indicadorImp">
                        <md-radio-button flex ="50" value="0" class="md-primary" > Imprimir mismo ubigeo </md-radio-button>
                        <md-radio-button flex ="50" value="1" class="md-primary" > Imprimir distinto ubigeo </md-radio-button>
                    </md-radio-group>                    
                </section>
                <md-card-content>
                    <md-card ng-class="{borderImpresion: compaginado.codigo}" >
                        <md-card-title>
                            <md-card-title-text>
                                <span>Imprimir por PÁGINA</span>
                            </md-card-title-text>
                        </md-card-title>
                        <md-card-content>
                            <md-select ng-focus="focusCompaginado()" ng-change="getCompaginado()" ng-model="compaginado.codigo" placeholder="Seleccione el compaginado...">
                                <md-option ng-repeat="pag in paginas" ng-value="pag.ID">
                                    [[pag.REGISTROINICIAL]]  -  [[pag.REGISTROFINAL]]
                                </md-option></md-select>
                        </md-card-content>
                    </md-card>
                    <md-card ng-class="{borderImpresion: rango.inicial || rango.final}">
                        <md-card-title>
                            <md-card-title-text>
                                <span>Imprimir por RANGO</span>
                            </md-card-title-text>
                        </md-card-title>
                        <md-card-content>
                            <div layout="row">
                                <md-input-container flex="40">
                                    <label>Rango inicial</label>
                                    <input ng-focus="focusTxtRango()" ng-model="rango.inicial" maxlength="4">
                                </md-input-container>
                                <md-input-container flex="40">
                                    <label>Rango final</label>
                                    <input ng-focus="focusTxtRango()" ng-model="rango.final" maxlength="4">
                                </md-input-container>
                                <div flex>            
                                    <md-button ng-disabled="(rango.inicial ==='') || (rango.final ==='')" class="md-raised" ng-click="getRangoElectores()" >BUSCAR</md-button>
                                </div>
                            </div>
                        </md-card-content>
                    </md-card>
                    <md-card ng-class="{borderImpresion: chbPrintTodos}">
                        <md-card-title>
                            <md-card-title-text>
                                <md-radio-group ng-model="indicador">
                                    <md-checkbox ng-disabled="indicadorImp===''" ng-change="getPrintTodos()" ng-model="chbPrintTodos" aria-label="Checkbox 1">
                                        Imprimir TODOS los registros
                                    </md-checkbox>              
                                </md-radio-group>
                            </md-card-title-text>
                        </md-card-title>
                    </md-card>
                </md-card-content>
            </md-card>
        </div>        
    </div>   
</div>                          
