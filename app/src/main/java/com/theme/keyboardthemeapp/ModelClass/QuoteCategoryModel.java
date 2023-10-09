package com.theme.keyboardthemeapp.ModelClass;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QuoteCategoryModel{

	@SerializedName("QuoteCategoryModel")
	private List<QuoteCategoryModelItem> quoteCategoryModel;

	public List<QuoteCategoryModelItem> getQuoteCategoryModel(){
		return quoteCategoryModel;
	}
}