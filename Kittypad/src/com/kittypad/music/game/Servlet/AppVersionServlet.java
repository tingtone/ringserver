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

/**
 * Servlet implementation class AppVersionServlet
 */
public class AppVersionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppVersionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String appName=req.getParameter("appname");
		try {
			AppUtil.init();
			JSONArray jsonArray = new JSONArray();
			AppItem appItem=AppUtil.getLatestAppItem(appName);
			if(appItem!=null){
			Map<String, String> appMap=appItem.josonMap();
			jsonArray.put(appMap);
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
