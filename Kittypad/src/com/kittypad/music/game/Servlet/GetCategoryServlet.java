package com.kittypad.music.game.Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.json.JSONArray;
import com.kittypad.music.game.util.CategoryItem;
import com.kittypad.music.game.util.MusicItem;
import com.kittypad.music.game.util.UserMusicUtil;
import com.kittypad.music.game.util.UserUtil;

/**
 * Servlet implementation class GetCategoryServlet
 */
public class GetCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			UserMusicUtil.init();
			req.setCharacterEncoding("UTF-8");
			
			String startStr = req.getParameter("start");
		    String lang=req.getParameter("lang");
			int start = 0;
			if (startStr != null) {
				start = Integer.parseInt(startStr);
			}
			if(lang==null)
				lang="en";
			JSONArray jsonArray = new JSONArray();
		
				List<CategoryItem> results = null;
		   try{
			    
			     results=UserMusicUtil.getCategoryList(start);
			
			
				for (CategoryItem category : results) {
					Map<String, String> categoryMap = category.josonMap(lang);
					jsonArray.put(categoryMap);
				}
			String response = null;
			response = jsonArray.toString();
			resp.getOutputStream().write(response.getBytes());
			resp.flushBuffer();
		   }catch(Exception e){
			   e.printStackTrace(resp.getWriter());
			  // e.printStackTrace( resp.getOutputStream());
			   resp.flushBuffer();
			
		   }
		   UserUtil.disconnect();
		 
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			resp.getOutputStream().write(e1.getMessage().getBytes());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			resp.getOutputStream().write(e1.getMessage().getBytes());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
