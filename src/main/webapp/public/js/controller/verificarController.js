app.controller('verificarCtrl', ['$scope', '$http', '$mdDialog', '$timeout', function ($scope, $http, $mdDialog, $timeout) {

        $scope.ubigeoDepartamento = {};
        $scope.ubigeoProvincia = {};
        $scope.ubigeoDistrito = {};
        $scope.ubigeoCCPP = {};
        $scope.ubigeoExpediente = {};

        $scope.controlsProvincia = [];
        $scope.controlsProvincia = [{}];
        $scope.controlsDistrito = [];
        $scope.controlsDistrito = [{}];
        $scope.controlsCCPP = [];
        $scope.controlsCCPP = [{}];
        $scope.controlsExpediente = [];
        $scope.controlsExpediente = [{}];

        $scope.controlsProv = [];
        $scope.controlsProv = [{}];
        $scope.controlsDist = [];
        $scope.controlsDist = [{}];
        $scope.ubigeoExpediente.codigo = "";
        
        $scope.validar = true;                
        
        $scope.expediente = {
            id: 0,
            expediente: '',
            fechaExpediente: null,
            ambito: {id: null, nombreAmbito:'', departamento:'', provincia:'', distrito:''}
        }; 
        
        $scope.elector = {
            id: 0,
            dni: '',
            apellidoPaterno: '',
            apellidoMaterno: '',
            nombre: '',            
            ambito: {id: null, nombreAmbito:'', tipoAmbito: null, ambitoPadre:null},                                    
            observacion:0,
            observacionUser:0
        };
        
        $scope.opcion = {
            id:null,
            codigo:null,
            descripcion:''
        };

        $scope.listarProvincias = function () {
            $scope.ubigeoProvincia.codigo = "";
            $scope.ubigeoDistrito.codigo = "";
            $scope.ubigeoCCPP.codigo = "";
            $scope.ubigeoExpediente.codigo = "";
            $scope.ubigeoDistrito.id = "";
            $http({
                url: C_SERVER + '/registro/listarProvincias/' + $scope.ubigeoDepartamento.codigo,
                method: 'GET',
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response) {
                $scope.controlsProvincia = [];
                $scope.controlsDistrito = [];
                $scope.controlsCCPP = [];
                $scope.controlsExpediente = [];
                $scope.provincias = response;
                $scope.controlsProvincia = [{}];
                $scope.controlsDistrito = [{}];
                $scope.controlsCCPP = [{}];
                $scope.controlsExpediente = [{}];
            });
        };

        $scope.listarDistritos = function () {
            $scope.ubigeoDistrito.codigo = "";
            $scope.ubigeoCCPP.codigo = "";
            $scope.ubigeoExpediente.codigo = "";
            $scope.ubigeoCCPP.id = "";
            $http({
                url: C_SERVER + '/registro/listarDistritos/' + $scope.ubigeoProvincia.codigo,
                method: 'GET',
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response, status) {
                $scope.controlsDistrito = [];
                $scope.controlsCCPP = [];
                $scope.controlsExpediente = [];
                $scope.distritos = response;
                $scope.controlsDistrito = [{}];
                $scope.controlsCCPP = [{}];
                $scope.controlsExpediente = [{}];
            });
        };

        $scope.listarCCPP = function () {
            $scope.ubigeoCCPP.codigo = "";
            $scope.ubigeoExpediente.codigo = "";
            $scope.ubigeoExpediente.id = "";
            $http({
                url: C_SERVER + '/registro/listarCCPP/' + $scope.ubigeoDistrito.codigo,
                method: 'GET',
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response, status) {
                $scope.controlsCCPP = [];
                $scope.controlsExpediente = [];
                $scope.ccpps = response;
                $scope.controlsCCPP = [{}];
                $scope.controlsExpediente = [{}];
            });
        };

        $scope.listarExpedientes = function () {
            $scope.ubigeoExpediente.codigo = "";
            $http({
                url: C_SERVER + '/registro/listarExpedientes/' + $scope.ubigeoCCPP.codigo,
                method: 'GET',
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response, status) {
                $scope.controlsExpediente = [];
                $scope.expedientes = response;
                $scope.controlsExpediente = [{}];
            });
        };

        $scope.querySearchUbigeo = function (query) {
            return $http.get(C_SERVER + "/registro/buscarUbigeo/" + query)
                    .then(function (response) {
                        return response.data;
                    });
        };

        $scope.querySearchCCPP = function (query) {
            return $http.get(C_SERVER + "/registro/buscarCCPP/" + query)
                    .then(function (response) {
                        return response.data;
                    });
        };

        $scope.selectedUbigeo = function (query) {
            if (query !== undefined) {
                $scope.ubigeoDepartamento.codigo = query.UBIGEO.substring(0, 2);
                $scope.listarProvincias();
                $scope.ubigeoProvincia.codigo = query.UBIGEO.substring(0, 4);
                $scope.listarDistritos();
                $scope.ubigeoDistrito.codigo = query.UBIGEO.substring(0, 6);
                $scope.listarCCPP();
            }
        };

        $scope.selectedCCPP = function (query) {
            if (query !== undefined) {
                $scope.ubigeoDepartamento.codigo = query.ubigeo.substring(0, 2);
                $scope.listarProvincias();
                $scope.ubigeoProvincia.codigo = query.ubigeo.substring(0, 4);
                $scope.listarDistritos();
                $scope.ubigeoDistrito.codigo = query.ubigeo.substring(0, 6);
                $scope.listarCCPP();
                $scope.ubigeoCCPP.codigo = query.id;
                $scope.listarExpedientes();
            }
        }; 
        
        $scope.search = function() {            
            $http({
                url: C_SERVER + '/verificar/expediente/' + $scope.ubigeoExpediente.codigo,
                method: 'GET'
            }).success(function (response) {                                
                if (response.result === "NO_CONTENT") {
                    var alert = $mdDialog.alert({
                        title: 'Atencion.', textContent: 'No se encontraron registros pendientes.', ok: 'ACEPTAR'
                    });
                    $mdDialog.show(alert).finally(function () {
                        $scope.validar = true;
                        alert = undefined;
                    });
                } else {
                    $scope.validar = false;                    
                    $scope.expediente = response.expediente;                                        
                    $scope.electores = response.electores;                    
                    $scope.opciones = response.opciones;
                }                                
            });           
            
        };
        
        $scope.return = function() { 
            $scope.validar = true;
        };        
        
        $scope.save = function() { 
            var confirm = $mdDialog.confirm()
                    .title('Verificar Expediente.')
                    .textContent('Esta seguro que desea guardar la informacion?')
                    .targetEvent()
                    .ok('Si')
                    .cancel('No');
            $mdDialog.show(confirm).then(function () {
                for (var i = 0; i < $scope.electores.length; i++) {
                    if ($scope.electores[i].observacionUser !== undefined){
                        $scope.electores[i].observacion = $scope.electores[i].observacion + ',' + $scope.electores[i].observacionUser;
                    }                    
                }                
                var list = JSON.stringify($scope.electores);
                
                $http({
                    url: C_SERVER + '/verificar/guardar/',
                    method: 'POST',
                    data: list,
                    contentType: 'application/json',
                    header: {'Content-Type': 'application/json; charset=UTF-8'}
                }).success(function (response) {
                    if (response.result === "OK") {
                        $scope.validar = true;
                    }                                        
                });
            }, function () {
            });
        };
        
        
        $scope.padron = {
            numele: '',
            apePat: '',
            apeMat: '',
            nombres: '',
            ubigeo:''
        };
        
        $scope.searchElector = function() {
            $scope.modalSearchElector();
        };
        
        $scope.modalSearchElector = function (ev) {
            $mdDialog.show({
                templateUrl: C_SERVER + '/buscarElector/modal',
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
        
        $scope.submitSearch = function() {
            $scope.padrones = {};
//            if ($scope.padron.numele !== '' || $scope.padron.nombres !== '') {                                
                var p = JSON.stringify($scope.padron);
                $http({
                    url: C_SERVER + '/buscarElector/buscar',
                    method: 'POST',
                    data: p,
                    contentType: 'application/json',
                    header: {'Content-Type': 'application/json; charset=UTF-8'}
                }).success(function (response) {                    
                    $scope.padrones = response;
                });                
//            }            
        };
        
        $scope.close = function () {
            $mdDialog.cancel();
        };
        
    }]);
