package com.example.bytecrunch.repository;

public class NewsRepoJava {
//
//    private ArticleDatabase db;
//
//    // Constructor to initialize the ArticleDatabase instance
//    public NewsRepoJava(ArticleDatabase db) {
//        this.db = db;
//    }
//
//    // Method to fetch breaking news using Retrofit
//    public LiveData<Resource<ResultsItem>> getBreakingNews(String countryCode, int pageNumber, String apiKey) {
//        // Make a network request to get breaking news
//        // Example: return RetrofitInstance.getApi().getBreakingNews(countryCode, pageNumber)
//        // This should return a LiveData object containing the response
//        return RetrofitInstance.getApi().getTopNews(countryCode, pageNumber, apiKey);
//
//    }
//
//    public LiveData<Resource<NewsResponse>> getBreakingNews(String countryCode, int pageNumber) {
//        MutableLiveData<Resource<NewsResponse>> result = new MutableLiveData<>();
//        result.setValue(Resource.loading(null));
//
//        RetrofitInstance.getApi().getTopNews(countryCode, pageNumber).enqueue(new Callback<NewsResponse>() {
//            @Override
//            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    result.setValue(Resource.success(response.body()));
//                } else {
//                    result.setValue(Resource.error("Error: " + response.message(), null));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<NewsResponse> call, Throwable t) {
//                result.setValue(Resource.error("Network failure: " + t.getMessage(), null));
//            }
//        });
//
//        return result;
//    }
//
//
//    // Method to search for news using Retrofit
//    public LiveData<Resource<Response>> searchNews(String searchQuery, int pageNumber) {
//        // Make a network request to search for news
//        // Example: return RetrofitInstance.getApi().searchForNews(searchQuery, pageNumber)
//        // This should return a LiveData object containing the search results
//    }
//
//    // Method to insert or update an article in the local database
//    public void upsert(Article article) {
//        // Insert or update the article in the Room database
//        // Example: db.getArticleDao().upsert(article)
//    }
//
//    // Method to retrieve saved news from the local database
//    public LiveData<List<Article>> getSavedNews() {
//        // Retrieve all saved articles from the Room database
//        // Example: return db.getArticleDao().getAllArticles()
//        // This should return a LiveData object containing the list of articles
//    }
//
//    // Method to delete an article from the local database
//    public void deleteArticle(Article article) {
//        // Delete the specified article from the Room database
//        // Example: db.getArticleDao().deleteArticle(article)
//    }
}
