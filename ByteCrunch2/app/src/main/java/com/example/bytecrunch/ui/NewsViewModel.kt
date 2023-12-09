package com.example.bytecrunch.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.Response
import com.example.bytecrunch.apiResponse.ResultsItem
import com.example.bytecrunch.apiResponse.ResponseAPI
import com.example.bytecrunch.repository.NewsRepo
import com.example.bytecrunch.helper.Resource
import com.example.bytecrunch.helper.Constants.Companion.COUNTRY_USA
import com.example.bytecrunch.helper.NewsApplication


class NewsViewModel (
        app: Application,
        val newsRepo: NewsRepo
) : AndroidViewModel(app) {

    /**
     * Setup Variables
     */
    val topNews: MutableLiveData<Resource<ResponseAPI>> = MutableLiveData()
    var topNewsPage = 1
    var topNewsResponse: ResponseAPI? = null

    val searchNews: MutableLiveData<Resource<ResponseAPI>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: ResponseAPI? = null
    var newSearchQuery:String? = null
    var oldSearchQuery:String? = null


    /**
     * Call getTopNew method
     */
    init {
        getTopNews(COUNTRY_USA)
    }

    /**
     * Function that execute API call
     */
    fun getTopNews(countryCode: String) = viewModelScope.launch {
//        safeTopNewsCall(countryCode)

        topNews.postValue(Resource.Loading())
        val response = newsRepo.getTopNews(countryCode, topNewsPage)
        topNews.postValue(handleTopNewsResponse(response))

    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
//        safeSearchNewsCall(searchQuery)


        newSearchQuery = searchQuery
        searchNews.postValue(Resource.Loading())
        val response = newsRepo.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))

    }


    private suspend fun safeTopNewsCall(countryCode: String) {
        topNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsRepo.getTopNews(countryCode, topNewsPage)
                topNews.postValue(handleTopNewsResponse(response))
            } else {
                topNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> topNews.postValue(Resource.Error("Network Failure"))
                else -> {
                    Log.e("TAG", "safeTopNewsCall: ", t)
                    topNews.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    /**
     * Handle API response and return success state or error state
     */
    private fun handleTopNewsResponse(response: Response<ResponseAPI>) : Resource<ResponseAPI> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->

                // Handle loading news
                topNewsPage++
                if(topNewsResponse == null) {
                    topNewsResponse = resultResponse
                } else {
                    val oldNews = topNewsResponse?.articles?.results
                    val newNews = resultResponse.articles?.results
                    newNews?.let { oldNews?.addAll(it) }
                }
                return Resource.Success(topNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private suspend fun safeSearchNewsCall(searchQuery: String) {
        newSearchQuery = searchQuery
        searchNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsRepo.searchNews(searchQuery, searchNewsPage)
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleSearchNewsResponse(response: Response<ResponseAPI>) : Resource<ResponseAPI> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(searchNewsResponse == null || newSearchQuery != oldSearchQuery) {
                    searchNewsPage = 1
                    oldSearchQuery = newSearchQuery
                    searchNewsResponse = resultResponse
                } else {
                    searchNewsPage++
                    val oldNews = searchNewsResponse?.articles?.results
                    val newNews = resultResponse.articles?.results
                    newNews?.let { oldNews?.addAll(it) }
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private val TAG = "NewsViewModel"
    fun saveArticle(resultsItem: ResultsItem) = viewModelScope.launch {
        Log.d(TAG, "saveArticle: resultItem is : " + resultsItem)
        newsRepo.updateInsert(resultsItem)
    }

    fun getSavedNews() = newsRepo.getSavedNews()

    fun deleteArticle(resultsItem: ResultsItem) = viewModelScope.launch {
        newsRepo.deleteArticle(resultsItem)
    }

//    // Saves a `ResultsItem` object representing a news article to the local database using the `newsRepo` instance.
//    fun saveArticle(resultsItem: ResultsItem) = viewModelScope.launch {
//        newsRepo.updateInsert(resultsItem)
//    }
//
//    // Retrieves all saved news articles from the local database using the `newsRepo` instance and returns the results as a LiveData stream.
//    fun getSavedNews(): LiveData<List<ResultsItem>> {
//        return newsRepo.getSavedNews()
//    }
//
//    // Deletes a `ResultsItem` object representing a saved news article from the local database using the `newsRepo` instance.
//    fun deleteArticle(resultsItem: ResultsItem) = viewModelScope.launch {
//        newsRepo.deleteArticle(resultsItem)
//    }





    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    /**
     * Getter function to get Koltin var for fragments written in Java
     */
    fun grabTopNews(): MutableLiveData<Resource<ResponseAPI>> {
        return topNews
    }

    fun grabSearchNews(): MutableLiveData<Resource<ResponseAPI>> {
        return searchNews
    }
}
