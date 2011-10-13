package com.kittypad.music.game.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;









public class UserMusicUtil {
	
	 public static boolean DEBUG=false;
	 
	 private final static String mDomain="userMusic";
	 private final static String userName="feebee90";
	 private final static String password="schsch";
	 private final static String url="jdbc:mysql://ringserver.cnjznhc4ikvb.us-east-1.rds.amazonaws.com/ringserver";
	 private static Connection connection=null;
	 private static Statement statement=null;	
	 private static int MAX_NUMBER_OF_WORDS_TO_PUT_IN_INDEX = 200;
	 private static int RESULTS_PER_PAGE = 10;
	 private static String[] stopWords = {
		  	    "a", "an", "and", "are", "as", "at", "be", "but", "by",
			    "for", "if", "in", "into", "is", "it",
			    "no", "not", "of", "on", "or", "such",
			    "that", "the", "their", "then", "there", "these",
			    "they", "this", "to", "was", "will", "with"
	};
	 private static String implode(String symbol,String[] key)
	{   String output=key[0];
		for(int i=1;i<key.length;i++)
			output=output+symbol+key[i];
		
		return output;
	}
	 private static boolean inArray(String word){
		 for(int i=0;i<stopWords.length;i++)
			 if(word.equalsIgnoreCase(stopWords[i]))
				 return true;
		 return false;
	 }
	 private static  String buildSelectSql(String key,String columnName)
	{
	    
	   String []array=key.split(" ");
	  
	   String underline_key=implode("_",array);
	   String select="(select * from userMusic where "+ columnName+" like \"%"+key+"%\" or "+columnName+" like \"%"+underline_key+"%\") ";//full match
	   ArrayList<String> valid_key=new ArrayList<String>();
	
	  
	   for(int i=0;i<array.length;i++)
	   {
	       String word=array[i];
	       if(inArray(word))
	          continue;
	       valid_key.add(word);
	   }
	
	   int keySize=valid_key.size();
	   if(keySize>0){
	   //full match of each valid word
	      select+="union ( select * from  userMusic where ";
	     int i;
	      for(i=0;i<(keySize-1);i++)
	         select+=columnName+" like \"%"+valid_key.get(i)+"%\"  and ";
	      select+=columnName+" like \"%"+valid_key.get(i)+"%\" ) ";
	   //part match of each valid word
	      select+="union ( select * from  userMusic where ";
	      for(i=0;i<keySize-1;i++)
	         select+=columnName+" like \"%"+valid_key.get(i)+"%\"  or ";
	      select+=columnName+" like \"%"+valid_key.get(i)+"%\")";
	   }
	   
	   return select;
	}

	 private static   ArrayList<MusicItem> transfer(ResultSet result) throws SQLException
	    {
	    	ArrayList<MusicItem> musicItems=new ArrayList<MusicItem>();
	    	while(result.next())
	    		musicItems.add(new MusicItem(result.getString(2),result.getString(2),result.getDate(3),
	    				result.getString(4),result.getInt(5),result.getString(6),result.getInt(7),result.getString(8),
	    				result.getInt(9),result.getInt(10),result.getString(11),result.getString(12)));
	    	
	    	return musicItems;
	    }
	 public static void init() throws ClassNotFoundException, SQLException
	 {
		 
				String driver = "com.mysql.jdbc.Driver"; 
				Class.forName(driver); 
				if(connection==null){
					connection = DriverManager.getConnection(url, userName, password); 
					statement=connection.createStatement();
				}
				
		 
	 }
	 public static int getTotalRingCount() throws SQLException
	{
	 
	  String count="select count(*) from userMusic";
	  ResultSet result=statement.executeQuery(count);
	  if(result.next()){   
		  int summary=result.getInt(1);
	  	  if(DEBUG)
	  		  System.out.println("count:"+summary);
	  	  return summary;
	  }
	  return -1;
	}
	
	 public static  ArrayList<MusicItem> getResultsByKeyword(String platform,String key, int start) throws SQLException
	{ 
	  
	   
	   int beginPos=start*RESULTS_PER_PAGE;
	   int numItem=RESULTS_PER_PAGE;
	   String select;
	   if(platform.equalsIgnoreCase("android"))
	      select=buildSelectSql(key,"musicName");
	   else 
	      select=buildSelectSql(key,"musicName");
	   select+=" limit "+beginPos+","+ numItem ;
	   if(DEBUG){
		   System.out.println(select);
	  
	   }
	   ResultSet result= statement.executeQuery(select);
	   
	   return transfer(result);
	}
	 public static  ArrayList<MusicItem> getResultsByCategory(String platform,String key, int start) throws SQLException{
	  
	   int beginPos=start*RESULTS_PER_PAGE;
	   int numItem=RESULTS_PER_PAGE;
	   String select;
	   if(platform.equalsIgnoreCase("android"))
	      select=buildSelectSql(key,"category");
	   else
	      select=buildSelectSql(key,"category");
	   select+=" limit "+beginPos+","+ numItem ;
	   if(DEBUG){
	      System.out.println(select);
	   }
	   ResultSet result=statement.executeQuery(select);
	   return transfer(result);
	}
	 public static  ArrayList<MusicItem> getResultsByDownloadCount(String platform,int start) throws SQLException
	{
	
	   int beginPos=start*RESULTS_PER_PAGE;
	   int numItem=RESULTS_PER_PAGE;
	   String select;
	   if(platform.equalsIgnoreCase("android"))
	      select="select * from userMusic order by \"download_count\" desc";
	   else
	      select="select * from userMusic order by \"download_count\" desc";
	   select+=" limit "+ beginPos+","+ numItem ;
	   if(DEBUG){
	     System.out.println(select);
	   }
	   ResultSet result= statement.executeQuery(select);
	
	   return transfer(result);

	}
	 public  static ArrayList<MusicItem> getResultsByDate(String platform,int start) throws SQLException
	{
	 
	   int beginPos=start*RESULTS_PER_PAGE;
	   int numItem=RESULTS_PER_PAGE;
	   String select;
	   if(platform.equalsIgnoreCase("android"))
	      select="select * from userMusic order by \"add_date\" desc";
	   else
	      select="select * from  userMusic order by \"add_date\" desc";
	    select+=" limit "+beginPos+"," +numItem ;
	   if(DEBUG){
	      System.out.println(select);
	   }
	  ResultSet result=statement.executeQuery(select);
	  return transfer(result);

	}
	 public static  ArrayList<MusicItem> getResultsByArtist(String platform, String key, int start) throws SQLException
	{
	 
	   int beginPos=start*RESULTS_PER_PAGE;
	   int numItem=RESULTS_PER_PAGE;
	   String select;
	   if(platform.equalsIgnoreCase("android"))
	      select=buildSelectSql(key,"artists");
	   else
	      select=buildSelectSql(key,"artists");
	   select+=" limit "+ beginPos+","+ numItem ;
	   if(DEBUG){
	   	   System.out.println(select);
	   }
	   ResultSet result= statement.executeQuery(select);
	  
	   return transfer(result);
	}
	 public static  ArrayList<MusicItem> getResultsByRandom(String platform) throws SQLException{
	  
	   int ringSize;
	   int beginPos;
	   int numItem;
	   String select;
	   if(platform.equalsIgnoreCase("android")){
	      ringSize=getTotalRingCount();
	      if(DEBUG){
	          System.out.println("select");
	       }
	      beginPos=(int)(Math.random()*(ringSize-10));
	      numItem=RESULTS_PER_PAGE;
	      select="select * from userMusic order by \"add_date\" desc";
	     
	   }
	   else{
		   ringSize=getTotalRingCount();
	      
	       beginPos=(int)(Math.random()*(ringSize-10));
	       numItem=RESULTS_PER_PAGE;
	      select="select * from userMusic order by \"add_date\" desc";
	     
	   }
	   if(beginPos<0)
		   beginPos=0;
	    select+=" limit "+beginPos+" , "+ numItem ;
	   if(DEBUG){
	   	 System.out.println(select);
	   }
	   ResultSet result= statement.executeQuery(select);
	   
	  
	   return transfer(result);
	}
	 public static  int updateAvgRate(String ID_Name,int rateValue) throws SQLException
	{
	  
	   String select ="select * from userMusic where ID_Name = \""+ID_Name+"\"";
	   ResultSet result=statement.executeQuery(select);
	   if(DEBUG){
		 System.out.println(result);
	   }
	   
	   if(result.next()){
		   int rate_count=result.getInt("rate_count");
		   int avg_rate=result.getInt("avg_rate");
		   avg_rate=(avg_rate*rate_count+rateValue)/(++rate_count);
	       String update="update userMusic set avg_rate=\""+avg_rate+"\", rate_count=\""+rate_count+"\" where ID_Name=\""+ID_Name+"\"";
	       statement.executeUpdate(update);
	       return avg_rate;
		
	   }
	   return -1;
	  
	}
	 public static int  downloadCountIncrease(String ringUrl) throws SQLException
	 {
	  
	   String select ="select * from userMusic where ID_Name = \""+ringUrl+"\"";
	   ResultSet result=statement.executeQuery(select);
	   if(DEBUG){
		  System.out.println(select);
	   }
	   if(result.next()){
	
			int download_count=result.getInt("download_count");
			download_count++;
			String update="update userMusic set download_count=\""+download_count+"\" where ID_Name=\""+ringUrl+"\"";
			statement.executeUpdate(update);
			return download_count;
	   }
	  return -1;

	}
	
	public static void insertItem(MusicItem musicItem) throws SQLException
	{
		PreparedStatement ps=null;
		
				ps =  connection.prepareStatement( "INSERT into "+mDomain+" values (?,?,?,?,?,?,?,?,?,?,?,?)" );
				ps.setString(1, musicItem.getID_Name());
				ps.setString(2, musicItem.getUUID());
				Timestamp timestamp=new Timestamp(musicItem.getAdd_date().getTime());
				ps.setTimestamp(3,timestamp);
				ps.setString(4, musicItem.getArtist());
				ps.setInt(5,musicItem.getAvg_rate());
				ps.setString(6,musicItem.getCategory());
				ps.setInt(7,musicItem.getDownload_count());
				ps.setString(8, musicItem.getMusicName());
				ps.setInt(9,musicItem.getRate_count());
				ps.setInt(10, musicItem.getSize());
				ps.setString(11, musicItem.getType());
				ps.setString(12, musicItem.getS3_url());
				ps.executeUpdate();
			
			
	  
        
		
	}
    
	 public static ArrayList<Integer> getItemDownloadCountsOfUser(String UUID) throws SQLException
	 {
	     String select="select * from userMusic where UUID=\""+UUID+"\""; 
		  ResultSet result=statement.executeQuery(select);

			
			    	ArrayList<Integer> downloads=new ArrayList<Integer>();
			    	while(result.next())
			    		downloads.add(result.getInt("download_count"));
			    	
			    	return downloads;
			    }

	 }
	 
	 


