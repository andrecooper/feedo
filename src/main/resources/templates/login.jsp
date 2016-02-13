<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page session="true" %>
<html>
<head>
    <title>Login Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
</head>

<body>

<div class="panel panel-default row login-box ">
    <h4>Authorization box:</h4>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>

    <div class="panel-body">
        <form name='loginForm' class="input-group"
              action="<c:url value='/login'/>" method='POST'>
            <div class="row">
                <input type='text' class="form-control col-md-12" placeholder="username" name='username'>
            </div>
            <div class="row">
                <input type='password' class="form-control col-md-12" placeholder="password" name='password'/>
            </div>
            <br>

            <div class="row">
                <button type="submit" class="btn btn-default col-md-5"/>
                login</button>
                <div class="col-md-3 col-md-offset-3" style="padding-top: 12px">
                    <a href="#myModal" data-toggle="modal" data-target="#myModal">register</a>
                </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        </form>
    </div>
</div>

<div id="myModal" class="modal fade" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content login-box">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Registration form</h4>
            </div>

            <div class="modal-body">
                <form:form method="post" action="api/user" modelAttribute="user">
                    <table>
                        <tr>
                            <td><form:input class="form-control" path="username"/></td>
                        </tr>
                        <tr>
                            <td><form:password class="form-control" path="password"/></td>
                        </tr>
                        <tr>
                            <td><br>
                                <input type="submit" class="btn btn-default" value="Register me"/></td>
                        </tr>
                    </table>
                </form:form>
            </div>
        </div>
    </div>
</div>
</div>



</body>
</html>