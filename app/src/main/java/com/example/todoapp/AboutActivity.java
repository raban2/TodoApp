package com.example.todoapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firsttodoapp.R;

public class AboutActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_about);
            getSupportActionBar().setTitle("back");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
}