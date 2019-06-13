package com.stelmach.piotr.socialportal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stelmach.piotr.socialportal.Api.SocialPortalUser;
import com.stelmach.piotr.socialportal.Models.PortalUser;
import com.stelmach.piotr.socialportal.Models.SignInModel;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignIn extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SocialPortalUser.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SocialPortalUser socialPortalUser = retrofit.create(SocialPortalUser.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final TextView emailText=findViewById(R.id.emailText);
        final TextView nameText=findViewById(R.id.nameText);
        final TextView passwordText=findViewById(R.id.PasswordText);
        final TextView password2Text=findViewById(R.id.Password2Text);

        Button signInBtn=findViewById(R.id.SignInButton);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInModel signInModel=new SignInModel(emailText.getText().toString(),
                        nameText.getText().toString(),passwordText.getText().toString(),
                        password2Text.getText().toString());

                if(passwordText.getText().toString().equals(password2Text.getText().toString())) {

                    Call<PortalUser> userSignIn = socialPortalUser.RegisterNewUser(signInModel);
                    userSignIn.enqueue(new Callback<PortalUser>() {
                        @Override
                        public void onResponse(Call<PortalUser> call, Response<PortalUser> response) {
                            Log.d("RETROFIT_SIGN_IN", response.message());
                            Log.d("RETROFIT_SIGN_IN", response.body().getEmail());
                            Log.d("RETROFIT_SIGN_IN", response.body().getName());
                            Log.d("RETROFIT_SIGN_IN", response.body().getId());

                            if (response.code() == 200 && response.body() != null) {
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<PortalUser> call, Throwable t) {
                            Log.d("RETROFIT_SIGN_IN", t.getLocalizedMessage());
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(),"Password must match!",Toast.LENGTH_LONG);
                }
            }
        });
    }

}
