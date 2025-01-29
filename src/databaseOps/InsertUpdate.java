package databaseOps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;


public class InsertUpdate {

	public static void main(String[] args) {
		
		try {
			//Loading the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Establishing the connection
			Connection cnn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myDB", "root", "password");
			
			
			//Reading file data
			String filePath = "/Users/divakarmanchala/Documents/userslist.csv";
			BufferedReader buff = new BufferedReader(new FileReader(filePath));	
			
			String line;
			while ((line = buff.readLine()) != null) {
				String[] arr = line.split(",");
				int fileUserId = Integer.parseInt(arr[0]);
				String userQuery = "SELECT * FROM employees WHERE id = ?";
				PreparedStatement prpt = cnn.prepareStatement(userQuery);
				prpt.setInt(1, fileUserId);
				ResultSet results = prpt.executeQuery();
				boolean isUserFound = false;
				while (results.next()) {
					String updateQuery = "UPDATE employees SET first_name = ?, last_name = ?, age = ?, salary = ? WHERE id = ?";
					PreparedStatement updatePrpt = cnn.prepareStatement(updateQuery);
					updatePrpt.setString(1, arr[1]);
					updatePrpt.setString(2, arr[2]);
					updatePrpt.setInt(3, Integer.parseInt(arr[3]));
					updatePrpt.setDouble(4, Double.parseDouble(arr[4]));
					updatePrpt.setInt(5, fileUserId);
					updatePrpt.executeUpdate();
					System.out.println(arr[1] + " details updated");
					isUserFound = true;
					updatePrpt.close();
				}
				if (!isUserFound) {
					String insertQuery = "INSERT INTO employees VALUES(?, ?, ?, ?, ?)";
					PreparedStatement insertPrpt = cnn.prepareStatement(insertQuery);
					insertPrpt.setInt(1, Integer.parseInt(arr[0]));
					insertPrpt.setString(2, arr[1]);
					insertPrpt.setString(3, arr[2]);
					insertPrpt.setInt(4, Integer.parseInt(arr[3]));
					insertPrpt.setInt(5, Integer.parseInt(arr[4]));
					insertPrpt.executeUpdate();
					System.out.println(arr[1] + " details inserted");
					insertPrpt.close();
				}
			}
			cnn.close();
			buff.close();
			
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
