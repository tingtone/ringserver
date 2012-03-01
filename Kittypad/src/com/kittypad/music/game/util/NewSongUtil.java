package com.kittypad.music.game.util;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class NewSongUtil {
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
		 
		 
		 public static void insertItem(NewSongItem newSong) throws SQLException, UnsupportedEncodingException
		 {  PreparedStatement ps=null;
			 
					ps =  connection.prepareStatement( "INSERT into NewSong (add_date,musicName,size,s3_url) values (?,?,?,?)" );			
					Timestamp timestamp=new Timestamp(newSong.getAdd_date().getTime());
					ps.setTimestamp(1,timestamp);
					ps.setString(2, newSong.getMusicName());			
					ps.setInt(3, newSong.getSize());
					ps.setString(4, newSong.getS3_url());
					ps.executeUpdate();

		 }
		 public static NewSongItem getRecommendSong(int index) throws SQLException{
			 String select="select * from NewSong  order by ID asc limit "+index+" ,1";
			 ResultSet result=statement.executeQuery(select);
			 if(result.next()){
				NewSongItem newSong=new NewSongItem(result.getInt(1),result.getString(3),result.getInt(4),result.getString(5),result.getDate(2));
				return newSong;
			 }
			 return null;
		 }
		 

}
