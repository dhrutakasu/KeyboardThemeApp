package com.theme.keyboardthemeapp.ModelClass;

import com.google.gson.annotations.SerializedName;

public class QuoteCategoryModelItem{

	@SerializedName("cat_name")
	private String catName;

	@SerializedName("id")
	private int id;

	public String getCatName(){
		return catName;
	}

	public int getId(){
		return id;
	}
}