package com.slidingtest.provider;

import java.util.HashMap;
import java.util.Map;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class DbDataStore {


    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "donelist.db";

    static Map<String, DbTable> DBTABLES = new HashMap<String, DbTable>();
    static {
        DBTABLES.put(DoneListTable.getInstance().getName(), DoneListTable.getInstance());
    }

    public static final class DoneListTable extends DbTable implements BaseColumns {

        private DoneListTable() {
        };

        private static final DoneListTable sInstance = new DoneListTable();

        static DoneListTable getInstance() {
            return sInstance;
        }

        public static final String TABLE_DONELIST = "donelist";
        public static final String CONTENT = "content";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DONELIST + "( " + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," +CONTENT+" TEXT,"+ DATE + " TEXT," + TIME
                + " TEXT" + ")";

        @Override
        String getName() {
            return TABLE_DONELIST;
        }

        @Override
        void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }
    }

}
