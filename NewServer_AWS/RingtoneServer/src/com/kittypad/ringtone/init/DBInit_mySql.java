package com.kittypad.ringtone.init;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.kittypad.ringtone.util.MusicItem;
import com.mysql.jdbc.Driver;




import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClient;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;

public class DBInit_mySql {
	
	private  AmazonRDS ringServer;
	private final String mDomain="MusicItem";
	private final String userName="feebee90";
	private final String password="schsch";
	private final String url="jdbc:mysql://ringserver.cnjznhc4ikvb.us-east-1.rds.amazonaws.com/ringserver";
	private Connection connection;
	private Statement statement;
	
	public DBInit_mySql(boolean connect) {
		try{
			String driver = "com.mysql.jdbc.Driver"; 
			Class.forName(driver); 
			connection = DriverManager.getConnection(url, userName, password); 
			statement=connection.createStatement();
			} catch (SQLException sqlex) { 
			System.out.println("connot connect to mysql server!"); 
			sqlex.printStackTrace(); 
			} catch (Exception ex) { 
			System.out.println("NoClassDefException"); 
			ex.printStackTrace(); 
			} 
		 
	}  
	public DBInit_mySql(){
		
	}
    public void createTable() throws SQLException
    {
    	
    	String sql=" create table MusicItem ( "+
    	          "ID_Name varchar(255) primary key not null,"+
                  "UUID varchar(255) NOT NULL, "+
                  "add_date datetime NOT NULL, "+
                  "artists varchar(255) NOT NULL, "+
                  "avg_rate int, "+
                  "category varchar(255) NOT NULL,"+
                  "download_count int, "+
                  "musicName varchar(255) NOT NULL,"+
                  "rate_count int, "+
                  "size int, "+
                  "type varchar(255) NOT NULL,"+
                  "s3_url varchar(255) NOT NULL)";
    	statement.execute(sql);
                 
    	
    }
    public void dropTable() throws SQLException
    {
    	
    	String sql="drop Table MusicItem";
    	statement.execute(sql);
    }
    public void createIndex() throws SQLException
    {
    	String sql="create index musicIndex on MusicItem (UUID(10)) ;";
    	statement.execute(sql);
    }
    public void dropIndex()
    {
    	
    }
    public void createView() throws SQLException
    {
    	String sql="create view mp3ItemView as select * from MusicItem where type=\"mp3\"";
    	statement.execute(sql);
    }
    public void dropView() throws SQLException
    {
    	String sql="drop view mp3ItemView";
    	statement.execute(sql);
    	
    }
   public  List<MusicItem> select() throws SQLException
    {
    	List<MusicItem> musicItems=new ArrayList<MusicItem>();
    	String sql="select * from MusicItem limit 0,10";
    	ResultSet result=statement.executeQuery(sql);
    	while(result.next())
    		musicItems.add(new MusicItem(result.getString(2),result.getString(2),result.getDate(3),
    				result.getString(4),result.getInt(5),result.getString(6),result.getInt(7),result.getString(8),
    				result.getInt(9),result.getInt(10),result.getString(11),result.getString(12)));
    	
    	return musicItems;
    }
   public  int getTotalRingCount() throws SQLException
	{
	 
	  String count="select count(*) from MusicItem";
	  ResultSet result=statement.executeQuery(count);
	  if(result.next()){   
		  int summary=result.getInt(1);
	  	
	  	  return summary;
	  }
	  return -1;
	}
	 public   int getMp3RingCount() throws SQLException
	{
	 
	  String count="select count(*) from MusicItem where utype = \"mp3\"";
	  ResultSet result=statement.executeQuery(count);
	  if(result.next()){   
		  int summary=result.getInt(1);
	  	
	  	  return summary;
	  }
	  return -1;

	}
    

	
   public  void readData2(String fileName,String s3_url,int length) throws FileNotFoundException, SQLException {
		
		File file=new File(fileName);
		Scanner scanner=new Scanner(file);
		 List<ReplaceableItem> sampleData = new ArrayList<ReplaceableItem>();
		 PreparedStatement ps =  connection.prepareStatement( "INSERT into "+mDomain+" values (?,?,?,?,?,?,?,?,?,?,?,?)" );
		 int count=0;
		 int continue_count=0;
		 int duplicate_count=0;
		
		for(int i=0;i<length;i++)
		 {
				 String line=scanner.nextLine();
				 
			     if(line.indexOf("[u")!=-1)
			     {   continue_count++;
			    	 continue;
			     }
				 String []column=line.split("\\*");
				try{
				
				 String UUID=column[0];
				 String artist=column[1];
				 String musicName=column[2];
				 String category=column[3];
				 String type=column[4];
				
				 int size=Integer.parseInt(column[5]);
				 MusicItem item=new MusicItem(UUID,artist,musicName,category,type,size,s3_url);
				 insertItem(ps,item);
				 count++;
				 if(count%10==0)
					 System.out.println("count:"+count);
				 }catch(SQLException e)
				 {
					System.out.println(line);
					duplicate_count++;
				 }catch(Exception e)
				 {
					 continue_count++;
					 continue;
				 }		
		}
		 scanner.close();
		 System.out.println("Done!\nCount="+count+"\nContinue_cout="+continue_count+"\nduplicate_count="+duplicate_count);
		
	
		
	}
	public void readData3(String fileName,String s3_url,int length) throws FileNotFoundException, SQLException
	{
		
			
			File file=new File(fileName);
			Scanner scanner=new Scanner(file);
			 List<ReplaceableItem> sampleData = new ArrayList<ReplaceableItem>();
			 PreparedStatement ps =  connection.prepareStatement( "INSERT into "+mDomain+" values (?,?,?,?,?,?,?,?,?,?,?,?)" );
			 int count=0;
			 int continue_count=0;
			 int duplicate_count=0;
			
			for(int i=0;i<length;i++)
			 {
					 String line=scanner.nextLine();
					 
				     if(line.indexOf("[u")!=-1)
				     {   continue_count++;
				    	 continue;
				     }
					 String []column=line.split("\\*");
					try{
					
					 String UUID=column[0];
					 String musicName=column[1];
					 String category=column[2];
					 String type=column[3];
					 int size=Integer.parseInt(column[4]);
					 MusicItem item=new MusicItem(UUID,musicName,category,type,size,s3_url);
					 insertItem(ps,item);
					 count++;
					 if(count%10==0)
						 System.out.println("count:"+count);
					 }catch(SQLException e)
					 {
						System.out.println(line);
						duplicate_count++;
					 }catch(Exception e)
					 {
						 continue_count++;
						 continue;
					 }		
			}
			 scanner.close();
			 System.out.println("Done!\nCount="+count+"\nContinue_cout="+continue_count+"\nduplicate_count="+duplicate_count);
			
		
			
		}
	
	public void readData(String fileName) throws FileNotFoundException{
		 File file=new File(fileName);//MusicItem.csv
		 Scanner scanner=new Scanner(file);
		 List<ReplaceableItem> sampleData = new ArrayList<ReplaceableItem>();
		 PreparedStatement ps = null;
		 try {
			ps =  connection.prepareStatement( "INSERT into "+mDomain+" values (?,?,?,?,?,?,?,?,?,?,?,?)" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 int count=0;
		
		
		//while(scanner.hasNext())
		 for(int i=0;i<14035;i++)
			 scanner.nextLine();
		 for(int i=14305;i<80738;i++)
		 {
				 String line=scanner.nextLine();
				 String []column=line.split(";");
				 String ID_Name=column[0].substring(1,column[0].length()-1);
				 String UUID=column[1].substring(1,column[1].length()-1);
				 Date date=formatDate(column[2].substring(1,column[2].length()-1));
				 String artist=column[3].substring(1,column[3].length()-1);
				 int avg_rate=Integer.parseInt(column[4].substring(1,column[4].length()-1));
				 String category=column[5].substring(1,column[5].length()-1);
				 int download_count=Integer.parseInt(column[6].substring(1,column[6].length()-1));
				 String musicName=column[7].substring(1,column[7].length()-1);
				 int rate_count=Integer.parseInt(column[8].substring(1,column[8].length()-1));
				 int size=Integer.parseInt(column[9].substring(1,column[9].length()-1));
				 String type=column[10].substring(1,column[10].length()-1);
				 String s3_url=column[11].substring(1,column[11].length()-1);	
				 MusicItem item=new MusicItem(ID_Name,UUID,date,artist,avg_rate,category,
											download_count,musicName,rate_count,size,type,s3_url);
				 
				 try {
					insertItem(ps,item);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 count++;
				 if(count%5==0)
					 System.out.println("count:"+count);
		}
		 scanner.close();
		
		
		
	}
	private void insertItem(PreparedStatement ps,MusicItem musicItem) throws SQLException
	{
		
			
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

	private  Date formatDate(String date)
	{
		int year=Integer.parseInt(date.substring(0,4));
		int month=Integer.parseInt(date.substring(5,7));
		int day=Integer.parseInt(date.substring(8,10));
		int hour=Integer.parseInt(date.substring(11,13));
		int min=Integer.parseInt(date.substring(14,16));
		int second=Integer.parseInt(date.substring(17,19));
		Date datetime=new Date(year,month,day,hour,min,second);
		return datetime;
	}
    public static void main(String []args) throws Exception{
            DBInit_mySql dbinit=new DBInit_mySql(true);
           // dbinit.dropTable();
           // dbinit.dropView();
           // dbinit.createTable();
            //dbinit.readData3("midilist_all.txt","kittypad_ringtone_midi",38765);
           // System.out.println(dbinit.getTotalRingCount()+"");
            dbinit.readData3("midilist_lost.txt","kittypad_ringtone_midi",35705-29100);
          //  dbinit.readData3("songlist_all.txt","kittypad_ringtone",6376);
          //  dbinit.readData2("mp3list1.txt","ringtone_ring",30000);
           // dbinit.readData2("mp3list2.txt","ringtone_ring",30000);
           // dbinit.readData2("mp3list3.txt","ringtone_ring",30000);
           // dbinit.readData2("mp3list4.txt","ringtone_ring",30000);
           // dbinit.readData2("mp3list5.txt","ringtone_ring",30000);
           // dbinit.readData2("mp3list6.txt","ringtone_ring",30000);
           // dbinit.readData2("mp3list7.txt","ringtone_ring",2865);
           // dbinit.readData2("mp3list_lost.txt","ringtone_ring",254);
            
    
    	
    	
    }
	

}
