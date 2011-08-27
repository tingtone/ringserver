package com.kittypad.ringtone.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;

/*
 * This class provides functions to pre-process music files and regulate them
 * We need to filter files with large size
 * This program is also used to process the file name to exclude some irregular syntax
 */
public class FileProcessUtils {
	static int totalFolderCount = 0;
	static int totalFileCount = 0;
	static int changes = 0;
	static ArrayList<File> fileArray = new ArrayList<File>();
	public static void main(String[] args){
	//	String mp3Path = "/Users/apple/Desktop/Funny music";
	//	String midiPath = "/Users/apple/Desktop/midi/";
	//	String midiPath1 = "/Users/apple/Desktop/midi1/";
	//	String midiPath2 = "/Users/apple/Desktop/midi2/";
	//	processMidiFileName(midiPath);
		String midiPath3 = "/Users/apple/Desktop/midi2-1/";
		String m4rPath = "/Users/apple/Desktop/m4r/";
		File file = new File(midiPath3);
		System.out.println("Begin Scanning file "+ midiPath3);
		if(!file.exists()){
			System.out.println("File Not Exist");
		}	
		try {
			generateMidiListFromS3Files(file);
			System.out.println("Generate midi list over");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		String m4rPath = "/Users/apple/Desktop/m4r/";
		File file = new File(m4rPath);
		System.out.println("Begin Scanning file "+ m4rPath);
		if(!file.exists()){
			System.out.println("File Not Exist");
		}	
		try {
			generateM4rListFromS3Files(file);
			System.out.println("Generate M4r list over");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	/*	File file = new File(midiPath1);
		System.out.println("Begin Scanning file "+ midiPath1);
		if(!file.exists()){
			System.out.println("File Not Exist");
		}	
		try {
			generateMidiListFromS3Files(file);
			System.out.println("Generate Midi list over");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file = new File(midiPath2);
		System.out.println("Begin Scanning file "+ midiPath2);
		if(!file.exists()){
			System.out.println("File Not Exist");
		}	
		try {
			generateMidiListFromS3Files(file);
			System.out.println("Generate Midi list over");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
	}
	
	private static void processMp3FileName(String path){
		File file = new File(path);
		System.out.println("Begin Scanning file "+ path);
		if(!file.exists()){
			System.out.println("File Not Exist");
		}
		ArrayList<File> al = getFile(file);
		System.out.println("Begin to handle file names");
		if(al.isEmpty()){
			return;
		}
		System.out.println(al.size());
		for(File f:al){
			handleMp3File(f);
		}
		System.out.println("Handle over");
		System.out.println("Have changed "+ changes+" file names");
	}
	
	private static void handleMp3File(File f) {
		// TODO Auto-generated method stub
		String fileName = f.getName();
		String filePath = f.getAbsolutePath();
		int temp = filePath.lastIndexOf("/");
		String path = filePath.substring(0, temp);
		if(fileName.endsWith(".mp3") && !fileName.equals(".mp3")){ //handle mp3 files only
			String newfileName = fileName.split(".mp3")[0];
			boolean needchange = false;
			if(newfileName.contains("&")){
				needchange = true;
				newfileName = newfileName.replaceAll("&", "_");
			}
			if(newfileName.contains(".")){
				needchange = true;
				newfileName = newfileName.replace(".", "");
			}
			if(newfileName.contains("!")){
				needchange = true;
				newfileName = newfileName.replaceAll("!", " ");
			}
			if(newfileName.contains("*")){
				needchange = true;
				newfileName = newfileName.replace("*", "_");
			}
			newfileName = newfileName.replaceAll("[^0-9a-zA-Z_ ]", "_");
			needchange = true;
			newfileName = path +"/" +newfileName + ".mp3";
			if(needchange){
				f.renameTo(new File(newfileName));
				System.out.println(fileName +" changes to "+ newfileName);
				changes++;
			}	
		}
	}
	
	private static void processMidiFileName(String path){
		File file = new File(path);
		System.out.println("Begin Scanning file "+ path);
		if(!file.exists()){
			System.out.println("File Not Exist");
		}
		ArrayList<File> al = getFile(file);
		System.out.println("Begin to handle file names");
		if(al.isEmpty()){
			return;
		}
		System.out.println(al.size());
		for(File f:al){
			handleMidiFile(f);
		}
		System.out.println("Handle over");
		System.out.println("Have changed "+ changes+" file names");
	}
	
	private static void handleMidiFile(File f) {
		// TODO Auto-generated method stub
		String fileName = f.getName();
		String filePath = f.getAbsolutePath();
		int temp = filePath.lastIndexOf("/");
		String path = filePath.substring(0, temp);
		String newfileName = fileName.toLowerCase();
		int index = newfileName.lastIndexOf("_");
		
		newfileName = path +"/" +newfileName.substring(0, index) + ".mid";
		System.out.println(newfileName);
		f.renameTo(new File(newfileName));
	}

	public static ArrayList<File> getFile(File f){
		System.out.println("skanning sub file fold: "+f.getPath());
		
		File[] ff = f.listFiles();
		for(File child:ff){
			if(child.isDirectory()){
				System.out.println("Find sub file fold: "+ child.getPath());
				totalFolderCount++;
				getFile(child);
			}else{

					fileArray.add(child);
					totalFileCount++;
			}
		}
		return fileArray;
	}
	
	
	/*generate music list for every sub folder*/
	static void generateMp3List(File f) throws FileNotFoundException{
		String path = f.getAbsolutePath();
		File songlist = new File(path+"/songlist.txt");
		PrintWriter pw = new PrintWriter(songlist); 
		File[] ff = f.listFiles();
		for(File child:ff){
			if(child.isDirectory()){
				generateMp3List(child);
			}else{
				if(child.getName().contains(".mp3") && !child.getName().equals(".mp3")){
					UUID id = UUID.randomUUID();
					String fileName = child.getName();
					String musicName = fileName.split("\\.mp3")[0]; //get music name exclude the suffix
					int temp = path.lastIndexOf("/");
					String category = path.substring(temp+1,path.length()); //get category
					String type = "mp3";
					long size = child.length();
					pw.println(id+"*" + musicName + "*" +category +"*"+type+"*"+size);
				}	
			}		
		}
		pw.close();
	}
	
	
	/*generate midi music list for every sub folder*/
	static void generateMidiList(File f) throws FileNotFoundException{
		String path = f.getAbsolutePath();
		File songlist = new File(path+"/midilist.txt");
		PrintWriter pw = new PrintWriter(songlist); 
		File[] ff = f.listFiles();
		for(File child:ff){
			if(child.isDirectory()){
				generateMidiList(child);
			}else{
				if(child.getName().contains(".mid") && !child.getName().equals(".mid")){
					UUID id = UUID.randomUUID();
					String fileName = child.getName();
					String musicName = fileName.substring(0, fileName.length()-4); //get music name exclude the suffix
					int temp = path.lastIndexOf("/");
					String category = path.substring(temp+1,path.length()); //get category
					String type = "mid";
					long size = child.length();
					if(size > 40960){ //exclude files with size larger than 40K
					}
					else {
						pw.println(id+"*" + musicName + "*" +category +"*"+type+"*"+size);

					}
				}	
			}		
		}
		pw.close();
	}
	
	
	/*generate midi music list for files download from s3
	 * the name of every file is UUID+midiName
	 * In this method, we extract UUID and midiName and save them in midilist.txt in every subfolder*/
	static void generateMidiListFromS3Files(File f) throws FileNotFoundException{
		String path = f.getAbsolutePath();
		File songlist = new File(path+"/midilist.txt");
		PrintWriter pw = new PrintWriter(songlist); 
		File[] ff = f.listFiles();
		for(File child:ff){
			if(child.isDirectory()){
				generateMidiListFromS3Files(child);
			}else{
				if(child.getName().contains(".mid") && !child.getName().equals(".mid")){
					
					String fileName = child.getName();
					int index = UUID.randomUUID().toString().length();
					String id = child.getName().substring(0, index);
					
					String nameString = fileName.substring(index, fileName.length());
					String musicName = nameString.substring(0, nameString.length()-4); //get music name exclude the suffix
					int temp = path.lastIndexOf("/");
					String category = path.substring(temp+1,path.length()); //get category
					String type = "mid";
					category = "midi";
					long size = child.length();
					if(size > 40960  || size < 500){ //exclude files with size larger than 40K and smaller then 0.5K
					}
					else {
						pw.println(id+"*" + musicName + "*" +category +"*"+type+"*"+size);
					//	System.out.println(id+"*" + musicName + "*" +category +"*"+type+"*"+size);

					}
				}	
			}		
		}
		pw.close();
	}
	
	/*generate M4r music list for files download from s3
	 * the name of every file is UUID+midiName
	 * In this method, we extract UUID and midiName and save them in midilist.txt in every subfolder*/
	static void generateM4rListFromS3Files(File f) throws FileNotFoundException{
		String path = f.getAbsolutePath();
		File songlist = new File(path+"/m4rlist.txt");
		PrintWriter pw = new PrintWriter(songlist); 
		File[] ff = f.listFiles();
		for(File child:ff){
			if(child.isDirectory()){
				generateM4rListFromS3Files(child);
			}else{
				String nameStr = child.getName();
				if(nameStr.contains(" ")){
					nameStr = nameStr.split(" ")[1];
				}
				if(nameStr.contains(".m4a")){
					nameStr = path+"/"+nameStr.split("\\.m4a")[0]+".m4r";
					System.out.println(nameStr);
					child.renameTo(new File(nameStr));
				}
				if(child.getName().contains(".m4r") && !child.getName().equals(".m4r")){
					
					String fileName = child.getName();
					int index = UUID.randomUUID().toString().length();
					String id = child.getName().substring(0, index);
					
					String nameString = fileName.substring(index, fileName.length());
					String musicName = nameString.substring(0, nameString.length()-4); //get music name exclude the suffix
					int temp = path.lastIndexOf("/");
					String category = path.substring(temp+1,path.length()); //get category
					String type = "m4r";
					category = "m4r";
					long size = child.length();
					if(size > 1024000  || size < 500){ //exclude files with size larger than 1000K and smaller then 0.5K
					}
					else {
						pw.println(id+"*" + musicName + "*" +category +"*"+type+"*"+size);
						System.out.println(id+"*" + musicName + "*" +category +"*"+type+"*"+size);

					}
				}	
			}		
		}
		pw.close();
	}
}
