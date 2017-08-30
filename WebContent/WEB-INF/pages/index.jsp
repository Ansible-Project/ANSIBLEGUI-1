<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/asserts/css/main.css" />" rel="stylesheet">
<title>V3Operations</title>
</head>
<body>
<h2>Welcome to V3OPSAUTOMATION</h2>
<table align="center">
            <tr>
                <td><a href="login">Login</a>
                </td>
                <td><a href="register">Register</a>
                </td>
                <td><a href="ajax">ajaxtest</a>
                </td>
            </tr>

        </table>
        <c:if test="${not empty lists}">

		<ul>
			<c:forEach var="listValue" items="${lists}">
				<li>${listValue.getUsername()}</li>
				<li>${listValue.getPassword()}</li>
			</c:forEach>
		</ul>

	</c:if>
        

</body>
</html>