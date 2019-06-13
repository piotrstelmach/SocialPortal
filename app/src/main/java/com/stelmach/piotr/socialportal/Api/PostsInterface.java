package com.stelmach.piotr.socialportal.Api;

import com.stelmach.piotr.socialportal.Models.Post;
import com.stelmach.piotr.socialportal.Models.PostToSend;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostsInterface {

    String BASE_URL="http://social-portal.herokuapp.com/";

    @GET("api/posts")
    Call<List<Post>> GetAllPosts();

    @GET("api/posts/{id}")
    Call<Post> GetPostById(@Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST("api/posts")
    Call<Post> CreateNewPost(@Header("Authorization") String token, @Body PostToSend postBody);

    @Headers("Content-Type: application/json")
    @DELETE("api/posts/{id}")
    Call<Post> DeletePost(@Header("Authorization") String token, @Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST("api/posts/comment/{id}")
    Call<Post> CommentPost(@Header("Authorization") String token,@Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST("api/posts/like/{id}")
    Call<Post> LikePost(@Header("Authorization") String token,@Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST("api/posts/like/{id}")
    Call<Post> DislikePost(@Header("Authorization") String token,@Path("id") String id);

    @Headers("Content-Type: application/json")
    @DELETE("api/posts/comment/{id}/{comment_id}")
    Call<Post> DeleteCommentFromPost(@Header("Authorization") String token,@Path("id") String id,
                                     @Path("comment_id") String commentId);




}
