package com.example.activitytimer;

import android.annotation.SuppressLint;
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
    private Cursor mCursor;
    //private OnActivityClickListener mListener;

    /*interface OnTaskClickListener {
        void onEditClick(Activity activity);

        void onDeleteClick(Activity activity);
    }*/

    public CursorReciclerViewAdapter(Cursor cursor) {
        Log.d(TAG, "CursorReciclerViewAdapter: construtor chamado");
        this.mCursor = cursor;
        // this.mListener = listener;
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
        Log.d(TAG, "onBindViewHolder: começa");

        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            Log.d(TAG, "onBindViewHolder: fornecendo instruções");
            holder.name.setText(R.string.instructions_heading);
            holder.description.setText(R.string.instructions);
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        } else {
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Não foi possível mover o  cursor para a posição " + position);
            }

            @SuppressLint("Range") final Activity activity = new Activity(mCursor.getLong(mCursor.getColumnIndex(ActivitiesContract.Columns._ID)),
                    mCursor.getString(mCursor.getColumnIndex(ActivitiesContract.Columns.ACTIVITIES_NAME)),
                    mCursor.getString(mCursor.getColumnIndex(ActivitiesContract.Columns.ACTIVITIES_DESCRIPTION)),
                    mCursor.getInt(mCursor.getColumnIndex(ActivitiesContract.Columns.ACTIVITIES_SORT_ORDER)));

            holder.name.setText(activity.getName());
            holder.description.setText(activity.getDescription());
            holder.editButton.setVisibility(View.VISIBLE);  // TODO add onClick listener
            holder.deleteButton.setVisibility(View.VISIBLE); // TODO add onClick listener
        }

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: começa");
        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            return 1; // fib, pq populamos um unica ViewHolder com as instruções;
        } else {
            return mCursor.getCount();
        }
    }

    /**
     * Troca (swap) por um 'Cursor' novo e retorna o velho Cursor.
     * o velho cursor retornado <em>não é</em> fechado.
     *
     * @param newCursor o novo Cursor a ser usado.
     * @return Retorna o Cursor previamente posto, ou nulo se não existe um
     * Se o novo cursor dado stá na mesma instância como previamente colocado.
     * então 'null1 também é retornado.
     */
    Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
        }
        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if (newCursor != null) {
            //
            notifyDataSetChanged();
        } else {
            //
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
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
