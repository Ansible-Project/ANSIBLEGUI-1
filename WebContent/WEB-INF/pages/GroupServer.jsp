<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<title>Storing IPADDRESS INFORMATION</title>
<link href="<c:url value="/asserts/css/main.css" />" rel="stylesheet">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
	$(function() {

		$('#groupname').change(function() {
			populateSelect();
			//alert("FWEFW");
		});

	});

	function populateSelect() {
		groupname = $('#groupname').val();
		$.ajax({
			type : "POST",
			url : 'gethostlist?groupname=' + groupname,
			//data: {"groupname " : groupname },
			success : function(data) {
				var slctSubcat = $("#hostname"), option = "";
				slctSubcat.empty();

				for (var sb = 0; sb < data.length; sb++) {
					option = option
							+ "<option value='" + data[sb].hostname + "'>"
							+ data[sb].hostname + "</option>";
				}
				slctSubcat.append(option);

				// alert(data[0].hostname)
			},
			error : function(jqXHR, exception) {
				alert(jqXHR + "::::" + exception);
			}
		});
	}
</script>


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
	<h2>GROUP Execute</h2>
	<h2>${message}</h2>
	<form:form method="POST" action="single"
		modelAttribute="singleServerForm">

		<table>

			<tr>

				<td><form:label path="groupname">Group Name</form:label></td>
				<td><form:select path="groupname" id="groupname">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${grouplist}" />
					</form:select></td>
			</tr>
			<tr>
				<td><form:label path="rolename">ROLE NAME</form:label></td>
				<td><form:select path="rolename" id="rolename">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${roleslist}" />
					</form:select></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>