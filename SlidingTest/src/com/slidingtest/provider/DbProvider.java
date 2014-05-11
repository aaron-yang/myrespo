package com.slidingtest.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;


public class DbProvider extends ContentProvider {

    private static final String TAG = "[AARON]DbProvider";
    /* URI authority string */
    public static final String AUTHORITY = "com.slidingtest.provider";

    /* URI paths names */
    public static final String DONELIST_TABLE = "donelist";

    private DbHelper dbHelper;

    /* UriMatcher codes */
    private static final int DONELIST_MATCH = 0;
    private static final int DONELIST_MATCH_IDS = 1;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, DONELIST_TABLE, DONELIST_MATCH);
        sUriMatcher.addURI(AUTHORITY, DONELIST_TABLE + "/#", DONELIST_MATCH_IDS);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        if (match == UriMatcher.NO_MATCH) {
            Log.e(TAG, "delete(): Wrong URI: " + uri);
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        if (uriWithID(match)) {
            selection = BaseColumns._ID + "=" + uri.getLastPathSegment()
                    + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
        }

        SQLiteDatabase db;
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            // TODO Implement proper error handling
            Log.e(TAG, "delete(): Error opening writable database " + e);

            throw e;
        }

        int count;
        synchronized (this) {
            try {
                count = db.delete(tableName(match), selection, selectionArgs);
            } catch (SQLException e) {
                // TODO Implement proper error handling
                Log.e(TAG, "delete(): DB rows delete error " + e);

                throw e;
            }
        }


        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = sUriMatcher.match(uri);
        if (match == UriMatcher.NO_MATCH) {
            Log.e(TAG, "insert(): Wrong URI: " + uri);
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        if (uriWithID(match)) {
            Log.e(TAG, "insert(): Insert not allowed for this URI: " + uri);
            throw new IllegalArgumentException("Insert not allowed for this URI: " + uri);
        }

        SQLiteDatabase db;
        long rowId;

        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            // TODO Implement proper error handling
            Log.e(TAG, "insert(): Error opening writeable database" + e);
            throw e;
        }

        synchronized (this) {
            try {
                rowId = db.insert(tableName(match), null, values);
            } catch (SQLException e) {
                // TODO Implement proper error handling
                Log.e(TAG, "Insert() failed " + e);

                throw e;
            }
        }

        if (rowId <= 0) {
            Log.e(TAG, "insert(): Error: insert() returned " + rowId);
            throw new RuntimeException("DB insert failed");
        }

        uri = ContentUris.withAppendedId(UriHelper.removeQuery(uri), rowId);
        getContext().getContentResolver().notifyChange(uri, null);

        return uri;
    }

    @Override
    public boolean onCreate() {
        dbHelper = DbHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = sUriMatcher.match(uri);
        if (match == UriMatcher.NO_MATCH) {
            Log.e(TAG, "query(): Wrong URI");
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        int where_append_count = 0;
        qb.setTables(tableName(match));
        if (uriWithID(match)) {
            qb.appendWhere((where_append_count++ == 0 ? "" : " AND ")
                    + (BaseColumns._ID + "=" + uri.getLastPathSegment()));
        }

        if (TextUtils.isEmpty(sortOrder)) {
            sortOrder = "_ID ASC";
        }

        SQLiteDatabase db;
        try {
            db = dbHelper.getReadableDatabase();
        } catch (SQLiteException e) {
            // TODO Implement proper error handling
            Log.e(TAG, "query(): Error opening readable database " + e);

            throw e;
        }

        Cursor cursor;
        synchronized (this) {
            try {
                cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            } catch (Throwable e) {
                Log.e(TAG, "query(): Exception at db query " + e);

                throw new RuntimeException("Exception at db query: " + e.getMessage());
            }
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        if (match == UriMatcher.NO_MATCH) {
            Log.e(TAG, "update(): Wrong URI: " + uri);
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        if (uriWithID(match)) {
            selection = BaseColumns._ID + "=" + uri.getLastPathSegment()
                    + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
        }

        SQLiteDatabase db;
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            // TODO Implement proper error handling
            Log.e(TAG, "update(): Error opening writable database " + e);

            throw e;
        }

        int count;
        synchronized (this) {
            try {
                count = db.update(tableName(match), values, selection, selectionArgs);
            } catch (SQLException e) {
                // TODO Implement proper error handling
                Log.e(TAG, "update() failed " + e);

                throw e;
            }
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    private String tableName(int uri_match) {
        switch (uri_match) {
        case DONELIST_MATCH:
            return DONELIST_TABLE;
        default:
            throw new Error(TAG + " No table defined for #" + uri_match);
        }
    }

    private boolean uriWithID(int uri_match) {
        switch (uri_match) {
        case DONELIST_MATCH_IDS:
            return true;

        default:
            return false;
        }
    }

}
