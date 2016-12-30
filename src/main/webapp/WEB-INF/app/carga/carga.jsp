<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<t:master title="Carga">

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/controller/cargaController.js"></script>

    <h1><i class="fa fa-users"></i>Selecciones Lista</h1>
    <div class="row" ng-controller="cargaCtrl">

        <div class="form-group col-xs-6 ">
            <label for="ubigeo">Ubigeo</label>
            <input ng-model="busqueda.ubigeo" type="text" class="form-control" id="ubigeo" placeholder="Ubigeo">
        </div>
        <div class="form-group col-xs-6">
            <label for="local">Local de Votacion</label>
            <input ng-model="busqueda.local" type="text" class="form-control" id="local" placeholder="Local de Votacion">
        </div>
        <div class="form-group col-xs-12 ">
        <button type="button" class="btn btn-primary" ng-click="search()">Buscar</button>
        </div>
        <div class="form-group col-xs-12">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Nombre Local</th>
                    <th>Ubigeo</th>
                    <th>Departamento</th>
                    <th>Provincia</th>
                    <th>Distrito</th>
                </tr>
            </thead>
            <tbody>                                       
                <c:forEach items="${locales}" var="local" >
                    <tr>
                        <td>${local.nombre}</td>
                        <td>${local.ubigeo}</td>
                        <td>${local.departamento}</td>
                        <td>${local.provincia}</td>
                        <td>${local.distrito}</td>  
                    </tr>
                </c:forEach> 
            </tbody>
        </table>
        </div>    
        <div class="form-group col-xs-12">
        <button class="btn btn-primary"><i class="fa fa-chevron-left"></i>Anteriores</button>
        <button class="btn btn-primary">Siguientes<i class="fa fa-chevron-right"></i></button>
        </div>
    </div> 

</t:master>