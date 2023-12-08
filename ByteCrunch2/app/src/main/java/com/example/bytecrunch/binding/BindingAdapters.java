package com.example.bytecrunch.binding;


import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.bytecrunch.R;
import com.example.bytecrunch.apiResponse.AuthorsItem;

import java.util.List;

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
     * @param siteName  Name of source news from layout files call
     */
     @BindingAdapter("glide_circular")
    public static void loadAvatar (ImageView view, String siteName) {

         String url = null;
         switch (siteName) {
             case "The Verge" : url = "https://asset.brandfetch.io/idTeHQLr1-/idc4yVMXo-.jpeg";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "VentureBeat" : url = "https://asset.brandfetch.io/idgE7o9_EH/idQwCfjb4e.jpeg";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "TechCrunch" : url = "https://media.licdn.com/dms/image/C560BAQEeAGfIE7wntw/company-logo_200_200/0/1631309596840?e=1710374400&v=beta&t=zSnvB3rDAdB-D5tG1e9al7x6S8JCvHScohpcRXZh59U";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "Tom's Guide" : url = "https://media.licdn.com/dms/image/D4E0BAQEIWLI_F17vlw/company-logo_200_200/0/1685561242046?e=1710374400&v=beta&t=xUEysVYWChFy9n-79gdfzmFPI9Y9J9BflVymt0O6PUI";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "Yahoo New" : url = "https://media.licdn.com/dms/image/D4E0BAQE8SlwfLWJuDg/company-logo_200_200/0/1663278242829/yahoo_logo?e=1710374400&v=beta&t=rsv-RMKtxfPJuhaQrs9THJFYFUi-V35ZdurKCkOY6dU";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "Yahoo! Finance" : url = "https://media.licdn.com/dms/image/C4E0BAQF7lGZR28cb3w/company-logo_200_200/0/1630587703617/yahoo_finance_logo?e=1710374400&v=beta&t=3LeQVmRBiVzCXAPcFWnJ9F5a0QtgEi0ZBrnXfLN2bIA";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "NPR" : url = "https://media.licdn.com/dms/image/D4D0BAQEjhYY8mziJ-g/company-logo_200_200/0/1697580629714/npr_logo?e=1710374400&v=beta&t=NYguhj_xSRu6DhMj2xsh-fYkamS5ci1Hyy-XxQZWAg4";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "Forbes" : url = "https://media.licdn.com/dms/image/D4E0BAQF67bPz45hiLw/company-logo_200_200/0/1696813943410/forbes_magazine_logo?e=1710374400&v=beta&t=I5BccGDbFJn4xF0Q4KDzGdf1o69Sc9EThxS_HP7UIas";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "IGN" : url = "https://media.licdn.com/dms/image/C4E0BAQGV61MuXNThLw/company-logo_200_200/0/1675334361927/ign_entertainment_logo?e=1710374400&v=beta&t=edxvWLkFJUN6L3qTWA4lRJsjEoEnY3M7PQzWYqodTXM";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "IGN Southeast" : url = "https://media.licdn.com/dms/image/C4E0BAQGV61MuXNThLw/company-logo_200_200/0/1675334361927/ign_entertainment_logo?e=1710374400&v=beta&t=edxvWLkFJUN6L3qTWA4lRJsjEoEnY3M7PQzWYqodTXM";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "Mashable" : url = "https://media.licdn.com/dms/image/D560BAQETbMMXC3pSig/company-logo_200_200/0/1688155245496/mashable_logo?e=1710374400&v=beta&t=Sdh9ztIq1Dj367Oql_piqBKB4TSQUqpt1EJK1qn1Neg";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "Polygon" : url = "https://cdn1.vox-cdn.com/uploads/chorus_asset/file/8402075/941450_609208285758470_875871287_n.0.png";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "engadget" : url = "https://media.licdn.com/dms/image/C510BAQEzrRn6f_9adA/company-logo_200_200/0/1631304833807?e=1710374400&v=beta&t=dfE0lv_M_GOu7eKaMqf1MEAGGWwbQlfFjBmjQ4HP4_Q";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "Business Insider" : url = "https://media.licdn.com/dms/image/D4E0BAQH5XQv8BsAq5w/company-logo_200_200/0/1699965585156/insiderbusiness_logo?e=1710374400&v=beta&t=lp7LxgUva-WxrWDAjUqf47dZh0ptMFDisc5P5jUOYiM";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "Xbox Wire" : url = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fen.wikipedia.org%2Fwiki%2FFile%3AXbox_app_logo.svg&psig=AOvVaw1qP8F3De1uuGgXCvJk-fLY&ust=1702103293993000&source=images&cd=vfe&ved=0CBIQjRxqFwoTCIjFqISb_4IDFQAAAAAdAAAAABAE";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "pcgamer" : url = "https://upload.wikimedia.org/wikipedia/commons/5/5a/PC_Gamer_square_logo.png";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "gamesradar" : url = "https://static-cdn.jtvnw.net/jtv_user_pictures/gamesradar-profile_image-a0672b8671d3dac3-300x300.png";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "GameSpot" : url = "https://static-cdn.jtvnw.net/jtv_user_pictures/gamesradar-profile_image-a0672b8671d3dac3-300x300.png";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "Gizmodo" : url = "https://eu-images.contentstack.com/v3/assets/blt6b0f74e5591baa03/blte97b650a3d225cad/6509f6cbfea9d0ba8d83ac63/Untitled_design_-_2023-09-19T143010.857.png?width=850&auto=webp&quality=95&format=jpg&disable=upscale";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "Wired" : url = "https://media.licdn.com/dms/image/C4E0BAQHD1h2ctwCoLg/company-logo_200_200/0/1630605710063/wired_logo?e=1710374400&v=beta&t=9bktMUt-Ht9zp7VdylcwoR-1tFCCmFTP9K0Ksqugzyc";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "The New York Times" : url = "https://media.licdn.com/dms/image/C560BAQEp-f9Ptu2yVQ/company-logo_200_200/0/1631319717018?e=1710374400&v=beta&t=50Th-xJJXVsJeK49UjMv2YCkito4D07wKwkCz1CqQkE";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "USA Today" : url = "https://media.licdn.com/dms/image/C560BAQFVChLRUaoRmw/company-logo_200_200/0/1674768908373/usa_today_logo?e=1710374400&v=beta&t=2Y30qGO2l5ZhFwhzqS7fRGMnk19sik7dsy1U6Xvq7iw";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
             case "Bloomberg" : url = "https://media.licdn.com/dms/image/C4D0BAQF0uyE7RGKDGg/company-logo_200_200/0/1631374698859/bloomberg_lp_logo?e=1710374400&v=beta&t=qjrRfuVXpkdMej0O42YrStqrhN7D-tcOxGa9L5bFJmQ";
                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
//             case "" : url = "";
//                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
//             case "" : url = "";
//                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
//             case "" : url = "";
//                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
//             case "" : url = "";
//                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
//             case "" : url = "";
//                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;
//             case "" : url = "";
//                 Glide.with(view.getContext()).load(url).circleCrop().into(view); break;

             default:
                 Glide.with(view.getContext()).load(R.drawable.byte_crunch_logo).circleCrop().into(view); break;
         }
     }




    /**
     * Binding Adapter for blog post background color
     * @param view
     * @param color
     */
     @BindingAdapter("set_ImgPost_BG")
    public static void setImgPostBG (ImageView view, String color) {

         Log.d("BIND", "setImgPostBG: color is : " + color);
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



    /**
     * Set the Fav icon accordingly.
     * Changes article post fav icon.
     * @param view
     * @param isFav
     */
    @BindingAdapter("fav_check")
    public static void  setFav (ImageView view, boolean isFav) {
         if (isFav)
             view.setImageResource(R.drawable.fav_yes_logo_red);
         else
             view.setImageResource(R.drawable.fav_no_logo_red);

    }

    /**
     * Binding Adapter for blog post author name
     * @param textView
     * @param authors
     */
    @BindingAdapter("set_authorName")
    public static void setAuthorName(TextView textView, List<AuthorsItem> authors) {
        if (authors != null && !authors.isEmpty()) {
            AuthorsItem firstAuthor = authors.get(0);
            textView.setText(firstAuthor.getName());
        } else {
            textView.setText("");
        }
    }

}
