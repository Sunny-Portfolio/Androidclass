package com.example.bytecrunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.bytecrunch.localdb.ArticleDB;
import com.example.bytecrunch.repository.NewsRepo;
import com.example.bytecrunch.ui.NewsViewModel;
import com.example.bytecrunch.ui.NewsViewModelProviderFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    NavHostFragment navHostFragment;
    BottomNavigationView bottomNavigationView;
    SettingsPref settingsPref;

    NewsViewModel viewModel;
    DisclaimerFragment disclaimerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppTheme);
//        setTheme(R.style.SetFont_DarkThem);

        settingsPref = new SettingsPref(this);
//        setTheme(R.style.SetFont_DarkThem);

        setCurrentTheme(settingsPref.getThemeSelection());
        setCurrentFont(settingsPref.getFontSelection());



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Setup disclaimer confirmation diaglog
         * made sure it will only show once
         */
        SharedPreferences settings = this.getSharedPreferences("APP_Settings", Context.MODE_PRIVATE);
        Boolean isDisclaimerAgreed = settings.getInt("key_disclaimer", 0) != 0;

        if (!isDisclaimerAgreed) {
            disclaimerFragment = new DisclaimerFragment();
            disclaimerFragment.show(getSupportFragmentManager(), "disclaimer");
        }



        // Initialize NewsRepo with the ArticleDatabase instance
        NewsRepo newsRepo = new NewsRepo(ArticleDB.Companion.invoke(this));

        // Create an instance of NewsViewModelProviderFactory
        NewsViewModelProviderFactory viewModelProviderFactory = new NewsViewModelProviderFactory(getApplication(), newsRepo);

        // Obtain NewsViewModel from ViewModelProvider
        viewModel = new ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel.class);


        /**
         * Setup the bottom navigation bar
         */
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());


    }


    /**
     * Set the current theme to saved theme from sahred preference
     * @param themeSelection
     */
    private void setCurrentTheme(int themeSelection) {
        switch (themeSelection) {
            case SettingsPref.THEME_LIGHT:
                setTheme(R.style.AppTheme);
                break;
            case SettingsPref.THEME_DARK:
                setTheme(R.style.DarkTheme);
                break;
            default:
                setTheme(R.style.AppTheme);

        }
    }

    /**
     * Set the current font to saved font from shared preference
     * @param fontSelection
     */
    private void setCurrentFont(String fontSelection) {
        String item_id = "SetFont_" + fontSelection;
        int id = getResources().getIdentifier(item_id, "style", this.getPackageName());
        setTheme(id);
    }



}
