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
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(MusicItem.class);
		query.setRange(start*500, (start+1)*500);
		List<MusicItem> searchResult = null;
		try{
			searchResult = (List<MusicItem>) query.execute();
		}finally{
			query.closeAll();
		}
		
		for(int i = 0 ; i<searchResult.size(); i++){
			MusicItem mItem = searchResult.get(i);
			pm = PMF.get().getPersistenceManager();
			MusicItem m = pm.getObjectById(MusicItem.class, mItem.getKey());
			m.setAvg_rate((int)(Math.random()*5+1));
			m.setRate_count(1);
			m.setDownloadCount((int)(Math.random()*2000+500));
			pm.close();	
		}
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.println("update " + searchResult.size() + " items");
		//	pw.println("update  items");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		
	}
}
