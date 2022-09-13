package com.example.activitytimer;

import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CursorReciclerViewAdapter extends RecyclerView.Adapter<CursorReciclerViewAdapter.ActivityViewHolder> {
    private static final String TAG = "CursorReciclerViewAdapt";
    private Cursor cursor;

    public CursorReciclerViewAdapter(Cursor cursor) {
        Log.d(TAG, "CursorReciclerViewAdapter: construtor chamado");
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: nova view requisitada");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_items, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ActivityViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ActivityViewHolder";

        TextView name = null;
        TextView description = null;
        ImageButton editButton = null;
        ImageButton deleteButton = null;

        public ActivityViewHolder(View itemView) {
            super(itemView);
//            Log.d(TAG, "TaskViewHolder: começa");

            this.name = (TextView) itemView.findViewById(R.id.ali_name);
            this.description = (TextView) itemView.findViewById(R.id.ali_description);
            this.editButton = (ImageButton) itemView.findViewById(R.id.ali_edit);
            this.deleteButton = (ImageButton) itemView.findViewById(R.id.ali_delete);
        }

    }

}
