'use strict';
angular.module('app').factory('CargaService', ['$http', '$q', function ($http, $q) {

    var REST_SERVICE_URI = C_SERVER + '/carga';
    
    var factory = {
        getDepartamentos : getDepartamentos,
        getProvincias : getProvincias,
        getDistritos : getDistritos,
        getCCPP : getCCPP,
        getExpedientes: getExpedientes,
        validarFormato: validarFormato,
        getObservaciones: getObservaciones,
        getObservaciones2: getObservaciones2,
        generarFichaV:generarFichaV,
        guardar: guardar,
    };
    
    return factory;
    
    function getDepartamentos(){
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+"/listar-departamentos")
            .then(
                function (response){
                    deferred.resolve(response.data);
                },
                function (errResponse){
                    console.error('[SERVICE].Error al recuperar datos.')
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
    
    function getProvincias(ubigeo){
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+"/listar-provincias/"+ubigeo)
            .then(
                function (response){
                    deferred.resolve(response.data);
                },
                function (errResponse){
                    console.error('[SERVICE].Error al recuperar datos.')
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getDistritos(ubigeo){
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+"/listar-distritos/"+ubigeo)
            .then(
                function (response){
                    deferred.resolve(response.data);
                },
                function (errResponse){
                    console.error('[SERVICE].Error al recuperar datos.')
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    } 
    
    function getCCPP(ubigeo){
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+"/listar-ccpp/"+ubigeo)
            .then(
                function (response){
                    deferred.resolve(response.data);
                },
                function (errResponse){
                    console.error('[SERVICE].Error al recuperar datos.')
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
    
    function getExpedientes(ambito){
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+"/listar-expedientes/"+ambito)
            .then(
                function (response){
                    deferred.resolve(response.data);
                },
                function (errResponse){
                    console.error('[SERVICE].Error al recuperar datos.')
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
    
    function validarFormato(data){
        var deferred = $q.defer();
        $http({
            url:REST_SERVICE_URI+'/validar-formato',
            method: 'POST',
            data: data,     
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined} 
        }).then(
            function (response){
                deferred.resolve(response.data);
            },
            function (errResponse){
                console.error('[SERVICE].Error.')
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function getObservaciones(){
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+"/observaciones/")
            .then(
                function (response){
                    deferred.resolve(response.data);
                },
                function (errResponse){
                    console.error('[SERVICE].Error al recuperar datos.')
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
    
    function getObservaciones2(data){
        var deferred = $q.defer();   
        $http({
            url:REST_SERVICE_URI+'/observaciones/',
            method: 'POST',
            data: data,     
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined} 
        }).then(
            function (response){
                deferred.resolve(response.data);
            },
            function (errResponse){
                console.error('[SERVICE].Error.')
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function generarFichaV(data){
        var deferred = $q.defer();   
        $http({
            url:REST_SERVICE_URI+'/documento/',
            method: 'POST',
            data: data,     
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined} 
        }).then(
            function (response){
                deferred.resolve(response.data);
            },
            function (errResponse){
                console.error('[SERVICE].Error.')
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }     
    
    function guardar(data){
        var deferred = $q.defer();
        $http({
            url:REST_SERVICE_URI+'/guardar',
            method: 'POST',
            data: data,     
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined} 
        }).then(
            function (response){
                deferred.resolve(response.data);
            },
            function (errResponse){
                console.error('[SERVICE].Error.')
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);