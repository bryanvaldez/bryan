app.controller('expedienteCtrl', ['$scope', '$http', '$mdDialog', '$timeout', function ($scope, $http, $mdDialog, $timeout) {

        $scope.name = null;
        $scope.year = null;
        $scope.showUp = false;
        $scope.countSelectedList = 0;
        $scope.selected = [];

        $scope.showLoading = false;
        $scope.showTable = true;

        $scope.expedientes = [];
        $scope.info = '';
        $scope.ambito = {
            id: null
        };

        $scope.expediente = {
            id: null,
            expediente: null,
            fechaExpediente: null,
            ambito: {id: $scope.ambito.id}
        };

        $scope.reset = function () {
            $scope.name = '';
            $scope.year = '';
            $scope.info = '';
            $scope.expediente = {
                id: null,
                expediente: null,
                fechaExpediente: null,
                ambito: {id: $scope.ambito.id}
            };
        };

        $scope.modal = function (ev) {
            $mdDialog.show({
                templateUrl: C_SERVER + '/mantenimiento/expediente/modal',
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

        $scope.getExpedientes = function () {
            $http({
                url: C_SERVER + '/mantenimiento/expediente/lista/',
                method: 'GET'
            }).success(function (response) {
                $scope.expedientes = response;
            });
        };

        $scope.close = function () {
            $mdDialog.cancel();
        };

        $scope.add = function () {
            $scope.reset();
            $scope.modal();
        };

        $scope.submit = function () {

            $http({
                url: C_SERVER + '/mantenimiento/expediente/existExpediente/' + $scope.name,
                method: 'GET'
            }).success(function (response) {
                console.log(response);
                if (response === 0) {
                    $scope.expediente.expediente = $scope.name + '-' + $scope.year;
                    if ($scope.expediente.id == null) {
                        $scope.save();
                    } else {
                        $scope.expediente.ambito = {};
                        $scope.save();
                    }
                } else {
                    $scope.info = 'El número del expediente ya se encuentra registrado.';
                }
            });


        };

        $scope.save = function () {
            $http({
                url: C_SERVER + '/mantenimiento/expediente/',
                method: 'POST',
                data: $scope.expediente,
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response) {
                $mdDialog.cancel();
                $scope.getExpedientes();
                $scope.reset();
            });
        };

        $scope.delete = function (id) {
            var confirm = $mdDialog.confirm()
                    .title('Eliminar Expediente.')
                    .textContent('Esta seguro que desea eliminar el expediente?')
                    .targetEvent()
                    .ok('Si')
                    .cancel('No');
            $mdDialog.show(confirm).then(function () {
                $http({
                    url: C_SERVER + '/mantenimiento/expediente/' + id,
                    method: 'DELETE',
                    header: {'Content-Type': 'application/json; charset=UTF-8'}
                }).success(function (status) {
                    if (status === "NO_CONTENT") {
                        var alert = $mdDialog.alert({
                            title: 'Atencion.', textContent: 'No es posible eliminar el expediente por que tiene electores registrados.', ok: 'ACEPTAR'
                        });
                        $mdDialog.show(alert).finally(function () {
                            alert = undefined;
                        });
                    } else if (status === "NOT_FOUND") {
                        var alert = $mdDialog.alert({
                            title: 'Atencion.', textContent: 'Ocurrio un error al eleiminar el expediente.', ok: 'ACEPTAR'
                        });
                        $mdDialog.show(alert).finally(function () {
                            alert = undefined;
                        });
                    }
                    $scope.getExpedientes();
                });
            }, function () {
            });
        };

        $scope.edit = function (id) {
            for (var i = 0; i < $scope.expedientes.length; i++) {
                if ($scope.expedientes[i].id === id) {
                    $scope.expediente = angular.copy($scope.expedientes[i]);
                    $scope.expediente.fechaExpediente = new Date($scope.expediente.fechaExpediente);
                    $scope.name = $scope.expediente.expediente.substring(0, 6);
                    $scope.year = $scope.expediente.expediente.substring(7, 11);
                    console.log($scope.expediente.fechaExpediente);
                    console.log($scope.name);
                    console.log($scope.year);
                    break;
                }
            }
            $scope.modal();
        };

        $scope.toggle = function (item) {
            var idx = $scope.selected.indexOf(item);
            if (idx > -1) {
                $scope.setUnselected(item);
                $scope.selected.splice(idx, 1);
            } else {
                $scope.setSelected(item);
                $scope.selected.push(item);
            }
        };

        $scope.exists = function (item) {
            return $scope.selected.indexOf(item) > -1;
        };

        $scope.setUnselected = function (item) {
            $http({
                url: C_SERVER + '/mantenimiento/expediente/unselected',
                method: 'POST',
                data: item,
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (data) {
                $scope.countSelectedList = data;
            });
        };

        $scope.setSelected = function (item) {
            $http({
                url: C_SERVER + '/mantenimiento/expediente/selected',
                method: 'POST',
                data: item,
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (data) {
                $scope.countSelectedList = data;
            });
        };

        $scope.showSelected = function () {
            $http({
                url: C_SERVER + '/mantenimiento/expediente/listaSelected/',
                method: 'GET'
            }).success(function (response) {
                for (var i = 0; i < response.length; i++) {
                    $scope.selected.push(response[i].id);
                }
                $scope.countSelectedList = $scope.selected.length;
            });
        };

        $scope.exportar = function () {
            if ($scope.countSelectedList > 0) {
                window.open(C_SERVER + "/mantenimiento/expediente/exportar/" + $scope.selected);

                $scope.cerrar();
                $scope.countSelectedList = 0;
                $scope.selected = [];
            } else {
                var alert = $mdDialog.alert({
                    title: 'Atencion.',
                    textContent: 'No ha seleccionado ningun expediente.',
                    ok: 'ACEPTAR'
                });

                $mdDialog.show(alert).finally(function () {
                    alert = undefined;
                });
            }
        };

        $scope.ver = function () {
            if ($scope.countSelectedList > 0) {
                $scope.showUp = true;
                $http({
                    url: C_SERVER + '/mantenimiento/expediente/listaSelected/',
                    method: 'GET'
                }).success(function (response) {
                    $scope.expedientes = response;
                });
            } else {
                var alert = $mdDialog.alert({
                    title: 'Atencion.',
                    textContent: 'No ha seleccionado ningun expediente.',
                    ok: 'ACEPTAR'
                });

                $mdDialog.show(alert).finally(function () {
                    alert = undefined;
                });
            }
        };

        $scope.cerrar = function () {
            $scope.showUp = false;
            $scope.getExpedientes();
        };

        $scope.querySearch = function (query) {
            return $http.get(C_SERVER + '/mantenimiento/expediente/search/' + query)
                    .then(function (response) {
                        return response.data;
                    });

        };

        $scope.selectedItemSearch = function (query) {
            if (query !== undefined) {
                $http({
                    url: C_SERVER + '/mantenimiento/expediente/searchLista/' + query,
                    method: 'GET'
                }).success(function (response) {
                    $scope.expedientes = response;
                });
            }
        };

        $scope.selectedEmptySearch = function (query) {
            if ($scope.searchText === "") {
                $scope.getExpedientes();
            }

        };

        $scope.search = function () {
            $scope.showLoading = true;
            $scope.showTable = false;
            $timeout(function () {
                $scope.showLoading = false;
                $scope.showTable = true;
                $scope.searchQuery = angular.copy($scope.query);
            }, 400);

        };

        $scope.showSelected();
        $scope.getExpedientes();

    }]);