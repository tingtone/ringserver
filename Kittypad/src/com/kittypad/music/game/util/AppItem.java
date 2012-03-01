package com.kittypad.music.game.util;

import java.util.HashMap;
import java.util.Map;

import com.kittypad.music.game.translate.MTranslate;

public class AppItem {
	String appName;
	String appVersion;
	String appLink;
	public AppItem(String appName,String appVersion,String appLink){
		this.appName=appName;
		this.appVersion=appVersion;
		this.appLink=appLink;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getAppLink() {
		return appLink;
	}
	public void setAppLink(String appLink) {
		this.appLink = appLink;
	}
	public Map<String,String> josonMap() {
		Map<String, String> appMap = new HashMap<String, String>();
		
		appMap.put("appname", appName);
		appMap.put("appversion",appVersion);
		appMap.put("applink", appLink);
        return appMap;
	   
	}
	
}
