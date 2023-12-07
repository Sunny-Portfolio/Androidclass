package com.example.bytecrunch.localdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.bytecrunch.apiResponse.ResultsItem;

import kotlin.jvm.Volatile;


//@Database(entities = {ResultsItem.class}, version = 1)
//@TypeConverters(Converters.class)
public abstract class ArticleDatabase extends RoomDatabase {

    @Volatile
    private static ArticleDatabase db;
    private static final Object LOCK = new Object();

    public abstract ArticleDAO getArticleDao();


    /**
     * get instance of the ArticleDatabase
     * @param context
     * @return
     */
    public static ArticleDatabase getDb(Context context) {
        if (db == null) {
            synchronized (LOCK) {
                if (db == null) {
                    db = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ArticleDatabase.class, "article_db.db").build();
                }
            }
        }
        return db;
    }
}

