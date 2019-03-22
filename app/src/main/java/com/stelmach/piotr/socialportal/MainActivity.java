package com.stelmach.piotr.socialportal;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView token =findViewById(R.id.tokenTextView);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //String txt = sharedPref.getString("dane", "");
        token.setText(sharedPref.getString("Token","Alternative"));
    }
}
