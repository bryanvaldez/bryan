app.controller('registroCtrl', ['$scope', '$http', '$mdDialog', '$timeout', function ($scope, $http, $mdDialog, $timeout) {

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

        $scope.showLista = true;
        $scope.showRegistro = false;
        $scope.showReimprimir = false;
        $scope.numele = "";
        $scope.ubigeoElectorDpto = {};
        $scope.ubigeoElectorProv = {};
        $scope.ubigeoElectorDist = {};
        $scope.ambito = {};
        $scope.controlsProv = [];
        $scope.controlsProv = [{}];
        $scope.controlsDist = [];
        $scope.controlsDist = [{}];
        $scope.ubigeoExpediente.codigo = "";

        $scope.totalMismoUbigeo = 0;
        $scope.totalVPMismoUbigeo = 0;
        $scope.totalDiferenteUbigeo = 0;
        $scope.totalVPDiferenteUbigeo = 0;
        $scope.indicadorVistaPrevia = 0;
        $scope.isNewElector = 0;

        $scope.indicadorImp = "";
        $scope.compaginado = {};
        $scope.rango = {};
        $scope.rango.inicial = "";
        $scope.rango.final = "";
        $scope.chbPrintTodos = false;

        $scope.auxInicio = "";
        $scope.auxFinal = "";

        $scope.expediente = {
            id: 0,
            expediente: '',
            fechaExpediente: null,
            ambito: {id: null, nombreAmbito: ''}
        };

        $scope.expediente = {
            id: 0,
            expediente: '',
            fechaExpediente: null,
            ambito: {id: null, nombreAmbito: '', departamento: '', provincia: '', distrito: ''}
        };

        $scope.expedienteImpresion = {
            id: 0,
            registroInicial: 0,
            registroFinal: 0,
            indicador: 0,
            pagina: 0,
            expediente: {id: 0, expediente: ''}
        };

//        $scope.setExpediente = function (expediente) {
//            if (expediente.id !== null) {
//                $scope.expediente.id = expediente.id;
//                $scope.expediente.expediente = expediente.expediente;
//                $scope.expediente.fechaExpediente = expediente.fechaExpediente;
//                $scope.expediente.ambito.id = expediente.ambito.id;
//                $scope.expediente.ambito.nombreAmbito = expediente.ambito.nombreAmbito;
//            }
//        };

        $scope.fetchExpediente = function (id) {
            $http({
                url: C_SERVER + '/registro/fetchExpediente/' + id,
                method: 'GET',
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response, status) {
                if (status === 204) {
                    console.log("No se encontró información del expediente.");
                }
                $scope.setExpediente(response);
            });
        };

        $scope.cleanElector = function () {
            $scope.elector = {
                id: null,
                dni: '',
                apellidoPaterno: '',
                apellidoMaterno: '',
                nombre: '',
                ubigeo: '',
                expediente: {id: 0, expediente: ''},
            };
        };
        $scope.cleanElector();

        $scope.cleanExpedientePadron = function () {
            $scope.expedientePadron = {
                id: null,
                dni: '',
                apellidoPaterno: '',
                apellidoMaterno: '',
                nombre: '',
                ubigeoElector: '',
                ubigeoLista: '',
                ordenRegistro: null,
                indicador: null,
                estado: null,
                observacion: '',
                expediente: {id: 0, expediente: ''},
                ambito: {id: null, nombreAmbito: ''}
            };
        };
        $scope.cleanExpedientePadron();

        $scope.listarProv = function () {
            $scope.ubigeoElectorProv.ubigeo = '';
            $scope.ubigeoElectorDist.ubigeo = '';
            $http({
                method: 'GET',
                url: C_SERVER + "/registro/fetchUbigeo/" + $scope.ubigeoElectorDpto.ubigeo
            }
            ).then(function successCallback(responseProv) {
                $scope.controlsProv = [];
                $scope.controlsDist = [];
                $scope.lstProv = responseProv.data;
                $scope.controlsProv = [{}];
                $scope.controlsDist = [{}];
            }, function errorCallback(responseProv) {
            }
            );
        };

        $scope.listarDist = function () {
            $scope.ubigeoElectorDist.ubigeo = '';
            $http({
                method: 'GET',
                url: C_SERVER + "/registro/fetchUbigeo/" + $scope.ubigeoElectorProv.ubigeo
            }
            ).then(function successCallback(responseDist) {
                $scope.controlsDist = [];
                $scope.lstDist = responseDist.data;
                $scope.controlsDist = [{}];
            }, function errorCallback(responseDist) {
            }
            );
        };

        $scope.searchDni = function () {

            if ($scope.numele.length === 8) {
                $http({
                    method: 'GET',
                    url: C_SERVER + "/registro/fetchPadron/" + $scope.numele
                }).then(function successCallback(responseElector) {
                    if (responseElector.data === "") {
                        $scope.isNewElector = 1;
                        $scope.cleanElector();
                        $scope.ubigeoElectorDpto = {};
                        $scope.ubigeoElectorProv = {};
                        $scope.ubigeoElectorDist = {};
                        $scope.controlsProv = [];
                        $scope.controlsProv = [{}];
                        $scope.controlsDist = [];
                        $scope.controlsDist = [{}];
                        console.log("no se encontro al elector");
                        var confirm = $mdDialog.confirm()
                                .title('Elector no Encontrado.')
                                .textContent('No se encontr\u00f3 el n\u00famero de DNI en el Padr\u00f3n. \n ¿Desea registrarlo?')
                                //.ariaLabel('Lucky day')
                                .targetEvent()
                                .ok('Si')
                                .cancel('No');

                        $mdDialog.show(confirm).then(function () {
                            $scope.add();
                        }, function () {
                            $scope.numele = '';
                        });
                    } else {
                        $scope.isNewElector = 0;
                        $scope.elector = responseElector.data;
                        $scope.ubigeoElectorDpto.ubigeo = $scope.elector.ubigeoElector.substring(0, 2) + "0000";
                        $scope.listarProv();
                        $scope.ubigeoElectorProv.ubigeo = $scope.elector.ubigeoElector.substring(0, 4) + "00";
                        $scope.listarDist();
                        $scope.ubigeoElectorDist.ubigeo = $scope.elector.ubigeoElector;
                        console.log("elector encontrado");
                        if ($scope.elector.expediente.id === 0) {
                            $scope.registrarElector();
                        } else {
                            if ($scope.elector.expediente.id === $scope.ubigeoExpediente.codigo) {
                                $mdDialog.show(
                                        $mdDialog.alert()
                                        .parent(angular.element(document.querySelector('#popupContainer')))
                                        .clickOutsideToClose(false)
                                        .title('Elector ya Registrado')
                                        .textContent('El n\u00famero de DNI ya se encuentra registrado en la lista.')
                                        .ariaLabel('Alert Dialog Demo')
                                        .ok('Aceptar')
                                        .targetEvent()
                                        ).then(function () {
                                    $scope.numele = '';
                                });
                            } else {
                                var confirm = $mdDialog.confirm()
                                        .title('Elector ya Registrado')
                                        .textContent('El n\u00famero de DNI se encuentra registrado en otra lista. ¿Desea registrarlo en esta lista?')
                                        //.ariaLabel('Lucky day')
                                        .targetEvent()
                                        .ok('Si')
                                        .cancel('No');

                                $mdDialog.show(confirm).then(function () {
                                    $scope.registrarElector();
                                }, function () {
                                    $scope.numele = '';
                                });
                            }

                        }

                    }
                }, function errorCallback(response) {
                }
                );
            }

        };

//        $scope.changeNumEle = function () {
////            if ($scope.numele.length < 8) {
////                $scope.cleanValues();
////            }
//        };

        $scope.cleanValues = function () {
            $scope.cleanElector();
            $scope.ubigeoElectorDpto = {};
            $scope.ubigeoElectorProv = {};
            $scope.ubigeoElectorDist = {};
            $scope.controlsProv = [];
            $scope.controlsProv = [{}];
            $scope.controlsDist = [];
            $scope.controlsDist = [{}];
        }

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
        }

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

        $scope.listaSelected = function () {
            if ($scope.ubigeoExpediente.codigo !== "") {
                $http({
                    //url: C_SERVER + '/verificar/expediente/' + $scope.ubigeoExpediente.codigo,
                    url: C_SERVER + '/registro/fetchExpediente/' + $scope.ubigeoExpediente.codigo,
                    method: 'GET'
                }).success(function (response) {
                    console.log(JSON.stringify(response));
                    $scope.expediente = response;
                    $scope.showLista = false;
                    $scope.showRegistro = true;
                    $scope.showReimprimir = false;
                    $scope.getContadores();
                    $scope.listarAmbitos();
                    $scope.getExpedientesPadron();
                });

            }
        };

        $scope.exportarSelected = function () {
            if ($scope.ubigeoExpediente.codigo !== "") {
                $http({
                    url: C_SERVER + '/registro/countExpedientePadron/' + $scope.ubigeoExpediente.codigo,
                    method: 'GET'
                }).success(function (response) {
                    console.log(JSON.stringify(response));
                    if (response > 0) {
                        window.open(C_SERVER + "/registro/exportarExpediente/" + $scope.ubigeoExpediente.codigo);
                    } else {
                        $mdDialog.show(
                                $mdDialog.alert()
                                .parent(angular.element(document.querySelector('#popupContainer')))
                                .clickOutsideToClose(false)
                                .title('Expediente sin registros')
                                .textContent('El expediente seleccionado no cuenta con registros.')
                                .ariaLabel('Alert Dialog Demo')
                                .ok('Aceptar')
                                .targetEvent()
                                ).then(function () {                            
                        });
                    }
                });
            }
        };

        $scope.backListaSelected = function () {
            $scope.showLista = true;
            $scope.showRegistro = false;
            $scope.showReimprimir = false;
        };

        $scope.pageReimprimir = function () {
            $scope.showLista = false;
            $scope.showRegistro = false;
            $scope.showReimprimir = true;
            $scope.chbPrintTodos = false;
            $scope.indicadorImp = "";
            $scope.rango.inicial = "";
            $scope.rango.final = "";
            $scope.compaginado = {};
            $scope.electoresImp = [{}];
        };

        $scope.pageRegistro = function () {
            $scope.showLista = false;
            $scope.showRegistro = true;
            $scope.showReimprimir = false;
        };

        $scope.vistaPreviaMSelected = function () {

            $scope.expedientePadron.expediente.id = $scope.ubigeoExpediente.codigo;
            $scope.expedientePadron.indicador = 0;

            $http({
                url: C_SERVER + '/registro/listarElectoresPrevia/',
                method: 'POST',
                data: $scope.expedientePadron,
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response) {
                $scope.electoresPrevia = response;
                $scope.indicadorVistaPrevia = 0;
                $scope.modal();
            });
        };

        $scope.vistaPreviaDSelected = function () {
            $scope.expedientePadron.expediente.id = $scope.ubigeoExpediente.codigo;
            $scope.expedientePadron.indicador = 1;

            $http({
                url: C_SERVER + '/registro/listarElectoresPrevia/',
                method: 'POST',
                data: $scope.expedientePadron,
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response) {
                $scope.electoresPrevia = response;
                $scope.indicadorVistaPrevia = 1;
                $scope.modal();
            });
        };

        $scope.printVP = function () {
            if ($scope.indicadorVistaPrevia === 1) {
                window.open(C_SERVER + "/registro/printDifUbigeo/" + $scope.ubigeoExpediente.codigo);
                $scope.printVPUbigeo();
            } else {
                window.open(C_SERVER + "/registro/printMismoUbigeo/" + $scope.ubigeoExpediente.codigo);
            }
        };

        $scope.generarListaImpresion = function (indicador) {
            if (indicador === 0) {
                window.open(C_SERVER + "/registro/printMismoUbigeo/" + $scope.ubigeoExpediente.codigo);
            } else {
                window.open(C_SERVER + "/registro/printDifUbigeo/" + $scope.ubigeoExpediente.codigo);
                $scope.printVPUbigeo();
            }
        };

        $scope.printVPUbigeo = function () {
            window.open(C_SERVER + "/registro/printDifUbigeo2/" + $scope.ubigeoExpediente.codigo);
        };

        $scope.backElectorSelected = function () {
            $scope.showLista = false;
            $scope.showRegistro = true;
            $scope.showVista = false;
            $scope.showReimprimir = false;
        };

        $scope.eliminarSelected = function (elector) {

            $scope.elector.id = 1;
            $scope.elector.dni = "46752638";
            $scope.elector.apellidoPaterno = "QUISPE";
            $scope.elector.apellidoMaterno = "CONDORI";
            $scope.elector.nombre = "ANGEL DAVID";
            $scope.elector.ubigeoElector = "140613";
            $scope.elector.ubigeoLista = "140101";
            $scope.elector.ordenRegistro = 1;
            $scope.elector.indicador = 1;
            $scope.elector.estado = 4;
            $scope.elector.observacion = null;
            $scope.elector.expediente.id = 3;
            $scope.elector.expediente.expediente = "EXP_1_20161020";
            $scope.elector.ambito.id = 2;
            $scope.elector.ambito.nombreAmbito = "CCPP LORETO";

//        $scope.elector.id = elector.id;
//        $scope.elector.dni = elector.dni;
//        $scope.elector.apellidoPaterno = elector.apellidoPaterno;
//        $scope.elector.apellidoMaterno = elector.apellidoMaterno;
//        $scope.elector.nombre = elector.nombre;
//        $scope.elector.ubigeoElector = elector.ubigeoElector;
//        $scope.elector.ubigeoLista = elector.ubigeoLista;
//        $scope.elector.ordenRegistro = elector.ordenRegistro;
//        $scope.elector.indicador = elector.indicador;
//        $scope.elector.estado = elector.estado;
//        $scope.elector.observacion = null;
//        $scope.elector.expediente.id = elector.expediente.id;
//        $scope.elector.expediente.expediente = elector.expediente.expediente;
//        $scope.elector.ambito.id = elector.ambito.id;
//        $scope.elector.ambito.nombreAmbito = elector.ambito.nombreAmbito;

            $http({
                url: C_SERVER + '/registro/eliminarElector/',
                method: 'POST',
                data: $scope.elector,
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (status) {
                if (status === 404) {
                    console.log("Ocurrió un error.");
                } else {
                    console.log("Elector Eliminado.");
                }
            });
        };

        $scope.close = function () {
            $mdDialog.cancel();
        };

        $scope.modal = function (ev) {
            $mdDialog.show({
                templateUrl: C_SERVER + '/registro/vistaPrevia',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: false,
                bindToController: true,
                escapeToClose: true,
                scope: $scope,
                preserveScope: true
            }).then(function (answer) {

            }, function () {

            });
        };

        $scope.getContadores = function () {
            $http({
                method: 'GET',
                url: C_SERVER + "/registro/getContadores/" + $scope.ubigeoExpediente.codigo
            }
            ).then(function successCallback(responseCont) {
                $scope.totalMismoUbigeo = responseCont.data[0];
                $scope.totalVPMismoUbigeo = responseCont.data[1];
                $scope.totalDiferenteUbigeo = responseCont.data[2];
                $scope.totalVPDiferenteUbigeo = responseCont.data[3];
            }, function errorCallback(responseCont) {

            }
            );
        };

        $scope.listarAmbitos = function () {
            $http({
                method: 'GET',
                url: C_SERVER + "/registro/fetchAmbitosReg/" + $scope.ubigeoCCPP.codigo
            }
            ).then(function successCallback(responseAmb) {
                $scope.ambitos = responseAmb.data;
                $scope.ambito.codigo = responseAmb.data[0].N_AMBITO_PK;
            }, function errorCallback(responseAmb) {
            }
            );
        };

        $scope.elector = {
            id: null,
            dni: '',
            apellidoPaterno: '',
            apellidoMaterno: '',
            nombre: '',
            ubigeo: '',
            expediente: {id: 0, expediente: ''}
        };

        $scope.getExpedientesPadron = function () {
            $http({
                method: 'GET',
                url: C_SERVER + "/registro/fetchExpPadByCodExpediente/" + $scope.ubigeoExpediente.codigo
            }
            ).then(function successCallback(responseExpPad) {
                $scope.expElectores = responseExpPad.data;
            }, function errorCallback(responseExpPad) {
            }
            );
        };


        $scope.registrarElector = function () {
            $scope.expedientePadron.id = null;
            $scope.expedientePadron.dni = $scope.numele;
            $scope.expedientePadron.ubigeoElector = $scope.ubigeoElectorDist.ubigeo;
            $scope.expedientePadron.ubigeoLista = $scope.ubigeoDistrito.codigo;
            $scope.expedientePadron.apellidoPaterno = $scope.elector.apellidoPaterno;
            $scope.expedientePadron.apellidoMaterno = $scope.elector.apellidoMaterno;
            $scope.expedientePadron.nombre = $scope.elector.nombre;
            $scope.expedientePadron.observacion = '';
            $scope.expedientePadron.ambito.id = $scope.ambito.codigo;
            $scope.expedientePadron.expediente.id = $scope.ubigeoExpediente.codigo;
            $http({
                method: 'POST',
                url: C_SERVER + "/registro/registrarElector/",
                data: $scope.expedientePadron,
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }
            ).then(function successCallback(responseCont) {

                console.log("Elector Registrado.");
                $scope.getContadores();
                $scope.cleanValues();
                $scope.cleanExpedientePadron();
                $scope.numele = "";
                $scope.ambito.codigo = $scope.ambitos[0].N_AMBITO_PK;
                $scope.getExpedientesPadron();


            }, function errorCallback(responseCont) {

            }
            );
        };

        $scope.add = function () {
            $scope.modalElector();
        };

        $scope.modalElector = function (ev) {
            $mdDialog.show({
                templateUrl: C_SERVER + '/registro/nuevoElector',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: true,
                fullscreen: false,
                bindToController: true,
                escapeToClose: true,
                scope: $scope,
                preserveScope: true
            }).then(function (answer) {

            }, function () {

            });
        };

        $scope.updateAmbito = function (expElector) {

            $scope.expedientePadron.id = expElector.N_EXPEDIENTE_PADRON_PK;
            $scope.expedientePadron.dni = expElector.C_DOCUMENTO_IDENTIDAD;
            $scope.expedientePadron.apellidoPaterno = expElector.C_APELLIDO_PATERNO;
            $scope.expedientePadron.apellidoMaterno = expElector.C_APELLIDO_MATERNO;
            $scope.expedientePadron.nombre = expElector.C_NOMBRE;
            $scope.expedientePadron.ubigeoElector = expElector.C_UBIGEO_ELECTOR;
            $scope.expedientePadron.ubigeoLista = expElector.C_UBIGEO_EXPEDIENTE;
            $scope.expedientePadron.ordenRegistro = expElector.N_ORDEN_REGISTRO;
            $scope.expedientePadron.indicador = expElector.N_INDICADOR;
            $scope.expedientePadron.estado = expElector.N_ESTADO;
            $scope.expedientePadron.observacion = expElector.C_OBSERVACION;
            $scope.expedientePadron.expediente.id = expElector.N_EXPEDIENTE;
            $scope.expedientePadron.ambito.id = expElector.N_AMBITO;
            $http({
                method: 'POST',
                url: C_SERVER + "/registro/updateElector/",
                data: $scope.expedientePadron,
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }
            ).then(function successCallback(responseCont) {
                if (status === 404) {
                    console.log("Ocurrió un error.");
                } else {
                    $scope.numele = "";
                    $scope.cleanExpedientePadron();
                    $scope.ambito.codigo = $scope.ambitos[0].N_AMBITO_PK;
                    $scope.getExpedientesPadron();
                }

            }, function errorCallback(responseCont) {

            }
            );

        };

        $scope.submit = function () {
            $scope.registrarElector();
            $scope.close();
        };

        $scope.getCompaginados = function () {
            $scope.compaginado = {};
            $scope.electoresImp = {};
            $scope.rango.inicial = "";
            $scope.rango.final = "";
            $scope.chbPrintTodos = false;
            $scope.urlComp = "";
            $scope.auxInicio = "";
            $scope.auxFinal = "";
            if ($scope.indicadorImp === "0") {
                $scope.urlComp = "/registro/getCompaginadosMUbigeo/";

            } else if ($scope.indicadorImp === "1") {
                $scope.urlComp = "/registro/getCompaginadosDUbigeo/";
            }
            if ($scope.urlComp !== "") {
                $http({
                    method: 'GET',
                    url: C_SERVER + $scope.urlComp + $scope.ubigeoExpediente.codigo
                }
                ).then(function successCallback(responseComp) {
                    $scope.paginas = responseComp.data;
                }, function errorCallback(responseComp) {
                }
                );
            }
        };

        $scope.getCompaginado = function () {
            $http({
                method: 'GET',
                url: C_SERVER + "/registro/getElectoresByCompaginado/" + $scope.compaginado.codigo
            }
            ).then(function successCallback(responseECom) {
                $scope.electoresImp = responseECom.data;
                if ($scope.electoresImp.length > 0) {
                    $scope.auxInicio = $scope.electoresImp[0].ordenRegistro;
                    $scope.auxFinal = $scope.electoresImp[$scope.electoresImp.length - 1].ordenRegistro;
                }

            }, function errorCallback(responseComp) {
            }
            );
        };
        $scope.focusTxtRango = function () {
            $scope.compaginado = {};
            $scope.chbPrintTodos = false;
        };
        $scope.focusCompaginado = function () {
            $scope.rango.inicial = "";
            $scope.rango.final = "";
            $scope.chbPrintTodos = false;
        };
        $scope.getPrintTodos = function () {

            if ($scope.chbPrintTodos === true) {
                $scope.rango.inicial = "";
                $scope.rango.final = "";
                $scope.compaginado = {};
                $http({
                    method: 'GET',
                    url: C_SERVER + "/registro/getElectoresTodos/" + $scope.ubigeoExpediente.codigo + "/" + $scope.indicadorImp
                }
                ).then(function successCallback(responseT) {
                    $scope.electoresImp = responseT.data;
                    if ($scope.electoresImp.length > 0) {
                        $scope.auxInicio = $scope.electoresImp[0].ordenRegistro;
                        $scope.auxFinal = $scope.electoresImp[$scope.electoresImp.length - 1].ordenRegistro;
                    }
                }, function errorCallback(responseT) {
                }
                );
            } else {
                $scope.electoresImp = {};
                $scope.auxInicio = "";
                $scope.auxFinal = "";
            }
        };

        $scope.getRangoElectores = function () {
            $http({
                method: 'GET',
                url: C_SERVER + "/registro/getElectoresByRango/" + $scope.ubigeoExpediente.codigo + "/" + $scope.indicadorImp + "/" + $scope.rango.inicial + "/" + $scope.rango.final
            }
            ).then(function successCallback(responseElectoresRango) {
                $scope.electoresImp = responseElectoresRango.data;
                if ($scope.electoresImp.length > 0) {
                    $scope.auxInicio = $scope.electoresImp[0].ordenRegistro;
                    $scope.auxFinal = $scope.electoresImp[$scope.electoresImp.length - 1].ordenRegistro;
                }
            }, function errorCallback(responseT) {
            }
            );
        };

        $scope.reImprimir = function () {
            if ($scope.compaginado.codigo !== undefined) {
                window.open(C_SERVER + "/registro/reImprimir/" + $scope.ubigeoExpediente.codigo + "/" + $scope.indicadorImp + "/0/" + $scope.compaginado.codigo + "/" + $scope.auxInicio + "/" + $scope.auxFinal);

                if ($scope.indicadorImp === "1") {
                    window.open(C_SERVER + "/registro/reImprimir/" + $scope.ubigeoExpediente.codigo + "/" + $scope.indicadorImp + "/1/" + $scope.compaginado.codigo + "/" + $scope.auxInicio + "/" + $scope.auxFinal);
                }
            } else {
                window.open(C_SERVER + "/registro/reImprimir/" + $scope.ubigeoExpediente.codigo + "/" + $scope.indicadorImp + "/0/0/" + $scope.auxInicio + "/" + $scope.auxFinal);

                if ($scope.indicadorImp === "1") {
                    window.open(C_SERVER + "/registro/reImprimir/" + $scope.ubigeoExpediente.codigo + "/" + $scope.indicadorImp + "/1/0/" + $scope.auxInicio + "/" + $scope.auxFinal);
                }
            }
        };

        $scope.generarFichaV = function () {
            window.open(C_SERVER + "/registro/generateFVDoc/" + $scope.ubigeoExpediente.codigo);
            window.open(C_SERVER + "/registro/generarFichaVerificacion/" + $scope.ubigeoExpediente.codigo);
        };
    }]);

