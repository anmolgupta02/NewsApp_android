package com.example.internetnews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {

    @GET
    Call<NewsModal> getAllNews(@Url String URL);

    @GET
    Call<NewsModal> getNewsByCategory(@Url String URL);

}
