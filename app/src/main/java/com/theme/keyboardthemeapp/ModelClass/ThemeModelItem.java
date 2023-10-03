package com.theme.keyboardthemeapp.ModelClass;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ThemeModelItem{

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	@SerializedName("thumburl")
	private String thumburl;

	@SerializedName("url")
	private String url;

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setCategories(List<CategoriesItem> categories){
		this.categories = categories;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
	}

	public void setThumburl(String thumburl){
		this.thumburl = thumburl;
	}

	public String getThumburl(){
		return thumburl;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"ThemeModelItem{" + 
			"created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",categories = '" + categories + '\'' + 
			",thumburl = '" + thumburl + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}