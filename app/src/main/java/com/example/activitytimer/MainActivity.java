package com.example.activitytimer;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        Log.d(TAG, "activityEditRequest: starts");
        if (mTwoPane) {
            Log.d(TAG, "activityEditRequest: in two-pane mode (tablet)");
        } else {
            Log.d(TAG, "activityEditRequest: in single-pane mode (phone)");
            //no modo 'single-pane', começa o detalhamento para o item Id selecionado
            Intent detailIntent = new Intent(this, AddEditActivity.class);
            if (activity != null) { // editar a atividade
                detailIntent.putExtra(Activity.class.getSimpleName(), activity);
                startActivity(detailIntent);
            } else { // adicionar uma nota atividade
                startActivity(detailIntent);
            }
        }
    }


}
