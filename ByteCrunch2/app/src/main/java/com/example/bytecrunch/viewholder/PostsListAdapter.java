package com.example.bytecrunch.viewholder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.bytecrunch.apiResponse.ResultsItem;
import com.example.bytecrunch.R;
import com.example.bytecrunch.databinding.PostNewsImageBinding;
import com.example.bytecrunch.databinding.PostNewsTextBinding;


// TODO: 12/2/23 Postlistadapter newpost need to change to ResultsItem
//public class PostsListAdapter extends ListAdapter<NewsPost,MyViewHolder> {
public class PostsListAdapter extends ListAdapter<ResultsItem,MyViewHolder> {


    public static final int VIEWTYPE_POST_TXT = 0;
    public static final int VIEWTYPE_POST_IMG = 1;
    public static final int VIEWTYPE_POST_VID = 2;

    OnPostItemClickEvent listener;


    public interface OnPostItemClickEvent {
        void onPostTextClick();
        void onPostImageClick();
        void onPostLongClick();

    }

    public void setOnNewsPostClickListener(OnPostItemClickEvent listener) {
        this.listener = listener;
    }

    // TODO: 12/2/23 Postlistadapter newpost need to change to ResultsItem
    public PostsListAdapter(@NonNull DiffUtil.ItemCallback<ResultsItem> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /**
         * Instantiate viewHolders
         */
        switch (viewType) {
            case VIEWTYPE_POST_TXT:
                Log.d("LISTAdapter", "onCreateViewHolder: Txt");
                PostNewsTextBinding postNewsTextBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.post_news_text,parent,false);
                return new TextViewHolder(postNewsTextBinding);
            case VIEWTYPE_POST_IMG:
                Log.d("LISTAdapter", "onCreateViewHolder: Img");
                PostNewsImageBinding postNewsImageBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.post_news_image,parent,false);
                return new ImageViewHolder(postNewsImageBinding);
            default:
                Log.d("LISTAdapter", "onCreateViewHolder: default");
                PostNewsTextBinding defaultPostNewsTextBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.post_news_text,parent,false);
                return new TextViewHolder(defaultPostNewsTextBinding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(getItem(position));
        // TODO: 12/5/23 set on click listener?? or check if there is one for open web view

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }
}
