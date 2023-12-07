package com.example.bytecrunch.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.bytecrunch.apiResponse.ResultsItem;

//public class NewsPostCallback extends DiffUtil.ItemCallback<NewsPost> {
public class NewsPostCallback extends DiffUtil.ItemCallback<ResultsItem> {



        /**
         * Fake news call back
         * @param oldItem The item in the old list.
         * @param newItem The item in the new list.
         * @return
         */
//    @Override
//    public boolean areItemsTheSame(@NonNull NewsPost oldItem, @NonNull NewsPost newItem) {
//        return oldItem.getPostID() == newItem.getPostID();
//    }
//
//    @Override
//    public boolean areContentsTheSame(@NonNull NewsPost oldItem, @NonNull NewsPost newItem) {
//        return oldItem.getPostID() == newItem.getPostID();
//    }


    /**
     * Check News from API call back for same news
     * @param oldItem The item in the old list.
     * @param newItem The item in the new list.
     * @return
     */
    @Override
    public boolean areItemsTheSame(@NonNull ResultsItem oldItem, @NonNull ResultsItem newItem) {
        // Compare the URL instead as some news may not have PostID
        return oldItem.getUrl() == newItem.getUrl();
    }

    @Override
    public boolean areContentsTheSame(@NonNull ResultsItem oldItem, @NonNull ResultsItem newItem) {
        // Compare directly instead of PostID
        return oldItem == newItem;
    }



}
