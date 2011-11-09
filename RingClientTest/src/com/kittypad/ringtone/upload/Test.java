package com.kittypad.ringtone.upload;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Test {
	
	public static void main(String [] args) throws IOException, InterruptedException{
	   File file=new File("./plist");
	   File musicInfoFile=new File("filelist2.txt");
	   Scanner scanner=new Scanner(musicInfoFile);
	  
	   
	   String UUID="db12db0732e77ebe306fa7befb2aa6d204e9c761";
	   System.out.println("开始测试！");
	  while(scanner.hasNext()){
		   String line=scanner.nextLine();
		   String []info=line.split("\\*");
		   String musicName=info[0];
		   String type=info[1];
		   String category=info[2];
		   String filePath="./plist/"+info[0]+"."+info[1];
		   System.out.println(filePath);
		   File file2=new File(filePath);
		  musicName=musicName.replace(" ", "%20");
		  Util.uploadMusic(file2, UUID, musicName, type, "kittypad", category);
		  // System.out.println(line);
		String url="https://s3.amazonaws.com/kittypad_music_game/"+URLEncoder.encode(UUID+musicName)+"."+type;
	  // downloadUploadedForTest(url,musicName+".plist");
		  
	  }
	//	Thread.sleep(500000);
			
	  
		
	  
	  /* for(File child:children){
		   String musicName=
	   }
		   
		
		String UUID2="SYSTEM2";
		
		Util.uploadMusic(file2, UUID2, musicName2, type2, artist, category);

		
		*/
		
	}
	
	 public static void downloadUploadedForTest(String stringUrl,String fileName) throws IOException {
			
			int count = 0;
			URL url = null;
			HttpURLConnection urlConn = null;
			InputStream stream = null;
			DataInputStream is = null;
			File f = null;
		
			
				url = new URL(stringUrl);
				urlConn = (HttpURLConnection)url.openConnection();
				urlConn.addRequestProperty("User-Agent", "Mozilla/4.76");
				//urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Android 0.5; en-us) AppleWebKit/522+ (KHTML, like Gecko) Safari/419.3 -Java");
				urlConn.setConnectTimeout(4000);
				urlConn.connect();
				stream=urlConn.getInputStream();
				//stream = urlConn.getInputStream();
				byte[] buff = new byte[4096];
				is = new DataInputStream(stream);
				int len;
				f = new File(fileName);
			
				FileOutputStream file =  new FileOutputStream(f);
				int percent = 0;
				int last_percent = 0;
				while ((len = is.read(buff)) > 0) {
					file.write(buff, 0, len);
					file.flush();
					count = count + len;
				
					}
				file.flush();
				file.close();
				
				urlConn.disconnect();
	   
			
		}

}
