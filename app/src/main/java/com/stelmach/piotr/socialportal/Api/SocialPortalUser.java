package com.stelmach.piotr.socialportal.Api;

import com.stelmach.piotr.socialportal.Models.AuthData;
import com.stelmach.piotr.socialportal.Models.CurrentUser;
import com.stelmach.piotr.socialportal.Models.LoginModel;
import com.stelmach.piotr.socialportal.Models.PortalUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SocialPortalUser {

    String BASE_URL="http://social-portal.herokuapp.com/";

    @Headers("Content-Type: application/json")
    @POST("api/users/login")
    Call<AuthData> LoginUserAndGetAuthData(@Body LoginModel LoginData);

    @Headers("Content-Type: application/json")
    @POST("api/users/register")
    Call<PortalUser> RegisterNewUser(@Body String SignInUser);

    @GET("api/users/current")
    Call<CurrentUser> GetCurrentUser(@Header("Authorization") String token);
}
