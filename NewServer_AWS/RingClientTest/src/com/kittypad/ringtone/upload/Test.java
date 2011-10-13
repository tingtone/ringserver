package com.kittypad.ringtone.upload;

import java.io.File;

public class Test {
	
	public static void main(String [] args){
		File file=new File("./mid/ymz_playing.mid");
		String UUID="SYSTEM";
		String musicName="ymz_playing";
		String type="mid";
		Util.uploadMusic(file, UUID, musicName, type);
		File file2=new File("./mid/playing.mid");
		String UUID2="SYSTEM2";
		String musicName2="playing";
		String type2="mid";
		String category="china";
		String artist="ymz";
		Util.uploadMusic(file2, UUID2, musicName2, type2, artist, category);
		
	}

}
