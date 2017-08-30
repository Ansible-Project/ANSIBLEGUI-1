<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<title>Storing IPADDRESS INFORMATION</title>
<link href="<c:url value="/asserts/css/main.css" />" rel="stylesheet">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
</head>
<body>

<c:if test="${ empty username}">
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
	<h2>ADDING HOSTNAMES</h2>
	<h2>${message}</h2>
	<form:form method="POST" action="host" modelAttribute="hostForm">

		<table>
			<tr>

				<td><form:label path="groupname">Group Name</form:label></td>
				<td><form:select path="groupname" id="groupname">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${grouplist}" />
					</form:select></td>
				<td><form:errors path="groupname" cssClass="error" /></td>
			</tr>
			<tr>
				<td><form:label path="hostname">Host Name/Ipaddress</form:label></td>
				<td><form:input path="hostname" /></td>
				<td><form:errors path="hostname" cssClass="error" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>