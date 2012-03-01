package com.kittypad.music.game.Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.json.JSONArray;
import com.kittypad.music.game.util.AppItem;
import com.kittypad.music.game.util.AppUtil;
import com.kittypad.music.game.util.NewSongItem;
import com.kittypad.music.game.util.NewSongUtil;

/**
 * Servlet implementation class NewSongServlet
 */
public class NewSongServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewSongServlet() {
        super();
        // TODO Auto-generated constructor stub
      
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int index=Integer.parseInt(req.getParameter("index"));
		try {
			NewSongUtil.init();
			JSONArray jsonArray = new JSONArray();
			 NewSongItem newSongItem=NewSongUtil.getRecommendSong(index);
			if(newSongItem!=null){
			Map<String, String> songMap=newSongItem.josonMap();
			jsonArray.put(songMap);
			}
			String response = null;
			response = jsonArray.toString();
			resp.getOutputStream().write(response.getBytes());
			resp.flushBuffer();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
