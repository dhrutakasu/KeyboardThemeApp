package com.theme.keyboardthemeapp.ModelClass;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class JokeModel{

	@SerializedName("JokeModel")
	private List<JokeModelItem> jokeModel;

	public void setJokeModel(List<JokeModelItem> jokeModel){
		this.jokeModel = jokeModel;
	}

	public List<JokeModelItem> getJokeModel(){
		return jokeModel;
	}

	@Override
 	public String toString(){
		return 
			"JokeModel{" + 
			"jokeModel = '" + jokeModel + '\'' + 
			"}";
		}
}