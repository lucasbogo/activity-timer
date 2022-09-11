package com.example.activitytimer;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.Duration;

/**
 * Criado por Lucas Bogo em 09/09/2022
 *
 * 'Provider' para o aplicativo ActivityTimer;
 *
 * Essa é a única classe que conhece {@link AppDatabase}
 *
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

    private static final int TIMINGS = 200;
    private static final int TIMINGS_ID = 201;

    //private static final int ACTIVITIES_TIMINGS = 300;
    //private static final int ACTIVITIES_TIMINGS_ID = 301;

    private static final int ACTIVITIES_DURATIONS = 400;
    private static final int ACTIVITIES_DURATIONS_ID = 401;
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
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
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
        // Passei os parâmetros do 'query method' acima
        return sqLiteQueryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

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
                if (recordId >=0) {
                    returnUri = ActivitiesContract.BuildActivityUri(recordId);
                }else {
                    throw new android.database.SQLException("Falha ao inserir dados " + uri.toString());
                }
                break;
            case TIMINGS:
            /*    db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(TimingsContract.Timings.buildTimingUri(recordId));
                if (recordId >=0) {
                    returnUri = TimingsContract.Timings.buildTimingUri(recordId);
                }else {
                    throw new android.database.SQLException("Falha ao inserir dados " + uri.toString());
                break;*/

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        Log.d(TAG, "insert: Saindo do 'insert', retornando " + returnUri);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
