package com.project.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	// method having all initialization work:
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		// create our studentDbUtil and pass it in conn pool(dataSorce)
		try{
			studentDbUtil = new StudentDbUtil(dataSource);
		}
		catch(Exception e){
			throw new ServletException();
		}
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try{
			// read the command parameter from add-student.jsp:
			String theCommand = request.getParameter("command");
			
			// if command is missing ,then list out student as default:
			if (theCommand == null){
				theCommand = "LIST";
			}
			
			// route to the appropriate method:
			switch (theCommand){
				
			case "LIST" :
				// list student in MVC architecture 
				listStudent(request,response);
				break;
				
			case "ADD" :
				addStudent(request,response);
				break;
				
			case "LOAD":
				loadStudent(request,response);
				break;
				
			case "UPDATE":
				updateStudent(request,response);
				break;
				
			case "DELETE":
				deleteStudent(request,response);
				break;
				
			default:
				listStudent(request,response);
			}
			
		}
		catch (Exception e){
			throw new ServletException(e);
		}
	}



	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student id from form data:
		String theStudentId = request.getParameter("studentId");
		
		// delete student from database:
		studentDbUtil.deleteStudent(theStudentId);
		
		// send user back to list student
		listStudent(request,response);
	}



	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// get updated student data from html form:
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// create new student object:
		Student theStudent = new Student(id, firstName, lastName, email);
		
		// update data on database:
		studentDbUtil.updateStudent(theStudent);
		
		// send user to list:
		listStudent(request, response);
	}



	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// read student id:
		String theStudentId = request.getParameter("studentId");
		
		// get student from database:
		Student theStudent = studentDbUtil.getStudent(theStudentId);
		
		// place student in request attribute:
		request.setAttribute("THE_STUDENT", theStudent);
		
		// send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}



	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// read student data:
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// create a new student with that data:
		Student theStudent = new Student(firstName,lastName,email);
		
		// add student to the list:
		studentDbUtil.addStudent(theStudent);
		
		// send back to main page:
		listStudent(request,response);
	}



	private void listStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			// get student from db Util
			List<Student> students = studentDbUtil.getStudents();
		
			// add student to the request
			request.setAttribute("STUDENTS_LIST", students);
	
			// send to JSP page (view)
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("/list-students.jsp");
			dispatcher.forward(request, response);
	}

}
