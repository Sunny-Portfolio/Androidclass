package com.example.bytecrunch.ui;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bytecrunch.news.NewsPost;
import com.example.bytecrunch.repository.NewsRepo;

public class NewsViewModelProviderFactory implements ViewModelProvider.Factory {
    private NewsRepo newsRepo;
    private Application app;

    // Constructor for NewsViewModelProviderFactory
    public NewsViewModelProviderFactory(Application app, NewsRepo newsRepo) {
        this.app = app;
        this.newsRepo = newsRepo;
    }

    // Factory method to create NewsViewModel instances
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewsViewModel.class)) {
            return (T) new NewsViewModel(app, newsRepo); // Create a new instance of NewsViewModel
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
