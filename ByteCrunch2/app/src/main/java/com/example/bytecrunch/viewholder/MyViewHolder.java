package com.example.bytecrunch.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bytecrunch.apiResponse.ResultsItem;
import com.example.bytecrunch.databinding.PostNewsImageBinding;
import com.example.bytecrunch.databinding.PostNewsTextBinding;

abstract class MyViewHolder extends RecyclerView.ViewHolder {


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
    }

//    public abstract void bindData (NewsPost post);
    public abstract void bindData (ResultsItem post);


    // use for shared view animation
    public abstract PostNewsTextBinding getPostNewsTextBing();
    public abstract PostNewsImageBinding getPostNewsImageBinding();
}
