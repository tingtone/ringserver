package com.kittypad.music.game.Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kittypad.music.game.util.UserUtil;

/**
 * Servlet implementation class GetBeansServlet
 */
public class GetBeansServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String success="success";  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBeansServlet() {
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
			String UUID=req.getParameter("UUID");
			
			try {
				int beans=UserUtil.getBaseBeans(UUID);
				resp.getOutputStream().write((beans+"").getBytes());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.getOutputStream().write(e.getMessage().getBytes());
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
