package com.example.bytecrunch.viewholder;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bytecrunch.apiResponse.ResultsItem;
import com.example.bytecrunch.databinding.PostNewsImageBinding;
import com.example.bytecrunch.databinding.PostNewsTextBinding;

public class ImageViewHolder extends MyViewHolder{

    PostNewsImageBinding postNewsImageBinding;
    public ImageViewHolder(@NonNull PostNewsImageBinding postNewsImageBinding) {
        super(postNewsImageBinding.getRoot());
        this.postNewsImageBinding = postNewsImageBinding;
    }

    @Override
//    public void bindData(NewsPost post) {
    public void bindData(ResultsItem post) {

        Log.d("ImgViewHolder", "getPostNewsTextBing: binding data ");

        postNewsImageBinding.setNewsPostImage(post);
    }

    @Override
    public PostNewsTextBinding getPostNewsTextBing() {
        Log.d("ImgViewHolder", "getPostNewsTextBing: txt ");

        return null;
    }

    @Override
    public PostNewsImageBinding getPostNewsImageBinding() {
        Log.d("ImgViewHolder", "getPostNewsTextBing: img ");

        return null;
    }
}
