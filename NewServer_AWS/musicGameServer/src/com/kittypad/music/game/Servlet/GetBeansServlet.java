package com.kittypad.music.game.Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kittypad.music.game.util.MusicItem;
import com.kittypad.music.game.util.UserMusicUtil;
import com.kittypad.music.game.util.UserUtil;

/**
 * Servlet implementation class GetBeansServlet
 */
public class GetBeansServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @see HttpServlet#HttpServlet()
     */
    public GetBeansServlet() throws ClassNotFoundException, SQLException {
        super();
        // TODO Auto-generated constructor stub
        UserUtil.init();
        UserMusicUtil.init();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String UUID=req.getParameter("UUID");
		
		try {
			int beans=UserUtil.getBaseBeans(UUID);
			ArrayList<Integer> array=UserMusicUtil.getItemDownloadCountsOfUser(UUID);
			for(int i=0;i<array.size();i++)
				beans+=(array.get(i)>4?4:array.get(i))*5;
			resp.getOutputStream().write(beans);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resp.getWriter().append(e.getMessage());
		}
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	

}
