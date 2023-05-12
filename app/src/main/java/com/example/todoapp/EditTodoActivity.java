package com.example.todoapp;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.firsttodoapp.R;

public class EditTodoActivity extends AppCompatActivity {

    Fragment mFragment;
    FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
        getSupportActionBar().setTitle("back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFragment=new EditTodoFragment();
        mFragmentManager=getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.main_container,mFragment)
                .commit();
    }
}