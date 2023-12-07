package com.example.bytecrunch.news;

import android.util.Log;

import java.util.Date;

public class NewsPost {

    int postID;
    String title, username, newsImg, userImg, article, bgColor, videoURL;
    Date articleDate;



    String url;

    int viewType = 0;
    boolean isFav = false;


    /**
     * Setup Constructors
     */
    public NewsPost() {
    }


    // TODO: 12/2/23 The constructor needs to be updated for updated var. Current is for fake data, not the API
    public NewsPost(int postID, String title, String username, String newsImg, String userImg, String article, String bgColor, Date date, String videoURL, int viewType, boolean isFav) {
        this.postID = postID;
        this.title = title;
        this.username = username;
        this.newsImg = newsImg;
        this.userImg = userImg;
        this.article = article;
        this.articleDate = date;
        this.bgColor = bgColor;
        this.videoURL = videoURL;
        this.viewType = viewType;
        this.isFav = isFav;

        Log.d("TAG", "NewsPost: 1 " + NewsPost.this);
    }

    public NewsPost(int postID, String title, String username, String newsImg, String userImg, String article, String bgColor, Date date, int viewType) {
        this.postID = postID;
        this.title = title;
        this.username = username;
        this.newsImg = newsImg;
        this.userImg = userImg;
        this.article = article;
        this.articleDate = date;
        this.bgColor = bgColor;
        this.viewType = viewType;

        Log.d("TAG", "NewsPost: 2 " + NewsPost.this);

    }

    /**
     * Setters
     */
    public void setPostID(int postID) {
        this.postID = postID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNewsImg(String newsImg) {
        this.newsImg = newsImg;
        Log.d("NEWS", "setNewsImg: set this : " + newsImg);
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setDate(Date date) {
        this.articleDate = date;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public void setArticleDate(Date articleDate) {
        this.articleDate = articleDate;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * Getters
     */
    public int getPostID() {
        return postID;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getNewsImg() {
        Log.d("NEWS", "getNewsImg: get this : " + newsImg);
        return newsImg;
    }

    public String getUserImg() {
        return userImg;
    }

    public String getArticle() {
        return article;
    }

    public Date getDate() {
        return articleDate;
    }

    public String getBgColor() {
        return bgColor;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public int getViewType() {
        return viewType;
    }

    public boolean isFav() {
        return isFav;
    }


    public Date getArticleDate() {
        return articleDate;
    }



    public String getUrl() {
        return url;
    }



    /**
     * Overwrite toString()
     */
    @Override
    public String toString() {
        return "NewsPost{" +
                "postID=" + postID +
                ", title='" + title + '\'' +
                ", username='" + username + '\'' +
                ", newsImg='" + newsImg + '\'' +
                ", userImg='" + userImg + '\'' +
                ", article='" + article + '\'' +
                ", date='" + articleDate + '\'' +
                ", bgColor='" + bgColor + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", viewType=" + viewType +
                ", isFav=" + isFav +
                '}';
    }
}
