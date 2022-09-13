package com.example.activitytimer;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import java.security.InvalidParameterException;


public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "MainActivityFragment";

    public static final int LOADER_ID = 0;

    public MainActivityFragment() {
        Log.d(TAG, "MainActivityFragment: começa");
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: começa");
        super.onViewCreated(view, savedInstanceState);
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public androidx.loader.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: começa com id " + id);
        String[] projection = {ActivitiesContract.Columns._ID, ActivitiesContract.Columns.ACTIVITIES_NAME,
                ActivitiesContract.Columns.ACTIVITIES_DESCRIPTION, ActivitiesContract.Columns.ACTIVITIES_SORT_ORDER};

        String sortOrder = ActivitiesContract.Columns.ACTIVITIES_SORT_ORDER + "," + ActivitiesContract.Columns.ACTIVITIES_NAME;

        switch (id) {
        case LOADER_ID:
            return new CursorLoader(getActivity(),
                    ActivitiesContract.CONTENT_URI,
                    projection,
                    null,
                    null,
                    sortOrder);
        default:
            throw new InvalidParameterException(TAG + ".onCreateLoader chamado com invalid loader id" + id);
    }


        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.d(TAG, "Entrando com onLoadFinished");
            mAdapter.swapCursor(data);
            int count = mAdapter.getItemCount();

            Log.d(TAG, "onLoadFinished: contagem é: " + count);

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Log.d(TAG, "onLoaderReset: começa");
            mAdapter.swapCursor(null);
        }
    }


