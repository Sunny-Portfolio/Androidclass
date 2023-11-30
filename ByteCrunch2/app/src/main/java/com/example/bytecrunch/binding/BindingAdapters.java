package com.example.bytecrunch.binding;

import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.bytecrunch.R;
import com.example.bytecrunch.viewholder.ImageViewHolder;

public class BindingAdapters {


    /**
     * Binding adapter for post image
     */
    @BindingAdapter("glide_url")
    public static void loadImg (ImageView view, String url) {

        // Use Glide to load image from URL
        Glide.with(view.getContext()).load(url).into(view);

    }

    /**
     * Binding adapter for news site avatar
     * @param view
     * @param url
     */
     @BindingAdapter("glide_circular")
    public static void loadAvatar (ImageView view, String url) {

        // Use Glide to make Avatar to circular
         Glide.with(view.getContext()).load(url).circleCrop().into(view);
     }


    /**
     * Binding Adapter for blog post background color
     * @param view
     * @param color
     */
     @BindingAdapter("set_ImgPost_BG")
    public static void setImgPostBG (ImageView view, String color) {
         switch (color) {
             case "RED" :
                 view.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.kinda_grapefruit));
                 break;
             case "BLUE" :
                 view.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.kinda_blue));
                 break;
             case "GREEN" :
                 view.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.kinda_green));
                 break;
             case "GREY" :
                 view.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.kinda_grey));
                 break;
             case "PINK" :
                 view.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.kinda_pink));
                 break;
             case "ORANGE" :
                 view.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.kinda_orange));
                 break;
             default:
                 view.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.kinda_grapefruit));
         }
     }

}
