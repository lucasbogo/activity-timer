package com.example.activitytimer;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Criado por Lucas Bogo em 09/09/2022
 * <p>
 * 'Provider' para o aplicativo ActivityTimer;
 * <p>
 * Essa é a única classe que conhece {@link AppDatabase}
 */

public class AppProvider extends ContentProvider {

    // Contantes
    private static final String TAG = "AppProvider";

    private AppDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final String CONTENT_AUTHORITY = "com.example.activitytimer.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int ACTIVITIES = 100;
    private static final int ACTIVITIES_ID = 101;

   /* private static final int TIMINGS = 200;
    private static final int TIMINGS_ID = 201;*/

    //private static final int ACTIVITIES_TIMINGS = 300;
    //private static final int ACTIVITIES_TIMINGS_ID = 301;

/*    private static final int ACTIVITIES_DURATIONS = 400;
    private static final int ACTIVITIES_DURATIONS_ID = 401;*/
    // Final constantes

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Exemplo: content://com.example.activitytimer.provider/Activities
        matcher.addURI(CONTENT_AUTHORITY, ActivitiesContract.TABLE_NAME, ACTIVITIES);

        // Exemplo: content://com.example.activitytimer.provider/Activities/8
        matcher.addURI(CONTENT_AUTHORITY, ActivitiesContract.TABLE_NAME + "/#", ACTIVITIES_ID);

       /* matcher.addURI(CONTENT_AUTHORITY, TimingsContract.TABLE_NAME, TIMINGS);
        matcher.addURI(CONTENT_AUTHORITY, TimingsContract.TABLE_NAME + "/#", TIMINGS_ID);

        matcher.addURI(CONTENT_AUTHORITY, DurationsContract.TABLE_NAME, ACTIVITIES_DURATIONS);
        matcher.addURI(CONTENT_AUTHORITY, DurationsContract.TABLE_NAME + "/#", ACTIVITIES_DURATIONS_ID);*/

        return matcher;

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = AppDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sort_oder) {
        Log.d(TAG, "query: chamado com a URI " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "query: match é " + match);

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case ACTIVITIES:
                sqLiteQueryBuilder.setTables(ActivitiesContract.TABLE_NAME);
                break;

            case ACTIVITIES_ID:
                sqLiteQueryBuilder.setTables(ActivitiesContract.TABLE_NAME);
                long activitiesId = ActivitiesContract.getActivityId(uri);
                sqLiteQueryBuilder.appendWhere(ActivitiesContract.Columns._ID + " = " + activitiesId);
                break;

        /*  case TIMINGS:
                sqLiteQueryBuilder.setTables(TimingsContract.TABLE_NAME);
                break;

            case TIMINGS_ID:
                sqLiteQueryBuilder.setTables(TimingsContract.TABLE_NAME);
                long timingId = ActivitiesContract.getTimingId(uri);
                sqLiteQueryBuilder.appendWhere(ActivitiesContract.Columns._ID + " = " + timingId);
                break;

            case ACTIVITIES_DURATIONS:
                sqLiteQueryBuilder.setTables(DurationsContract.TABLE_NAME);
                break;

            case ACTIVITIES_DURATIONS_ID:
                sqLiteQueryBuilder.setTables(DurationsContract.TABLE_NAME);
                long activitiesDurationIdgId = ActivitiesContract.activitiesDurationId(uri);
                sqLiteQueryBuilder.appendWhere(ActivitiesContract.Columns._ID + " = " + activitiesDurationId);
                break; */


            default:
                throw new IllegalArgumentException("URI desconhecida: " + uri);

        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteQueryBuilder.query(db, projection, selection, selectionArgs, null, null, sort_oder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "query: match é " + match);

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case ACTIVITIES:
                sqLiteQueryBuilder.setTables(ActivitiesContract.TABLE_NAME);
                break;

            case ACTIVITIES_ID:
                sqLiteQueryBuilder.setTables(ActivitiesContract.TABLE_NAME);
                long activitiesId = ActivitiesContract.getActivityId(uri);
                sqLiteQueryBuilder.appendWhere(ActivitiesContract.Columns._ID + " = " + activitiesId);
                break;

        /*  case TIMINGS:
                sqLiteQueryBuilder.setTables(TimingsContract.TABLE_NAME);
                break;

            case TIMINGS_ID:
                sqLiteQueryBuilder.setTables(TimingsContract.TABLE_NAME);
                long timingId = ActivitiesContract.getTimingId(uri);
                sqLiteQueryBuilder.appendWhere(ActivitiesContract.Columns._ID + " = " + timingId);
                break;

            case ACTIVITIES_DURATIONS:
                sqLiteQueryBuilder.setTables(DurationsContract.TABLE_NAME);
                break;

            case ACTIVITIES_DURATIONS_ID:
                sqLiteQueryBuilder.setTables(DurationsContract.TABLE_NAME);
                long activitiesDurationIdgId = ActivitiesContract.activitiesDurationId(uri);
                sqLiteQueryBuilder.appendWhere(ActivitiesContract.Columns._ID + " = " + activitiesDurationId);
                break; */

            default:
                throw new IllegalArgumentException("URI desconhecida: " + uri);
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert: Entrando com 'insert' e chamado com uri: " + uri);
        // Processar a URI recebida
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "insert: 'match' é " + match);

        final SQLiteDatabase db;

        Uri returnUri;
        long recordId;

        switch (match) {
            case ACTIVITIES:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(ActivitiesContract.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    returnUri = ActivitiesContract.buildActivityUri(recordId);
                } else {
                    throw new android.database.SQLException("Falha ao inserir dados " + uri);
                }
                break;
           /*  case TIMINGS:
               db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(TimingsContract.Timings.buildTimingUri(recordId));
                if (recordId >=0) {
                    returnUri = TimingsContract.Timings.buildTimingUri(recordId);
                }else {
                    throw new android.database.SQLException("Falha ao inserir dados " + uri.toString());
                break;*/

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        if (recordId >= 0) {
            // something was inserted
            Log.d(TAG, "insert: Setting notifyChanged with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "insert: nothing inserted");
        }

        Log.d(TAG, "Exiting insert, returning " + returnUri);
        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update: chamado com uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "Combinação é " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case ACTIVITIES:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(ActivitiesContract.TABLE_NAME, selection, selectionArgs);
                break;

            case ACTIVITIES_ID:
                db = mOpenHelper.getWritableDatabase();
                long activityId = ActivitiesContract.getActivityId(uri);
                selectionCriteria = ActivitiesContract.Columns._ID + " = " + activityId;

                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " E (" + selection + ")";
                }
                count = db.delete(ActivitiesContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            /*case TIMINGS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(TimingsContract.TABLE_NAME, selection, selectionArgs);
                break;

            case TIMINGS_ID:
                db = mOpenHelper.getWritableDatabase();
                long timingsId = TimingsContract.getTimingsId(uri);
                selectionCriteria = TimingsContract.Columns._ID + " = " + timingsId;

                if ((selection != null) && (selection.length()>0)) {
                    selectionCriteria += " E (" + selection + ")";
                }
                count = db.delete(ActivitiesContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break; */

            default:
                throw new IllegalArgumentException("uri desconhecida " + uri);
        }
        if (count > 0) {
            // something was deleted
            Log.d(TAG, "delete: Setting notifyChange with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "delete: nada deletedo");
        }

        Log.d(TAG, "Saindo do update, returnando " + count);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d(TAG, "update: chamado com uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "Combinação é " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch (match) {
            case ACTIVITIES:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(ActivitiesContract.TABLE_NAME, values, selection, selectionArgs);
                break;

            case ACTIVITIES_ID:
                db = mOpenHelper.getWritableDatabase();
                long activityId = ActivitiesContract.getActivityId(uri);
                selectionCriteria = ActivitiesContract.Columns._ID + " = " + activityId;

                if ((selection != null) && (selection.length() > 0)) {
                    selectionCriteria += " E (" + selection + ")";
                }
                count = db.update(ActivitiesContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            /*case TIMINGS:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(TimingsContract.TABLE_NAME, values, selection, selectionArgs);
                break;

            case TIMINGS_ID:
                db = mOpenHelper.getWritableDatabase();
                long timingsId = TimingsContract.getTimingsId(uri);
                selectionCriteria = TimingsContract.Columns._ID + " = " + timingsId;

                if ((selection != null) && (selection.length()>0)) {
                    selectionCriteria += " E (" + selection + ")";
                }
                count = db.update(ActivitiesContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break; */

            default:
                throw new IllegalArgumentException("uri desconhecida " + uri);
        }

        if (count > 0) {
            // something was deleted
            Log.d(TAG, "update: Setting notifyChange with " + uri);
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.d(TAG, "update: nada deletado");
        }
        // Mostrar o número de registros atualizados
        Log.d(TAG, "Saindo do update, returnando " + count);
        return count;
    }
}
