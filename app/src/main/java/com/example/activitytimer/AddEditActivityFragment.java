package com.example.activitytimer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


/**
 *  placeholder fragment que contém uma view simples
 */
public class AddEditActivityFragment extends Fragment {
    private static final String TAG = "AddEditActivityFragment";

    public enum FragmentEditMode { EDIT, ADD }
    private FragmentEditMode mMode;

    private EditText mNameTextView;
    private EditText mNameDescriptionTextView;
    private EditText mNameSortOrderTextView;
    private Button mSaveButton;

    public AddEditActivityFragment() {
        Log.d(TAG, "AddEditActivityFragment: constructor called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);

        mNameTextView = (EditText) view.findViewById(R.id.add_edit_name);
        mNameDescriptionTextView = (EditText) view.findViewById(R.id.add_edit_description);
        mNameSortOrderTextView = (EditText) view.findViewById(R.id.add_edit_sort_order);
        mSaveButton = (Button) view.findViewById(R.id.add_edit_save);

        return view;
    }
}