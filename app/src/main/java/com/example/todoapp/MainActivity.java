package com.example.todoapp;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.firsttodoapp.R;
import com.example.todoapp.model.Task;
import com.example.todoapp.viewmodel.TodoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabAddNew;

    Fragment mfragment;
    FragmentManager mfragmentManager;
    AlertDialog.Builder mAlterDialog;
    TodoViewModel mTodoViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfragment= new TodoListFragment();
        mfragmentManager=getSupportFragmentManager();
        mfragmentManager.beginTransaction()
                .add(R.id.list_container,mfragment)
                .commit();
        fabAddNew = findViewById(R.id.fab_add_new_todo);

        //fab icon for referring to edit todo page while clicking
        fabAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditTodoActivity.class);
                startActivity(intent);
            }
        });

        mTodoViewModel= ViewModelProviders.of(this).get(TodoViewModel.class);
    }
    //for menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    //code for the share method
    private String getNotesText() {
        StringBuilder sb = new StringBuilder();
        List<Task> todos = mTodoViewModel.getAllTodos().getValue();
        for (Task todo : todos) {
            sb.append(todo.getTitle()).append(": ").append(todo.getDescription()).append("\n");
        }
        return sb.toString();
    }


    //code for extra options
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.menu_delete_all:
                //confirmation dailog box
                mAlterDialog = new AlertDialog.Builder(this);
                mAlterDialog.setMessage("Are you sure want to delete all??")
                        .setCancelable(false)
                        .setTitle(getString(R.string.app_name))
                        .setIcon(R.mipmap.ic_launcher);
                mAlterDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTodoViewModel.deleteAll();
                        //adding Toast
                        Toast.makeText(MainActivity.this, "All tasks deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                mAlterDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mAlterDialog.show();
                break;
            //for exiting the app  using the options
            case R.id.menu_logout:
                mAlterDialog = new AlertDialog.Builder(this);
                mAlterDialog.setMessage("Are you sure want to exit?")
                        .setCancelable(false)
                        .setTitle(getString(R.string.app_name))
                        .setIcon(R.mipmap.ic_launcher);
                mAlterDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences preferences = getApplicationContext().getSharedPreferences("todo_pref", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.apply();
                        finish();
                    }
                });
                mAlterDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mAlterDialog.show();
                break;

            case R.id.delete_completed:
                mAlterDialog = new AlertDialog.Builder(this);
                mAlterDialog.setMessage("Are you sure want to delete completed task??")
                        .setCancelable(false)
                        .setTitle(getString(R.string.app_name))
                        .setIcon(R.mipmap.ic_launcher);
            mAlterDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTodoViewModel.deleteCompleted();
                        Toast.makeText(MainActivity.this, "Completed tasks deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                mAlterDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mAlterDialog.show();
                break;

            //for sharing notes  using the gmail options
            case R.id.menu_2:
            // create the share intent
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Note Title");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Note Content");

                // start the share activity
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                return true;


            //add the about us page
            case R.id.menu_about_us:
                Intent aboutUsIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutUsIntent);
                break;



        }
        return super.onOptionsItemSelected(item);
    }
}
