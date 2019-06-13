package com.stelmach.piotr.socialportal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stelmach.piotr.socialportal.Api.PostsInterface;
import com.stelmach.piotr.socialportal.Api.SocialPortalUser;
import com.stelmach.piotr.socialportal.Models.CurrentUser;
import com.stelmach.piotr.socialportal.Models.Post;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ImageView mAvatarImageView;
    private TextView mNameTextView;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SocialPortalUser.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SocialPortalUser socialPortalUser = retrofit.create(SocialPortalUser.class);

    CurrentUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout= findViewById(R.id.drawer);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Social App");

        NavigationView navigationView=findViewById(R.id.mainNavView);
        navigationView.setNavigationItemSelectedListener(this);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.close,R.string.open);
        if(mDrawerLayout!=null) {
            mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        }
        mActionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PostsFragment()).commit();
            navigationView.setCheckedItem(R.id.postsDrawerBtn);
        }

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String userToken=sharedPref.getString("Token","Alternative");
        Log.d("LOAD_TOKEN",userToken);

        Call<CurrentUser> currentUserCall=socialPortalUser.GetCurrentUser(userToken);

        currentUserCall.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                Log.d("RETROFIT",response.message());
                Log.d("RETROFIT",response.body().toString());

                /*if(response.isSuccessful() && response.code()==200){

                    currentUser=response.body();
                    setCurrentUserData(currentUser);
                }*/

            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {
                Log.d("RETROFIT",t.getLocalizedMessage());
                Toast.makeText(MainActivity.this,"Cannot connect try again",Toast.LENGTH_LONG)
                        .show();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setCurrentUserData(CurrentUser user){
       mAvatarImageView=findViewById(R.id.avatarImageView);
        mNameTextView=findViewById(R.id.usernameTextView);

        String prefix;
        String avatarNormal=user.getAvatar();

        if(avatarNormal.charAt(0)=='/'){
            prefix="http:";
        }else{
            prefix="http://";
            avatarNormal.substring(2);
        }

        Log.d("SETDATA", "setCurrentUserData: "+ avatarNormal);
        Picasso.get().load(prefix+avatarNormal).into(mAvatarImageView);
        if(mNameTextView!=null) {
            mNameTextView.setText(user.getName());
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mActionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.postsDrawerBtn:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PostsFragment()).commit();
                break;
            case R.id.profileDrawerBtn:
                Log.d("FRAGMENT","FRAGMENT PROFILE SELECTED");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.editProfileDrawerBtn:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EditProfileFragment()).commit();
                break;
            case R.id.searchProfileDrawerBtn:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchFragment()).commit();
                break;
            case R.id.logoutUserDrawerBtn:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;


        }

        mDrawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }


}
