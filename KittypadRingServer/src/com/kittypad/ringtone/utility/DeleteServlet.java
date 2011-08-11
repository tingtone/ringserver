package com.kittypad.ringtone.utility;

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
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;


public class DeleteServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		final String kind = request.getParameter("kind");
		if(kind == null) {
			throw new NullPointerException();
		}
		
		final long start = System.currentTimeMillis();
		int deleted_count = 0;
		boolean is_finished = false;

		final DatastoreService dss = DatastoreServiceFactory
				.getDatastoreService();
		
		while (System.currentTimeMillis() - start < 16384) {
			final Query query = new Query(kind);
			query.setKeysOnly();
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
									+ taskcount).method(Method.GET));
		}
	}
}
