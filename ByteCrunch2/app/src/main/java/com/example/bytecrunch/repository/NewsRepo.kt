package com.example.bytecrunch.repository

import com.example.bytecrunch.apiResponse.ResultsItem
import com.example.bytecrunch.api.RetrofitInstance
import com.example.bytecrunch.localdb.ArticleDB

class NewsRepo ( val db: ArticleDB ) {

        /**
         * Fetches top news articles from the API based on the provided country code and page number.
         * Uses Retrofit to make the API call.
         *
         * @param countryUri: The country URI for which to fetch news.
         * @param pageNumber: The page number of results to retrieve.
         * @return The list of top news articles as `ResultsItem` objects.
         */
        suspend fun getTopNews(countryUri: String, pageNumber: Int) =
                RetrofitInstance.api.getTopNews(countryUri, pageNumber)

        /**
         * Searches for news articles based on the provided query and page number.
         * Uses Retrofit to make the API call.
         *
         * @param searchQuery: The search query string.
         * @param pageNumber: The page number of results to retrieve.
         * @return The list of searched news articles as `ResultsItem` objects.
         */
        suspend fun searchNews(searchQuery: String, pageNumber: Int) =
                RetrofitInstance.api.searchNews(searchQuery, pageNumber)

        /**
         * Updates or inserts a news article (`ResultsItem`) into the local database.
         *
         * @param resultsItem: The news article object to save.
         */
        suspend fun updateInsert(resultsItem: ResultsItem) = db.getArticleDAO().updateInsert(resultsItem)

        /**
         * Retrieves all saved news articles from the local database.
         *
         * @return A list of saved news articles as `ResultsItem` objects.
         */
        fun getSavedNews() = db.getArticleDAO().getAllArticles()

        /**
         * Deletes a saved news article (`ResultsItem`) from the local database.
         *
         * @param resultsItem: The news article object to delete.
         */
        suspend fun deleteArticle(resultsItem: ResultsItem) = db.getArticleDAO().deleteArticle(resultsItem)
}
