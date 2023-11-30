package com.example.bytecrunch.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.bytecrunch.R;
import com.example.bytecrunch.databinding.PostNewsImageBinding;
import com.example.bytecrunch.databinding.PostNewsTextBinding;
import com.example.bytecrunch.news.NewsPost;

public class PostsListAdapter extends ListAdapter<NewsPost,MyViewHolder> {


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


    protected PostsListAdapter(@NonNull DiffUtil.ItemCallback<NewsPost> diffCallback) {
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
                PostNewsTextBinding postNewsTextBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.post_news_text,parent,false);
                return new TextViewHolder(postNewsTextBinding);
            case VIEWTYPE_POST_IMG:
                PostNewsImageBinding postNewsImageBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.post_news_image,parent,false);
                return new ImageViewHolder(postNewsImageBinding);
            default:
                PostNewsTextBinding defaultPostNewsTextBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.post_news_text,parent,false);
                return new TextViewHolder(defaultPostNewsTextBinding);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(getItem(position));

    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }
}
