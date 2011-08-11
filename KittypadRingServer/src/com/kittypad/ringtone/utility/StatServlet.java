package com.kittypad.ringtone.utility;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;

public class StatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity globalStat = datastore.prepare(new Query("__Stat_Total__")).asSingleEntity();
		Long totalBytes = (Long) globalStat.getProperty("bytes");
		Long totalEntities = (Long) globalStat.getProperty("count");
		
		resp.getWriter().print("count:"+totalEntities+"  size:"+totalBytes);
	}
}
