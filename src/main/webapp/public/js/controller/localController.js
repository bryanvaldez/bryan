app.controller('localCtrl', function ($scope, $http, $mdDialog, Factory, $location, $timeout) {

    $scope.idLocal = 0;
    $scope.locales = [];
    
    $scope.showLoading = false;
    $scope.showTable = true;
    $scope.ambito = {
        id:null
    };
    $scope.local = {
        id:null,
        codigo:null,
        nombre:'',
        ubigeo:'',
        departamento:'',
        provincia:'',
        distrito:'',
        direccion:'',
        referencia:'',
        estado:0,        
        ambito:{id:$scope.ambito.id}
    };    
    
    $scope.reset = function() {
        $scope.local = {
            id:null,
            codigo:null,
            nombre:'',
            ubigeo:'',
            departamento:'',
            provincia:'',
            distrito:'',
            direccion:'',
            referencia:'',
            estado:0,
            ambito:{id:$scope.ambito.id}
        };        
    };
    
    $scope.modal = function(ev) {
        $mdDialog.show({
            templateUrl: C_SERVER + '/mantenimiento/local/modal',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: false,
            bindToController: true,
            escapeToClose: true,
            scope: $scope,
            preserveScope: true,
        }).then(function (answer) {

        }, function () {

        });        
    };
    
    $scope.getLocales = function(){
        $http({
            url: C_SERVER + '/mantenimiento/local/lista/',
            method: 'GET'
        }).success(function (response) {
            $scope.locales = response;
        });       
    };
    
    $scope.close = function(){
        $mdDialog.cancel();
    };
     
    $scope.add = function(){
        $scope.reset();
        $scope.modal();
    };
    
    $scope.submit = function(){
        if($scope.local.id == null){
            $scope.save();
        }else{
            $scope.local.ambito = {};            
            $scope.save();
        }
    };
    
    $scope.save = function(){
        var exist = false;
        for (var i = 0; i < $scope.locales.length; i++) {
            if ($scope.locales[i].codigo === $scope.local.codigo) {
                exist = true;
                break;                
            }                                
        }

        if (exist && !$scope.local.id) {
            var alert = $mdDialog.alert({
                title: 'Atencion.', textContent: 'Ya existe un Local de Votacion con el mismo Codigo.', ok: 'ACEPTAR'
            });
            $mdDialog.show(alert).finally(function () {
                alert = undefined;
                $scope.modal();
            });
        } else {
            $http({
                url: C_SERVER + '/mantenimiento/local/',
                method: 'POST',
                data: $scope.local,     
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response) {
                $mdDialog.cancel();
                $scope.getLocales();
                $scope.reset();
            }); 
        }                       
    };
    
    $scope.delete = function(id){
        var confirm = $mdDialog.confirm()
                .title('Eliminar Local de Votacion.')
                .textContent('Esta seguro que desea eliminar el local?')
                .targetEvent()
                .ok('Si')
                .cancel('No');
        $mdDialog.show(confirm).then(function () {
            $http({
                url: C_SERVER + '/mantenimiento/local/'+id,
                method: 'DELETE',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response) {
                $scope.getLocales();
            });
        }, function () {
        });                                          
    };
    
    $scope.edit = function(id){
        for(var i = 0; i < $scope.locales.length; i++){
            if($scope.locales[i].id === id) {
                $scope.local = angular.copy($scope.locales[i]);
                break;
            }
        }
        $scope.modal();
    }; 
    
    $scope.querySearch = function (query) {            
        return $http.get(C_SERVER + '/mantenimiento/local/search/' + query)
            .then(function (response) {                    
                return response.data;
        });

    };
        
    $scope.selectedItemSearch = function (query) {
        if (query !== undefined) {                            
            $http({
                url: C_SERVER + '/mantenimiento/local/searchLocales/' + query,
                method: 'GET'
            }).success(function (response) {
                $scope.locales = response;
            });
        }
    };
        
    $scope.selectedEmptySearch = function (query) {            
        if ($scope.searchText === "") {
            $scope.getLocales();
        }

    };
    
    $scope.ambitoChange = {
        id:0,
        nombreAmbito:'',
        idLocal:null
    };
    
    $scope.change = function(idLocal) {
        $scope.idLocal = idLocal;
        $scope.modalChange();
    };
    
    $scope.modalChange = function(ev) {
        $mdDialog.show({
            templateUrl: C_SERVER + '/mantenimiento/local/modalChange',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose: true,
            fullscreen: false,
            bindToController: true,
            escapeToClose: true,
            scope: $scope,
            preserveScope: true,
        }).then(function (answer) {

        }, function () {

        });        
    };
    
    $scope.getAmbitosPadre = function(){
        $http({
            url: C_SERVER + '/mantenimiento/local/listaAmbitos/',
            method: 'GET'
        }).success(function (response) {            
            $scope.ambitos = response;
        });       
    };
    
    $scope.submitChange = function(){        
        if ($scope.ambitoChange.id !== 0) {
            $scope.ambitoChange.idLocal = $scope.idLocal;
               
            var a = JSON.stringify($scope.ambitoChange);
            $http({
                url: C_SERVER + '/mantenimiento/local/changeAmbito',
                method: 'POST',
                data: a,
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response) {
                $mdDialog.cancel();
                $scope.getLocales();                
            });
        }        
    };
    
    $scope.search = function(){
        $scope.showLoading = true;
        $scope.showTable = false;
        $timeout(function () {
            $scope.showLoading = false;
            $scope.showTable = true;
            $scope.searchQuery = angular.copy($scope.query);
        }, 400);
                
    }    
    
    
    $scope.getLocales();
    $scope.getAmbitosPadre();
    
});