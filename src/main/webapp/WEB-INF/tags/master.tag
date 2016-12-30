<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@tag description="General Layout" pageEncoding="UTF-8"%>
<%@attribute name="title" required="true"%>
<%@attribute name="js" fragment="true"%>

<!DOCTYPE html>
<html ng-app="app">
    <head>
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/library/material-icon.css" type="text/css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/node_modules/angular-material/angular-material.min.css" type="text/css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/node_modules/animate.css/animate.min.css" type="text/css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/node_modules/font-awesome/css/font-awesome.min.css" type="text/css" media="screen,projection"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/node_modules/lf-ng-md-file-input/dist/lf-ng-md-file-input.css" />        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/program/main.css" type="text/css" />
        
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/angular/angular.min.js"></script>      
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/angular-aria/angular-aria.min.js"></script>  
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/angular-animate/angular-animate.min.js"></script> 
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/angular-material/angular-material.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/angular-ui-utils/modules/mask/mask.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/lf-ng-md-file-input/dist/lf-ng-md-file-input.js"></script>        
<!--        <script type="text/javascript" src="${pageContext.request.contextPath}/node_modules/angular-filter/dist/angular-filter.min.js"></script> -->
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/app.js"></script>                 
    </head>
    <body style="background-color: #fff;">
        <sec:authorize access="isAuthenticated()">
            <jsp:include page="/WEB-INF/tags/header.jsp"/>
        </sec:authorize>        
        <div class="container-fluid">
            <div class="row"> 
                <sec:authorize access="isAuthenticated()">
                    <jsp:doBody/>
                </sec:authorize>                        
                <sec:authorize access="isAnonymous()">
                    <jsp:doBody/>
                </sec:authorize>
            </div>
        </div>
    </body>
</html>
