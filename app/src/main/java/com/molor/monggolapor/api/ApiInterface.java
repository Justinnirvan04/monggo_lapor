package com.molor.monggolapor.api;

import com.molor.monggolapor.model.News;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("everything")
    Call<News> getNews(
//        @Query("country") String country,
//        @Query("category") String category,
            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<News> getNewsSearch(
            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );
}
