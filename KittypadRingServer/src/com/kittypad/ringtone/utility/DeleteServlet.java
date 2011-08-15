package com.kittypad.ringtone.utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;


public class DeleteServlet extends HttpServlet {
	/**
	 * this servlet is used to delete items in database
	 * if not type parameter, we will delete all 
	 * the url must gives the user and password so that the data are not deleted without caution
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		final String kind = request.getParameter("kind");
		final String type = request.getParameter("type");
		final String user = request.getParameter("user");
		final String password = request.getParameter("password");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user == null || password == null || !user.equals("admin") || !password.equals("admin")){
			pw.println("Permission denied!");
			return;
			
		}
		
		if(kind == null) {
			throw new NullPointerException();
		}
		
		final long start = System.currentTimeMillis();
		int deleted_count = 0;
		boolean is_finished = false;

		final DatastoreService dss = DatastoreServiceFactory
				.getDatastoreService();
		
		while (System.currentTimeMillis() - start < 16384) {
			Query query = new Query(kind);
			if(type == null){
				query.setKeysOnly();
			}
			else{
				query.addFilter("type", FilterOperator.EQUAL, type);
				pw.println("type to be deleted is "+type);
			}
			final ArrayList<Key> keys = new ArrayList<Key>();
			
			for (final Entity entity : dss.prepare(query).asIterable(
					FetchOptions.Builder.withLimit(128))) {
				keys.add(entity.getKey());
			}

			keys.trimToSize();

			if (keys.size() == 0) {
				is_finished = true;
				break;
			}

			while (System.currentTimeMillis() - start < 16384) {
				try {
					dss.delete(keys);
					deleted_count += keys.size();
					break;
				} catch (Throwable ignore) {
					continue;
				}
			}
		}
		
		if(!is_finished) {
			final int taskcount;
			final String tcs = request.getParameter("taskcount");
			if (tcs == null) {
				taskcount = 0;
			} else {
				taskcount = Integer.parseInt(tcs) + 1;
			}
			
			QueueFactory.getDefaultQueue().add(
					TaskOptions.Builder.withUrl("/del?kind=" + kind
									+ "&taskcount="
									+ taskcount+"&type="+type+"&user="
									+ user+"&password="+password).method(Method.GET));
		}
	}
}
