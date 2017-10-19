package com.bov.vitali.training.data.net;

import com.bov.vitali.training.data.net.response.FilmResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FilmsApi {
    String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/top_rated")
    Call<FilmResponse> getTopRatedFilms(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );
}