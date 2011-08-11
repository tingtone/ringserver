package com.kittypad.ringtone.utility;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;

public class SharedUtils {
	public static long getTotalRingCount() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity globalStat = datastore.prepare(new Query("__Stat_Total__")).asSingleEntity();
		Long totalEntities = (Long) globalStat.getProperty("count");
		
		return totalEntities;
	}
}
