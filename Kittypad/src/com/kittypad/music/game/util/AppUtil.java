package com.kittypad.music.game.util;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppUtil {
	 
	 private final static String userName="feebee90";
	 private final static String password="schsch";
	 private final static String url="jdbc:mysql://ringserver.cnjznhc4ikvb.us-east-1.rds.amazonaws.com/ringserver?useUnicode=true&characterEncoding=UTF-8";
	 private static Connection connection=null;
	 private static Statement statement=null;	
	

	 public static void init() throws ClassNotFoundException, SQLException
	 {
		 
				String driver = "com.mysql.jdbc.Driver"; 
				Class.forName(driver); 
				
					connection = DriverManager.getConnection(url, userName, password); 
					statement=connection.createStatement();
				
				
			 
		 
	 }
	 public static void disconnect() throws SQLException
	 {
		 statement.close();
		 connection.close();
		 statement=null;
		 connection=null;
		 
	 }
	 public static void insertItem(AppItem app) throws SQLException, UnsupportedEncodingException
	 {  PreparedStatement ps=null;
		 
				ps =  connection.prepareStatement( "INSERT into appVersion (appname,appversion,applink) values (?,?,?)" );			
				ps.setString(1, app.getAppName());
				ps.setString(2, app.getAppVersion());			
				ps.setString(3, app.getAppLink());
				ps.executeUpdate();
			
		 
	 }
	 public static AppItem getLatestAppItem(String appName) throws SQLException{
		 String select="select * from appVersion where appname=\""+appName+"\" order by id desc limit 0,1";
		 ResultSet result=statement.executeQuery(select);
		 if(result.next()){
			AppItem app=new AppItem(result.getString(2),result.getString(3),result.getString(4));
			return app;
		 }
		 return null;
	 }
	 
}
