'use strict';
app.controller('cargaCtrl',['$scope','$http','Factory','CargaService','$timeout','$mdDialog', function($scope, $http, Factory, CargaService, $timeout, $mdDialog){
    
    Factory.inicializar($scope);        
    
    $scope.showObs = false;
    $scope.showUpload = true;
    $scope.ambito = {};
    $scope.obsSelected = [];
    $scope.currentElector  = {};
    $scope.charge = false;
    
    $scope.selected = {
        departamento: null,
        provincia:null,
        distrito:null,
        ccpp:null,
        expediente:null
    };
        
    // Listar Departamento = 1/ Provincia = 2 / Distrito = 3 / CCPP = 4 / Expedientes = 5
    $scope.getUbigeos = function(type, ubigeo){ 
        if(type === 1){
            $scope.departamentos = [];
            CargaService.getDepartamentos()
            .then(function(data) {											
                $scope.departamentos = data;            
            });              
        }            
        
        if(type === 2){
            
            $scope.provincias = [];
            CargaService.getProvincias(ubigeo)
            .then(function(data) {											
                $scope.provincias = data;            
            });
        }
        if(type === 3){
            $scope.distritos = [];
            CargaService.getDistritos(ubigeo)
            .then(function(data) {											
                $scope.distritos = data;            
            });
        }    
        
        var done = clearSelected(type-1);
        
    }
    
    $scope.getCCPP = function(ubigeo){
        
        CargaService.getCCPP(ubigeo)
        .then(function(data) {											
            $scope.ccpp = data;           
        });
        
        var done = clearSelected(4);
    }      
    
    $scope.getExpedientes = function(ambito){        
        CargaService.getExpedientes(ambito)
        .then(function(data) {											
            $scope.expedientes = data;           
        });

        var done = clearSelected(4);
    };
    
    $scope.getAmbito = function(){
        
        for(var i = 0; i < $scope.departamentos.length; i++){
            if($scope.departamentos[i].UBIGEO == $scope.selected.departamento){
                $scope.ambito.departamento = {
                    id : $scope.departamentos[i].UBIGEO,
                    desc: $scope.departamentos[i].DEPARTAMENTO                    
                }                
                break;
            }            
        }
        
        for(var i = 0; i < $scope.provincias.length; i++){
            if($scope.provincias[i].UBIGEO == $scope.selected.provincia){
                $scope.ambito.provincia = {
                    id : $scope.provincias[i].UBIGEO,
                    desc: $scope.provincias[i].PROVINCIA                    
                }
                break;
            }            
        } 
        
        for(var i = 0; i < $scope.distritos.length; i++){
            if($scope.distritos[i].UBIGEO == $scope.selected.distrito){
                $scope.ambito.distrito = {
                    id : $scope.distritos[i].UBIGEO,
                    desc: $scope.distritos[i].DISTRITO                    
                }
                break;
            }            
        }    
        
        for(var i = 0; i < $scope.ccpp.length; i++){
            if($scope.ccpp[i].ID == $scope.selected.ccpp){
                $scope.ambito.ccpp = {
                    id : $scope.ccpp[i].ID,
                    desc: $scope.ccpp[i].CCPP                   
                }
                break;
            }            
        }
    }
    
    function clearSelected(level){        
        if(level == 5) return true;
        $scope.selected.expediente= null;
        if(level == 4) return true;
        $scope.selected.ccpp=null;
        if(level == 3) return true;
        $scope.selected.distrito=null;
        if(level == 2) return true;
        $scope.selected.provincia=null;
        if(level == 1) return true;
        $scope.selected.departamento=null; 
    }
    // Autocomplete
    $scope.searchByUbigeo = function (ubigeo) {        
        return $http.get(C_SERVER + "/carga/buscar-ubigeo/" + ubigeo)
                .then(function (response) {
                    return response.data;
                });
    };
    
    $scope.searchByAmbito = function (nombre) {
        return $http.get(C_SERVER + "/carga/buscar-ambito/" + nombre)
                .then(function (response) {
                    return response.data;
                });
    };
    
    $scope.selectedUbigeo = function (query) {        
        if (query !== undefined) {
            $scope.selected.departamento = query.UBIGEO.substring(0, 2);
            $scope.getUbigeos(2,$scope.selected.departamento);
            
            $scope.selected.provincia = query.UBIGEO.substring(0, 4);
            $scope.getUbigeos(3,$scope.selected.provincia);
            
            $scope.selected.distrito= query.UBIGEO.substring(0, 6);
            $scope.getCCPP($scope.selected.distrito); 
        }
    };

    $scope.selectedCCPP = function (query) {
        if (query !== undefined) {
            $scope.selected.departamento = query.C_UBIGEO.substring(0, 2);
            $scope.getUbigeos(2,$scope.selected.departamento);
            
            $scope.selected.provincia = query.C_UBIGEO.substring(0, 4);
            $scope.getUbigeos(3,$scope.selected.provincia);
            
            $scope.selected.distrito = query.C_UBIGEO.substring(0, 6);
            $scope.getCCPP($scope.selected.distrito);
            
            $scope.selected.ccpp = query.N_AMBITO_PK;
            $scope.getExpedientes($scope.selected.ccpp);
        }
    };
    // file
    $scope.$watch('filePadron.length',function(newVal,oldVal){
        if(oldVal){
            $scope.showObs = false;
        }
        if($scope.filePadron !== undefined && $scope.filePadron.length > 0){
            $scope.charge = true;
            $scope.showObs = false;
            $timeout(function () {
                validarFormato();
            }, 1000);            
        } 
    });
            
    function validarFormato(){             
        var fdata = new FormData();
        fdata.append('file', $scope.filePadron[0].lfFile);
        fdata.append('ambito',$scope.selected.distrito);
        $scope.obsValidation = [];
        $scope.obsHeaders = [];
        
        CargaService.validarFormato(fdata)
        .then(function(data) {	
            if(data.success === true){
                var result = validarCampos(data.data.data, data.data.columns);
                if(result.valid === true){
                    CargaService.getObservaciones()
                        .then(function(sdata){
                            $scope.getAmbito();
                            $scope.padron = sdata.data;
                            $scope.showUpload = false;
                            $scope.charge =false;
                    });
                                      
                } else {
                    $scope.charge =false;
                    $scope.obsValidation = result.obsDetail;
                    $scope.showObs = true;
                }
            } else {
                $scope.charge =false;
                $scope.obsHeaders = data.data.message;
                $scope.showObs = true;
            }            
        });         
    } 
        
    function validarCampos(data, headers){
        
        var tmpdata = angular.copy(data);
        
        var result = {
            valid:true,
            rowErrors: 0,
            obsDetail:[]
        };
        
        var countRowWithError = 0;
        var obsDetail = [];
        
        for(var i = 0; i < tmpdata.length; i++){ 
            var rowWithError = false;
            var rowDetail = {
                row: i+2,
                colObs: []
            }
            var row = tmpdata[i];
            for(var j = 0; j < headers.length; j++){ 
                var colWithError = false;
                var column = headers[j]; 
                var colDetail = {
                    col: column.excelColumn,
                    obs: ""
                }
                
                // Obligatory
                if(column.obligatory===1){                    
                    if(row[column.tableColumn] =="" || row[column.tableColumn] == null)
                        colWithError = true;                      
                }       
                
                // Validate content
                if (column.validation && !colWithError){                    
                    var regex = new RegExp(column.validation);
                    var match = regex.test(row[column.tableColumn])                    
                    if (!match)                        
                        colWithError = true;  
                }   
                
                if(colWithError){
                    colDetail.obs = column.messageValidation;
                    rowDetail.colObs.push(colDetail); 
                    rowWithError = true;
                }             
            } 
            
            if(rowWithError){
                countRowWithError++; 
                result.valid = false;
                obsDetail.push(rowDetail);
            }                           
        }
                
        result.rowErrors = countRowWithError;
        result.obsDetail = obsDetail;
        
        return result;
    } 
    
    // estado
    $scope.updateStatus = function(index, o){
                
        if(o !== undefined){
            var tmp = new Object();
            tmp[o.key] = o.desc; 
            $scope.padron[index].C_OBSERVACION.USUARIO = {};
            $scope.padron[index].C_OBSERVACION.USUARIO = tmp;
            $scope.padron[index].observacion = ($scope.padron[index].observacion != null)? $scope.padron[index].observacion+','+o.cod:o.cod;
            if(tmp.hasOwnProperty('OBS1') || tmp.hasOwnProperty('OBS6') || tmp.hasOwnProperty('OBS7')){
                $scope.padron[index].N_ESTADO = $scope.constantPadron.estado.rechazado.id;  
            }
        }  else {
            $scope.padron[index].C_OBSERVACION.USUARIO = {};
            $scope.padron[index].N_ESTADO = $scope.obsSelected[index].status;
        }
    }
    
    // Guardar
    $scope.guardar = function(){
        var fdata = new FormData();
        fdata.append('padron', JSON.stringify($scope.padron));
        fdata.append('expediente',$scope.selected.expediente);
        fdata.append('ambito',$scope.selected.ccpp);
        CargaService.guardar(fdata)
            .then(function(data){
                if(data.success){
                    $scope.showUpload = true;
                    $scope.lfApi.removeAll();
                    var alert = $mdDialog.alert({
                        title: 'Aviso.',
                        textContent: 'Se guardo correctamente.',
                        ok: 'ACEPTAR'
                    });
                    $mdDialog.show(alert).finally(function () {
                        alert = undefined;
                        $scope.modal();
                    });
                    //$scope.generarFichaV(data.data);
                }                
            });
    }
    
    $scope.generarFichaV = function (padron){
        var fdata = new FormData();
        fdata.append('padron', padron);
        fdata.append('expediente',$scope.selected.expediente);
        fdata.append('ambito',$scope.selected.ccpp);
        CargaService.generarFichaV(fdata)
            .then(function(data){
                window.open(C_SERVER + "/registro/generateFVDoc/" + $scope.ubigeoExpediente.codigo);
            });        
    }
    
    $scope.showDialogFmt = function(ev){
        $mdDialog.show({
            locals: {
                obsHeaders : $scope.obsHeaders,
                obsValidation : $scope.obsValidation
            },
            controller: DialogFmtController,
            templateUrl: C_SERVER + '/js/template/dialogFormatoError.tmpl.html',          
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose:true
        })
        .then(function(answer) {

        }, function() {

        });
    }
    
    function DialogFmtController($scope, $mdDialog, obsHeaders, obsValidation) {
        
        $scope.obsHeaders = obsHeaders;
        $scope.obsValidation = obsValidation;

        $scope.cancel = function() {
          $mdDialog.cancel();
        };
    }    
    
    $scope.showDialogObs = function(ev,data){  
        
        $mdDialog.show({
            locals: {obs:data},
            controller: DialogObsController,
            templateUrl: C_SERVER + '/js/template/dialogObsSistema.tmpl.html',          
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose:true
        })
        .then(function(answer) {

        }, function() {

        });
    }    
    
    function DialogObsController($scope, $mdDialog, obs) {
        
        $scope.obs = obs;
        $scope.cancel = function() {
          $mdDialog.cancel();
        };
    }
    
    $scope.searchElector = function(elector) {
        $scope.currentElector = elector;
//        for(var i = 0; i < $scope.padron.length; i++){
//            if($scope.padron[i].N_ORDEN_REGISTRO === elector.N_ORDEN_REGISTRO) {
//                $scope.currentElector = $scope.padron[i].N_ORDEN_REGISTRO;
//                break;
//            }
//        }        
        $scope.modalSearchElector();
    };
              
    $scope.padronSearch = {
        numele: '',
        apePat: '',
        apeMat: '',
        nombres: '',
        ubigeo:''
    };
        
    $scope.modalSearchElector = function (ev) {
        $scope.padronSearch = {
            numele: '',
            apePat: '',
            apeMat: '',
            nombres: '',
            ubigeo:''
        };        
        $scope.padrones = {};    
        $mdDialog.show({
            templateUrl: C_SERVER + '/buscarElector/modal2',
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
            var p = JSON.stringify($scope.padronSearch);
            $http({
                url: C_SERVER + '/buscarElector/buscar',
                method: 'POST',
                data: p,
                contentType: 'application/json',
                header: {'Content-Type': 'application/json; charset=UTF-8'}
            }).success(function (response) {
                $scope.padrones = response;
            });                         
    };
    
    $scope.edit = function(select){
        var fdata = new FormData();
        fdata.append('padron',select.numEle);
        CargaService.getObservaciones2(fdata)
            .then(function(data){
                if(data.success){
                    for(var i = 0; i < $scope.padron.length; i++){
                        if($scope.padron[i].N_ORDEN_REGISTRO === $scope.currentElector.N_ORDEN_REGISTRO) {              
                            $scope.padron[i].C_APELLIDO_MATERNO = data.data.C_APELLIDO_MATERNO;
                            $scope.padron[i].C_APELLIDO_MATERNO = data.data.C_APELLIDO_MATERNO;
                            $scope.padron[i].C_APELLIDO_PATERNO = data.data.C_APELLIDO_PATERNO;
                            $scope.padron[i].C_DOCUMENTO_IDENTIDAD = data.data.C_DOCUMENTO_IDENTIDAD;
                            $scope.padron[i].C_NOMBRE = data.data.C_NOMBRE;
                            $scope.padron[i].C_OBSERVACION = data.data.C_OBSERVACION;
                            $scope.padron[i].C_UBIGEO_ELECTOR = data.data.C_UBIGEO_ELECTOR;
                            $scope.padron[i].D_FECHA_REGISTRO = data.data.D_FECHA_REGISTRO;
                            $scope.padron[i].observacion = data.data.observacion; 
                            $scope.padron[i].N_ESTADO = data.data.N_ESTADO; 
                            
                            for(var x = 0; x < $scope.padron.length; x++){
                                if($scope.padron[i].C_DOCUMENTO_IDENTIDAD === $scope.padron[x].C_DOCUMENTO_IDENTIDAD && $scope.padron[i].N_ORDEN_REGISTRO != $scope.padron[x].N_ORDEN_REGISTRO) {              
                                    $scope.padron[i].N_ESTADO = $scope.constantPadron.estado.rechazado.id; 
                                }
                            }                             
                            
                            
                            break;
                        }
                    }                    
                    $mdDialog.cancel();
                    $scope.currentElector = {};
                }                
            });                       
    };
    
    $scope.close = function () {
        $mdDialog.cancel();
    };              
    
    $scope.getUbigeos(1);

}]);