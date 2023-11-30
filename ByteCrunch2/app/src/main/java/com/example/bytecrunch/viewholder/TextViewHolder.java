package com.example.bytecrunch.viewholder;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.bytecrunch.databinding.PostNewsImageBinding;
import com.example.bytecrunch.databinding.PostNewsTextBinding;
import com.example.bytecrunch.news.NewsPost;

public class TextViewHolder extends MyViewHolder {



    PostNewsTextBinding postNewsTextBinding;
    public TextViewHolder(@NonNull PostNewsTextBinding postNewsTextBinding) {
        super(postNewsTextBinding.getRoot());

        this.postNewsTextBinding = postNewsTextBinding;
    }

    @Override
    public void bindData(NewsPost post) {
        postNewsTextBinding.setNewsPostText(post);
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
