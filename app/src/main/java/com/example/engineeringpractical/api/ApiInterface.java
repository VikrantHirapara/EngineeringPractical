package com.example.engineeringpractical.api;

import com.example.engineeringpractical.mdoel.UserListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("search_by_date")
    Call<UserListResponse> getUsersList(@Query("tags") String tags, @Query("page") int page);
}
