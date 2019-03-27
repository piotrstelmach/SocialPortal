package com.stelmach.piotr.socialportal;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.stelmach.piotr.socialportal.Api.SocialPortalUser;
import com.stelmach.piotr.socialportal.Models.AuthData;
import com.stelmach.piotr.socialportal.Models.LoginModel;
import com.stelmach.piotr.socialportal.Models.PortalUser;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SocialPortalUser.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SocialPortalUser socialPortalUser = retrofit.create(SocialPortalUser.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final TextView emailTextView = findViewById(R.id.EmailTV);
        final TextView passwordTextView = findViewById(R.id.PasswordTV);
        TextView SignInTextView=findViewById(R.id.SignInTextView);

        Button loginButton = findViewById(R.id.LoginBtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginModel loginModel=new LoginModel(emailTextView.getText().toString(),passwordTextView.getText().toString());

                Call<AuthData> userLogin = socialPortalUser.LoginUserAndGetAuthData(loginModel);
                userLogin.enqueue(new Callback<AuthData>() {
                    @Override
                    public void onResponse(Call<AuthData> call, Response<AuthData> response) {
                        Log.d("RETROFIT", response.message());

                        //Testing receive data
                        AuthData authData=response.body();

                        Log.d("RETROFIT", authData.getSuccess().toString());
                        Log.d("RETROFIT", authData.getToken());

                        //save token to cache
                        //SharedPreferences sharedPreferences=getPreferences(Context.MODE_PRIVATE);
                        //SharedPreferences.Editor editor=sharedPreferences.edit();
                        //editor.putString("Token",response.body().getToken());
                        //editor.commit();

                        saveTokenToCache(response.body().getToken());

                        GoToMainActivity();

                    }

                    @Override
                    public void onFailure(Call<AuthData> call, Throwable t) {
                        Log.d("RETROFIT", t.getLocalizedMessage());
                    }
                });

            }

        });
        SignInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToSignInActivity();
            }
        });



    }

    public void GoToMainActivity(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void GoToSignInActivity(){
        Intent intent = new Intent(this,SignIn.class);
        startActivity(intent);
    }

    public void saveTokenToCache(String token){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Token",token);
        editor.apply();
    }

}
