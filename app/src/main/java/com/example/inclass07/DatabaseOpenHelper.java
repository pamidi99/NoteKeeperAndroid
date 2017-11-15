package com.example.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bhanu on 27-02-2017.
 */


    public class DatabaseOpenHelper extends SQLiteOpenHelper {
        static final String DB_NAME = "mynotes5.db";
        static final int DB_VERSION = 1;

        public DatabaseOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            NotesTable.onCreate(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            NotesTable.onUpgrade(db, oldVersion, newVersion);
        }

    }

