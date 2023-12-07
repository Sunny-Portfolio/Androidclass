package com.example.bytecrunch.api;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface NewsAPIJava {


    // TODO: 12/1/23 The var are not set, do it when I call these function
    @GET("article/getArticles")
    Call<Response> getTopNews(
            @Query("sourceLocationUri") String country,
            @Query("articlesPage") int pageNumber,
//            @Query("articlesSortBy") int sortBy,



//            @Query("sourceGroupUri") String sourceGroup,
//            @Query("categoryUri") String category,
//            @Query("lang") String lang,
            @Query("apiKey") String apiKey
    );

    @GET("article/getArticles")
    Call<Response> searchNews(
            @Query("q") String searchQuery,
            @Query("page") int pageNumber,
            @Query("apiKey") String apikey
    );

}


//        {
//        "$or": [
//        {
//        "sourceUri": "theverge.com"
//        },
//        {
//        "sourceLocationUri": "http://en.wikipedia.org/wiki/United_States"
//        },
//        {
//        "sourceLocationUri": "http://en.wikipedia.org/wiki/Canada"
//        },
//        {
//        "sourceGroupUri": "technology/top20"
//        }
//
//        ======
//        "conceptUri": "http://en.wikipedia.org/wiki/Technology"
//        },
//        {
//        "conceptUri": "http://en.wikipedia.org/wiki/Information_technology"
//        },
//        {
//        "conceptUri": "http://en.wikipedia.org/wiki/High_tech"
//        },
//        {
//        "categoryUri": "news/Technology"
//        },
//        {
//        "dateStart": "2023-12-01",
//        "dateEnd": "2023-12-01",
//        "lang": "eng"



