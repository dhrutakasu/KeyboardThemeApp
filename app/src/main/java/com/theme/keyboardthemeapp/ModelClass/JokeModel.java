package com.theme.keyboardthemeapp.ModelClass;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class JokeModel{

	@SerializedName("url")
	private String url;

	@SerializedName("status")
	private List<StatusItem> status;

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setStatus(List<StatusItem> status){
		this.status = status;
	}

	public List<StatusItem> getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"JokeModel{" + 
			"url = '" + url + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}