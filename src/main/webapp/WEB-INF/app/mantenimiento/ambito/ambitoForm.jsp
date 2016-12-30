
<md-dialog aria-label="CREAR USUARIO" class="modal-form">
    <form name="ambitoForm" novalidate ng-submit="ambitoForm.$valid && submit()"> 
    <md-toolbar>
        <div class="md-toolbar-tools">
            <h1>Ambitos</h1>
            <h2>CREAR</h2>
            <md-button class="md-icon-button btn-close" ng-click="cancel()">
                <md-icon aria-label="Close dialog" class="material-icons" ng-click="close()">close</md-icon>
            </md-button>
            <div ng-if="!ambito.id" class="steps-legend">
                <span  ng-class="index == 0? 'active':''"></span>
                <span  ng-class="index == 1? 'active':''"></span>                
            </div>   
        </div>
    </md-toolbar>

    <md-dialog-content>
            
<!----------------------------  UBIGEO   ------------------------------>
    <div ng-show="index == 0">        
        <div layout="row" layout-align="start">
            <md-input-container flex="30" class="md-icon-float md-block">
                Departamento 
            </md-input-container>
            <md-input-container flex="70" class="md-icon-float md-block">
                <label>Seleccione el departamento</label>
                <md-select ng-change="getProvincias(departamento,1)" ng-model="departamento" required ng-disabled="showdata">
                    <md-option ng-repeat="dep in departamentos | orderBy:'DESCRIPCION'" ng-value="dep.CODIGO">[[ dep.DESCRIPCION ]]</md-option>
                </md-select>
            </md-input-container>              
        </div>            

        <div layout="row" layout-align="start">
            <md-input-container flex="30" class="md-icon-float md-block">
                Provincia 
            </md-input-container>
            <md-input-container ng-repeat="control in controlsProv" flex="70" class="md-block">
                <label>Seleccione la provincia</label>
                <md-select ng-change="getDistritos(provincia)" ng-model="provincia"  required ng-disabled="showdata">
                    <md-option ng-repeat="prov in provincias | orderBy:'DESCRIPCION'" ng-value="prov.CODIGO">[[ prov.DESCRIPCION ]]</md-option>
                </md-select>
            </md-input-container>              
        </div>

        <div layout="row" layout-align="start">
            <md-input-container flex="30" class="md-icon-float md-block">
                Distrito 
            </md-input-container>
            <md-input-container ng-repeat="control in controlsDist" flex="70" class="md-block">
                <label>Seleccione el distrito</label>
                <md-select ng-change="getAmbitoPadre(ambito.ubigeo)" ng-model="ambito.ubigeo" required ng-disabled="showdata">
                    <md-option ng-repeat="dist in distritos | orderBy:'DESCRIPCION'" ng-value="dist.CODIGO">[[ dist.DESCRIPCION ]]</md-option>
                </md-select>
            </md-input-container>              
        </div>

<!----------------------------  TIPO AMBITO   ------------------------------>            
        <div layout="row">
            <md-input-container flex="100">

                <md-select placeholder="Assign to user" name="type" ng-model="ambito.tipoAmbito" ng-disabled="showdata">
                    <md-option value="1" selected>Principal</md-option>
                    <md-option value="2">Adjunto</md-option>
                </md-select>
            </md-input-container>
        </div>

<!----------------------------  PRINCIPAL   ------------------------------>  
        <div ng-if="ambito.tipoAmbito == constantAmbito.tipo.principal">
            <div layout="row">
                <md-button ng-disabled="ambito.ubigeo == null || ambito.nombreAmbito == '' || showdata" ng-click="addAnexo()" class="md-fab btn-new">
                    <md-icon class="material-icons">add</md-icon>
                </md-button>                    
                <md-input-container flex="100">
                    <label>Ingrese el nombre del Ambito Principal</label>
                    <input ng-disabled="ambito.ubigeo == null || showdata" ng-model="ambito.nombreAmbito" name="nombre" required/>
                </md-input-container>
            </div>
            
            <div layout="row" ng-repeat="anexo in anexos">
                <span ng-bind="$index + 1+'°'" flex="5" style="margin-top: 12px;"></span>
                <md-input-container flex="45">
                    <label>Ingrese nombre del adjunto</label>
                    <input  name="name" ng-model="anexo.nombreAmbito" ng-disabled="showdata"/>
                </md-input-container>

                <md-input-container flex="45">
                    <label>Seleccione el tipo de adjunto</label>
                    <md-select name="type" ng-model="anexo.categoria" required style="margin:0;" ng-disabled="showdata">
                        <md-option ng-repeat=" categoria in constantUbigeo.categorias" ng-value="categoria.id"><span ng-bind="categoria.nombre"></span></md-option>
                    </md-select>
                </md-input-container>
                <span flex="5" style="margin-top: 5px;">
                    <md-button ng-click="deleteAnexo($index, anexo)" class="md-icon-button" ng-hide="showdata">
                        <md-icon class="material-icons">delete</md-icon> 
                    </md-button>
                </span>
            </div>
        </div>

<!----------------------------  ADJUNTO   ------------------------------>  
        <!--<div ng-if="ambito.tipoAmbito == constantAmbito.tipo.anexo">
            <div layout="row" layout-align="space-between center">
                <span class="inline-label">Seleccione el Ambito Principal</span>
                <div class="custom-select">
                <select ng-disabled="ambito.ubigeo == null" required  ng-model="anexo.ambitoPadre" ng-options="ambito.id as ambito.nombreAmbito for ambito in lstAmbitosPadre "><option value="">-- Seleccionar --</option></select>                    
                </div>
            </div>                
            <md-input-container flex="100" style="width:100%">
                <label>Ingrese el nombre del Anexo...</label>
                <input ng-model="anexo.nombreAmbito" name="nombre" required />
            </md-input-container>
            <md-input-container flex="100" style="width:100%">
                <label>Selecciones el tipo de anexo</label>
                <md-select ng-disabled="anexo.nombreAmbito == null" name="type" ng-model="anexo.categoria" required style="margin:0;" required>
                    <md-option ng-repeat=" categoria in constantUbigeo.categorias" ng-value="categoria.id"><span ng-bind="categoria.nombre"></span></md-option>
                </md-select>
            </md-input-container>                
        </div>-->
    </div>
<!----------------------------  DATOS AUTORIDAD   ------------------------------>   
        <div ng-show="index == 1">
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
            <md-button ng-click="nextWindow()" ng-if="index !== 1 && showdata ==false" aria-label="Siguiente Ventana">SIGUIENTE</md-button>
            <md-button ng-click="backWindow()" ng-if="index > 0" aria-label="Ventana Anterior">ANTERIOR</md-button>            
            <md-button ng-if="index == 1" type="submit" class="btn-confirm">[[!ambito.id ? 'CREAR' : 'GUARDAR']]</md-button>[[userForm.$valid]]
            <span  ng-show="ambitoForm.$submitted && !ambitoForm.$valid" style="color:#e7694a; position: absolute; right: 0; bottom: 0;padding: 15px;">Faltan Campos Requeridos.</span>
    </md-dialog-actions>
    </form>
</md-dialog>