<md-dialog aria-label="CREAR USUARIO" class="modal-form">
    <form name="userForm" novalidate ng-submit="userForm.$valid && submit()"> 
        <md-toolbar>
            <div class="md-toolbar-tools">
                <h2>Mantenimiento de Usuarios<br/><span ng-bind="!user.id ? 'CREAR USUARIO' : 'EDITAR USUARIO'"></span></h2>
                <md-button class="md-icon-button btn-close" ng-click="close()">
                    <md-icon aria-label="Close dialog" class="material-icons">close</md-icon>
                </md-button>
                <div ng-if="user.perfil.id == constantUser.perfil.registrador" class="steps-legend">
                    <span  ng-class="index == 0? 'active':''"></span>
                    <span  ng-class="index == 1? 'active':''"></span>                
                </div>                
            </div>
        </md-toolbar>
        <md-dialog-content>            
            <div layout="row" layout-align="start">           
                <md-input-container class="md-block" flex="50">
                    <label>Ingrese el numero de DNI <span ng-bind="user.id ? '(Desactivado)': '' "></span></label>
                    <input ng-change="resetDni()" class="inputhide" ng-enter="getUserPadron(user.usuario)" ng-disabled="user.id" required ng-model="user.usuario" ui-mask="[[filterDni]]"/>
                    <div ng-messages="message" ng-hide="showHints">
                        [[message]]
                    </div>                    
                </md-input-container>           
                <md-input-container class="md-block" flex="50">
                    <label>Seleccione el Perfil</label>
                    <md-select ng-change="showOption(user.perfil.id)" ng-model="user.perfil.id" ng-disabled="!user.usuario" required>
                        <md-option ng-repeat="perfil in profiles | orderBy:'nombre'" ng-value="perfil.id">[[ perfil.nombre ]]</md-option>
                    </md-select>                  
                </md-input-container>
            </div>

            <div layout="row" layout-align="start"> 
                <md-input-container class="md-block" flex="50">
                    <label>Nombres</label>
                    <input ng-disabled="user.id || !user.id"  name="nombre" ng-model="user.nombre" required />
                </md-input-container>
                <md-input-container class="md-block" flex="50">
                    <label>Apellidos</label>
                    <input ng-disabled="user.id || !user.id"  name="apellidosCompletos" ng-model="apellidosCompletos" required />
                </md-input-container>
            </div>    

            
            <!--FECHAS-->
            <div ng-if="user.perfil.id == constantUser.perfil.registrador">
            <div layout="row" layout-align="space-between center" ng-show="index == 0">
                <md-input-container flex="50" >
                    <label>Fecha Inicial</label>
                    <md-datepicker onkeydown="return false" ng-model="user.fechaInicial" md-min-date="!user.id?date:null" ng-disabled="!user.usuario" required md-open-on-focus></md-datepicker>
                </md-input-container>
                <md-input-container flex="50" >
                    <label>Fecha Final</label>
                    <md-datepicker onkeydown="return false" ng-model="user.fechaFinal" md-min-date="user.fechaInicial" ng-disabled="!user.fechaInicial" required md-open-on-focus></md-datepicker>
                </md-input-container>
            </div>
            
           
            <!--UBIGEO-->
            <div layout="row" layout-align="space-between center" ng-show="index == 1">
                <md-input-container class="md-block" flex="100">
                    <span>Departamento</span>               
                </md-input-container>                
                <md-input-container class="md-block" flex="100">
                    <label>Seleccione el Departamento</label>
                    <md-select ng-change="getProvincias(departamento)" ng-model="departamento" required>
                        <md-option ng-repeat="dep in departamentos | orderBy:'DESCRIPCION'" ng-value="dep.CODIGO">[[ dep.DESCRIPCION ]]</md-option>
                    </md-select>                  
                </md-input-container>
            </div>
            <div layout="row" layout-align="space-between center" ng-show="index == 1">
                <md-input-container class="md-block" flex="100">
                    <span>Provincia</span>         
                </md-input-container> 
                <md-input-container  ng-repeat="control in controlsProv" class="md-block" flex="100">
                    <label>Seleccione la Provincia</label>
                    <md-select ng-model="provincia" ng-disabled="departamento=='' || departamento == null" ng-change="getDistritos(provincia)" required>
                        <md-option ng-repeat="prov in provincias | orderBy:'DESCRIPCION'" ng-value="prov.CODIGO">[[ prov.DESCRIPCION ]]</md-option>
                    </md-select>                  
                </md-input-container>
            </div>
            <div layout="row" layout-align="space-between center" ng-show="index == 1">
                <md-input-container class="md-block" flex="100">
                    <span>Distrito</span>     
                </md-input-container>                
                <md-input-container ng-repeat="control in controlsDist" class="md-block" flex="100">
                    <label>Seleccione el Distrito</label>
                    <md-select ng-disabled="provincia =='' || provincia == null" ng-change="getAmbitoPadre(ubigeo)" ng-model="user.ubigeo" required>
                        <md-option ng-repeat="dist in distritos | orderBy:'DESCRIPCION'" ng-value="dist.CODIGO">[[ dist.DESCRIPCION ]]</md-option>
                    </md-select>                  
                </md-input-container>
            </div>                       
            </div>
                                    
            <!--ESTADO-->
            <div layout="row" ng-hide=" user.perfil.id == constantUser.perfil.registrador && index == 1" layout-align="space-between center" ng-if="user.perfil.id == constantUser.perfil.admin && user.id || user.perfil.id == constantUser.perfil.registrador && user.id">
                <md-input-container flex="100" class="md-block" ng-cloak>   
                    <p>Seleccione el Estado:</p>
                    <md-radio-group layout="row"  ng-model="user.estado">
                        <md-radio-button flex ="25" ng-value="0" class="md-primary" > Habilitado </md-radio-button>
                        <md-radio-button flex ="25" ng-value="2" class="md-primary" > Deshabilitado </md-radio-button>
                    </md-radio-group>
                </md-input-container>
                <md-button ng-click="user.estado = 1" ng-if="user.estado != 1" class="md-fab btn-new">
                    <i style="margin-left: 2.5px;" class="material-icons">lock_close</i>
                </md-button>
                <md-button ng-if="user.estado == 1" style="background: #fff; color: #000;" class="md-fab btn-new">
                    <i class="material-icons">lock_open</i>
                </md-button>                  
            </div> 
            
            
            
        <!--BUTTON ACTION--> 
        </md-dialog-content>            
        <md-dialog-actions layout="row" layout-align="left">        
            <md-button class="btn-cancel" ng-click="close()">CANCELAR</md-button>            
            <md-button ng-click="nextWindow()" ng-if="index !== 1 && user.perfil.id == constantUser.perfil.registrador" aria-label="Siguiente Ventana">SIGUIENTE</md-button>
            <md-button ng-click="backWindow()" ng-if="index > 0 && user.perfil.id == constantUser.perfil.registrador" aria-label="Ventana Anterior">ANTERIOR</md-button>            
            <md-button ng-if="index == 1 || user.perfil.id != constantUser.perfil.registrador" type="submit" class="btn-confirm">[[!user.id ? 'CREAR' : 'GUARDAR']]</md-button><span ng-hide="!myForm.$invalid">Hola</span>
            <span  ng-show="userForm.$submitted && !userForm.$valid" style="color:#e7694a; position: absolute; right: 0; bottom: 0;padding: 15px;">Faltan Campos Requeridos.</span>
        </md-dialog-actions>        

        
    </form>
</md-dialog>
