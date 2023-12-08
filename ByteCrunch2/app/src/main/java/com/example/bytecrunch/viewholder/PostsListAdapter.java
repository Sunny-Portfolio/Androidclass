package com.example.bytecrunch.viewholder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.bytecrunch.apiResponse.ResultsItem;
import com.example.bytecrunch.R;
import com.example.bytecrunch.databinding.PostNewsImageBinding;
import com.example.bytecrunch.databinding.PostNewsTextBinding;

import javax.xml.transform.Result;


// TODO: 12/2/23 Postlistadapter newpost need to change to ResultsItem
//public class PostsListAdapter extends ListAdapter<NewsPost,MyViewHolder> {
public class PostsListAdapter extends ListAdapter<ResultsItem,MyViewHolder> {


    public static final int VIEWTYPE_POST_TXT = 0;
    public static final int VIEWTYPE_POST_IMG = 1;
    public static final int VIEWTYPE_POST_VID = 2;





    /**
     * Details of the Custom Adapter
     * @param diffCallback
     */
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


    /**
     * Interface for news article item click listener
     */
//    OnPostItemClickEvent listener;
    OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(ResultsItem resultsItem);
    }


//    public interface OnPostItemClickEvent {
//
//        void onPostItemClick(ResultsItem resultsItem);
//        void onPostTextClick();
//        void onPostImageClick();
//        void onPostLongClick();
//
//    }

//    public void setOnNewsPostClickListener(OnPostItemClickListener listener) {
//        this.listener = listener;
//    }
    // Method to set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(getItem(position));
        // TODO: 12/5/23 set on click listener?? or check if there is one for open web view


        /**
         * Set on click listener for the view holder
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "PostsListAdapter";
            @Override
            public void onClick(View v) {
                ResultsItem item = getItem(position);
                Log.d(TAG, "onClick: is clicked, ResultItem is : " + item);
                if (listener != null) {
                    Log.d(TAG, "onClick: is clicked 2, ResultItem is : " );
                    listener.onItemClick(item);
                    Log.d(TAG, "onClick: is clicked 3, ResultItem is : " );
                }
            }
        });
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
