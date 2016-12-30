<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:master title="Carga">  
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/program/carga.css" media="screen,projection"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/service/cargaService.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/controller/cargaController.js"></script>
    
    <div style="background:#fff; padding: 30px 50px 10px 50px;" ng-controller="cargaCtrl">
        <div class="carga-section" ng-show="showUpload">
            <div class="section-head animated fadeIn">
                <md-button href="${pageContext.request.contextPath}/main" class="md-icon-button btn-back" >
                    <md-icon class="material-icons">arrow_back</md-icon>
                </md-button>
                <h2 class="title-section">Carga Masiva - Seleccionar Expediente</h2>
            </div>

            <div layout="row">
                <div flex-gt-xs="50" layout="column" style="margin-right:20px;">   
                    <md-autocomplete 
                        md-selected-item="cargaCtrl.selectedItem"
                        md-search-text="cargaCtrl.searchTextUbigeo"
                        md-selected-item-change="selectedUbigeo(item)"
                        md-items="item in searchByUbigeo(cargaCtrl.searchTextUbigeo)"
                        md-item-text="item.DESCRIPCION"
                        md-min-length="1"
                        md-floating-label="Ingrese el nombre de un Departamento, Provincia o Distrito...">
                        <md-item-template>
                            <span md-highlight-text="cargaCtrl.searchTextUbigeo" md-highlight-flags="^i">[[item.DESCRIPCION]]</span>
                        </md-item-template>
                        <md-not-found>
                            No se encuentran resultados para "[[ cargaCtrl.searchTextUbigeo ]]".
                        </md-not-found>
                    </md-autocomplete>
                    
                    <md-input-container class="md-block">
                        <label>Seleccione un Departamento</label>
                        <md-select name="type" ng-model="selected.departamento" ng-change="getUbigeos(2,selected.departamento)">
                            <md-option ng-repeat="departamento in departamentos | orderBy:'DEPARTAMENTO'" ng-value="departamento.UBIGEO">
                                [[departamento.DEPARTAMENTO]]
                            </md-option>
                        </md-select>
                    </md-input-container>

                    <md-input-container class="md-block">
                        <label>Seleccione una Provincia</label>
                        <md-select name="type" ng-model="selected.provincia" ng-change="getUbigeos(3,selected.provincia)" ng-disabled="selected.departamento == null">
                            <md-option ng-repeat="provincia in provincias | orderBy:'PROVINCIA'" ng-value="provincia.UBIGEO">
                                [[provincia.PROVINCIA]]
                            </md-option>
                        </md-select>
                    </md-input-container> 

                    <md-input-container class="md-block">
                        <label>Seleccione un Distrito</label>
                        <md-select name="type" ng-model="selected.distrito" ng-change="getCCPP(selected.distrito)" ng-disabled="selected.provincia == null">                            
                            <md-option ng-repeat="distrito in distritos | orderBy:'DISTRITO'" ng-value="distrito.UBIGEO">
                                [[distrito.DISTRITO]]
                            </md-option>
                        </md-select>
                    </md-input-container>                 
                </div>
                <div class="line-vertical"></div>
                <div flex-gt-xs="50" layout="column" style="margin-left:20px;">
                    <md-autocomplete 
                        md-selected-item="cargaCtrl.selectedAmbito"
                        md-search-text="cargaCtrl.searchTextLocal"
                        md-selected-item-change="selectedCCPP(item)"
                        md-items="item in searchByAmbito(cargaCtrl.searchTextLocal)"
                        md-item-text="item.C_NOMBRE_AMBITO"
                        md-min-length="1"
                        md-floating-label="Ingrese el nombre de un Centro Poblado Principal"">
                        <md-item-template>
                            <span md-highlight-text="cargaCtrl.searchTextLocal" md-highlight-flags="^i">[[item.C_NOMBRE_AMBITO]]</span>
                        </md-item-template>
                        <md-not-found>
                            No se encuentran resultados para "[[ cargaCtrl.searchTextLocal ]]".
                        </md-not-found>
                    </md-autocomplete>
                    <md-input-container class="md-block">
                        <label>Seleccione un Centro Poblado</label>
                        <md-select name="type" ng-model="selected.ccpp" ng-disabled="selected.distrito == null" ng-change="getExpedientes(selected.ccpp);">
                            <md-option ng-repeat="cp in ccpp | orderBy:'CCPP'" ng-value="cp.ID">
                                [[cp.CCPP]]
                            </md-option>
                        </md-select>
                    </md-input-container>
                    <md-input-container class="md-block">
                        <label>Seleccione un Expediente</label>
                        <md-select name="type" ng-model="selected.expediente" ng-disabled="selected.ccpp == null">
                            <md-option ng-repeat="expediente in expedientes | orderBy:'EXPEDIENTE'" ng-value="expediente.ID">
                                [[expediente.EXPEDIENTE]]
                            </md-option>
                        </md-select>
                    </md-input-container>                 
                </div>
            </div>
                    <lf-ng-md-file-input 
                     lf-files="filePadron" 
                     lf-placeholder="" 
                     lf-browse-label="Seleccionar archivo a Cargar" 
                     lf-remove-label=""
                     lf-api="lfApi"
                     accept=".xls,.xlsx"
                     ng-disabled="selected.expediente == null"
                     progress ng-click=""></lf-ng-md-file-input>

                <div ng-show="charge">
                    <md-progress-linear md-mode="indeterminate"></md-progress-linear>                       
                </div>                            
            <div ng-show="showObs"> 
                <span style="color:#e51c23;">El archivo presenta errores de formato</span>                
                <md-button class="md-icon-button" aria-label="View" ng-click="showDialogFmt($event)">
                    <md-icon style="color:#e51c23;"class="material-icons">visibility</md-icon>
                </md-button>
            </div>
        
        </div>
        
        <div class="cargalist-section" ng-show="!showUpload">
            <div class="section-head animated fadeIn">
                <md-button href="${pageContext.request.contextPath}/carga/carga" class="md-icon-button btn-back" >
                    <md-icon class="material-icons">arrow_back</md-icon>
                </md-button>
                <h5 class="title-section">Carga Masiva</h5>
            </div>
            <ul class="breadcrumbs">
                <li ng-bind="ambito.departamento.desc"></li>
                <li ng-bind="ambito.provincia.desc"></li>
                <li ng-bind="ambito.distrito.desc"></li>
            </ul>
            <h3 ng-bind="ambito.ccpp.desc"></h3>
            <div class="line"></div>
            <table class="table-default">            
                <thead>
                    <tr>
                        <th style="width: 5%;" class="rownum">N°</th>
                        <th style="width: 15%;">DNI</th>
                        <th style="width: 15%;" class="space">Apellido paterno</th>
                        <th style="width: 15%;" >Apellido materno</th>
                        <th style="width: 15%;" >Nombre</th>
                        <th style="width: 15%;" >Observacion del sistema</th>
                        <th style="width: 15%;" >Observacion del usuario</th>
                        <th style="width: 5%;" class="space">Estado</th>
                    </tr>
                </thead>
                </table>        
            <div style="overflow-y: auto; height: 330px;">
                <table class="table-default">
                <tbody>
                    <tr ng-repeat="elector in padron">
                        <td style="width: 5%;" class="rownum">[[$index + 1]]</td>
                        <td style="width: 15%;">[[ elector.C_DOCUMENTO_IDENTIDAD ]]
                            <md-button ng-click="searchElector(elector)" class="md-icon-button btn-action" >
                                <md-icon class="material-icons">search</md-icon> 
                            </md-button>
                        </td>
                        <td style="width: 15%;" class="space">[[ elector.C_APELLIDO_PATERNO ]]</td>
                        <td style="width: 15%;" >[[ elector.C_APELLIDO_MATERNO ]]</td>
                        <td style="width: 15%;">[[ elector.C_NOMBRE ]]</td>
                        <td class="space action-col" style="width: 15%;text-align: center;">   
                            <md-button class="md-icon-button" ng-click="showDialogObs($event,elector.C_OBSERVACION.SISTEMA)">
                                <md-icon class="material-icons">visibility</md-icon> 
                            </md-button>
                        </td>
                        <td style="width: 15%;" class="action-col">      
                            <md-select ng-model="obsSelected[$index].current" placeholder="- Seleccione -" ng-init="idxpadre=$index;obsSelected[$index].status=elector.N_ESTADO;">
                                <md-option ng-click="updateStatus(idxpadre,o)"><em>Ninguno</em></md-option>
                                <md-option ng-value="o.key" ng-repeat="o in constantPadron.obsUsuario" ng-click="updateStatus(idxpadre,o)">[[ o.desc ]]</md-option> 
                            </md-select> 
                        </td>                   
<!--                        <td style="width: 10%;" class="space action-col">
                            <i class="label label-green" ng-if="elector.N_ESTADO == constantPadron.estado.habilitado.id" class="fa fa-circle" style="font-size:18px;"></i>
                            <i class="label label-yellow" ng-if="elector.N_ESTADO == constantPadron.estado.pendiente.id" class="fa fa-circle" style="font-size:18px;"></i>
                            <i class="label label-red" ng-if="elector.N_ESTADO == constantPadron.estado.rechazado.id" class="fa fa-circle" style="font-size:18px;"></i>
                            <i class="label " ng-if="elector.N_ESTADO == constantPadron.estado.dniRegistrado.id" class="fa fa-circle" style="font-size:18px;"></i>
                            <i class="label " ng-if="elector.N_ESTADO == constantPadron.estado.eliminado.id" class="fa fa-circle" style="font-size:18px;"></i>
                            
                            <span class="label label-green" ng-if="elector.N_ESTADO == constantPadron.estado.habilitado.id" ng-bind="constantPadron.estado.habilitado.valor"></span>
                            <span class="label label-yellow" ng-if="elector.N_ESTADO == constantPadron.estado.pendiente.id" ng-bind="constantPadron.estado.pendiente.valor"></span>
                            <span class="label label-red" ng-if="elector.N_ESTADO == constantPadron.estado.rechazado.id" ng-bind="constantPadron.estado.rechazado.valor"></span>
                            <span class="label " ng-if="elector.N_ESTADO == constantPadron.estado.dniRegistrado.id" ng-bind="constantPadron.estado.dniRegistrado.valor"></span>
                            <span class="label " ng-if="elector.N_ESTADO == constantPadron.estado.eliminado.id" ng-bind="constantPadron.estado.eliminado.valor"></span>
                        </td>-->
                        <td style="width: 5%;" class="space action-col"><i ng-class="{positivo: elector.N_ESTADO == constantPadron.estado.habilitado.id, negativo: elector.N_ESTADO == constantPadron.estado.rechazado.id, pendiente: elector.N_ESTADO == constantPadron.estado.pendiente.id}" class="fa fa-circle" style="font-size:18px;"></i></td>
                    </tr>
                </tbody>            
            </table>
            </div>
            <div class="layout action-btn" layout-align="center center">
                <md-button ng-click="guardar()" class="md-raised btn-default" >Guardar</md-button>
            </div>        
            
        </div>
        
    </div>
</t:master>