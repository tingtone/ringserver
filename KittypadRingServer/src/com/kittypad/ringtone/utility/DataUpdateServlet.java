package com.kittypad.ringtone.utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;


import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.kittypad.ringtone.MusicItem;
import com.kittypad.ringtone.PMF;
import com.kittypad.ringtone.SearchUtils;

public class DataUpdateServlet extends HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		int start = 0;
		String startStr= request.getParameter("start");
		if(startStr != null){
			start = Integer.parseInt(startStr);
		}
		
		StringBuffer queryBuffer = new StringBuffer();
		queryBuffer.append("SELECT * FROM " + MusicItem.class.getName());
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		List<MusicItem> result = null;
		Query query = pm.newQuery(queryBuffer.toString());
		query.setRange(start*500, (start+1)*500);
		result = (List<MusicItem>) query.execute();
		for(int i = 0 ; i<result.size(); i++){
			MusicItem mItem = result.get(i);
			mItem.setAvg_rate((int)(Math.random()*5+1));
			mItem.setRate_count(1);
			mItem.setDownloadCount((int)(Math.random()*2000+500));
			pm.makePersistent(mItem);
		}
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.println("update " + result.size() + " items");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		
	}
}
