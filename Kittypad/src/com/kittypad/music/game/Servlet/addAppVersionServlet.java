package com.kittypad.music.game.Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kittypad.music.game.util.AppItem;
import com.kittypad.music.game.util.AppUtil;

/**
 * Servlet implementation class addAppVersionServlet
 */
public class addAppVersionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private final static String userName="feebee90";
	 private final static String password="schsch";
	 private final static String error="identification error";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addAppVersionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName=req.getParameter("userName");
		String password=req.getParameter("password");
		String appName=req.getParameter("appName");
		String appVersion=req.getParameter("appVersion");
		String appLink=req.getParameter("appLink");
		if(userName.equals(userName)&&password.equals(password)){
			AppItem appItem=new AppItem(appName,appVersion,appLink);
			try {
				AppUtil.init();
				AppUtil.insertItem(appItem);
				AppUtil.disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.getOutputStream().write(e.getMessage().getBytes());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			resp.getOutputStream().write(error.getBytes());
		}
		//resp.getOutputStream().write(userName.getBytes());
		//resp.getOutputStream().write(password.getBytes());
		//resp.getOutputStream().write(appName.getBytes());
		//resp.getOutputStream().write(appVersion.getBytes());
		//resp.getOutputStream().write(appLink.getBytes());
		
	}
		
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName=req.getParameter("userName");
		String password=req.getParameter("password");
		String appName=req.getParameter("appName");
		String appVersion=req.getParameter("appVersion");
		String appLink=req.getParameter("appLink");
		if(userName.equals(userName)&&password.equals(password)){
			AppItem appItem=new AppItem(appName,appVersion,appLink);
			try {
				AppUtil.init();
				AppUtil.insertItem(appItem);
				AppUtil.disconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				resp.getOutputStream().write(e.getMessage().getBytes());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			resp.getOutputStream().write(error.getBytes());
		}
		//resp.getOutputStream().write(userName.getBytes());
		//resp.getOutputStream().write(password.getBytes());
		//resp.getOutputStream().write(appName.getBytes());
		//resp.getOutputStream().write(appVersion.getBytes());
		//resp.getOutputStream().write(appLink.getBytes());
		
	}

}
