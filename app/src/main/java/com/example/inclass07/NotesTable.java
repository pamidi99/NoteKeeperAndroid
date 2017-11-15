package com.example.inclass07;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by bhanu on 27-02-2017.
 */

public class NotesTable {

        static final String TABLENAME= "Notes";
        static final String COLUMN_ID = "id";
        static final String COLUMN_TITLE = "note";
        static final String COLUMN_PRIORITY= "priority";
        static final String COLUMN_UPDATETIME="update_time";
        static final String COLUMN_STATUS="status";

        static public void onCreate(SQLiteDatabase db){
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE " + TABLENAME + " (");
            sb.append(COLUMN_ID + " integer primary key autoincrement , ");
            sb.append(COLUMN_TITLE + " text not null , ");
            sb.append(COLUMN_PRIORITY+ " integer not null , ");
            sb.append(COLUMN_UPDATETIME+ " integer not null , ");
            sb.append(COLUMN_STATUS+ " text not null);");
            try {
                db.execSQL(sb.toString());
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
            NotesTable.onCreate(db);
        }
    }


