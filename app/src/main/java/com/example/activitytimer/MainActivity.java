package com.example.activitytimer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] projection = {ActivitiesContract.Columns._ID,
                ActivitiesContract.Columns.ACTIVITIES_NAME,
                ActivitiesContract.Columns.ACTIVITIES_DESCRIPTION,
                ActivitiesContract.Columns.ACTIVITIES_SORT_ORDER};


        ContentResolver contentResolver = getContentResolver();

        // Inserindo dados para teste via ContentValues
        ContentValues values = new ContentValues();

        values.put(ActivitiesContract.Columns.ACTIVITIES_NAME, "Atividade 1");
        values.put(ActivitiesContract.Columns.ACTIVITIES_DESCRIPTION, "Descrição 1");
        values.put(ActivitiesContract.Columns.ACTIVITIES_SORT_ORDER, 1);
        Uri uri = contentResolver.insert(ActivitiesContract.CONTENT_URI, values);

        Cursor cursor = contentResolver.query(ActivitiesContract.CONTENT_URI,
                projection, null, null, ActivitiesContract.Columns.ACTIVITIES_NAME);

        // Se o cursor não for igual a nulo:
        if (cursor != null) {
            Log.d(TAG, "onCreate: numeros de linhas:" + cursor.getCount());
            // Enquanto:
            while (cursor.moveToNext()) {
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + ": " + cursor.getString(i));
                }
                Log.d(TAG, "onCreate: teste");
            }
            cursor.close();
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
        if (id == R.id.menumain_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
