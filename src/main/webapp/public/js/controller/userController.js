'use strict';
app.controller('userCtrl',['$scope','$http', '$mdDialog', '$timeout', 'Factory', function($scope, $http, $mdDialog, $timeout, Factory){
    

    $scope.controls = [{}];
    $scope.index = 0;
    
    Factory.inicializar($scope);
    $scope.departamentos = [];
    $scope.provincias = [];
    $scope.distritos = [];
    
    $scope.departamento='';
    $scope.provincia='';
    $scope.distrito='';
    
    $scope.showLoading = false;
    $scope.showFecha = false;
    $scope.showEstado = false;
    $scope.showUbigeo = false;
    $scope.showTable = true;
    $scope.showStep = false;
    $scope.color = true;
    $scope.filterDni = "99999999";
    $scope.messageNoFound = '';
    $scope.date = new Date();
    $scope.message = '';
    $scope.apellidosCompletos ='';
    $scope.profiles = "";
    $scope.profile = "";
    $scope.statusSave = false;
    $scope.controlsProv = [];
    $scope.controlsProv = [{}];
    
    $scope.controlsDist = [];
    $scope.controlsDist = [{}];
    
    $scope.constans ={
        PERFIL_ADMIN:PERFIL_ADMIN,
        PERFIL_ONPE:PERFIL_ONPE,
        PERFIL_AMBITO:PERFIL_AMBITO
    };
    $scope.users = [];
    $scope.user={
        id:null,
        usuario:'',
        apellidoPaterno:'',
        apellidoMaterno:'',
        nombre:'',
        clave:'',
        estado:1,
        ubigeo:'',
        fechaInicial:null,
        fechaFinal:null,
        perfil:{id:null, nombre:'', estado:null}        
    };    
  
    $scope.fetchAllUsers = function(){        
       
        $http({
            url: C_SERVER + '/seguridad/user/',
            method: 'GET'
        }).success(function (response) {
            $scope.users = response;
        });
    }
    
    $scope.fetchAllProfiles = function(){        
        $http({
            url: C_SERVER + '/seguridad/perfil/',
            method: 'GET'
        }).success(function (response) {
            $scope.profiles = response;
        });
    }
    
    $scope.add = function(){
        $scope.apellidosCompletos ='';
        $scope.reset();
        $scope.modal();
    }
    
    $scope.edit = function (id) {
        $scope.reset();
        for(var i = 0; i < $scope.users.length; i++){
            if($scope.users[i].id === id) {
                $scope.setUser($scope.users[i]);
                break;
            }
        }
        
        if($scope.user.perfil.id == $scope.constantUser.perfil.registrador){
            $scope.departamento = $scope.user.ubigeo.substring(0, 2) + "0000";
            $scope.getProvincias($scope.departamento);            
            $scope.provincia = $scope.user.ubigeo.substring(0, 4) + "00";
            $scope.getDistritos($scope.provincia);
            $scope.distrito = $scope.user.ubigeo;            
        }
        $scope.modal();
    };
    
    $scope.setUser = function (user){
        if(user.id != null){
            $scope.user.id = user.id;            
            $scope.user.clave = user.clave;
            $scope.user.estado = user.estado;
            $scope.user.perfil.id = user.perfil.id;
            $scope.user.perfil.nombre = user.perfil.nombre;
            $scope.user.perfil.estado = user.perfil.estado;
            $scope.user.ubigeo = user.ubigeo;
            $scope.user.fechaInicial = user.fechaInicial == null? null: new Date(user.fechaInicial);
            $scope.user.fechaFinal = user.fechaFinal == null? null: new Date (user.fechaFinal);                          
        }
            $scope.user.usuario = user.usuario;
            $scope.user.apellidoPaterno = user.apellidoPaterno;
            $scope.user.apellidoMaterno = user.apellidoMaterno;
            $scope.user.nombre = user.nombre;
            $scope.apellidosCompletos = user.apellidosCompletos;
    }
    
    $scope.modal = function(ev){
        $scope.controls = [{}];
        $mdDialog.show({
            templateUrl: C_SERVER + '/seguridad/usuario/editar/',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: false,
            bindToController: true,
            escapeToClose: true,
            scope: $scope,
            preserveScope: true,
            autoWrap: false
        }).then(function (answer) {

        }, function () {

        });        
    };
            
    $scope.close = function (){     
        $mdDialog.cancel();
        $scope.distritos = [];
        $scope.provincias =[];
        $scope.reset();
    };    
    
    $scope.submit = function (){
        $scope.saveUser($scope.user);          
    };
        
    
    $scope.saveUser = function(user){
        $http({
            url: C_SERVER + '/seguridad/user/',
            method: 'POST',
            data: $scope.user,     
            contentType: 'application/json',
            header: {'Content-Type': 'application/json; charset=UTF-8'}
        }).success(function (response) {
            if(response.success != true){
                $scope.message= response.message;
            }else{
                $mdDialog.cancel();
                //$scope.reset();
                $scope.fetchAllUsers();                 
            }           
        });
    }
    
    $scope.showOption = function(perfil){
        $scope.departamento='';
        $scope.provincia='';
        $scope.user.ubigeo='';
        $scope.user.fechaInicial=null;
        $scope.user.fechaFinal=null;
    };
    
    $scope.step = function(step){
        if(step == 1){
            $scope.color = true;
            $scope.showFecha = true;
            $scope.showUbigeo = false;
        }else{
            $scope.color = false;
            $scope.showFecha = false;
            $scope.showUbigeo = true;
        }
    };
    
    $scope.resetDni = function(){
        $scope.distritos = [];
        $scope.provincias =[];
        if($scope.statusSave){
            $scope.reset();
            $scope.statusSave = false;
        }
    };
    
    $scope.reset = function(){
        $scope.index = 0;
        $scope.departamento='';
        $scope.provincia='';
        $scope.ubigeo='';        
        
        $scope.showEstado = false;
        $scope.showFecha = false;
        $scope.showStep = false;
        $scope.showUbigeo = false;
        $scope.color = true;           
        
        $scope.message = '';
        $scope.apellidosCompletos ='';
        $scope.user={
            id:null,
            usuario:'',
            apellidoPaterno:'',
            apellidoMaterno:'',
            nombre:'',
            clave:'',
            estado:1,
            ubigeo:'',
            fechaInicial:null,
            fechaFinal:null,
            perfil:{id:null, nombre:'', estado:null}
        };
    }    
    
    $scope.createUser = function(){
        $http({
            url: C_SERVER + '/seguridad/user/',
            method: 'POST',
            data: $scope.user,     
            contentType: 'application/json',
            header: {'Content-Type': 'application/json; charset=UTF-8'}
        }).success(function (response) {
            $mdDialog.cancel();
            $scope.reset();
            $scope.fetchAllUsers();
            
        });        
    }

    $scope.search = function(){
        $scope.showLoading = true;
        $scope.showTable = false;
        $timeout(function () {
            $scope.showLoading = false;
            $scope.showTable = true;
            $scope.searchQuery = angular.copy($scope.query);
        }, 400);
                
    }
        
    $scope.getUserPadron = function(dni){
        if(dni != null && dni != ''){
            $http({
                url: C_SERVER + '/seguridad/user/'+dni,
                method: 'GET',    
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response, status) {
                if(status == 204){
                    $scope.message= "DNI no existe.";
                    $scope.statusSave = true;
                }else{
                    $scope.setUser(response);
                    $scope.statusSave = true;
                }
            });             
        }else{
        }   
    }    
    
    
    $scope.getDepartamentos = function () {
        $http({
            url: C_SERVER + '/mantenimiento/ambito/departamentos',
            method: 'GET'
        }).success(function (response) {
            var data = JSON.parse(response.data);
            $scope.departamentos = JSON.parse(data.departamentos);
        });
    };
    
    $scope.getProvincias = function (dep) {
        if($scope.user.id == null){
            $scope.provincia = '';
            $scope.user.ubigeo = '';
        }
        if(dep != '' && dep != null){
            $http({
                url: C_SERVER + '/mantenimiento/ambito/provincias/' + dep,
                method: 'GET'
            }).success(function (response) {                
                var data = JSON.parse(response.data);
                $scope.controlsProv = [];
                $scope.controlsDist = [];
                $scope.provincias = JSON.parse(data.provincias);
                $scope.controlsProv = [{}];
                $scope.controlsDist = [{}];
            });
        }
    };
    
    $scope.getDistritos = function (prov) {
        if($scope.user.id == null){
            $scope.user.ubigeo = '';                    
        }
        if(prov != '' && prov != null){
            $scope.provincia = prov;
            $http({
                url: C_SERVER + '/mantenimiento/ambito/distritos/' + prov,
                method: 'GET'
            }).success(function (response) {
                var data = JSON.parse(response.data);
                $scope.controlsDist = [];
                $scope.distritos = JSON.parse(data.distritos);
                $scope.controlsDist = [{}];
            });            
        }
    };        
        
    $scope.nextWindow = function () {
        $scope.index++;
    };
    
    $scope.backWindow = function () {
        if ($scope.index > 0) {
            $scope.index--;
        }
    };        
    
    $scope.fetchAllUsers();
    $scope.fetchAllProfiles();
    $scope.getDepartamentos();

}]);
