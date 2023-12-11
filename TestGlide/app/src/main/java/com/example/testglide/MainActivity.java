package com.example.testglide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MainActivity extends AppCompatActivity {

    ImageView view, view2;
    String url = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_1280.jpg";
    String url2 = "https://picsum.photos/300";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.imageView);
        view2 = findViewById(R.id.imageView2);


        Glide.with(this).load(url).into(view);

        Glide.with(this)
                .load(url2)
                .fitCenter()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.nopic)
                .into(view);



    }
}