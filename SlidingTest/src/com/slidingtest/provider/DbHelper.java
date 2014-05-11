package com.slidingtest.provider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = "[AARON]DbHelper";
    private static DbHelper dbHelper;
    private SQLiteDatabase db_r = null;
    private SQLiteDatabase db_w = null;
    private static final String TEMP_SUFFIX = "_temp_";

    public static synchronized DbHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context);
        }
        return dbHelper;
    }

    public DbHelper(Context context) {
        super(context, DbDataStore.DB_NAME, null, DbDataStore.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        Log.i(TAG, "onCreate Database");
        Collection<DbTable> tables = DbDataStore.DBTABLES.values();
        Iterator<DbTable> iterator = tables.iterator();

        try {
            db.beginTransaction();
            while (iterator.hasNext()) {
                iterator.next().onCreate(db);
            }
            db.setTransactionSuccessful();
        } catch (Throwable e) {
            Log.e(TAG, "onCreate(): DB creation failed:" + e);
            throw new RuntimeException("DB creation failed: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Get table names in the old DB
        Collection<String> old_tables = DbUtils.listTables(db);
        if (old_tables == null || old_tables.size() == 0) {
            Log.d(TAG, "onUpgrade(): no existing tables; calling onCreate()...");
            onCreate(db);
            return;
        }

        // Get table names in the new DB
        Set<String> new_tables = DbDataStore.DBTABLES.keySet();

        try {
            db.beginTransaction();
            // Remove old tables which are not in the new DB scheme
            HashSet<String> obsolete_tables = new HashSet<String>();
            for (String table : old_tables) {
                if (!new_tables.contains(table)) {
                    Log.d(TAG, "onUpgrade(): remove table: " + table);
                    DbUtils.dropTable(db, table);
                    obsolete_tables.add(table);
                }
            }
            old_tables.removeAll(obsolete_tables);

            // Create and upgrade new tables
            DbTable table_descriptor;
            for (String table : new_tables) {
                table_descriptor = DbDataStore.DBTABLES.get(table);

                // Check if the new table exists in the old DB
                if (old_tables.contains(table)) {
                    String temp_name = getTempTableName(table, old_tables, new_tables);
                    table_descriptor.onUpgrade(db, oldVersion, newVersion, temp_name);
                } else {
                    table_descriptor.onCreate(db);
                }
            }
            db.setTransactionSuccessful();
        } catch (Throwable e) {
            Log.e(TAG, "onUpgrade(): DB upgrade failed:" + e);

            throw new RuntimeException("DB upgrade failed: " + e.getMessage());
        } finally {
            db.endTransaction();
        }

        db.execSQL("DROP TABLE IF EXISTS " + DbDataStore.DB_NAME);
        onCreate(db);
    }

    /**
     * Generates temporary name for use during table upgrade. The name is guaranteed to be unique, i.e. not used as
     * table name in the old and new DB schemes.
     * 
     * @param tableName
     * @param oldTableNames
     * @param newTableNames
     * @return table name
     */
    private String getTempTableName(String tableName, Collection<String> oldTableNames, Set<String> newTableNames) {
        String temp_name_base = tableName + TEMP_SUFFIX;

        if (!oldTableNames.contains(temp_name_base) && !newTableNames.contains(temp_name_base)) {
            return temp_name_base;
        }

        Random random = new Random();
        String temp_name;
        for (;;) {
            temp_name = temp_name_base + random.nextInt();
            if (!oldTableNames.contains(temp_name) && !newTableNames.contains(temp_name)) {
                return temp_name;
            }
        }
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        if (db_r == null || !db_r.isOpen()) {
            try {
                db_r = super.getReadableDatabase();
            } catch (SQLiteException e) {
                db_r = null;
                Log.e(TAG, "getReadableDatabase(): Error opening");
                throw e;
            }
        }
        return db_r;
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        if (db_w == null || !db_w.isOpen() || db_w.isReadOnly()) {
            try {
                db_w = super.getWritableDatabase();
            } catch (SQLiteException e) {
                db_w = null;
                Log.e(TAG, "getWritableDatabase(): Error");
                throw e;
            }
        }
        return db_w;
    }

}
