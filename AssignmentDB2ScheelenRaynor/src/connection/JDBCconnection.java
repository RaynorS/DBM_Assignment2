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
		String uname = null;
		String psswrd = null;
		/* Location of the database */
		String url = "jdbc:oracle:thin:@134.58.95.246:1521:XE";
		/* Sample query */
		String query = "SELECT FName, LName FROM Assignment_DB.Student";
		/* Reading log-in data (username and password) */
		try
		{
			BufferedReader br1 = new BufferedReader (new InputStreamReader(System.in));
			System.out.print("Enter your username on OracleXE: ");
			uname = br1.readLine();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter your password on OracleXE: ");
			psswrd = br2.readLine();
		}
		catch (IOException e)
		{
			System.out.print("Failed to get uname/passwd");
			System.out.println(":" + e.getMessage());
			System.exit(1);
		}
		try
		{
			/* Connect to Oracle database */
			Connection conn = DriverManager.getConnection(url, uname, psswrd);
			System.out.println("Connection established...");
			System.out.println();
			/* Create statement */
			Statement stmt = conn.createStatement();
			/* Execute the query */
			ResultSet rs = stmt.executeQuery(query);
			/* Output */
			System.out.println(query);
			System.out.println("First name // Last name");
			System.out.println("------------------------------");
			while (rs.next())
			{
				System.out.print(rs.getString(1));
				System.out.print((" // "));
				System.out.println(rs.getString(2));
			}
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
	
	

}
