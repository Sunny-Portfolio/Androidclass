package com.example.bytecrunch.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.bytecrunch.news.NewsPost;

public class NewsPostCallback extends DiffUtil.ItemCallback<NewsPost> {


    @Override
    public boolean areItemsTheSame(@NonNull NewsPost oldItem, @NonNull NewsPost newItem) {
        return oldItem.getPostID() == newItem.getPostID();
    }

    @Override
    public boolean areContentsTheSame(@NonNull NewsPost oldItem, @NonNull NewsPost newItem) {
        return oldItem.getPostID() == newItem.getPostID();
    }
}
