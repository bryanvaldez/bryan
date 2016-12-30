app.controller('ambitoCtrl', ['$scope', '$http', '$mdDialog', '$timeout', 'Factory', function ($scope, $http, $mdDialog, $timeout, Factory) {

        Factory.inicializar($scope);
        $scope.showdata = false;
        $scope.controls = [{}];
        $scope.index = 0;
        $scope.indexAuth = true;
        $scope.showLoading = false;
        $scope.showTable = true;
        $scope.ambitos = [];
        $scope.departamentos = [];
        $scope.provincias = [];
        $scope.distritos = [];
        $scope.anexos = [];
        $scope.lstAmbitos = [];
        $scope.anexos = [];
        $scope.lstAmbitosPadre = [];
        $scope.switchAmbito = true;
        $scope.controlsProv = [];
        $scope.controlsProv = [{}];
        $scope.controlsDist = [];
        $scope.controlsDist = [{}];

        $scope.ambito = {
            id: null,
            nombreAmbito: '',
            tipoAmbito: null,
            categoria: null,
            informacion: '',
            ubigeo: null,
            departamento: '',
            provincia: '',
            distrito: '',
            ambitoPadre: null
        };
        $scope.observacion = {
            cargo: '',
            apellidoPaterno: '',
            apellidoMaterno: '',
            nombres: '',
            telefono: '',
            direccion: '',
            email: '',
            cargo_: '',
            apellidoPaterno_: '',
            apellidoMaterno_: '',
            nombres_: '',
            telefono_: '',
            direccion_: '',
            email_: ''
        };
        $scope.anexo = {
            id: null,
            nombreAmbito: '',
            tipoAmbito: null,
            categoria: null,
            informacion: '',
            ubigeo: null,
            departamento: '',
            provincia: '',
            distrito: '',
            ambitoPadre: null
        };

        $scope.setAmbito = function (ambito, tipo) {
            if (ambito.id != null) {
                $scope.ambito.id = ambito.id;
                $scope.ambito.nombreAmbito = ambito.nombreAmbito;
                $scope.ambito.tipoAmbito = ambito.tipoAmbito;
                $scope.ambito.categoria = ambito.categoria;
                $scope.ambito.informacion = ambito.informacion;
                $scope.ambito.ubigeo = ambito.ubigeo;
                $scope.ambito.departamento = ambito.departamento;
                $scope.ambito.provincia = ambito.provincia;
                $scope.ambito.distrito = ambito.distrito;
                $scope.ambito.ambitoPadre = ambito.ambitoPadre;
            }
            if ($scope.constantAmbito.tipo.anexo == tipo) {
                $scope.ambito.nombreAmbito = ambito.nombreAmbito
                $scope.ambito.categoria = ambito.categoria;
                $scope.ambito.tipoAmbito = $scope.constantAmbito.tipo.anexo;
                $scope.ambito.ambitoPadre = ambito.ambitoPadre;
            }
        };

        $scope.addAnexo = function () {
            $scope.anexos.push({});
        };

        $scope.deleteAnexo = function (id) {
            var confirm = $mdDialog.confirm()
                    .title('Eliminar Ambito Adjunto.')
                    .textContent('Esta seguro que desea eliminar el Ambito Adjunto?')
                    .targetEvent()
                    .ok('Si')
                    .cancel('No');
            $mdDialog.show(confirm).then(function () {
                $http({
                    url: C_SERVER + '/mantenimiento/ambito/deleteAnexo/' + id,
                    method: 'GET',
                    header: {'Content-Type': 'application/json; charset=UTF-8'}
                }).success(function (response) {
                    var alert = $mdDialog.alert({
                        title: 'Aviso.',
                        textContent: response.message,
                        ok: 'ACEPTAR'
                    });
                    $mdDialog.show(alert).finally(function () {
                        $scope.getAmbitos();
                        alert = undefined;
                    });
                });
            }, function () {
            });
        };

        $scope.reset = function () {
            //$scope.anexos = [];
            //$scope.showdata = false;
            $scope.index = 0;
            $scope.switchAmbito = true;
            $scope.departamento = '';
            $scope.provincia = '';
            $scope.ambito = {
                id: null,
                nombreAmbito: '',
                tipoAmbito: null,
                categoria: null,
                informacion: '',
                //ubigeo: null,
                ubigeo: '',
                departamento: '',
                provincia: '',
                distrito: '',
                ambitoPadre: null
            };
            $scope.anexo = {
                id: null,
                nombreAmbito: '',
                tipoAmbito: null,
                categoria: null,
                informacion: '',
                ubigeo: null,
                departamento: '',
                provincia: '',
                distrito: '',
                ambitoPadre: null
            };
            $scope.observacion = {
                cargo: '',
                apellidoPaterno: '',
                apellidoMaterno: '',
                nombres: '',
                telefono: '',
                direccion: '',
                email: '',
                cargo_: '',
                apellidoPaterno_: '',
                apellidoMaterno_: '',
                nombres_: '',
                telefono_: '',
                direccion_: '',
                email_: ''
            };

        };

        $scope.modal = function (ev) {
            $scope.controls = [{}];
            $mdDialog.show({
                templateUrl: C_SERVER + '/mantenimiento/popup',
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
                $scope.showdata = false;
            });
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

        $scope.modalEdit = function (ev) {
            $mdDialog.show({
                templateUrl: C_SERVER + '/mantenimiento/popupEdit',
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

        $scope.modalAuth = function (ev) {
            $scope.controls = [{}];
            $mdDialog.show({
                templateUrl: C_SERVER + '/mantenimiento/popupAuth',
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
            $scope.showdata = false;
            $scope.reset();
            $mdDialog.cancel();
        };

        $scope.getAmbitos = function () {
            $http({
                url: C_SERVER + '/mantenimiento/ambito/',
                method: 'GET'
            }).success(function (response) {
                $scope.ambitos = response;
                for (var i = 0; i < $scope.ambitos.length; i++) {
                    if ($scope.ambitos[i].informacion !== null) {
                        $scope.ambitos[i].infoParse = JSON.parse($scope.ambitos[i].informacion);
                    }
                }
            });
        };

        $scope.getAmbitoPadre = function (ubigeo) {
            $scope.lstAmbitosPadre = [];
            for (var i = 0; i < $scope.ambitos.length; i++) {
                if ($scope.ambitos[i].ubigeo === ubigeo) {
                    if ($scope.ambitos[i].ambitoPadre == null) {
                        $scope.lstAmbitosPadre.push($scope.ambitos[i]);
                    }
                }
            }
        };

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
            if ($scope.ambito.id == null) {
                $scope.provincia = '';
                $scope.ambito.ubigeo = '';
            }

            $http({
                url: C_SERVER + '/mantenimiento/ambito/provincias/' + dep,
                method: 'GET'
            }).success(function (response) {
                console.log("provincias");
                var data = JSON.parse(response.data);
                $scope.controlsProv = [];
                $scope.controlsDist = [];
                $scope.provincias = JSON.parse(data.provincias);
                $scope.controlsProv = [{}];
                $scope.controlsDist = [{}];
            });
        };

        $scope.getDistritos = function (prov) {
            if ($scope.ambito.id == null) {
                $scope.ambito.ubigeo = '';
            }
            $http({
                url: C_SERVER + '/mantenimiento/ambito/distritos/' + prov,
                method: 'GET'
            }).success(function (response) {
                var data = JSON.parse(response.data);
                $scope.controlsDist = [];
                $scope.distritos = JSON.parse(data.distritos);
                $scope.controlsDist = [{}];
            });
        };

        $scope.addAmbito = function () {
            $scope.anexos = [];
            $scope.reset();
            $scope.modal();
        };

        $scope.ver = function (id) {
            $scope.showdata = true;
            $scope.edit(id);
        };

        $scope.download = function (id) {
            window.open(C_SERVER + "/mantenimiento/ambito/exportar/" + id);
        };

        $scope.edit = function (id) {
            $scope.anexos = [];

            for (var i = 0; i < $scope.ambitos.length; i++) {
                if ($scope.ambitos[i].id === id) {
                    $scope.reset();
                    $scope.setAmbito($scope.ambitos[i], $scope.constantAmbito.tipo.principal);
                    break;
                }
            }
            if ($scope.ambito.tipoAmbito == $scope.constantAmbito.tipo.principal) {
                for (var i = 0; i < $scope.ambitos.length; i++) {
                    if ($scope.ambitos[i].ambitoPadre == $scope.ambito.id) {
                        $scope.anexos.push($scope.ambitos[i]);
                    }
                }
            } else {
                $scope.getAmbitoPadre($scope.ambito.ubigeo);
            }

            if ($scope.ambito.informacion != null) {
                var autoridad = JSON.parse($scope.ambito.informacion);
                $scope.observacion.cargo = autoridad.cargo;
                $scope.observacion.apellidoPaterno = autoridad.apellidoPaterno;
                $scope.observacion.apellidoMaterno = autoridad.apellidoMaterno;
                $scope.observacion.nombres = autoridad.nombres;
                $scope.observacion.telefono = autoridad.telefono;
                $scope.observacion.direccion = autoridad.direccion;
                $scope.observacion.email = autoridad.email;

                $scope.observacion.cargo_ = autoridad.cargo_;
                $scope.observacion.apellidoPaterno_ = autoridad.apellidoPaterno_;
                $scope.observacion.apellidoMaterno_ = autoridad.apellidoMaterno_;
                $scope.observacion.nombres_ = autoridad.nombres_;
                $scope.observacion.telefono_ = autoridad.telefono_;
                $scope.observacion.direccion_ = autoridad.direccion_;
                $scope.observacion.email_ = autoridad.email_;
            }

            $scope.departamento = $scope.ambito.ubigeo.substring(0, 2) + "0000";
            $scope.getProvincias($scope.departamento);
            $scope.provincia = $scope.ambito.ubigeo.substring(0, 4) + "00";
            $scope.getDistritos($scope.provincia);
            $scope.distrito = $scope.ambito.ubigeo;

            $scope.modal();
        };

        $scope.editAuth = function (auth, id) {

            if (auth == 1) {
                $scope.indexAuth = true;
            } else {
                $scope.indexAuth = false;
            }

            $scope.anexos = [];

            for (var i = 0; i < $scope.ambitos.length; i++) {
                if ($scope.ambitos[i].id === id) {
                    $scope.reset();
                    $scope.setAmbito($scope.ambitos[i], $scope.constantAmbito.tipo.principal);
                    break;
                }
            }
            if ($scope.ambito.tipoAmbito == $scope.constantAmbito.tipo.principal) {
                for (var i = 0; i < $scope.ambitos.length; i++) {
                    if ($scope.ambitos[i].ambitoPadre == $scope.ambito.id) {
                        $scope.anexos.push($scope.ambitos[i]);
                    }
                }
            } else {
                $scope.getAmbitoPadre($scope.ambito.ubigeo);
            }

            if ($scope.ambito.informacion != null) {
                var autoridad = JSON.parse($scope.ambito.informacion);
                $scope.observacion.cargo = autoridad.cargo;
                $scope.observacion.apellidoPaterno = autoridad.apellidoPaterno;
                $scope.observacion.apellidoMaterno = autoridad.apellidoMaterno;
                $scope.observacion.nombres = autoridad.nombres;
                $scope.observacion.telefono = autoridad.telefono;
                $scope.observacion.direccion = autoridad.direccion;
                $scope.observacion.email = autoridad.email;

                $scope.observacion.cargo_ = autoridad.cargo_;
                $scope.observacion.apellidoPaterno_ = autoridad.apellidoPaterno_;
                $scope.observacion.apellidoMaterno_ = autoridad.apellidoMaterno_;
                $scope.observacion.nombres_ = autoridad.nombres_;
                $scope.observacion.telefono_ = autoridad.telefono_;
                $scope.observacion.direccion_ = autoridad.direccion_;
                $scope.observacion.email_ = autoridad.email_;
            }

            $scope.departamento = $scope.ambito.ubigeo.substring(0, 2) + "0000";
            $scope.getProvincias($scope.departamento);
            $scope.provincia = $scope.ambito.ubigeo.substring(0, 4) + "00";
            $scope.getDistritos($scope.provincia);
            $scope.distrito = $scope.ambito.ubigeo;
            $scope.modalAuth();
        };

        $scope.saveAmbitos = function (lstambito) {
            var list = JSON.stringify(lstambito);
            $http({
                url: C_SERVER + '/mantenimiento/ambito/lista',
                method: 'POST',
                data: list,
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response) {
                $mdDialog.cancel();
                $scope.reset();
                $scope.getAmbitos();
            });
        };

        $scope.saveAmbito = function (ambito) {
            var exist = false;
            for (var i = 0; i < $scope.ambitos.length; i++) {
                if ($scope.ambitos[i].nombreAmbito === ambito.nombreAmbito) {
                    if ($scope.ambitos[i].ubigeo === ambito.ubigeo) {
                        //exist = true;
                        break;
                    }
                }
            }
            if (exist) {
                var alert = $mdDialog.alert({
                    title: 'Atencion.', textContent: 'Ya existe un Ambito con el mismo nombre, en este ubigeo.', ok: 'ACEPTAR'
                });
                $mdDialog.show(alert).finally(function () {
                    alert = undefined;
                    $scope.modal();
                });
            } else {
                $http({
                    url: C_SERVER + '/mantenimiento/ambito/',
                    method: 'POST',
                    data: ambito,
                    contentType: 'application/json',
                    header: {'Content-Type': 'application/json; charset=UTF-8'}
                }).success(function (response) {
                    $mdDialog.cancel();
                    $scope.reset();
                    $scope.getAmbitos();
                });
            }
        };

        $scope.submit = function () {
            $scope.lstAmbitos = [];
            $scope.ambito.informacion = JSON.stringify($scope.observacion);
            if ($scope.ambito.tipoAmbito == $scope.constantAmbito.tipo.principal) {
                var statusAnexo = false;
                if ($scope.anexos.length > 0) {
                    $scope.lstAmbitos.push($scope.ambito);
                    for (var i = 0; i < $scope.anexos.length; i++) {
                        if ($scope.anexos[i].nombreAmbito != null && $scope.anexos[i].categoria != null) {
                            $scope.reset();
                            $scope.setAmbito($scope.anexos[i], $scope.constantAmbito.tipo.anexo);
                            $scope.lstAmbitos.push(angular.copy($scope.anexos[i]));
                            statusAnexo = true;
                        }
                    }
                    if (statusAnexo) {
                        $scope.saveAmbitos($scope.lstAmbitos);
                    } else {
                        $scope.saveAmbito($scope.ambito);
                    }
                    $scope.anexos = [];
                } else {
                    $scope.saveAmbito($scope.ambito);
                }

            } else {
                $scope.setAmbito($scope.ambito, $scope.constantAmbito.tipo.anexo)
                $scope.saveAmbito($scope.ambito);
                $scope.reset();
            }
        };

        $scope.submitAutoridad = function () {
            $scope.lstAmbitos = [];
            $scope.ambito.informacion = JSON.stringify($scope.observacion);
            if ($scope.ambito.tipoAmbito == $scope.constantAmbito.tipo.principal) {
                var statusAnexo = false;
                if ($scope.anexos.length > 0) {
                    $scope.lstAmbitos.push($scope.ambito);
                    for (var i = 0; i < $scope.anexos.length; i++) {
                        if ($scope.anexos[i].nombreAmbito != null && $scope.anexos[i].categoria != null) {
                            $scope.reset();
                            $scope.setAmbito($scope.anexos[i], $scope.constantAmbito.tipo.anexo);
                            $scope.lstAmbitos.push(angular.copy($scope.anexos[i]));
                            statusAnexo = true;
                        }
                    }
                    if (statusAnexo) {
                        $scope.saveAmbitos($scope.lstAmbitos);
                    } else {
                        $scope.saveAmbito($scope.ambito);
                    }
                    $scope.anexos = [];
                } else {
                    $scope.saveAmbito($scope.ambito);
                }

            } else {
                $scope.setAmbito($scope.ambito, $scope.constantAmbito.tipo.anexo)
                $scope.saveAmbito($scope.ambito);
                $scope.reset();
            }
        };


        $scope.delete = function (ambito) {
            var confirm = $mdDialog.confirm()
                    .title('Eliminar Ambito.')
                    .textContent('Esta seguro que desea eliminar el Ambito principal:' + ambito.nombreAmbito)
                    .targetEvent()
                    .ok('Si')
                    .cancel('No');
            $mdDialog.show(confirm).then(function () {
                $http({
                    url: C_SERVER + '/mantenimiento/ambito/' + ambito.id,
                    method: 'GET',
                    header: {'Content-Type': 'application/json; charset=UTF-8'}
                }).success(function (response) {
                    var alert = $mdDialog.alert({
                        title: 'Aviso.',
                        textContent: response.message,
                        ok: 'ACEPTAR'
                    });
                    $mdDialog.show(alert).finally(function () {
                        $scope.getAmbitos();
                        alert = undefined;
                    });
                });
            }, function () {
            });
        };

        $scope.nextWindow = function () {
            $scope.index++;
        };

        $scope.backWindow = function () {
            if ($scope.index > 0) {
                $scope.index--;
            }
        };

        $scope.getAmbitos();
        $scope.getDepartamentos();


    }]);
