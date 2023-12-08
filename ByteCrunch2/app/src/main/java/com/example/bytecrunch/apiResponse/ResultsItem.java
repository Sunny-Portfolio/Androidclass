package com.example.bytecrunch.apiResponse;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;


//@Entity(tableName = "articles_table")
@Entity(tableName = "articles_table")
public class ResultsItem implements Serializable {


	// id variables for Room
	@PrimaryKey(autoGenerate = true)
	private int id;


	@SerializedName("date")
	private String date;

	@SerializedName("dateTime")
	private String dateTime;

	@SerializedName("image")
	private String image;

	@SerializedName("eventUri")
	private String eventUri;

//	@SerializedName("sentiment")
//	private Object sentiment;

//	@SerializedName("wgt")
//	private int wgt;

	@SerializedName("dataType")
	private String dataType;

	@SerializedName("dateTimePub")
	private String dateTimePub;

	@SerializedName("videos")
	private List<Object> videos;

	@SerializedName("source")
	private Source source;

	@SerializedName("title")
	private String title;

	@SerializedName("body")
	private String body;

	@SerializedName("uri")
	private String uri;

	@SerializedName("url")
	private String url;

	@SerializedName("relevance")
	private int relevance;

//	@SerializedName("sim")
//	private Object sim;

	@SerializedName("time")
	private String time;

	@SerializedName("lang")
	private String lang;

	@SerializedName("isDuplicate")
	private boolean isDuplicate;

	@SerializedName("authors")
	private List<Object> authors;

	@SerializedName("viewType")
	private int viewType = 0;


	@SerializedName("bgColor")
	private String bgColor = "GREY";







	/**
	 * Getters
	 */
	public String getBgColor() {
		return bgColor;
	}

	public int getViewType() {return viewType;}
	public int getId() {
		return id;
	}

	public String getDate(){
		return date;
	}

	public String getDateTime(){
		return dateTime;
	}

	public String getImage(){
		return image;
	}

	public String getEventUri(){
		return eventUri;
	}

//	public Object getSentiment(){
//		return sentiment;
//	}

//	public int getWgt(){
//		return wgt;
//	}

	public String getDataType(){
		return dataType;
	}

	public String getDateTimePub(){
		return dateTimePub;
	}

	public List<Object> getVideos(){
		return videos;
	}

	public Source getSource(){
		return source;
	}

	public String getTitle(){
		return title;
	}

	public String getBody(){
		return body;
	}

	public String getUri(){
		return uri;
	}

	public String getUrl(){
		return url;
	}

	public int getRelevance(){
		return relevance;
	}

//	public Object getSim(){
//		return sim;
//	}

	public String getTime(){
		return time;
	}

	public String getLang(){
		return lang;
	}

	public boolean isIsDuplicate(){
		return isDuplicate;
	}

	public List<Object> getAuthors(){
		return authors;
	}



	/**
	 * Setters
	 */
	public void setId(int id) {
		this.id = id;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setEventUri(String eventUri) {
		this.eventUri = eventUri;
	}

//	public void setSentiment(Object sentiment) {
//		this.sentiment = sentiment;
//	}

//	public void setWgt(int wgt) {
//		this.wgt = wgt;
//	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setDateTimePub(String dateTimePub) {
		this.dateTimePub = dateTimePub;
	}

	public void setVideos(List<Object> videos) {
		this.videos = videos;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setRelevance(int relevance) {
		this.relevance = relevance;
	}

//	public void setSim(Object sim) {
//		this.sim = sim;
//	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public void setDuplicate(boolean duplicate) {
		isDuplicate = duplicate;
	}

	public void setAuthors(List<Object> authors) {
		this.authors = authors;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}





	@Override
 	public String toString(){
		return "ResultsItem{" +
						"date = '" + date + '\'' +
						",dateTime = '" + dateTime + '\'' +
						",image = '" + image + '\'' +
						",eventUri = '" + eventUri + '\'' +
						",dataType = '" + dataType + '\'' +
						",dateTimePub = '" + dateTimePub + '\'' +
						",videos = '" + videos + '\'' +
						",source = '" + source + '\'' +
						",title = '" + title + '\'' +
						",body = '" + body + '\'' +
						",uri = '" + uri + '\'' +
						",url = '" + url + '\'' +
						",relevance = '" + relevance + '\'' +
						",time = '" + time + '\'' +
						",lang = '" + lang + '\'' +
						",isDuplicate = '" + isDuplicate + '\'' +
						",authors = '" + authors + '\'' +
						"}";
//		return
//			"ResultsItem{" +
//			"date = '" + date + '\'' +
//			",dateTime = '" + dateTime + '\'' +
//			",image = '" + image + '\'' +
//			",eventUri = '" + eventUri + '\'' +
//			",sentiment = '" + sentiment + '\'' +
//			",wgt = '" + wgt + '\'' +
//			",dataType = '" + dataType + '\'' +
//			",dateTimePub = '" + dateTimePub + '\'' +
//			",videos = '" + videos + '\'' +
//			",source = '" + source + '\'' +
//			",title = '" + title + '\'' +
//			",body = '" + body + '\'' +
//			",uri = '" + uri + '\'' +
//			",url = '" + url + '\'' +
//			",relevance = '" + relevance + '\'' +
//			",sim = '" + sim + '\'' +
//			",time = '" + time + '\'' +
//			",lang = '" + lang + '\'' +
//			",isDuplicate = '" + isDuplicate + '\'' +
//			",authors = '" + authors + '\'' +
//			"}";
	}
}