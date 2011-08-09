package com.kittypad.ringtone.DBInit;
/*copyright yanling
 *The main class to generate music lists and init Database 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.UUID;


public class DBInitHttpRequest {
	static int totalFolderCount = 0;
	static int totalFileCount = 0;
	static int items = 0;

	public static void main(String[] args) throws FileNotFoundException{
		String path = "/Users/apple/Desktop/Funny music";
		File file = new File(path);
		System.out.println("Begin Scanning file "+ path);
		if(!file.exists()){
			System.out.println("File Not Exist");
		}
		System.out.println("Begin generating music list and add it to database");
	//	generateList(file); //this function is used to generate music list in every folder
		Init(file); //read all musiclist and add them to database
	//	System.out.println("Generating list and add it to database over");
		System.out.println("There are"+ items+" music files");	
	}
	
	static void Init(File f) throws FileNotFoundException{
		File[] ff = f.listFiles();
		for(File child:ff){
			String fileName = child.getName();
			String path = child.getAbsolutePath();
			
			if(child.isDirectory()){
				Init(child);
			}
			else if(fileName.equals("songlist.txt")){
				Scanner s = new Scanner(child);
				while(s.hasNext()){
					String line = s.nextLine();
					String[] ss = line.split("\\*");
					String id = ss[0];
					String musicName = ss[1];
					String category = ss[2];
					String type = ss[3];
					String size = ss[4];
					int index = path.lastIndexOf("/");
					
					category = "funny";
					
					String dir = path.substring(0, index);
					String urlString = "http://kittypad-ringtone.appspot.com/music?keyStr="+URLEncoder.encode(id+musicName) + "&UUID="+id+
							"&musicName=" + URLEncoder.encode(musicName)+
							"&category="+URLEncoder.encode(category)+"&type="+URLEncoder.encode(type) +"&size="+size;
					URL url;
					System.out.println(musicName);
					try {
						url = new URL(urlString);
						url.openStream().close();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(0);
					}catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(0);
					}
					items++;
				}
			}
		}
	}
	/*generate music list for every sub folder*/
	static void generateList(File f) throws FileNotFoundException{
		String path = f.getAbsolutePath();
		File songlist = new File(path+"/songlist.txt");
		PrintWriter pw = new PrintWriter(songlist); 
		File[] ff = f.listFiles();
		for(File child:ff){
			if(child.isDirectory()){
				totalFolderCount++;
				generateList(child);
			}else{
				if(child.getName().contains(".mp3") && !child.getName().equals(".mp3")){
					UUID id = UUID.randomUUID();
					String fileName = child.getName();
					String musicName = fileName.split(".mp3")[0]; //get music name exclude the suffix
					int temp = path.lastIndexOf("/");
					String category = path.substring(temp+1,path.length()); //get category
					String type = "mp3";
					long size = child.length();
					pw.println(id+"*" + musicName + "*" +category +"*"+type+"*"+size);
					items++;
				}	
			}		
		}
		pw.close();
	}
}