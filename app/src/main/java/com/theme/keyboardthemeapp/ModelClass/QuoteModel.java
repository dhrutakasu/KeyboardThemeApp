package com.theme.keyboardthemeapp.ModelClass;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QuoteModel{

	@SerializedName("QuoteModel")
	private List<QuoteModelItem> quoteModel;

	public List<QuoteModelItem> getQuoteModel(){
		return quoteModel;
	}
}