package com.example.bytecrunch.localdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.bytecrunch.apiResponse.ResultsItem;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;


/**
 * DAO queries for ROOM local database
 * Using RXjava and LiveData for Asynchronous queries
 */
@Dao
public interface ArticleDAOJava {


    /**
     * Inserts an article into the database.
     * If an existing article has the same ID, it will be replaced with the new article.
     * @param resultsItem
     * @return
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    long upsert(ResultsItem resultsItem);
    Single<Long> updateInsert(ResultsItem resultsItem);



    /**
     * Retrieves all articles from the database using LiveData
     * @return A LiveData list of all articles.
     */
    @Query("SELECT * FROM articles_table")
    LiveData<List<ResultsItem>> getAllArticles();


    /**
     * Deletes a given article from the database.
     * @param resultsItem
     * @return
     */
    @Delete
//    Void deleteArticle(ResultsItem resultsItem);
    Completable deleteArticle(ResultsItem resultsItem);

}
