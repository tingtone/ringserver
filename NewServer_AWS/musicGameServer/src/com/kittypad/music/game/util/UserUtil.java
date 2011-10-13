package com.kittypad.music.game.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;



public class UserUtil {
	
	 private final static String mDomain="user";
	 private final static String userName="feebee90";
	 private final static String password="schsch";
	 private final static String url="jdbc:mysql://ringserver.cnjznhc4ikvb.us-east-1.rds.amazonaws.com/ringserver";
	 private static Connection connection=null;
	 private static Statement statement=null;	

	 public static void init() throws ClassNotFoundException, SQLException
	 {
		 
				String driver = "com.mysql.jdbc.Driver"; 
				Class.forName(driver); 
				if(connection==null){
					connection = DriverManager.getConnection(url, userName, password); 
					statement=connection.createStatement();
				}
				
			 
		 
	 }
	 public static void insertItem(UserItem user) throws SQLException
	 {  PreparedStatement ps=null;
		 
				ps =  connection.prepareStatement( "INSERT into "+mDomain+" values (?,?,?,?,?)" );			
				ps.setString(1, user.getUUID());
				ps.setString(2, user.getUserName());			
				ps.setString(3, user.getEmail());
				ps.setString(4,user.getLocation());
				ps.setInt(5, user.getKittyad_beans());
				ps.executeUpdate();
			
		 
	 }
	 public static void registerItem(UserItem user) throws SQLException
	 {
		 PreparedStatement ps=null;
		 try {
				ps =  connection.prepareStatement( "INSERT into "+mDomain+" values (?,?,?,?,?)" );			
				ps.setString(1, user.getUUID());
				ps.setString(2, user.getUserName());			
				ps.setString(3, user.getEmail());
				ps.setString(4,user.getLocation());
				ps.setInt(5, user.getKittyad_beans());
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				UserUtil.updateBaseItem(user);
			}
		 
	 }
	 public static void updateBaseItem(UserItem user) throws SQLException
	 {

		 
			  String update="update User set userName=\""+user.getUserName()+"\", email=\""
			  +user.getEmail()+"\", location =\""+user.getLocation()+"\" where UUID=\""+user.getUUID()+"\"";
		        statement.executeUpdate(update);
				
		
	 }
	 public static void updateBeans(String UUID) throws SQLException
	 {
		
		 
			   String update="update User set kittypad_beans=\""+5+"\" where UUID=\""+UUID+"\"";
		        statement.executeUpdate(update);
		
		
	 }
	 public static int getBaseBeans(String UUID) throws SQLException
	 {
		
			   String select ="select * from User where UUID = \""+UUID+"\"";
			   ResultSet result= statement.executeQuery(select);
			   if(result.next()){
				   int beans=result.getInt("kittypad_beans");  
				   return beans;
			   }
			   else
				   return 0;
		
			
	 }
}
