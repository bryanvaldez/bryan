'use strict';
angular.module('app').factory('SecurityService', ['$http', '$q', function ($http, $q) {

    var REST_SERVICE_URI = C_SERVER + '/seguridad/user';
    
    var factory = {
        allUsers : allUsers
    };
    
    return factory;
    
    function allUsers(){
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
                function (response){
                    deferred.resolve(response.data);
                },
                function (errResponse){
                    console.error('Error al recuperar usuarios.')
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}]);

