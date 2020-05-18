package com.project.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

	public void addStudent(Student theStudent) throws Exception{
		
		Connection myCon = null;
		PreparedStatement myStmt = null;
		
		try{
			//getting connection:
			myCon = dataSource.getConnection();
			
			// create sql staement for insert
			String sql = "insert into student "
					+ "(first_name, last_name, email)"
					+ "values (?, ?, ?)";
			myStmt = myCon.prepareStatement(sql);
			
			// set parameter value for student:
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			
			// execute sql insert:
			myStmt.execute();
		}
		finally{
			// cleanup JDBC objects
			close(myCon, myStmt, null);
		}
	}

	public Student getStudent(String theStudentId) throws Exception {
		
		Student theStudent = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int studentId;
		
		try{
			// convert studentId int:
			studentId = Integer.parseInt(theStudentId);
			
			// get connection to database:
			myConn = dataSource.getConnection();
			
			// create sql to get selected statement:
			String sql = "select * from student where id=?";
			
			// create prepared statement:
			myStmt = myConn.prepareStatement(sql);
			
			// set parameters:
			myStmt.setInt(1, studentId);
			
			//execute statement:
			myRs = myStmt.executeQuery();
			
			if (myRs.next()){
				//retrieve data from result set row:
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				theStudent = new Student(studentId , firstName ,lastName ,email);
			}
			else{
				throw new Exception("Hmmm !! could not find student Id : " + studentId);
			}

			return theStudent;
		}
		finally{
			// clear JDBC objects:
			close(myConn, myStmt, myRs);
		}
	}

	public void updateStudent(Student theStudent) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try{	
			// setup connection to database:
			myConn = dataSource.getConnection();
			
			// make sql query:
			String sql = "update student "
					+ "set first_name=?, last_name=?, email=? "
					+ "where id=?";
			
			// create prepared statement:
			myStmt = myConn.prepareStatement(sql);
			
			// set parameters:
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getId());
			
			// execute query to update database:
			myStmt.execute();
		}
		finally {
			// close connection:
			close(myConn, myStmt, null);
		}
	}
	
}
