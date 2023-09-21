package com.theme.keyboardthemeapp.ModelClass;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GifModel{

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	@SerializedName("thumburl")
	private String thumburl;

	@SerializedName("url")
	private String url;

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
			"GifModel{" + 
			"categories = '" + categories + '\'' + 
			",thumburl = '" + thumburl + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}