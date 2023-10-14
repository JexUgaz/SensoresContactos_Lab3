package com.example.jplab4.retrofit.services;
import com.example.jplab4.retrofit.entities.randomuser.ResultMain;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomUserService {
    @GET("/api/")
    Call<ResultMain> getRandomUser();
}
