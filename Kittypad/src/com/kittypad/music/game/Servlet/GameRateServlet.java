package com.kittypad.music.game.Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kittypad.music.game.util.UserMusicUtil;


/**
 * Servlet implementation class RateServlet
 */
public class GameRateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String success="success";   
    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @see HttpServlet#HttpServlet()
     */
    public GameRateServlet() throws ClassNotFoundException, SQLException {
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
			String ringUrl=req.getParameter("ID_Name");
			if(ringUrl!=null)
				ringUrl=new String(ringUrl.getBytes("ISO-8859-1"),"utf-8");
			String rateValue=req.getParameter("rateValue");
			int value=Integer.parseInt(rateValue);
			int new_value=-1;
			try {
				 
				new_value=UserMusicUtil.updateAvgRate(ringUrl, value);
				resp.getOutputStream().write(success.getBytes());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			//	resp.getOutputStream().write(e1.getMessage().getBytes());
			} 
			String response = new_value+"";
			resp.getOutputStream().write(response.getBytes());
			resp.flushBuffer();
			UserMusicUtil.disconnect();
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
