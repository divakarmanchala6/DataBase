package databaseOps;

import java.sql.*;

public class CheckDataTable {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection cnn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDB", "root", "password");
			
			int usersCount = 0;
			
			String rowsCount = "SELECT * FROM employees";
			
			PreparedStatement prpt = cnn.prepareStatement(rowsCount);
			
			ResultSet results = prpt.executeQuery();
			
			while (results.next()) {
				usersCount += 1;
			}
			
			if (usersCount == 0) {
				System.out.println("Users table is empty");
			} else {
				System.out.println("Totals users in the table: " + usersCount);
			}
			
			
			
			
			cnn.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

}
