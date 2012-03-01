package com.kittypad.music.game.Servlet;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.util.json.JSONArray;
import com.kittypad.music.game.util.MusicItem;
import com.kittypad.music.game.util.NewSongItem;
import com.kittypad.music.game.util.NewSongUtil;
import com.kittypad.music.game.util.S3StorageManager;
import com.kittypad.music.game.util.UserMusicUtil;

/**
 * Servlet implementation class UploadNewSongServlet
 */
public class UploadNewSongServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;	
    private static String bucketName = "kittypad_music_game";
    private S3StorageManager manager;
    
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadNewSongServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  try {
			  NewSongUtil.init();
			//  UserUtil.init();
			
		     manager=new S3StorageManager();
			 InputStream stream = null;
			    stream=req.getInputStream();
			    String musicName=new String(req.getParameter("musicName").getBytes("ISO-8859-1"),"utf-8");
			    int size=Integer.parseInt(req.getParameter("size"));
			    NewSongItem newSongItem=new NewSongItem(musicName,size,bucketName);	
			    DataInputStream data=new DataInputStream(stream);
			    manager.storePublicRead(newSongItem, data, true);
			  
			    try {
					NewSongUtil.insertItem(newSongItem);
				//	UserUtil.insertItem(userItem);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					resp.getWriter().append(e.getMessage());
				}
				//UserUtil.disconnect();
				NewSongUtil.disconnect();
				JSONArray jsonArray = new JSONArray();
				jsonArray.put(newSongItem.josonMap());
				resp.getOutputStream().write(jsonArray.toString().getBytes());
			    
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			resp.getOutputStream().write(e1.getMessage().getBytes());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			resp.getOutputStream().write(e1.getMessage().getBytes());
		}

	}

}
