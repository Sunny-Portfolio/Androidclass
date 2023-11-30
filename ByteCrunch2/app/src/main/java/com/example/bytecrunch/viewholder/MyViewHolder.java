package com.example.bytecrunch.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bytecrunch.databinding.PostNewsImageBinding;
import com.example.bytecrunch.databinding.PostNewsTextBinding;
import com.example.bytecrunch.news.NewsPost;

abstract class MyViewHolder extends RecyclerView.ViewHolder {


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindData (NewsPost post);


    // use for shared view animation
    public abstract PostNewsTextBinding getPostNewsTextBing();
    public abstract PostNewsImageBinding getPostNewsImageBinding();
}
