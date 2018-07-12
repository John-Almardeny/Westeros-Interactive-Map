package westrosPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Database Class for manipulating and controlling Westeros DB
 * @author Yahya Almardeny
 * @version 01/04/2017
 */
public class Database {
	
	public static Connection myConnection;
	public static PreparedStatement preparedStatement;
	public static ResultSet result = null;
	
	
	/**
	 * the constructor to establish a connection with the database 
	 * @param username
	 * @param password
	 * @param database
	 * @param host
	 */
	public Database(String username, String password, String database, String host){
		try{ 
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			myConnection = DriverManager.getConnection
					("jdbc:mysql://" + host + "/" + database + "?user=" + username + "&password=" + password);
		}
		catch(Exception e) { 
			notification("Failed to connect the database","check the connection with the server. "
									+ "Some features of this app will not work");
		 }
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * static method to select from database
	 * @param selectQuery
	 * @return results
	 */
	public static ResultSet read(String selectQuery){	
		try {
			preparedStatement = myConnection.prepareStatement(selectQuery);
			result = preparedStatement.executeQuery();
		} catch (SQLException e) {
			notification("Failed to read from the database","");
		}
		
		return result;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * static method to update database
	 * @param updateStatement
	 * @return success
	 */
	public static int update(String updateStatement){
		int success = 0;
		try {
			preparedStatement = myConnection.prepareStatement(updateStatement);
		    success = preparedStatement.executeUpdate(updateStatement);
		} catch (SQLException e) {
			notification("Failed to update the database","");
		}  
		return success;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * static method to insert into database
	 * @param insertStatement
	 * @return success
	 */
	public static int insert(String insertStatement){
		int success=0;
		try {
			preparedStatement = myConnection.prepareStatement(insertStatement);
			success = preparedStatement.executeUpdate(insertStatement);
		} catch (SQLException e) {
			notification("Failed to add to the database","");
		} 
		 return success;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * static method to delete from database
	 * @param deleteStatement
	 * @return success
	 */
	public static int delete(String deleteStatement){
		int success=0;
		try {
			preparedStatement = myConnection.prepareStatement(deleteStatement);
			success = preparedStatement.executeUpdate(deleteStatement);
		} catch (SQLException e) {
			notification("Failed to delete from the database","");
		}
		return success;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * static method to close the connection with database
	 */
	public static void close(){
		try {
			result.close();
			preparedStatement.close();
			myConnection.close();
		} catch (SQLException e) {
			notification("Failed to close the connection with the database","");
		}
		
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * private static methods to display custom message box
	 * as a notification to the user
	 * @param header
	 * @param content
	 */
	private static void notification(String header, String content){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	
}
