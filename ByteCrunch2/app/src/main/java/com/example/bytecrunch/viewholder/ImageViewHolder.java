package com.example.bytecrunch.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.bytecrunch.databinding.PostNewsImageBinding;
import com.example.bytecrunch.databinding.PostNewsTextBinding;
import com.example.bytecrunch.news.NewsPost;

public class ImageViewHolder extends MyViewHolder{

    PostNewsImageBinding postNewsImageBinding;
    public ImageViewHolder(@NonNull PostNewsImageBinding postNewsImageBinding) {
        super(postNewsImageBinding.getRoot());
        this.postNewsImageBinding = postNewsImageBinding;
    }

    @Override
    public void bindData(NewsPost post) {

        postNewsImageBinding.setNewsPostImage(post);
    }

    @Override
    public PostNewsTextBinding getPostNewsTextBing() {
        return null;
    }

    @Override
    public PostNewsImageBinding getPostNewsImageBinding() {
        return null;
    }
}
