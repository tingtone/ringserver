package com.kittypad.music.game.Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kittypad.music.game.util.UserItem;
import com.kittypad.music.game.util.UserUtil;

/**
 * Servlet implementation class UserRegisterServlet
 */
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int recommendedBeans=100;
	private static final String success="success";  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegisterServlet() {
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
			
			String userName=req.getParameter("userName");
			if(userName==null)
				userName="";
			else
				userName=new String(userName.getBytes("ISO-8859-1"),"utf-8");
			String location=req.getParameter("location");
			if(location==null)
				location="";
			else
				location=new String(location.getBytes("ISO-8859-1"),"utf-8");
			String email=req.getParameter("email");
			if(email==null)
				email="";
			String recommender=req.getParameter("recommender");

			if(recommender!=null)
			{
				recommender=new String(recommender.getBytes("ISO-8859-1"),"utf-8");
				UserUtil.updateBeans(recommender,recommendedBeans);
			}
			UserItem user=new UserItem(UUID,email,userName,location,false);
			
			try {
				UserUtil.registerItem(user);
				resp.getOutputStream().write(success.getBytes());
			
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
