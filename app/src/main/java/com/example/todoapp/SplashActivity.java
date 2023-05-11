package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Creating object of Handler for displaying the Splash Screen
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {
                //starting splash activity and go to the  MainActivity by creating Anonymous methods
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();  //terminating the Splash Screen after 3 seconds
            }
        },3000);
    }
}