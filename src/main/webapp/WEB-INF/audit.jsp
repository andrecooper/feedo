<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<head lang="en">
    <meta charset="UTF-8"/>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"/>
    <title>Currency feed</title>
</head>
<body ng-app="auditApp" ng-controller="auditController">

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <a href="/feed/" class="navbar-brand">Quotes Service</a>
            <ul class="nav navbar-nav navbar">
                <li class="active"><a href="/feed/">Dashboard</a></li>
                <li><a href="/audit/">Admin</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">
                    <% Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                        String userName = auth.getName();
                        pageContext.getOut().write("I'm " + userName);
                    %>
                    <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                </a></li>
                <li><a href="/logout"><span class="glyphicon glyphicon-log-out" title="logout"
                                            aria-hidden="true"></span></a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container" id="quoteTable"  style="max-height: 80% ; overflow-y: scroll; overflow-x: hidden">
    <div class="container task-container">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Audit dashboard</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-xs-12">
                        <p/>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Date</th>
                                <th>User</th>
                                <th>RequestMethod</th>
                                <th>Arguments</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="audit in auditList">
                                <td> {{ audit.date | date : "dd-MM-yyyy HH:mm:ss" }}</td>
                                <td> {{audit.user }}</td>
                                <td> {{audit.requestMethod }}</td>
                                <td> {{audit.params }}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    angular
            .module("auditApp", []).controller("auditController", function ($scope, $http, $timeout) {
                console.log('[Audit controller Init]');
                function getAudit() {
                    var response = $http.get("/audit/get");
                    response.success(function (data, status, header, config) {
                        console.log("received audit entries:", data);
                        $scope.auditList = data;
                    });
                    response.error(function (data, status, header, config) {
                        console.log('Failed to audit', status);
                    })
                };

                getAudit();
            })

</script>
</body>
</html>