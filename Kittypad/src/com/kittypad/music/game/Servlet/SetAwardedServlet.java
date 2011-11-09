package com.kittypad.music.game.Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.json.JSONArray;
import com.kittypad.music.game.util.MusicItem;
import com.kittypad.music.game.util.UserMusicUtil;

/**
 * Servlet implementation class SetAwardedServlet
 */
public class SetAwardedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String success="success";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetAwardedServlet() {
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
				
					String ID_Name=new String(req.getParameter("ID_Name").getBytes("ISO-8859-1"),"utf-8");
					
					try {
						UserMusicUtil.updateAwarded(ID_Name);
						resp.getOutputStream().write(success.getBytes());
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						
						resp.getOutputStream().write(e.getMessage().getBytes());
					}
					UserMusicUtil.disconnect();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				resp.getOutputStream().write(e1.getMessage().getBytes());
			
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				resp.getOutputStream().write(e1.getMessage().getBytes());
			}
			resp.flushBuffer();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
