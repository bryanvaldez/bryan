app.controller('loginCtrl', function ($scope, $timeout) {
    $scope.user = "";
    $scope.password = "";
    $scope.focusUser = false;
    $scope.showHint = true;
    $scope.filterDni = "99999999";
    
    $timeout(function () {
        $scope.focusUser = true;
    }, 100);

    $scope.virtualKey = function (ev) {
        $scope.focusUser = false;
        if ($scope.password.length <= 20) {
            $scope.password = $scope.password + ev;
        }
        $scope.showHint = true;
    };
    
    $scope.backspace = function () {
        $scope.password = "";// $scope.password.substr(0, $scope.password.length - 1);
        $scope.showHint = true;
    };


});

app.controller('claveCtrl', function ($scope, $timeout) {
    $scope.passwordA = "";
    $scope.passwordB = "";
    $scope.focusUser = false;
    $scope.showHint = true;
    $scope.filterDni = "99999999";
    $scope.showMessage = true;
    
    $scope.A = true;
    $scope.B = true;

    

    $timeout(function () {
        $scope.focusClave = true;
    }, 100);


    $scope.focusA = function(){
        $scope.A = true;
        $scope.B = false;
    };
    
    $scope.focusB = function(){
        $scope.A = false;
        $scope.B = true;
    };
    
    $scope.virtualKeyAB = function (ev) {
        $scope.focusClave = false;
        if($scope.A){
            if($scope.passwordA.length <= 20){
                $scope.passwordA = $scope.passwordA + ev;
                $scope.showHint = true;
                $scope.showMessage = false;
            }
        }
        if($scope.B){
            if($scope.passwordB.length <= 20){
                $scope.passwordB = $scope.passwordB + ev;
                $scope.showHint = true;
                $scope.showMessage = false;
            }
        }

    }; 
    
    $scope.backspaceAB = function () {
        if($scope.A){
            $scope.passwordA = "";
            $scope.showHint = true;
        }
        if($scope.B){
            $scope.passwordB = "";
            $scope.showHint = true;
        }        
    };    

});