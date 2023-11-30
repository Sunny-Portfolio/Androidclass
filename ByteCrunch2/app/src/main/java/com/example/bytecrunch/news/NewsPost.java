package com.example.bytecrunch.news;

public class NewsPost {

    int postID;
    String title, username, newsImg, userImg, article, date, bgColor, videoURL;

    int viewType = 0;
    boolean isFav = false;


    /**
     * Setup Constructors
     */
    public NewsPost() {
    }

    public NewsPost(int postID, String title, String username, String newsImg, String userImg, String article, String date, String bgColor, String videoURL, int viewType, boolean isFav) {
        this.postID = postID;
        this.title = title;
        this.username = username;
        this.newsImg = newsImg;
        this.userImg = userImg;
        this.article = article;
        this.date = date;
        this.bgColor = bgColor;
        this.videoURL = videoURL;
        this.viewType = viewType;
        this.isFav = isFav;
    }

    public NewsPost(int postID, String title, String username, String newsImg, String userImg, String article, String date, String bgColor, int viewType) {
        this.postID = postID;
        this.title = title;
        this.username = username;
        this.newsImg = newsImg;
        this.userImg = userImg;
        this.article = article;
        this.date = date;
        this.bgColor = bgColor;
        this.viewType = viewType;
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
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setDate(String date) {
        this.date = date;
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
        return newsImg;
    }

    public String getUserImg() {
        return userImg;
    }

    public String getArticle() {
        return article;
    }

    public String getDate() {
        return date;
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
                ", date='" + date + '\'' +
                ", bgColor='" + bgColor + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", viewType=" + viewType +
                ", isFav=" + isFav +
                '}';
    }
}
