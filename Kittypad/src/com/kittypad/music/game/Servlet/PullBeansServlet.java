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
 * Servlet implementation class PushBeansServlet
 */
public class PullBeansServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final int pullBeans=-100;
    private static final String success="success";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PullBeansServlet() {
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
			req.setCharacterEncoding("UTF-8");
			String UUID=req.getParameter("UUID");
			int count=Integer.parseInt(req.getParameter("count"));
			try {
				UserUtil.updateBeans(UUID, pullBeans*count);
				resp.getOutputStream().write(success.getBytes());
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
