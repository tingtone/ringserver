package com.kittypad.music.game.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kittypad.music.game.translate.MTranslate;

public class NewSongItem {
	private int index;

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	private Date add_date;
	private int size;
	private String s3_url;
	private String musicName;
	public Date getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Date add_date) {
		this.add_date = add_date;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getS3_url() {
		return s3_url;
	}
	public void setS3_url(String s3_url) {
		this.s3_url = s3_url;
	}
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public NewSongItem(int index,String musicName,int size,String s3_url,Date add_date)
	{
		this.index=index;
		this.musicName=musicName;
		this.size=size;
		this.s3_url=s3_url;
		this.add_date=add_date;
	}
	public NewSongItem(String musicName,int size,String s3_url){
		this.musicName=musicName;
		this.size=size;
		this.s3_url=s3_url;
		this.add_date=new Date();
		this.index=0;
		
	}
	public Map<String,String> josonMap(){
		Map<String, String> musicMap = new HashMap<String, String>();
		musicMap.put("url", "https://s3.amazonaws.com/" +s3_url+"/"+musicName+size+".mid");
		musicMap.put("musicName", musicName);
		musicMap.put("displayMusicName",musicName);
		musicMap.put("category","recommended");
		musicMap.put("displayCategory", "recommended");
		musicMap.put("type", "mid");
		musicMap.put("size", Integer.toString(size));
		musicMap.put("downloads", "0");
		musicMap.put("rate", "0");
		musicMap.put("awarded", "false");
       return musicMap;
	   
	}

}
