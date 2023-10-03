package com.theme.keyboardthemeapp.Retrofit;

import com.theme.keyboardthemeapp.ModelClass.GifModel;
import com.theme.keyboardthemeapp.ModelClass.JokeModelItem;
import com.theme.keyboardthemeapp.ModelClass.QuoteCategoryModel;
import com.theme.keyboardthemeapp.ModelClass.QuoteModel;
import com.theme.keyboardthemeapp.ModelClass.ThemeModelItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetrofitInterface {
    @Streaming
    @GET
    @Headers({
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhwcXJ6ZmpnY2lsdHlnbXB5anNzIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY5NTc0MTkxNCwiZXhwIjoyMDExMzE3OTE0fQ.DCJOX18WZTsqTz2dq23iBFqpTWTgg5-9f7VsAtLROmY",
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhwcXJ6ZmpnY2lsdHlnbXB5anNzIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY5NTc0MTkxNCwiZXhwIjoyMDExMzE3OTE0fQ.DCJOX18WZTsqTz2dq23iBFqpTWTgg5-9f7VsAtLROmY"
    })
    Call<List<JokeModelItem>> getJokeData(@Url String Url);

    @Streaming
    @GET
    Call<QuoteCategoryModel> getQuoteCategoryData(@Url String Url);

    @Streaming
    @GET
    Call<QuoteModel> getQuotesData(@Url String Url);
    @Streaming
    @GET
    @Headers({
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhwcXJ6ZmpnY2lsdHlnbXB5anNzIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY5NTc0MTkxNCwiZXhwIjoyMDExMzE3OTE0fQ.DCJOX18WZTsqTz2dq23iBFqpTWTgg5-9f7VsAtLROmY",
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhwcXJ6ZmpnY2lsdHlnbXB5anNzIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY5NTc0MTkxNCwiZXhwIjoyMDExMzE3OTE0fQ.DCJOX18WZTsqTz2dq23iBFqpTWTgg5-9f7VsAtLROmY"
    })
    Call<List<ThemeModelItem>> getThemesData(@Url String Url);
    @Streaming
    @GET
    @Headers({
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhwcXJ6ZmpnY2lsdHlnbXB5anNzIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY5NTc0MTkxNCwiZXhwIjoyMDExMzE3OTE0fQ.DCJOX18WZTsqTz2dq23iBFqpTWTgg5-9f7VsAtLROmY",
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImhwcXJ6ZmpnY2lsdHlnbXB5anNzIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY5NTc0MTkxNCwiZXhwIjoyMDExMzE3OTE0fQ.DCJOX18WZTsqTz2dq23iBFqpTWTgg5-9f7VsAtLROmY"
    })
    Call<GifModel> getGifsData(@Url String Url);
}