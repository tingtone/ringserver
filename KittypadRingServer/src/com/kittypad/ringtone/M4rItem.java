/*copyright yanling
 *The class of Music Item 
 */

package com.kittypad.ringtone;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;




@PersistenceCapable(identityType= IdentityType.APPLICATION)
public class M4rItem {
	@Persistent
	@PrimaryKey
	private Key key;
	
	@Persistent
	private String UUID;
	
	@Persistent
	private String musicName;
	
	@Persistent
	private String category;
	
	@Persistent
	private String type;
	
	@Persistent
	private long size;
	
	@Persistent
	private Set<String> fts;
	
	
	@Persistent
	private String artist;
	
	@Persistent
	private int download_count;
	
	@Persistent
	private int avg_rate;
	
	@Persistent
	private int rate_count;
	
	@Persistent
	private Date add_date;
	
	public void setKey(Key key){
		this.key = key;
	}
	public void setFts(Set<String> fts){
		this.fts = fts;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setArtist (String artist){
		this.artist = artist;
	}
	
	public int getAvg_rate() {
		return avg_rate;
	}

	public void setAvg_rate(int avgRate) {
		this.avg_rate = avgRate;
	}
	
	public int getRate_count() {
		return rate_count;
	}

	public void setRate_count(int rateCount) {
		this.rate_count = rateCount;
	}
	
	public void setDownloadCount(int download_count){
		this.download_count = download_count;
	}
	
	public M4rItem(String UUID, String musicName, String category, Key key, String type, long size){
		this.UUID = UUID;
		this.category = category;
		this.musicName = musicName;
		this.setKey(key);
		this.type = type;
		this.size = size;
		this.avg_rate = (int)(Math.random()*5+1);
		this.rate_count = 1;
		
		this.fts = new HashSet<String>();
		SearchUtils.updateFTSStuffForM4rItem(this);
		this.download_count = (int)(Math.random()*2000+500);
		this.add_date = new Date();
	}
	
	public String getUUID(){
		return UUID;
	}
	public String getMusicName(){
		return musicName;
	}
	
	public String getCategory(){
		return category;
	}
	
	public String getType(){
		return type;
	}
	
	public Key getKey(){
		return key;
	}
	
	public long getSize(){
		return size;
	}
	
	public Set<String> getFts(){
		return fts;
	}
	
	
	public int getDownloadCount(){
		return download_count;
	}
	
	public Date getAddDate(){
		return add_date;
	}
	
	public String getArtist(){
		return artist;
	}
	
	public static void insert(String keyStr, String UUID, String musicName, String category, String type, long size){
		Key key = KeyFactory.createKey(M4rItem.class.getSimpleName(), keyStr);
		M4rItem item = new M4rItem(UUID, musicName, category, key, type, size);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.makePersistent(item);
	}
	
	public static List<M4rItem> getItems(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(MusicItem.class);
		//	query.setOrdering("when DESC");
		List<M4rItem> items = (List<M4rItem>) query.execute();
		return items;
	}

}
