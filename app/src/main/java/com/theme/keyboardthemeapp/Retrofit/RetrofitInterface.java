package com.theme.keyboardthemeapp.Retrofit;

import com.theme.keyboardthemeapp.ModelClass.JokeModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetrofitInterface {
    @Streaming
    @GET
    Call<JokeModel> getJokeData(@Url String Url);
}