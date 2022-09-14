package com.example.activitytimer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements CursorReciclerViewAdapter.OnActivityClickListener {
    private static final String TAG = "MainActivity";


    private final boolean mTwoPane = false;

    private static final String ADD_EDIT_FRAGMENT = "AddEditFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.task_details_container) != null) {
            // The detail container view will be present only in the large-screen layouts (res/values-land and res/values-sw600dp).
            // If this view is present, then the activity should be in two-pane mode.
            mTwoPane = true;
        }
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
                activityEditRequest(null);
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

    @Override
    public void onEditClick(Activity activity) {
        activityEditRequest(activity);
    }

    @Override
    public void onDeleteClick(Activity activity) {
        getContentResolver().delete(ActivitiesContract.buildActivityUri(activity.getId()), null, null);
    }

    private void activityEditRequest(Activity activity) {

        if (mTwoPane) {
            AddEditActivityFragment fragment = new AddEditActivityFragment();

            Bundle arguments = new Bundle();
            arguments.putSerializable(Activity.class.getSimpleName(), activity);
            fragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.activity_detail_container, fragment);
            fragmentTransaction.commit();

        } else {
            Intent detailIntent = new Intent(this, AddEditActivity.class);

            if (activity != null) {
                detailIntent.putExtra(Activity.class.getSimpleName(), activity);
                startActivity(detailIntent);

            } else {
                startActivity(detailIntent);
            }
        }
    }


}
