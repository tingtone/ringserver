package com.kittypad.ringtone;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * use this servlet to init the music database
 * There is an application HttpRequest to provide all music info
 */
public class MusicItemServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		String UUID = request.getParameter("UUID");
		String musicName = request.getParameter("musicName");
		String category = request.getParameter("category");
		String keyStr = request.getParameter("keyStr");
		String type = request.getParameter("type");
		long size = Long.parseLong(request.getParameter("size"));
		if(type.equals("m4r") || type.equals("aac")){
			M4rItem.insert(keyStr, UUID, musicName, category, type, size);
		}
		else{
			MusicItem.insert(keyStr, UUID, musicName, category, type, size);
		}		
	}
}
