package com.stelmach.piotr.socialportal.Api;

import com.stelmach.piotr.socialportal.Models.PostUserProfile;
import com.stelmach.piotr.socialportal.Models.UserEducation;
import com.stelmach.piotr.socialportal.Models.UserExperience;
import com.stelmach.piotr.socialportal.Models.UserProfile;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SocialPortalProfile {

    @GET("api/profile")
    Call<UserProfile> getCurrentUserProfile(@Header("Authorization") String token);

    @GET("api/profile/handle/{handle}")
    Call<UserProfile> getUserProfileByHandle(@Path("handle") String profileHandle);

    @GET("api/profile/all")
    Call<List<UserProfile>> getAllUserProfiles();

    @GET("api/profile/user/{user_id}")
    Call<UserProfile> getUserProfileById(@Path("user_id") String userId);

    @Headers("Content-Type: application/json")
    @POST("api/profile")
    Call<UserProfile> createOrEditUserProfile(@Header("Authorization") String token,
                                              @Body UserProfile userProfile);
    @Headers("Content-Type: application/json")
    @POST("api/profile/experience")
    Call<UserProfile> addExperienceToUserProfile(@Header("Authorization") String token,
                                                 @Body UserExperience userExperience);
    @Headers("Content-Type: application/json")
    @POST("api/profile/education")
    Call<PostUserProfile> addEducationToUserProfile(@Header("Authorization") String token,
                                                    @Body UserEducation userEducation);

    @DELETE("api/profile/experience/{exp_id")
    Call<UserProfile> deleteExperienceFromUserProfile(@Header("Authorization") String token,
                                                      @Path("exp_id") String expId);

    @DELETE("api/profile/education/{education_id}")
    Call<UserProfile> deleteEducationFromUserProfile(@Header("Authorization") String token,
                                                     @Path("education_id") String userId);

}
