package com.theme.keyboardthemeapp.ModelClass;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GifModelItem{

	@SerializedName("hindithemekeyboard")
	private List<HindithemekeyboardItem> hindithemekeyboard;

	@SerializedName("id")
	private int id;

	@SerializedName("thumburl")
	private String thumburl;

	@SerializedName("url")
	private String url;

	public void setHindithemekeyboard(List<HindithemekeyboardItem> hindithemekeyboard){
		this.hindithemekeyboard = hindithemekeyboard;
	}

	public List<HindithemekeyboardItem> getHindithemekeyboard(){
		return hindithemekeyboard;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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
			"GifModelItem{" + 
			"hindithemekeyboard = '" + hindithemekeyboard + '\'' + 
			",id = '" + id + '\'' + 
			",thumburl = '" + thumburl + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}