app.controller('mainCtrl', ['$scope', '$http', function ($scope, $http) {
    $scope.updateExp = function (id) {        
        if (id === 5) {            
            $http({
                url: C_SERVER + '/mantenimiento/updateExpediente',
                method: 'GET'
            }).success(function (status) {                                
            });
        }
    };
}]);