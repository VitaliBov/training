package com.bov.vitali.training.data.net;

import com.bov.vitali.training.data.net.response.LoginResponse;
import com.bov.vitali.training.data.net.response.PublicationResponse;
import com.bov.vitali.training.data.net.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TrainingApi {
    String BASE_URL = "https://api.medium.com/v1/";

    @POST("/v1/tokens")
    @FormUrlEncoded
    Call<LoginResponse> getToken(
            @Field("code") String code,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType,
            @Field("redirect_uri") String redirectUri);

    @POST("/v1/tokens")
    @FormUrlEncoded
    Call<LoginResponse> refreshToken(
            @Field("refresh_token") String refreshToken,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("grant_type") String grantType);

    @GET("/v1/me")
    Call<UserResponse> getUser();

    @GET("/v1/users/{userId}/publications")
    Call<PublicationResponse> getPublications(@Path("userId") String userId);
}