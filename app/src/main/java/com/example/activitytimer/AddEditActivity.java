package com.example.activitytimer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;


public class AddEditActivity extends AppCompatActivity {
    private static final String TAG = "AddEditActivity";

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}