<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Spring MVC Form Handling</title>
    <link href="<c:url value="/asserts/css/main.css" />" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<h2 style="margin-left:390px">Login</h2>
<h2 style="margin-left:390px">${message}</h2>
<form:form method="POST" action="request" modelAttribute="loginForm">
  
   <table style="margin-left:390px">
   <tr>
        <td width="20%"><form:label path="username">Username</form:label></td>
        <td width="20%"><form:input path="username" class="form-control" /></td>
        <td width="40%"><form:errors path="username" cssClass="error" /></td>
    </tr>
    <tr>
        <td width="20%"><form:label path="password">Password</form:label></td>
        <td width="20%"><form:password  path="password" class="form-control"/></td>
        <td width="40%"><form:errors path="password" cssClass="error" /></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" class="btn btn-default" value="Submit"/>
        </td>
    </tr>
</table>  
</form:form>
</body>
</html>