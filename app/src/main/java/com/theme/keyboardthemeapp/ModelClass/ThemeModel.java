package com.theme.keyboardthemeapp.ModelClass;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ThemeModel{

	@SerializedName("ThemeModel")
	private List<ThemeModelItem> themeModel;

	public List<ThemeModelItem> getThemeModel(){
		return themeModel;
	}
}