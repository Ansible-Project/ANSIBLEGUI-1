<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DashBoard</title>
</head>
<body>
<c:if test="${empty username}">
   <div id="success" class="success">
     <c:redirect url = "login"/>
   </div>
</c:if>

	<table align="center">
		<tr>
			<td><a href="Dashboard">Home</a></td>
			<td><a href="addhost">ADD HOST</a></td>
			<td><a href="addgroup">ADD GROUP</a></td>
			<td><a href="rungroup">RUN PLAYBOOK GROUP</a></td>
			<td><a href="singleserver">RUN PLAYBOOK SINGLE SERVER</a></td>
			<td><a href="messages">BUILD</a></td>
			<td><a href="logout">Logout</a></td>

		</tr>

	</table>
	
</body>
</html>