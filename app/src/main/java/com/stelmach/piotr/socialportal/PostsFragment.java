package com.stelmach.piotr.socialportal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stelmach.piotr.socialportal.Api.PostsInterface;
import com.stelmach.piotr.socialportal.Api.SocialPortalUser;
import com.stelmach.piotr.socialportal.Models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostsFragment extends Fragment {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SocialPortalUser.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    PostsInterface postsInterface=retrofit.create(PostsInterface.class);

    List<Post> postList;

    Context fragmentContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentContext=container.getContext();
        return inflater.inflate(R.layout.fragment_posts,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Call<List<Post>> listCall=postsInterface.GetAllPosts();

        listCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d("RETROFIT IN FRAGMENT",response.message());
                if(response.code()==400 || response.code()==401){
                    Toast.makeText(fragmentContext,"Unathorized",Toast.LENGTH_LONG);
                }
                postList=response.body();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d("RETROFIT",t.getLocalizedMessage());
            }
        });
    }
}
