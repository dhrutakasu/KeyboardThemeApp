package com.theme.keyboardthemeapp.ModelClass;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ThemeModelItem{

	@SerializedName("keyboard")
	private List<KeyboardItem> keyboard;

	@SerializedName("id")
	private int id;

	@SerializedName("thumburl")
	private String thumburl;

	@SerializedName("url")
	private String url;

	public List<KeyboardItem> getKeyboard(){
		return keyboard;
	}

	public int getId(){
		return id;
	}

	public String getThumburl(){
		return thumburl;
	}

	public String getUrl(){
		return url;
	}
}