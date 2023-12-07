package com.example.bytecrunch.apiResponse;

import com.google.gson.annotations.SerializedName;

public class ResponseAPI{

	@SerializedName("articles")
	private Articles articles;

	public Articles getArticles(){
		return articles;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"articles = '" + articles + '\'' + 
			"}";
		}
}