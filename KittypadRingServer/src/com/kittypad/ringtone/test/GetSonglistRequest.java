package com.kittypad.ringtone.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class GetSonglistRequest {
	public static void main(String[] args) {
	    //System.out.println("");
		String urlString = "http://bingliu630.appspot.com/songlist";
		URL url;
		
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
		
	}
	

}
