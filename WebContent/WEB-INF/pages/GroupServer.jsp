<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<title>Storing IPADDRESS INFORMATION</title>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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

	<nav class="navbar navbar-inverse" >
  <div class="container-fluid">
    <ul class="nav navbar-nav ">
     <li class="active"><a href="Dashboard">Home</a></li>
			<li><a href="addhost">ADD HOST</a></li>
			<li><a href="addgroup">ADD GROUP</a></li>
			<li><a href="rungroup">GROUP RUN</a></li>
			<li><a href="singleserver">SINGLE INSTANCE  RUN</a></li>
			<li><a href="messages">BUILD</a></li>
			<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Settings<span class="caret"></span></a>
        <ul class="dropdown-menu">
          <li><a href="#">Page 1-1</a></li>
          <li><a href="#">Page 1-2</a></li>
          <li><a href="#">Page 1-3</a></li>
        </ul>
      </li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
    </ul>
  </div>
</nav>
	

	<form:form method="POST" action="single"
		modelAttribute="singleServerForm">

		<table style="margin-left:390px">

			<tr>

				<td width="40%"><form:label path="groupname">Group Name</form:label></td>
				<td width="40%"><form:select path="groupname" class="form-control"  id="groupname">
						<form:option value="NONE" label="--- Select ---" />
						<form:options items="${grouplist}" />
					</form:select></td>
					<td><form:errors path="groupname" cssClass="error" /></td>
			</tr>
			<tr>
				<td width="40%"><form:label path="rolename">ROLE NAME</form:label></td>
				<td width="40%"><form:select path="rolename" class="form-control"  id="rolename">
						<form:option value="NONE"  label="--- Select ---" />
						<form:options items="${roleslist}" />
					</form:select>
					<td><form:errors path="rolename" cssClass="error" /></td>
						<h2>${message}</h2></td>
			</tr>

			<tr>
				<td width="40%" colspan="2"><input type="submit" class="btn btn-default" value="Submit" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>