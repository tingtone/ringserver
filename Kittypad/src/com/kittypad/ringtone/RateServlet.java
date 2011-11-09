package com.kittypad.ringtone;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kittypad.ringtone.util.MusicUtil;

/**
 * Servlet implementation class RateServlet
 */
public class RateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @see HttpServlet#HttpServlet()
     */
    public RateServlet() throws ClassNotFoundException, SQLException {
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
			String ringUrl=new String(req.getParameter("ID_Name").getBytes("ISO-8859-1"),"utf-8");
			String rateValue=req.getParameter("rateValue");
			int value=Integer.parseInt(rateValue);
			int new_value=-1;
			try {
				 
				new_value=MusicUtil.updateAvgRate(ringUrl, value);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} 
			String response = new_value+"";
			resp.getOutputStream().write(response.getBytes());
			resp.flushBuffer();
			MusicUtil.disconnect();
			
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
