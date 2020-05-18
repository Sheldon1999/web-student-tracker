<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

	<title>Student tracker</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">

</head>

<body>

	<div id = "wrapper">
		<div id="header">
			<h2>M.J.P. University</h2>
		</div>
	</div>

	<div id="container">
		<div id="content">
		
		<!-- putting a button to add a student -->
			<input type="button" value="Add Student" 
				onclick="window.location.href='add-student-form.jsp'; return false;"
				class="add-student-button"
			/>
			
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				
				<c:forEach var="temp" items="${STUDENTS_LIST}">
					<!-- set up a link for each element -->
					<c:url var="tempLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="studentId" value="${temp.id}" />
					</c:url>
					<tr>
						<td> ${temp.firstName} </td>
						<td> ${temp.lastName} </td>
						<td> ${temp.email} </td>
						<td> 
							<a href="${tempLink}">Update</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>