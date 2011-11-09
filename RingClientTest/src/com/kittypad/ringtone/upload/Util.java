package com.kittypad.ringtone.upload;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Util {
	public static String  BASE_URL="http://kittypad.elasticbeanstalk.com/game/";
	public static String UPLOAD_URL="Upload";
	public static String USER_REGISTER_URL="Register";//这里的注册是指用户修改其详细信息，不注册也可上传
	public static String BEFORE_DOWNLOAD_URL="GetBeans";//下载前需要获得beans的数量，如果数量不够则不能下载
	
	public static String AFTER_DOWNLOAD_URL="DownloadInc";//下载后需要对下载的歌曲的download＿count加1
    public static String SEARCH_URL="Search";
    public static String RATE_URL="Rate";
    
 //   public static String BSAE_URL="http://ringserver-jbyvh3m2vc.elasticbeanstalk.com/";
//	public static String AFTER_DOWNLOAD_URL="DownloadInc";//下载后需要对下载的歌曲的download＿count加1
 //   public static String SEARCH_URL="Search";
 //   public static String RATE_URL="Rate";
    
    
    public static void uploadMusic(File file,String UUID,String musicName,String type)
	{
		URL url = null;
		HttpURLConnection urlConn = null;
		OutputStream stream = null;
		DataInputStream is = null;
	
		int filesize=0;
		
		try {
			FileInputStream fin=new FileInputStream(file);
			int size=fin.available();
		    String urlStr=BASE_URL+UPLOAD_URL+"?UUID="+UUID+"&musicName="+musicName+"&type="+type+"&size="+size;
			url=new URL(urlStr);
			urlConn = (HttpURLConnection)url.openConnection();
			//urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 0.5; en-us) AppleWebKit/522+ (KHTML, like Gecko) Safari/419.3 -Java");
			urlConn.setConnectTimeout(40000);
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestMethod("POST");
			
			stream = urlConn.getOutputStream();
			byte[] buff = new byte[4096]; 
		    is=new DataInputStream(fin);			
		    int len=0;
			while ((len = is.read(buff)) > 0) {
				stream.write(buff, 0, len);
				stream.flush();
				filesize = filesize + len;
			}
			System.out.println("FileSize:"+filesize);
			stream.flush();
			//System.out.println(filesize);
			stream.close();
			is.close();
			System.out.println(urlConn.getResponseMessage());
			
			
		
			

		} catch (Exception e) {
			
			e.printStackTrace();
		} 

			
			
		
		

		
	}
    public static void uploadMusic(File file,String UUID,String musicName,String type,String artist,String category)
    //artist can be understand as uploader
    {
    	URL url = null;
		HttpURLConnection urlConn = null;
		OutputStream stream = null;
		DataInputStream is = null;
	
		int filesize=0;
		
		try {
			FileInputStream fin=new FileInputStream(file);
			int size=fin.available();
		    String urlStr=BASE_URL+UPLOAD_URL+"?UUID="+UUID+"&musicName="+musicName+"&type="+type+"&size="+size+"&artist="+artist+"&category="+category;
		    //urlStr=URLEncoder.encode(urlStr);
			System.out.println(urlStr);
		    url=new URL(urlStr);
			urlConn = (HttpURLConnection)url.openConnection();
			//urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 0.5; en-us) AppleWebKit/522+ (KHTML, like Gecko) Safari/419.3 -Java");
			urlConn.setConnectTimeout(4000);
			
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			stream = urlConn.getOutputStream();
			byte[] buff = new byte[4096]; 
		    is=new DataInputStream(fin);			
		    int len=0;
			while ((len = is.read(buff)) > 0) {
				stream.write(buff, 0, len);
				filesize = filesize + len;
			}
			System.out.println();
			System.out.println(filesize);
			stream.close();
			
			InputStream inStream=urlConn.getInputStream();
			Scanner data=new Scanner(inStream);
			while(data.hasNext())
				System.out.println(data.nextLine());
			System.out.println(urlConn.getContent());

		} catch (Exception e) {
			
			e.printStackTrace();
		} 
    }

    
    
  


}
