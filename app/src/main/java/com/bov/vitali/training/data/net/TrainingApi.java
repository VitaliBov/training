package com.bov.vitali.training.data.net;

import com.bov.vitali.training.data.model.Publication;
import com.bov.vitali.training.data.model.PublicationContributor;
import com.bov.vitali.training.data.model.User;
import com.bov.vitali.training.data.net.response.LoginResponse;

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
    Call<User> getUser();

    @GET("/v1/users/{userId}/publications")
    Call<Publication> getPublications(@Path("userId") String userId);

    @GET("/v1/publications/{{publicationId}}/contributors")
    Call<PublicationContributor> getPublicationContributors(
            @Path("publicationId") String publicationId);
}