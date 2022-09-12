package com.example.activitytimer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    // Verificar se a 'activity' está no modo '2-pane'
    // i.e. running in landscape on a tablet
    private boolean mTwoPane = false;

    private static final String ADD_EDIT_FRAGMENT = "AddEditFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menumain_addActivity:
                taskEditRequest(null);
                break;
            case R.id.menumain_showDurations:
                break;
            case R.id.menumain_settings:
                break;
            case R.id.menumain_showAbout:
                break;
            case R.id.menumain_generate:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void taskEditRequest(Activity activity) {
        Log.d(TAG, "taskEditRequest: starts");
        if (mTwoPane) {
            Log.d(TAG, "taskEditRequest: in two-pane mode (tablet)");
        } else {
            Log.d(TAG, "taskEditRequest: in single-pane mode (phone)");
            // in single-pane mode, start the detail activity for the selected item Id.
            Intent detailIntent = new Intent(this, AddEditActivity.class);
            if (activity != null) { // editing a task
                detailIntent.putExtra(Activity.class.getSimpleName(), activity);
                startActivity(detailIntent);
            } else { // adding a new task
                startActivity(detailIntent);
            }
        }
    }
}
