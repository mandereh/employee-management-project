package employee.crud.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	public static final String dbURL = "jdbc:mysql://localhost:3307/employee_db";
	public static final String dbUsername = "root";
	public static final String dbPassword = "4332242";
	public static Connection connection = getConnection();
	
	
	public static Connection getConnection() {
		System.out.println("getConnection");
		try {
			//Load mysql jdbc driver
			Class.forName("com.mysql.jdbc.Driver");
			//Open connection
			connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			if (connection != null) {
				System.out.println("Connected.");
				return connection;
			}else {
				System.out.println("Connection issue.");
				return null;
			}
		
		} catch (Exception e) {
			System.out.println("Exception in DB connection." + e.getMessage());
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(DBConnection.connection);
	}
}
