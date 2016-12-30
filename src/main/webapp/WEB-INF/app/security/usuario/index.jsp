<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:master title="Usuarios">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/program/security/usuario.css" media="screen,projection"/>	    
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/controller/userController.js"></script>

    <div ng-controller="userCtrl">
        <div ng-model="session" ng-init="session =${USUARIO_AUTENTICADO.id}"></div>
        <div style="padding: 30px 50px;" class="users-section" ng-cloak>
            <div class="section-head animated fadeIn" >
                <md-button href="${pageContext.request.contextPath}/main" class="md-icon-button btn-back" >
                    <md-icon class="material-icons">arrow_back</md-icon>
                </md-button>
                <h5 class="title-section">Mantenimiento de Usuarios: ([[ (users | filter:searchQuery).length ]])</h5>

                <div class="input-group" >
                    <input type="text" ng-model="query"  ng-enter="search()" placeholder="Busque por DNI, Nombre o Apellido..."  ><button ng-click="search()"><md-icon class="material-icons">search</md-icon></button>
                </div> 

                <md-button ng-click="add()" class="md-fab btn-new animated bounceInRight" >
                    <md-icon class="material-icons">add</md-icon>
                </md-button>
            </div>
            <div class="line" style="margin-right:74px;"></div>
            <table class="table-default">
                <thead>
                    <tr>
                        <th style="width: 3%;">N°</th>
                        <th style="width: 10%;" class="space">DNI</th>
                        <th style="width: 15%;" class="space">Perfil</th> 
                        <th style="width: 20%;" class="space">Apellidos</th>                            
                        <th style="width: 17%;">Nombres</th>                            
                        <th style="width: 15%;" class="space">Periodo de Activación</th>
                        <th style="width: 5%;" class="space"></th>
                        <th style="width: 5%;" class="space"></th>
                    </tr>
                </thead>
            </table>
            <div ng-show="showTable" >
                <div style="overflow-y: auto; height: 450px;">
                    <table class="table-default">         
                        <tbody>
                            <tr ng-repeat="user in users| filter:searchQuery | orderBy:'-id'">
                                <td style="width: 3%;" class="rownum"><span ng-bind="$index + 1"></span></td>
                                <td style="width: 10%;" class="space"><span ng-bind="user.usuario | uppercase"></span></td>
                                <td style="width: 15%;" class="space"><span ng-bind="user.perfil.nombre | uppercase"></span></td>                        
                                <td style="width: 20%;" class="space"><span ng-bind="user.apellidoPaterno + ' ' + user.apellidoMaterno | uppercase"></span></td>                            
                                <td style="width: 17%;"><span ng-bind="user.nombre | uppercase"></span></td>
                                <td style="width: 15%;"><span ng-bind="user.fechaInicial == null ? 'INDEFINIDO' : (user.fechaInicial | date:'MM/dd/yyyy')+' - '+(user.fechaFinal | date:'MM/dd/yyyy')"></span></td>
                                <!--                            <td class="space"><span ng-bind="user.estado == 2 ? 'Deshabilitado' : 'Habilitado'"></span></td>-->
                                <td style="width: 5%;" class="space action-col" style="width:20px;"><md-button ng-disabled="session == user.id" ng-click="edit(user.id)" class="md-icon-button" ><i class="fa fa-edit" style="font-size:17px;">[[]]</i></md-button></td>
                        <td style="width: 5%;" class="space action-col"><i ng-class="{positivo: user.estado == 0, negativo: user.estado == 2, pendiente: user.estado == 1}" class="fa fa-circle" style="font-size:18px;"></i></td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="loading-message" ng-hide="(users | filter:searchQuery).length"><h3>No se encontraron datos.</h3></div>                             
                </div>                                 
            </div> 
            <div class="loading" ng-show="showLoading" layout="row" layout-sm="column" layout-align="center center">
                <md-progress-circular md-mode="indeterminate"></md-progress-circular>
            </div>              
        </div>
    </t:master>

