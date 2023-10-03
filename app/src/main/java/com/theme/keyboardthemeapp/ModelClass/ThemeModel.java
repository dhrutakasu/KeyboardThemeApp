package com.theme.keyboardthemeapp.ModelClass;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ThemeModel{

	@SerializedName("ThemeModel")
	private List<ThemeModelItem> themeModel;

	public void setThemeModel(List<ThemeModelItem> themeModel){
		this.themeModel = themeModel;
	}

	public List<ThemeModelItem> getThemeModel(){
		return themeModel;
	}

	@Override
 	public String toString(){
		return 
			"ThemeModel{" + 
			"themeModel = '" + themeModel + '\'' + 
			"}";
		}
}