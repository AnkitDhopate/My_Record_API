package com.example.myrecordapi;

import com.example.myrecordapi.model.ApiModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface
{
    @POST("/create")
    Call<ApiModel> postUser(@Body ApiModel apiModel);

    @GET("/api")
    Call<ArrayList<ApiModel>> getUsers() ;
}
