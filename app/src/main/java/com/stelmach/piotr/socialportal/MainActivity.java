package com.stelmach.piotr.socialportal;

import android.content.Context;
import android.R;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.stelmach.piotr.socialportal.Api.PostsInterface;
import com.stelmach.piotr.socialportal.Api.SocialPortalUser;
import com.stelmach.piotr.socialportal.Models.CurrentUser;
import com.stelmach.piotr.socialportal.Models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SocialPortalUser.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SocialPortalUser socialPortalUser = retrofit.create(SocialPortalUser.class);

    PostsInterface postsInterface=retrofit.create(PostsInterface.class);

    CurrentUser currentUser;
    List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDrawerLayout=findViewById(R.id.drawerLayout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.close,R.string.open);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final TextView token =findViewById(R.id.tokenTextView);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //token.setText(sharedPref.getString("Token","Alternative"));

        String userToken=sharedPref.getString("Token","Alternative");


        Call<CurrentUser> currentUserCall=socialPortalUser.GetCurrentUser(userToken);

        currentUserCall.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                Log.d("RETROFIT",response.message());
                if(response.code()==400 || response.code()==401){
                    Toast.makeText(MainActivity.this,"Unathorized",Toast.LENGTH_LONG);
                }
                currentUser=response.body();
            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {
                Log.d("RETROFIT",t.getLocalizedMessage());
            }
        });

        Call<List<Post>> listCall=postsInterface.GetAllPosts();

        listCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d("RETROFIT",response.message());
                if(response.code()==400 || response.code()==401){
                    Toast.makeText(MainActivity.this,"Unathorized",Toast.LENGTH_LONG);
                }
                postList=response.body();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d("RETROFIT",t.getLocalizedMessage());
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mActionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
