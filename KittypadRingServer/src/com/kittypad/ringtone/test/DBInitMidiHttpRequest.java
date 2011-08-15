package com.kittypad.ringtone.test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.UUID;

/*copyright yanling
 *The main class to generate music lists and init Database 
 *this is used to test in kittypad-ringtone.appspot
 */
public class DBInitMidiHttpRequest {
	static int totalFolderCount = 0;
	static int totalFileCount = 0;
	static int items = 0;
	static int globalCount = 1;
	public static void main(String[] args) throws FileNotFoundException{
		String path = "/Users/apple/Desktop/midi";
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
					String urlString = "http://kittypad-ringtone.appspot.com/music?keyStr="+URLEncoder.encode(id+musicName) + "&UUID="+id+
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
	/*generate music list for every sub folder*/
	static void generateList(File f) throws FileNotFoundException{
		String path = f.getAbsolutePath();
		File songlist = new File(path+"/midilist.txt");
		PrintWriter pw = new PrintWriter(songlist); 
		File[] ff = f.listFiles();
		for(File child:ff){
			if(child.isDirectory()){
				totalFolderCount++;
				generateList(child);
			}else{
				if(child.getName().contains(".mid") && !child.getName().equals(".mid")){
					UUID id = UUID.randomUUID();
					String fileName = child.getName();
					String musicName = fileName.split(".mid")[0]; //get music name exclude the suffix
					int temp = path.lastIndexOf("/");
					String category = path.substring(temp+1,path.length()); //get category
					String type = "mid";
					long size = child.length();
					pw.println(id+"*" + musicName + "*" +category +"*"+type+"*"+size);
					items++;
				}	
			}		
		}
		pw.close();
	}
}
