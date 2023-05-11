package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TodoListFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_todo_list);
    }
}