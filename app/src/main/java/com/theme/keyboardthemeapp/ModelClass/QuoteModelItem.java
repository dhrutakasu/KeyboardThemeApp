package com.theme.keyboardthemeapp.ModelClass;

import com.google.gson.annotations.SerializedName;

public class QuoteModelItem{

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private String status;

	public int getParentId(){
		return parentId;
	}

	public int getId(){
		return id;
	}

	public String getStatus(){
		return status;
	}
}