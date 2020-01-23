package tatoc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TatocJdbc {

	public static void createConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // register JDBC Driver
			Connection con = DriverManager.getConnection("jdbc:mysql://10.0.1.86/tatoc", "tatocuser", "tatoc01"); // open Connection
			Statement stmt = con.createStatement(); // execute statement
			ResultSet rs = stmt.executeQuery("select * from credentials");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));	
			}
			rs = stmt.executeQuery("select * from identity");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "  " + rs.getString(2));
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
