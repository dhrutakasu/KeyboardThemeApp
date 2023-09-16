package com.theme.keyboardthemeapp.ModelClass;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QuoteCategoryModel{

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	@SerializedName("url")
	private String url;

	public void setCategories(List<CategoriesItem> categories){
		this.categories = categories;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}
}