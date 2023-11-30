package com.example.bytecrunch.binding;

import android.util.Log;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.bytecrunch.R;

public class BindingAdapters {


    /**
     * Binding adapter for post image
     */
    @BindingAdapter("glide_url")
    public static void loadImg (ImageView view, String url) {
        Log.d("BIND", "loadImg: Glide img loaded 1");

        // Use Glide to load image from URL
        Glide.with(view.getContext()).load(url).into(view);
//        GlideApp.with(view.getContext()).load(url).into(view);

        Log.d("BIND", "loadImg: Glide img loaded 2");

    }

    /**
     * Binding adapter for news site avatar
     * @param view
     * @param url
     */
     @BindingAdapter("glide_circular")
    public static void loadAvatar (ImageView view, String url) {
         Log.d("BIND", "loadAvatar: 1");

        // Use Glide to make Avatar to circular
         Glide.with(view.getContext()).load(url).circleCrop().into(view);
         Log.d("BIND", "loadAvatar: 2");
     }


    /**
     * Binding Adapter for blog post background color
     * @param view
     * @param color
     */
     @BindingAdapter("set_ImgPost_BG")
    public static void setImgPostBG (ImageView view, String color) {
//    @BindingAdapter("set_background")
//    public static void setBackground (ImageView view, String color) {
         Log.d("BIND", "setImgPostBG: ");
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

//    @BindingAdapter("set_background")
//    public static void setBackground(ImageView view,String color) {
//
//        switch (color) {
//            case "RED" : view.setBackgroundColor(view.getContext().getResources().getColor(R.color.red));
//                break;
//            case "BLACK" : view.setBackgroundColor(view.getContext().getResources().getColor(R.color.black));
//                break;
//            case "YELLOW" : view.setBackgroundColor(view.getContext().getResources().getColor(R.color.yellow));
//                break;
//            case "BLUE" : view.setBackgroundColor(view.getContext().getResources().getColor(R.color.blue));
//                break;
//            case "PURPLE" : view.setBackgroundColor(view.getContext().getResources().getColor(R.color.purple));
//                break;
//            default: view.setBackgroundColor(view.getContext().getResources().getColor(R.color.red));
//        }
//
//    }

}
