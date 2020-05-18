package com.project.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Define datasource/connection pool for Resource Injection:
	// name(jdbc/web_student_tracker) must match through context config file.
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1: set up printwriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		// 2: get connection to the database:
		java.sql.Connection myConn = null;
		java.sql.Statement mySmt = null;
		ResultSet myRs = null;
		
		try{
			myConn = dataSource.getConnection();
		
			// 3: Create SQL statement
			String sql = "select * from student";
			mySmt = myConn.createStatement();
			
			// 4: Execute SQL statement
			myRs = mySmt.executeQuery(sql);
			
			// 5. Process result set
			while(myRs.next()){
				String email = myRs.getString("email");
				out.println(email);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
