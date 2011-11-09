package com.kittypad.music.game.util;

import java.util.HashMap;
import java.util.Map;

import com.kittypad.music.game.translate.MTranslate;

public class CategoryItem {
	private String category;
	private int counts;
	public CategoryItem(String category,int counts)
	{
		this.category=category;
		this.counts=counts;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		
		this.counts = counts;
	}
	public Map<String,String> josonMap(String toLanguage) {
		Map<String, String> categoryMap = new HashMap<String, String>();
		String displayCategory;
		try {
			displayCategory = MTranslate.translate(category, toLanguage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			displayCategory=category;
		}
		categoryMap.put("category", category);
		categoryMap.put("displayCategory",displayCategory);
		categoryMap.put("counts", counts+"");
        return categoryMap;
	   
	}

	

}
