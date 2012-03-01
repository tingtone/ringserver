package com.kittypad.music.game.Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.json.JSONArray;
import com.kittypad.music.game.util.UserItem;
import com.kittypad.music.game.util.UserUtil;

/**
 * Servlet implementation class UserInfoServlet
 */
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			UserUtil.init();
			String UUID=new String(req.getParameter("UUID").getBytes("ISO-8859-1"),"utf-8");
			JSONArray jsonArray = new JSONArray();
			try {
				UserItem userItem=UserUtil.getUserItem(UUID);
				if(userItem!=null)
					jsonArray.put(userItem.josonMap());
				String response = jsonArray.toString();
				resp.getOutputStream().write(response.getBytes());
				resp.flushBuffer();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
				resp.getWriter().append(e.getMessage());
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
