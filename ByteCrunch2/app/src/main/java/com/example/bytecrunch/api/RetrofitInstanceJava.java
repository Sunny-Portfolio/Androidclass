package com.example.bytecrunch.api;



import com.example.bytecrunch.helper.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;


public class RetrofitInstanceJava {
    private static Retrofit retrofit = null;
    private static HttpLoggingInterceptor logging;
    private static NewsAPI newsAPI = null;


    // Create a singleton Retrofit instance
    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {

            // Create HttpLoggingInterceptor and set its level
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Create OkHttpClient and attach HttpLoggingInterceptor
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            // Build the retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.NEWSAPI_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    /**
     * Instantiation of the API
     */
    public static NewsAPI getApi() {
        if (newsAPI == null) {
            newsAPI = getRetrofitInstance().create(NewsAPI.class);
        }
        return newsAPI;
    }
}
