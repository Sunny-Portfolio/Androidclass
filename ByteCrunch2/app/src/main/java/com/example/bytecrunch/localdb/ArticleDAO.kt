package com.example.bytecrunch.localdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bytecrunch.apiResponse.ResultsItem

@Dao
interface ArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(resultsItem: ResultsItem): Long

    @Query("SELECT * FROM articles_table")
    fun getAllArticles(): LiveData<List<ResultsItem>>

    @Delete
    suspend fun deleteArticle(resultsItem: ResultsItem)
}
