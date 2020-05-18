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
			// list student in MVC architecture 
			listStudent(request,response);
		}
		catch (Exception e){
			throw new ServletException();
		}
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
