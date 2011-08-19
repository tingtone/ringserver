package com.kittypad.ringtone.DBInit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;


public class DBInitMidiHttpRequest {
	static int totalFolderCount = 0;
	static int totalFileCount = 0;
	static int items = 0;
	static int globalCount = 1;
	public static void main(String[] args) throws FileNotFoundException{
		String path = "/Users/apple/Desktop/midi";
		String path1 = "/Users/apple/Desktop/midi1";
		String path2 = "/Users/apple/Desktop/midi2";
		File file = new File(path);
		File file1 = new File(path1);
		File file2 = new File(path2);
		System.out.println("Begin Scanning file "+ path);
		if(!file.exists()){
			System.out.println("File Not Exist");
		}
		System.out.println("Begin generating music list and add it to database");
		Init(file); //read all musiclist and add them to database
		
		if(!file1.exists()){
			System.out.println("File Not Exist");
		}
		System.out.println("Begin generating music list and add it to database");
		Init(file1); //read all musiclist and add them to database
		
		if(!file2.exists()){
			System.out.println("File Not Exist");
		}
		System.out.println("Begin generating music list and add it to database");
		Init(file2); //read all musiclist and add them to database
		
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
			else if(fileName.equals("midilist.txt")){
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
					
					
					String dir = path.substring(0, index);
					String urlString = "http://kittypad-ring.appspot.com/music?keyStr="+URLEncoder.encode(id+musicName) + "&UUID="+id+
							"&musicName=" + URLEncoder.encode(musicName)+
							"&category="+URLEncoder.encode(category)+"&type="+URLEncoder.encode(type) +"&size="+size;
				//	System.out.println(urlString);
					URL url;
					System.out.println((globalCount++)+":"+musicName);
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
}
