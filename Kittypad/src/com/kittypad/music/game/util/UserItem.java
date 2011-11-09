package com.kittypad.music.game.util;


public class UserItem {
	
	String UUID;//unique
	String email;
	String userName;
	String location;
	int kittyad_beans;
	public UserItem(String UUID,String email,String userName,String location,int kittypad_beans)
	{
		this.UUID=UUID;
		this.email=email;
		this.userName=userName;
		this.kittyad_beans=kittypad_beans;
		
	}
	public UserItem(String UUID,String email,String userName,String location,boolean giveBeans)
	{
		this.UUID=UUID;
		this.email=email;
		this.userName=userName;
		if(giveBeans)
		    this.kittyad_beans=5;
		else
			this.kittyad_beans=0;
		this.location=location;
	}
	


	public String getUUID() {
		return UUID;
	}
	public void setUUID(String UUID) {
	    this.UUID = UUID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getKittyad_beans() {
		return kittyad_beans;
	}
	public void setKittyad_beans(int kittyad_beans) {
		this.kittyad_beans = kittyad_beans;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	

}
