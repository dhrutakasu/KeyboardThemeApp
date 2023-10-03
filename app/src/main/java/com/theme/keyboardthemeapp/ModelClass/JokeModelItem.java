package com.theme.keyboardthemeapp.ModelClass;

import com.google.gson.annotations.SerializedName;

public class JokeModelItem{

	@SerializedName("parent_id")
	private int parentId;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private String status;

	public void setParentId(int parentId){
		this.parentId = parentId;
	}

	public int getParentId(){
		return parentId;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"JokeModelItem{" + 
			"parent_id = '" + parentId + '\'' + 
			",id = '" + id + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}