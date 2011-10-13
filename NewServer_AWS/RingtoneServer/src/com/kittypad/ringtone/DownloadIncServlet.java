/**
 * @author mingzhu
 * this servlet is used for update download_count recode in mysql when  someone download a music file
 * 
 */
 package com.kittypad.ringtone;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kittypad.ringtone.util.MusicUtil;

/**
 * Servlet implementation class DownloadIncServlet
 */
public class DownloadIncServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @see HttpServlet#HttpServlet()
     */
    public DownloadIncServlet()  {
        super();
        // TODO Auto-generated constructor stub
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 try {
			MusicUtil.init();
			String ringUrl=req.getParameter("ID_Name");
			int new_download_count=-1;
			try {
				 
				 new_download_count=MusicUtil.downloadCountIncrease(ringUrl);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} 
			String response = new_download_count+"";
			resp.getOutputStream().write(response.getBytes());
			resp.flushBuffer();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
