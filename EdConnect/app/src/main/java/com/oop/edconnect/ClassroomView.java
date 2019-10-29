package com.oop.edconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ClassroomView extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private String classID;
    private String className;
    private BottomNavigationView bottomNavigationView;

    private Toolbar toolbar;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_view);

        // getting class name and Id from last activity
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            classID = (String) extras.get("classID");
            className = (String) extras.get("className");
        }

        // setting title to toolbar
        toolbar = findViewById(R.id.classview_toolbar);
        toolbar.setTitle(className);

        //setting listener
        bottomNavigationView = findViewById(R.id.classview_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.classview_fragment_container, new ClassDoubts(classID, className)).commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.classview_class:
                Toast.makeText(getApplicationContext(), "All Lessons ", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.classview_fragment_container, new LessonView(classID, className)).commit();
                break;

            case R.id.classview_doubts:
                getSupportFragmentManager().beginTransaction().replace(R.id.classview_fragment_container, new ClassDoubts(classID, className)).commit();
                break;

            case R.id.classview_info:
                getSupportFragmentManager().beginTransaction().replace(R.id.classview_fragment_container, new ClassDetails(classID,className)).commit();
                break;

        }

        return true;
    }


}
