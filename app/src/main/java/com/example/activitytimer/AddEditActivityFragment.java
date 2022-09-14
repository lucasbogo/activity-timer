package com.example.activitytimer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


/**
 * placeholder fragment que contém uma view simples
 */
public class AddEditActivityFragment extends Fragment {
    private static final String TAG = "AddEditActivityFragment";

    public enum FragmentEditMode {EDIT, ADD}

    private FragmentEditMode mMode;

    private EditText mNameTextView;
    private EditText mNameDescriptionTextView;
    private EditText mNameSortOrderTextView;
    private Button mSaveButton;
    private OnSaveClicked mSaveListener = null;

    interface OnSaveClicked {
    void onSaveClicked();
}

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

        Bundle arguments = getArguments();

        final Activity activity;
        if (arguments != null) {
            Log.d(TAG, "onCreateView: Buscando os detalhes da atividade");

            activity = (Activity) arguments.getSerializable(Activity.class.getSimpleName());
            if (activity != null) {
                Log.d(TAG, "onCreateView: Detalhes da atividade achado, editando... ");
                mNameTextView.setText(activity.getName());
                mNameDescriptionTextView.setText(activity.getDescription());
                mNameSortOrderTextView.setText(Integer.toString(activity.getSortOrder()));
                mMode = FragmentEditMode.EDIT;
            } else {
                mMode = FragmentEditMode.ADD;
            }
        } else {
            activity = null;
            Log.d(TAG, "onCreateView: Sem argumentos, adicionando novo registro");
            mMode = FragmentEditMode.ADD;


            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int so;
                    if (mNameSortOrderTextView.length() > 0) {
                        so = Integer.parseInt(mNameSortOrderTextView.getText().toString());
                    } else {
                        so = 0;
                    }

                    ContentResolver contentResolver = getActivity().getContentResolver();
                    ContentValues values = new ContentValues();

                    switch (mMode) {
                        case EDIT:
                            if (!mNameTextView.getText().toString().equals(activity.getName())) {
                                values.put(ActivitiesContract.Columns.ACTIVITIES_NAME, mNameTextView.getText().toString());
                            }
                            if (!mNameDescriptionTextView.getText().toString().equals(activity.getDescription())) {
                                values.put(ActivitiesContract.Columns.ACTIVITIES_DESCRIPTION, mNameDescriptionTextView.getText().toString());
                            }
                            if (so != activity.getSortOrder()) {
                                values.put(ActivitiesContract.Columns.ACTIVITIES_SORT_ORDER, so);
                            }
                            if (values.size() != 0) {
                                Log.d(TAG, "onClick: updating task");
                                contentResolver.update(ActivitiesContract.buildActivityUri(activity.getId()), values, null, null);
                            }
                            break;
                        case ADD:
                            if (mNameTextView.length() > 0) {
                                Log.d(TAG, "onClick: adding new task");
                                values.put(ActivitiesContract.Columns.ACTIVITIES_NAME, mNameTextView.getText().toString());
                                values.put(ActivitiesContract.Columns.ACTIVITIES_DESCRIPTION, mNameDescriptionTextView.getText().toString());
                                values.put(ActivitiesContract.Columns.ACTIVITIES_SORT_ORDER, so);
                                contentResolver.insert(ActivitiesContract.CONTENT_URI, values);
                            }
                            break;
                    }
                    Log.d(TAG, "onClick: Done editing");

                    if (mSaveListener != null) {
                        mSaveListener.onSaveClicked();
                    }
                }
            });
            Log.d(TAG, "onCreateView: Exiting...");

            return view;
        }
    }


