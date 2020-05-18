<%@ page import="java.util.*, com.project.web.jdbc.*" %>
<!DOCTYPE html>
<html>
<head>

	<title>Student tracker</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css">

</head>

<%
	// get the students from the request object (sent by servlet)
	List<Student> theStudents = (List<Student>) request.getAttribute("STUDENTS_LIST");

%>

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
				</tr>
				
				<% for (Student temp : theStudents) {%>
					<tr>
						<td> <%= temp.getFirstName() %></td>
						<td> <%= temp.getLastName() %></td>
						<td> <%= temp.getEmail() %></td>
					</tr>
				<% } %>
			</table>
		</div>
	</div>
</body>
</html>