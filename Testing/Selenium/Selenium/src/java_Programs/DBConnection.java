package java_Programs;

import java.sql.*;

public class DBConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBName", "userName", "password");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from emp");
			while (rs.next())
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
			
			//For executing Update query
			stmt.executeUpdate("update Table1 Set emp_name = Maruthi where emp_id=10");
			//For executing Delete query
			stmt.executeUpdate("DELETE FROM Registration WHERE id = 101");
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
