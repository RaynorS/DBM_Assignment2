package connection;
import java.sql.*;
import java.io.*;
public class JDBCconnection {

	/**
	 * @param args
	 */
	static
	{
	try
		{
			/* Type 4 Driver */
			Class.forName("oracle.jdbc.OracleDriver");
		}
		catch (ClassNotFoundException e)
		{
			System.err.println("Could not load Oracle 10g driver \n");
			System.err.println(e.getMessage());
			System.exit(1);
		}
	}
	public static void main(String[] args) {
		String uname = "student";
		String psswrd = "student";
		Integer courseNumber = 1;
		String query = null;
		String courseName = "";
		Boolean courseExists = false;
		/* Location of the database */
		String url = "jdbc:oracle:thin:@134.58.95.246:1521:XE";
		
		
		
		while (courseNumber != 0){
			try
			{
				/* Connect to Oracle database */
				Connection conn = DriverManager.getConnection(url, uname, psswrd);
				/* Create statement */
				Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE
				);
				ResultSet rs = null;
				/* Reading course number */
				while (courseExists == false)
				try
				{
					BufferedReader br1 = new BufferedReader (new InputStreamReader(System.in));
					System.out.print("Please enter the course number: ");
					courseNumber = Integer.parseInt(br1.readLine());
					/* Execute the query */
					if (courseNumber == 0) {
						
						break;
					}
					query = "SELECT CourseName, Instructor FROM Assignment_DB.Course WHERE CourseID = " + courseNumber;
					rs = stmt.executeQuery(query);
					/* Output */
					System.out.println();
					if (rs.next())
					{
						rs.first();
						courseName = rs.getString(1);
						String title = rs.getString(1) + " taught by " + rs.getString(2);
						System.out.println(title);
						String repeated = new String(new char[title.length()]).replace("\0", "*");
						System.out.println(repeated);
						System.out.println();
						courseExists = true;
					} else {
						System.out.println("Course number does not exist. Please try again.");
						courseExists = false;						
					}
				}
				
				catch (IOException e)
				{
					System.out.print("Failed to get course number!");
					System.out.println(":" + e.getMessage());
					System.exit(1);
				}
				if (courseNumber == 0) {
					break;
				}
				courseExists = false;
				
				query = "SELECT S.FName, S.Lname, nvl(to_char(E.Grade),'***') FROM Assignment_DB.Enrolled E LEFT JOIN Assignment_DB.Student S ON S.StudentID = E.StudentID WHERE CourseID = " + courseNumber;
				rs.close();
				rs = stmt.executeQuery(query);
				System.out.println("First name // Last name // Grade");
				System.out.println("--------------------------------");
				while (rs.next())
				{
					System.out.println(rs.getString(1) + " // " + rs.getString(2) + " // " + rs.getString(3));
				}
				query = "SELECT avg(E.Grade), E.CourseID FROM Assignment_DB.Enrolled E WHERE E.CourseID = " + courseNumber  + " GROUP BY E.CourseID" ;
				rs.close();
				rs = stmt.executeQuery(query);
				rs.first();
				System.out.println();
				System.out.println("The average grade of " + courseName + " is " + rs.getString(1) +".");
				query = "SELECT count(AssignmentID), CourseID FROM Assignment_DB.Assignment WHERE CourseID = " + courseNumber  + " GROUP BY CourseID" ;
				rs.close();
				rs = stmt.executeQuery(query);
				if (rs.next())
				{
					System.out.println(courseName + " has " + rs.getString(1) + " assignment(s).");
					
				}
				else {
					System.out.println(courseName + " has no assignment(s).");
				}
				System.out.println();
				rs.close();
				stmt.close();
				conn.close();
			}
			catch (SQLException e)
			{
				System.out.println("SQL Exception: ");
				System.err.println(e.getMessage());
			}
		}
		
		System.out.println("End of Session");
	}
	

}
