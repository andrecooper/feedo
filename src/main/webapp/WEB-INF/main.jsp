<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.GrantedAuthority" %>
<%@ page import="java.util.Collection" %>
<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">

<head lang="en">
    <meta charset="UTF-8"/>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.15/angular-cookies.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-animate.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-aria.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-messages.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.0.0/angular-material.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-google-chart/0.1.0/ng-google-chart.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.0.0/angular-material.min.css"/>
    <title>Currency feed</title>
</head>
<body ng-app="feedApp" ng-controller="feedController">

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <a href="/feed/" class="navbar-brand">Quotes Service</a>
            <ul class="nav navbar-nav navbar">
                <li class="active"><a href="/feed/">Dashboard</a></li>
                <li><a href="/audit/">Admin</a></li>
            </ul>


            <ul class="nav navbar-nav navbar-right">
                <li>
                    <%Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
                        for (GrantedAuthority authority : authorities) {
                                if (authority.getAuthority().equals("ROLE_DEMO")){
                                    pageContext.getOut().write("<span style=\"color:firebrick\">DEMO USER IS UNABLE TO FILTER DATA BY DATE. DISPLAYED DATA FOR LAST 5 DAYS<span>");
                                }
                        }
                    %>
                </li>
                <li><a href="#">
                    <% String userName = auth.getName();
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

<div class="container" id="quoteTable" style="max-height: 50% ; overflow-y: scroll; overflow-x: hidden; ">
    <div class="container task-container">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Quotes dashboard</h3>
            </div>
            <div class="panel-body">
                filter by date:
                <md-datepicker ng-model="startDate" md-placeholder="Enter date"></md-datepicker>
                <md-datepicker ng-model="endDate" md-placeholder="Enter date"></md-datepicker>
                <div class="row">
                    <div class="col-xs-6">
                        <b>Quotes from National Bank of Ukraine</b>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Date</th>
                                <th>Ask</th>
                                <th>Bid</th>
                                <th>Average</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="quote in nbuQuotes">
                                <td> {{ quote.date | date : "dd-MM-yyyy"}}</td>
                                <td> {{quote.ask }}</td>
                                <td> {{quote.bid }}</td>
                                <td> {{quote.average }}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-xs-6">
                        <b>Quotes from Stock Market</b>
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Date</th>
                                <th>Ask</th>
                                <th>Bid</th>
                                <th>Average</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="quote in marketQuotes">
                                <td> {{ quote.date | date : "dd-MM-yyyy" }}</td>
                                <td> {{quote.ask }}</td>
                                <td> {{quote.bid }}</td>
                                <td> {{quote.average }}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container" id="quoteChart">
    <div class="container task-container">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Difference between Market quotes and NBU</h3>
            </div>
            <div class="panel-body">
                <div google-chart chart="chartObject"></div>
            </div>
        </div>
    </div>
</div>

<script>
    angular
            .module("feedApp", ['ngMaterial', 'ngMessages', 'googlechart']).controller("feedController", function ($scope, $http, $timeout) {
                console.log('[Feed controller Init]');

                $scope.startDate = new Date();
                $scope.endDate = new Date();

                $scope.$watchGroup(['startDate', 'endDate'], function (newValue, oldValue) {
                    console.log("DATE UPDATE", $scope.startDate, $scope.endDate);
                    updateQuotesDate($scope.startDate, $scope.endDate);
                });

                $scope.onlyWeekendsPredicate = function (date) {
                    var day = date.getDay();
                    return day === 0 || day === 6;
                };

                function getNbuQuotes(filterString) {
                    console.log("get quotes from NBU" + filterString + "'");
                    var url = '/feed/nbu' + filterString;
                    var response = $http.get(url);
                    response.success(function (data, status, header, config) {
                        console.log("received quotes:", data);
                        $scope.nbuQuotes = data;
                    });
                    response.error(function (data, status, header, config) {
                        console.log('Failed to request NBU quotes', status);
                    })
                };

                function getMarketQuotes(filterString) {
                    console.log("get quotes from Market" + filterString + "'");
                    var url = '/feed/market' + filterString;
                    var response = $http.get(url);
                    response.success(function (data, status, header, config) {
                        console.log("received quotes:", data);
                        $scope.marketQuotes = data;
                    });
                    response.error(function (data, status, header, config) {
                        console.log('Failed to request Market quotes', status);
                    })
                };

                function getQuotesDiff(filterString) {
                    console.log("get quotes from Market" + filterString + "'");
                    var url = '/feed/diff' + filterString;
                    var response = $http.get(url);
                    response.success(function (data, status, header, config) {
                        console.log("received quotes:", data);
                        $scope.chartData = [];
                        angular.forEach(data, function (item) {
                            cArr = [];
                            angular.forEach(item, function (val, key) {
                                if (key === 'date') {
                                    val = new Date(val);
                                }
                                cArr.push({v: val});
                            });
                            $scope.chartData.push({c: cArr})

                        });

                        $scope.chartObject = {
                            "type": "AreaChart",
                            "displayed": false,
                            "data": {
                                "cols": [
                                    {
                                        "id": "date",
                                        "label": "Date",
                                        "type": "date",
                                        "p": {}
                                    },
                                    {
                                        "id": "ask",
                                        "label": "ask",
                                        "type": "number",
                                        "p": {}
                                    },
                                    {
                                        "id": "bid",
                                        "label": "bid",
                                        "type": "number",
                                        "p": {}
                                    },
                                    {
                                        "id": "average",
                                        "label": "average",
                                        "type": "number",
                                        "p": {}
                                    }
                                ],
                                "rows": $scope.chartData
                            },
                            "options": {
                                "isStacked": "true",
                                "fill": 20,
                                "displayExactValues": true,
                                "vAxis": {
                                    "title": "Sales unit",
                                    "gridlines": {
                                        "count": 10
                                    }
                                },
                                "hAxis": {
                                    "title": "Date"
                                }
                            },
                            "formatters": {}
                        };
                        console.log($scope.chartObject)
                        console.log("ROW ARR:", $scope.chartData);


                    });
                    response.error(function (data, status, header, config) {
                        console.log('Failed to request quotes diff', status);
                    })
                };

                function updateQuotesDate(startDate, endDate) {
                    var filterByDate = '?startDate=' + startDate.toLocaleDateString() + '&endDate=' + endDate.toLocaleDateString();
                    getMarketQuotes(filterByDate);
                    getNbuQuotes(filterByDate);
                    getQuotesDiff(filterByDate);
                }

                updateQuotesDate($scope.startDate, $scope.endDate);
            })

</script>


</body>


</html>