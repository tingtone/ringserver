/**
 * @author mingzhu
 * this servlet is used for update download_count recode in mysql when  someone download a music file
 * 
 */
 package com.kittypad.music.game.Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kittypad.music.game.util.UserMusicUtil;
import com.kittypad.music.game.util.UserUtil;



/**
 * Servlet implementation class DownloadIncServlet
 */
public class GameDownloadIncServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @see HttpServlet#HttpServlet()
     */
    public GameDownloadIncServlet() throws ClassNotFoundException, SQLException {
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
			int new_download_count=-1;
			try {
				 
				 new_download_count=UserMusicUtil.downloadCountIncrease(ringUrl);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} 
			String response = new_download_count+"";
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
