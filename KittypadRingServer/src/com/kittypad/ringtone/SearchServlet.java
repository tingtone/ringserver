package com.kittypad.ringtone;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.org.json.JSONArray;

/*
 * The SearchServlet response to the search request
 * This version only provides keywords searching
 * the response is a jsonarray
 */

public class SearchServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String key = req.getParameter("q");
		String startStr = req.getParameter("start");
		String type = req.getParameter("type");
		String platform = req.getParameter("platform");
		if(platform == null){
			platform = "android";
		}
		int start = 0;
		if (startStr != null) {
			start = Integer.parseInt(startStr);
		}
		JSONArray jsonArray = new JSONArray();
		if(platform.equals("android")){
			List<MusicItem> searchResults = null;
			if(type == null || type.equals("keyword")){
				searchResults = SearchUtils.getResultsByKeyword(key, start);
			}
			else if(type.equals("category")){
				searchResults = SearchUtils.getResultsByCategory(key, start);
			}
			else if(type.equals("download_count")){
				searchResults = SearchUtils.getResultsByDownloadCount(start);
			}
			else if(type.equals("add_date")){
				searchResults = SearchUtils.getResultsByDate(start);
			}
			else if(type.equals("artist")){
				searchResults = SearchUtils.getResultsByArtist(key, start);
			}
			else if(type.equals("random")) {
				searchResults = SearchUtils.getResultsByRandom();
			}
			
			
			
			for (MusicItem musicItem : searchResults) {
				Map<String, String> musicMap = new HashMap<String, String>();
				if(musicItem.getType().equals("mp3")){
					musicMap.put("url", "https://s3.amazonaws.com/kittypad_ringtone/" + musicItem.getUUID()+musicItem.getMusicName()+"."+musicItem.getType());
				}
				else if(musicItem.getType().equals("mid")){
					musicMap.put("url", "https://s3.amazonaws.com/kittypad_ringtone_midi/" + musicItem.getUUID()+musicItem.getMusicName()+"."+musicItem.getType());
				}
				
				musicMap.put("musicName", musicItem.getMusicName());
				musicMap.put("category", musicItem.getCategory());
				musicMap.put("type", musicItem.getType());
				musicMap.put("size", Long.toString(musicItem.getSize()));
				musicMap.put("downloads", Integer.toString(musicItem.getDownloadCount()));
				musicMap.put("rate", Integer.toString(musicItem.getAvg_rate()));
				jsonArray.put(musicMap);
			}
		}
		else if(platform.equals("iOS")){  //platform is iOS
			List<MusicItem> searchResults = null;
			if(type == null || type.equals("keyword")){
				searchResults = SearchUtils.getResultsByKeywordIOS(key, start);
			}
			else if(type.equals("category")){
				searchResults = SearchUtils.getResultsByCategoryIOS(key, start);
			}
			else if(type.equals("download_count")){
				searchResults = SearchUtils.getResultsByDownloadCountIOS(start);
			}
			else if(type.equals("add_date")){
				searchResults = SearchUtils.getResultsByDateIOS(start);
			}
			else if(type.equals("artist")){
				searchResults = SearchUtils.getResultsByArtistIOS(key, start);
			}
			else if(type.equals("random")) {
				searchResults = SearchUtils.getResultsByRandomIOS();
			}
			
			
			
			for (MusicItem musicItem : searchResults) {
				Map<String, String> musicMap = new HashMap<String, String>();
				if(musicItem.getType().equals("mp3")){
					musicMap.put("url", "https://s3.amazonaws.com/kittypad_ringtone/" + musicItem.getUUID()+musicItem.getMusicName()+"."+musicItem.getType());
				}
				else if(musicItem.getType().equals("mid")){
					musicMap.put("url", "https://s3.amazonaws.com/kittypad_ringtone_midi/" + musicItem.getUUID()+musicItem.getMusicName()+"."+musicItem.getType());
				}
				
				musicMap.put("musicName", musicItem.getMusicName());
				musicMap.put("category", musicItem.getCategory());
				musicMap.put("type", musicItem.getType());
				musicMap.put("size", Long.toString(musicItem.getSize()));
				musicMap.put("downloads", Integer.toString(musicItem.getDownloadCount()));
				musicMap.put("rate", Integer.toString(musicItem.getAvg_rate()));
				jsonArray.put(musicMap);
			}
		}
		
		String response = null;
		response = jsonArray.toString();
		resp.getOutputStream().write(response.getBytes());
		resp.flushBuffer();
	}
}
