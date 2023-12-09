package com.example.bytecrunch.apiResponse;



import android.util.Log;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import java.util.Random;





public class Articles{



	@SerializedName("totalResults")
	private int totalResults;

	@SerializedName("pages")
	private int pages;

	@SerializedName("count")
	private int count;

	@SerializedName("page")
	private int page;

	@SerializedName("results")
	private List<ResultsItem> results;


	/**
	 * Getters
	 */
	public int getTotalResults(){
		return totalResults;
	}

	public int getPages(){
		return pages;
	}

	public int getCount(){
		return count;
	}

	public int getPage(){
		return page;
	}

	public List<ResultsItem> getResults(){

		// set a background color for each resultsitem
		for (int i = 0; i < results.size(); i++) {
			ResultsItem resultsItem = results.get(i);
			resultsItem.setViewType(generateViewType(i));
			resultsItem.setBgColor(generateRandomBgColor());
			Log.d("TAG", "getResults: here is i " + i +
					"  View Type: " + resultsItem.getViewType() +
					"  BG color : " + resultsItem.getBgColor());
		}

		return results;
	}

	/**
	 * Setters
	 */
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setResults(List<ResultsItem> results) {
		this.results = results;
	}



	private int generateViewType(int position){

		if (position == 0) {
			return 1;
		}
		else if (position == 1) {
			return 0;
		}

		if (position != 0) {
			int ir = position/2 +1 ;
			if (ir%2 == 0) {
				if (position %2 == 0) {
					return 0;
				}
				else
					return 1;
			}
			else
			{
				if (position %2 == 0) {
					return 1;
				}
				else
					return 0;
			}
		}
		return 0;
	}

	// generate a random news background color
	private String generateRandomBgColor(){
		final String[] lstColors = {"RED","ORANGE","GREY","PINK","BLUE","GREEN"};
		Random random = new Random();
		int index = random.nextInt(lstColors.length);
		return lstColors[index];
	}


	@Override
 	public String toString(){
		return 
			"Articles{" + 
			"totalResults = '" + totalResults + '\'' + 
			",pages = '" + pages + '\'' + 
			",count = '" + count + '\'' + 
			",page = '" + page + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}
}