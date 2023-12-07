package com.example.bytecrunch.apiResponse;

import com.google.gson.annotations.SerializedName;

public class AuthorsItem{

	@SerializedName("isAgency")
	private boolean isAgency;

	@SerializedName("name")
	private String name;

	@SerializedName("type")
	private String type;

	@SerializedName("uri")
	private String uri;

	public boolean isIsAgency(){
		return isAgency;
	}

	public String getName(){
		return name;
	}

	public String getType(){
		return type;
	}

	public String getUri(){
		return uri;
	}

	@Override
 	public String toString(){
		return 
			"AuthorsItem{" + 
			"isAgency = '" + isAgency + '\'' + 
			",name = '" + name + '\'' + 
			",type = '" + type + '\'' + 
			",uri = '" + uri + '\'' + 
			"}";
		}
}