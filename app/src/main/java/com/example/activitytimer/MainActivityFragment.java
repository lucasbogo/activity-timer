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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.security.InvalidParameterException;


public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_ID = 0;
    private static final String TAG = "MainActivityFragment";
    private CursorReciclerViewAdapter mAdapter; // Adiciona a referencia do 'adapter'


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
        Log.d(TAG, "onCreateView: começa");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.activity_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new CursorReciclerViewAdapter(null);
        recyclerView.setAdapter(mAdapter);

        Log.d(TAG, "onCreateView: retornando");
        return view;
    }

    @Override
    public androidx.loader.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: começa com id " + id);
        String[] projection = {ActivitiesContract.Columns._ID, ActivitiesContract.Columns.ACTIVITIES_NAME,
                ActivitiesContract.Columns.ACTIVITIES_DESCRIPTION, ActivitiesContract.Columns.ACTIVITIES_SORT_ORDER};

        String sortOrder = ActivitiesContract.Columns.ACTIVITIES_SORT_ORDER + "," + ActivitiesContract.Columns.ACTIVITIES_NAME + " COLLATE NO CASE";

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

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "Entrando com onLoadFinished: ");
        mAdapter.swapCursor(data);
        int count = mAdapter.getItemCount();

        Log.d(TAG, "onLoadFinished: quantidade é: " + count);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);

    }
}


