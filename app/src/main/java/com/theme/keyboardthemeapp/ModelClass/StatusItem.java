package com.theme.keyboardthemeapp.ModelClass;

import com.google.gson.annotations.SerializedName;

public class StatusItem{

	@SerializedName("status")
	private String status;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"StatusItem{" + 
			"status = '" + status + '\'' + 
			"}";
		}
}