package com.example.bytecrunch.localdb;

import androidx.room.TypeConverter;

import com.example.bytecrunch.apiResponse.ResultsItem;
import com.example.bytecrunch.apiResponse.Source;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Type Converters for Objects and Complex Types so Room can save these data
 */
public class Converters {

    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromSource(Source source) {
        return gson.toJson(source);
    }

    @TypeConverter
    public static Source toSource(String json) {
        return gson.fromJson(json, Source.class);
    }

    @TypeConverter
    public static String fromObjectList(List<Object> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Object> toObjectList(String json) {
        Type listType = new TypeToken<List<Object>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    @TypeConverter
    public static String fromResultsItemList(List<ResultsItem> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<ResultsItem> toResultsItemList(String json) {
        Type listType = new TypeToken<List<ResultsItem>>() {}.getType();
        return gson.fromJson(json, listType);
    }



}



