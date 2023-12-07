package com.example.bytecrunch.apiResponse;

import com.google.gson.annotations.SerializedName;

public class Source{

	@SerializedName("dataType")
	private String dataType;

	@SerializedName("title")
	private String title;

	@SerializedName("uri")
	private String uri;

	public String getDataType(){
		return dataType;
	}

	public String getTitle(){
		return title;
	}

	public String getUri(){
		return uri;
	}

	@Override
 	public String toString(){
		return 
			"Source{" + 
			"dataType = '" + dataType + '\'' + 
			",title = '" + title + '\'' + 
			",uri = '" + uri + '\'' + 
			"}";
		}
}