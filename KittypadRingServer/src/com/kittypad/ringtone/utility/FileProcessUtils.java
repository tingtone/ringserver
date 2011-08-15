package com.kittypad.ringtone.utility;

import java.io.File;
import java.util.ArrayList;

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
	//	String path = "/Users/apple/Desktop/Funny music";
		String path = "/Users/apple/Desktop/midi/";
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
			handle(f);
		}
		System.out.println("Handle over");
		System.out.println("Have changed "+ changes+" file names");
	}
	
	private static void handle(File f) {
		// TODO Auto-generated method stub
		String fileName = f.getName();
		String filePath = f.getAbsolutePath();
		int temp = filePath.lastIndexOf("/");
		String path = filePath.substring(0, temp);
		String newfileName = fileName.toLowerCase();
		int index = newfileName.lastIndexOf("_");
		
	//	newfileName = newfileName.replaceAll("[^0-9a-zA-Z_ ]", "_");
		newfileName = path +"/" +newfileName.substring(0, index) + ".mid";
		System.out.println(newfileName);
		f.renameTo(new File(newfileName));
		/*if(fileName.endsWith(".mp3") && !fileName.equals(".mp3")){ //handle mp3 files only
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
		}*/
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
			//	if(child.getName().contains(".mp3")){
					fileArray.add(child);
					totalFileCount++;
			//	}
				
			}
		}
		return fileArray;
	}
	
}
