app.controller('reporteCtrl', ['$scope', '$http', '$mdDialog', '$timeout', function ($scope, $http, $mdDialog, $timeout) {

        $scope.reporte = {
            id: null,
            codigo: '',
            etiqueta: '',
            descripcion: '',
            query: '',
            filtro: '',
            estado: null
        };

        $scope.filtro = {
            idReporte: 0,
            filtro: '',
            ubigeo: '',
            fechaIni: '',
            fechaFin: '',
            ambito: 0,
            habilitado: 0,
            pendiente: 0,
            rechazado: 0
        };
        $scope.fecha = {
            inicio: new Date(),
            fin: new Date()
        };
        $scope.getReportes = function () {
            $http({
                url: C_SERVER + '/reporte/listar/',
                method: 'GET'
            }).success(function (response) {
                $scope.reportes = response;
            });
        };

        $scope.modal = function (ev) {
            $scope.ubigeo = ' ';
            $scope.searchCCPP = '';
            $mdDialog.show({
                templateUrl: C_SERVER + '/reporte/modal/' + $scope.reporte.filtro,
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

        $scope.close = function () {
            $mdDialog.cancel();
        };

        $scope.generarReportes = function (id) {
            for (var i = 0; i < $scope.reportes.length; i++) {
                if ($scope.reportes[i].id === id) {
                    $scope.reporte = angular.copy($scope.reportes[i]);
                    $scope.filtro.idReporte = $scope.reporte.id;
                    $scope.filtro.filtro = $scope.reporte.filtro;
                    $scope.filtro.ubigeo = '';
                    $scope.ubigeoDepartamento.codigo = 0;
                    $scope.ubigeoProvincia.codigo = 0;
                    $scope.ubigeoDistrito.codigo = 0;
                    $scope.provincias = [];
                    $scope.distritos = [];
                    $scope.fecha.inicio = new Date();
                    $scope.fecha.fin = new Date();
                    $scope.filtro.habilitado = 1;
                    $scope.filtro.pendiente = 1;
                    $scope.filtro.rechazado = 1;
                    break;
                }
            }
            $scope.modal();
        };

        $scope.submit = function (option) {
            if (option === 2) {
                if ($scope.fecha.inicio !== '' && $scope.fecha.fin !== '') {
                    var strStartDateTime = $scope.fecha.inicio.getDate() + "-" + ($scope.fecha.inicio.getMonth() + 1) + "-" + $scope.fecha.inicio.getFullYear();
                    var strEndDateTime = $scope.fecha.fin.getDate() + "-" + ($scope.fecha.fin.getMonth() + 1) + "-" + $scope.fecha.fin.getFullYear();
                    $scope.filtro.fechaIni = strStartDateTime;
                    $scope.filtro.fechaFin = strEndDateTime;
                }
            }

            //$scope.filtro.ubigeo = $scope.ubigeoDistrito.codigo;
            var filtros = JSON.stringify($scope.filtro);
            window.open(C_SERVER + "/reporte/exportar/" + filtros);
            $mdDialog.cancel();
        };

        $scope.getReportes();

        $scope.ubigeoDepartamento = {};
        $scope.ubigeoDepartamento.codigo = 0;
        $scope.ubigeoProvincia = {};
        $scope.ubigeoProvincia.codigo = 0;
        $scope.ubigeoDistrito = {};
        $scope.ubigeoDistrito.codigo = 0;

        $scope.controlsProvincia = [];
        $scope.controlsProvincia = [{}];
        $scope.controlsDistrito = [];
        $scope.controlsDistrito = [{}];
        
        $scope.ubigeo = ' ';

        $scope.listarProvincias = function () {
            $scope.ubigeoProvincia.codigo = 0;
            $scope.ubigeoDistrito.codigo = 0;
            $scope.ubigeoDistrito.id = "";
            $scope.provincias = [];
            $scope.distritos = [];
            if ($scope.ubigeoDepartamento.codigo !== 0) {
                $scope.filtro.ubigeo = $scope.ubigeoDepartamento.codigo.substring(0, 2);
                $http({
                    url: C_SERVER + '/registro/fetchUbigeo/' + $scope.ubigeoDepartamento.codigo,
                    method: 'GET',
                    contentType: 'application/json',
                    header: {'Content-Type': 'application/json; charset=UTF-8'}
                }).success(function (response) {
                    $scope.controlsProvincia = [];
                    $scope.controlsDistrito = [];
                    $scope.provincias = response;
                    $scope.controlsProvincia = [{}];
                    $scope.controlsDistrito = [{}];
                });
            }
        };

        $scope.listarDistritos = function () {

            $scope.ubigeoDistrito.codigo = 0;
            $scope.distritos = [];
            if ($scope.ubigeoProvincia.codigo !== 0) {
                $scope.filtro.ubigeo = $scope.ubigeoProvincia.codigo.substring(0, 4);
                $http({
                    url: C_SERVER + '/registro/fetchUbigeo/' + $scope.ubigeoProvincia.codigo,
                    method: 'GET',
                    contentType: 'application/json',
                    header: {'Content-Type': 'application/json; charset=UTF-8'}
                }).success(function (response, status) {
                    $scope.controlsDistrito = [];
                    $scope.distritos = response;
                    $scope.controlsDistrito = [{}];
                });
            }
        };

        $scope.getDistrito = function () {
            if ($scope.ubigeoDistrito.codigo !== 0) {
                $scope.filtro.ubigeo = $scope.ubigeoDistrito.codigo;
            }
        };

        $scope.querySearch = function (query) {
            return $http.get(C_SERVER + '/reporte/searchCCPP/' + query)
                    .then(function (response) {
                        return response.data;
                    });
        };

        $scope.selectedItemSearch = function (query) {
            if (query !== undefined) {
                $scope.ubigeo = query.DPTO + " / " + query.PROV + " / " + query.DIST;
                $scope.filtro.ambito = query.ID;
            }
        };

//      ultimo

        $scope.searchElector = function () {
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

    }]);
