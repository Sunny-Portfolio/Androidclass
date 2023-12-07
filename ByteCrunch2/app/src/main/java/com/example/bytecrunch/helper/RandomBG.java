package com.example.bytecrunch.helper;
import java.util.Random;

public class RandomBG {

    private String bg_color;
    private boolean isFav = false;


    // generate a random news background color
    private String getRandomBgColor(){

        final String[] lstColors = {"RED","ORANGE","GREY","PINK"};
        Random random = null;
        int index = random.nextInt(lstColors.length);
        this.bg_color = lstColors[index];

        return bg_color;
    }

    public String getBg_color() {
        getRandomBgColor();
        return bg_color;
    }

    public boolean getIsFav() {
        return isFav;
    }


    public void setFav(boolean fav) {
        isFav = fav;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }
}
