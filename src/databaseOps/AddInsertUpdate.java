package databaseOps;

import java.io.*;
import java.sql.*;


public class AddInsertUpdate {

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
					
					//splitting the line
					String[] usersArray = line.split(",");
					int fileUserId = Integer.parseInt(usersArray[0]);
					
					String userQuery = "SELECT * FROM employees";
					PreparedStatement getUsers = cnn.prepareStatement(userQuery);
					
					ResultSet usersResults = getUsers.executeQuery();
					boolean isUpdated = false;
					while (usersResults.next()) {
						int userId = usersResults.getInt("id");
						if (userId == fileUserId) {
							String updateQuery = "UPDATE employees SET first_name = ?, last_name = ?, age = ?, salary = ? WHERE id = ?";
							PreparedStatement updatePrpt = cnn.prepareStatement(updateQuery);
							updatePrpt.setString(1, usersArray[1]);
							updatePrpt.setString(2, usersArray[2]);
							updatePrpt.setInt(3, Integer.parseInt(usersArray[3]));
							updatePrpt.setInt(4, Integer.parseInt(usersArray[4]));
							updatePrpt.setInt(5, userId);
							updatePrpt.executeUpdate();
							System.out.println(usersArray[2] + " details updated");
							isUpdated = true;
							break;
						}
					}
					
					if (!isUpdated) {
						String insertQuery = "INSERT INTO employees VALUES(?, ?, ?, ?, ?)";
						PreparedStatement insertPrpt = cnn.prepareStatement(insertQuery);
						insertPrpt.setInt(1, Integer.parseInt(usersArray[0]));
						insertPrpt.setString(2, usersArray[1]);
						insertPrpt.setString(3, usersArray[2]);
						insertPrpt.setInt(4, Integer.parseInt(usersArray[3]));
						insertPrpt.setInt(5, Integer.parseInt(usersArray[4]));
						insertPrpt.executeUpdate();
						System.out.println(usersArray[1] + " details inserted");
					}
				}
			buff.close();
			
			cnn.close();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}

	}

}
