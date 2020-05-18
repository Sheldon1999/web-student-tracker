package com.project.web.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {

	private DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Student> getStudents() throws Exception{
		
		List<Student> students = new ArrayList<>();
		
		// to get a connection to the database
		// we need three variables:
		// to get connected to database:
		Connection myCon = null;
		// to specify query:
		Statement myStmt = null;
		// to contain results:
		ResultSet myRs = null;
		
		try{
			// get a connection:
			myCon = dataSource.getConnection();
			
			// create sql statement:
			String sql = "select * from student order by last_name";
			myStmt = myCon.createStatement();
			
			// execute query:
			myRs = myStmt.executeQuery(sql);
			
			// process result:
			while(myRs.next()){
				
				// retrieve data from result set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				// create new student object:
				Student tempStudent = new Student(id,firstName,lastName,email);
				
				// add it to list of students
				students.add(tempStudent);
			}
			
			return students;
		}
		finally{
			// close JDBC:
			close(myCon, myStmt, myRs);
		}
	}

	private void close(Connection myCon, Statement myStmt, ResultSet myRs) {
		
		try{
			// here close is not closing these.
			// but just put those back in connection pool
			// so that any other user can use them
			if (myRs != null){
				myRs.close();
			}
			
			if (myStmt != null){
				myStmt.close();
			}
			
			if (myCon != null){
				myCon.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
