package com.example.bytecrunch.repository

import com.example.bytecrunch.apiResponse.ResultsItem
import com.example.bytecrunch.api.RetrofitInstance
import com.example.bytecrunch.localdb.ArticleDB

class NewsRepo ( val db: ArticleDB ) {
        suspend fun getTopNews(countryUri: String, pageNumber: Int) =
                RetrofitInstance.api.getTopNews(countryUri, pageNumber)

        suspend fun searchNews(searchQuery: String, pageNumber: Int) =
                RetrofitInstance.api.searchNews(searchQuery, pageNumber)

        suspend fun updateInsert(resultsItem: ResultsItem) = db.getArticleDAO().updateInsert(resultsItem)

        fun getSavedNews() = db.getArticleDAO().getAllArticles()

        suspend fun deleteArticle(resultsItem: ResultsItem) = db.getArticleDAO().deleteArticle(resultsItem)
}
