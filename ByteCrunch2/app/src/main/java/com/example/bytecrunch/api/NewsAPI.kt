package com.example.bytecrunch.api


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.bytecrunch.helper.Constants.Companion.NEWSAPI_API_KEY
import com.example.bytecrunch.helper.Constants.Companion.COUNTRY_USA
import com.example.bytecrunch.apiResponse.ResponseAPI
import java.time.LocalDate


interface NewsAPI {



    /**
     * Retrofit GET news from API
     */
    @GET("article/getArticles")
    suspend fun getTopNews(
            @Query("sourceLocationUri") countryUri: String = COUNTRY_USA,
            @Query("articlesPage") pageNum: Int = 1,
            @Query("lang") lang: String = "eng",
            @Query("dataType") dataType: String = "news",
//            @Query("dateStart") dateStart: String = LocalDate.now().toString(),
//            @Query("dateEnd") dateEnd: String = LocalDate.now().minusDays(3).toString(),
//            @Query("categoryUri") catUri: String = "news/Technology, news/Science",
//            @Query("sourceGroupUri") source: String = "technology/top20, ERtop/technology20",
            @Query("categoryUri") catUri: String = "news/Technology",
            @Query("sourceGroupUri") source: String = "technology/top20",
            @Query("sourceGroupUri") source1: String = "science/top15",
            @Query("sourceGroupUri") source2: String = "general/top25",
            @Query("startSourceRankPercentile") startRank: Int = 0,
            @Query("endSourceRankPercentile") endRank: Int = 20,
            @Query("ignoreSourceUri") igUri1: String = "dailymed.nlm.nih.gov",
            @Query("ignoreSourceUri") igUri2: String = "foxnews.com",
            @Query("ignoreSourceUri") igUri3: String = "",
            @Query("ignoreSourceUri") igUri4: String = "",
            @Query("apiKey") apiKey: String = NEWSAPI_API_KEY
    ): Response<ResponseAPI>

    @GET("article/getArticles")
    suspend fun searchNews(
            @Query("keyword") searchItem: String,
            @Query("page") pageNum: Int = 1,
            @Query("lang") lang: String = "eng",
            @Query("dataType") dataType: String = "news",
            @Query("sourceLocationUri") countryUri: String = COUNTRY_USA,
            @Query("categoryUri") catUri: String = "news/Technology",
            @Query("sourceGroupUri") source: String = "technology/top20",
            @Query("apiKey") apiKey: String = NEWSAPI_API_KEY
    ): Response<ResponseAPI>
}