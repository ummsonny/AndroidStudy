package com.example.mission22;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BookDatabase {

    /**
     * TAG for debugging
     */
    public static final String TAG = "BookDatabase";

    /**
     * database name
     */
    public static String DATABASE_NAME = "book.db";

    /**
     * table name for BOOK_INFO
     */
    public static String TABLE_BOOK_INFO = "BOOK_INFO";

    /**
     * version
     */
    public static int DATABASE_VERSION = 2;


    /**
     * Helper class defined
     */
    private DatabaseHelper dbHelper;

    /**
     * Database object
     */
    private SQLiteDatabase db;


    private Context context;

    /**
     * Constructor
     */
    private BookDatabase(Context context) {
        this.context = context;
    }

    /**
     * Singleton instance
     */
    private static BookDatabase database;

    public static BookDatabase getInstance(Context context) {
        if (database == null) {
            database = new BookDatabase(context);
        }

        return database;
    }

    /**
     * open database
     *
     * @return
     */
    public boolean open() {
        println("opening database [" + DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    /**
     * close database
     */
    public void close() {
        println("closing database [" + DATABASE_NAME + "].");
        db.close();
        database = null;
    }

    /**
     * execute raw query using the input SQL
     * close the cursor after fetching any result
     *
     * @param SQL
     * @return
     */
    public Cursor rawQuery(String SQL) {
        println("\nexecuteQuery called.\n");

        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
            println("cursor count : " + c1.getCount());
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
        }

        return c1;
    }

    public boolean execSQL(String SQL) {
        println("\nexecute called.\n");

        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
            return false;
        }

        return true;
    }

    public void insertRecord(String name, String author, String contents) {
        try {
            db.execSQL( "insert into " + TABLE_BOOK_INFO + "(NAME, AUTHOR, CONTENTS) values ('" + name + "', '" + author + "', '" + contents + "');" );
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }

    public ArrayList<BookInfo> selectAll() {
        ArrayList<BookInfo> result = new ArrayList<BookInfo>();

        try {
            Cursor cursor = db.rawQuery("select NAME, AUTHOR, CONTENTS from " + TABLE_BOOK_INFO, null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String name = cursor.getString(0);
                String author = cursor.getString(1);
                String contents = cursor.getString(2);

                BookInfo info = new BookInfo(name, author, contents);
                result.add(info);
            }

        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }

        return result;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //*****onCreate??? ?????? ?????? ??????????????? ?????????. ?????? ?????????????????? ????????? ????????? onCreate??? ???????????? ?????? onOpen??? ?????????****************
        public void onCreate(SQLiteDatabase db) {
            // TABLE_BOOK_INFO
            println("creating table [" + TABLE_BOOK_INFO + "].");

            // drop existing table
            String DROP_SQL = "drop table if exists " + TABLE_BOOK_INFO;
            try {
                db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            // create table
            String CREATE_SQL = "create table " + TABLE_BOOK_INFO + "("
                    + "  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  NAME TEXT, "
                    + "  AUTHOR TEXT, "
                    + "  CONTENTS TEXT, "
                    + "  CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";
            try {
                db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }

             //insert 5 book records
            insertRecord(db, "Do it! ??????????????? ??? ???????????????", "?????????", "??????????????? ???????????? ????????????????????? ??????????????? ??????????????????.");
            insertRecord(db, "Programming Android", "Mednieks, Zigurd", "Oreilly Associates Inc?????? 2011??? 04?????? ??????????????????.");
            insertRecord(db, "???????????? ????????? ???????????????", "?????????,????????? ??????", "???????????????????????? 2011??? 10?????? ??????????????????.");
            insertRecord(db, "???????????????! ??????????????? ?????? ???????????????", "????????? ????????? ???", "?????????????????? 2011??? 09?????? ??????????????????.");
            insertRecord(db, "??????! ??????????????? ????????? ??????????????? ????????????", "?????????,????????? ??????", "DW Wave?????? 2010??? 10?????? ??????????????????.");

        }

        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DATABASE_NAME + "].");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");

            if (oldVersion > 1) {   // version 1
                database.execSQL("DROP TABLE IF EXISTS "+TABLE_BOOK_INFO);
            }

        }

        private void insertRecord(SQLiteDatabase _db, String name, String author, String contents) {
            try {
                _db.execSQL( "insert into " + TABLE_BOOK_INFO + "(NAME, AUTHOR, CONTENTS) values ('" + name + "', '" + author + "', '" + contents + "');" );
            } catch(Exception ex) {
                Log.e(TAG, "Exception in executing insert SQL.", ex);
            }
        }

    }

    private void println(String msg) {
        Log.d(TAG, msg);
    }

}