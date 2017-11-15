package com.example.inclass07;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhanu on 27-02-2017.
 */


    public class NoteDAO {
        private SQLiteDatabase db;

        public NoteDAO(SQLiteDatabase db) {
            this.db = db;
        }

        public long save(Note note){
            ContentValues values = new ContentValues();
            values.put(NotesTable.COLUMN_TITLE,note.getTitle());
            values.put(NotesTable.COLUMN_PRIORITY,note.getPriorityInt());
            values.put(NotesTable.COLUMN_UPDATETIME,note.getTime());
            values.put(NotesTable.COLUMN_STATUS,note.getCompleted());
            return db.insert(NotesTable.TABLENAME, null,values);
        }

        public boolean update(Note note){
            ContentValues values = new ContentValues();
            values.put(NotesTable.COLUMN_TITLE,note.getTitle());
            values.put(NotesTable.COLUMN_PRIORITY,note.getPriorityInt());
            values.put(NotesTable.COLUMN_UPDATETIME,note.getTime());
            values.put(NotesTable.COLUMN_STATUS,note.getCompleted());
            return db.update(NotesTable.TABLENAME,values, NotesTable.COLUMN_ID + "=?", new String[]{ note.getId()+""})>0;
        }

        public boolean delete(Note note){
            return db.delete(NotesTable.TABLENAME, NotesTable.COLUMN_ID + "=?", new String[]{note.getId()+""})>0;
        }

        public Note get(long id){
            Note note = null;
            Cursor c = db.query(true, NotesTable.TABLENAME, new String[]{
                            NotesTable.COLUMN_ID, NotesTable.COLUMN_TITLE, NotesTable.COLUMN_PRIORITY,NotesTable.COLUMN_UPDATETIME,NotesTable.COLUMN_STATUS},
                    NotesTable.COLUMN_ID + "=?",new String[]{id + ""}, null,null,null,null,null);
            if(c!= null && c.moveToFirst()){
                note = buildNoteFromCursor(c);
                if(!c.isClosed()){
                    c.close();
                }
            }
            return note ;
        }
        public ArrayList<Note> getAllPendingNotes(){
            ArrayList<Note> notes = new ArrayList<Note>();
            Cursor c = db.query(NotesTable.TABLENAME, new String[]{
                    NotesTable.COLUMN_ID,NotesTable.COLUMN_TITLE, NotesTable.COLUMN_PRIORITY,NotesTable.COLUMN_UPDATETIME,NotesTable.COLUMN_STATUS},NotesTable.COLUMN_STATUS+"=?",new String[]{"pending"},null,null,NotesTable.COLUMN_PRIORITY);
            if(c!= null && c.moveToFirst()){
                do{
                    Note note= buildNoteFromCursor(c);
                    if(note!=null){
                        notes.add(note);
                    }
                }while (c.moveToNext());
                if(!c.isClosed()){
                    c.close();
                }
            }
            return notes;

       }
    public ArrayList<Note> getAllCompletedNotes(){

        ArrayList<Note> notes = new ArrayList<Note>();
        try {
            Cursor c = db.query(NotesTable.TABLENAME, new String[]{
                    NotesTable.COLUMN_ID, NotesTable.COLUMN_TITLE, NotesTable.COLUMN_PRIORITY, NotesTable.COLUMN_UPDATETIME, NotesTable.COLUMN_STATUS}, NotesTable.COLUMN_STATUS + "=?", new String[]{"completed"}, null, null, NotesTable.COLUMN_PRIORITY);
            if (c != null && c.moveToFirst()) {
                do {
                    Note note = buildNoteFromCursor(c);
                    if (note != null) {
                        notes.add(note);
                    }
                } while (c.moveToNext());
                if (!c.isClosed()) {
                    c.close();
                }
            }
        }catch (Exception e){
            Log.d("demo","error "+e.getLocalizedMessage());
            e.printStackTrace();
        }
        return notes;

    }
        public ArrayList<Note> getAll(){
            ArrayList<Note> notes = new ArrayList<Note>();
            Cursor c = db.query(NotesTable.TABLENAME, new String[]{
                    NotesTable.COLUMN_ID,NotesTable.COLUMN_TITLE, NotesTable.COLUMN_PRIORITY,NotesTable.COLUMN_UPDATETIME,NotesTable.COLUMN_STATUS}, null,null,null,null,NotesTable.COLUMN_PRIORITY);
            if(c!= null && c.moveToFirst()){
                do{
                    Note note= buildNoteFromCursor(c);
                    if(note!=null){
                        notes.add(note);
                    }
                }while (c.moveToNext());
                if(!c.isClosed()){
                    c.close();
                }
            }
            return notes;
        }

        private Note buildNoteFromCursor(Cursor c){
            Note note = null;
            if(c!=null){
                note = new Note();
                note.setId(c.getLong(0));
                note.setTitle(c.getString(1));
                note.setPriority(c.getInt(2));
                note.setTime(c.getLong(3));
                note.setCompleted(c.getString(4));
            }
            return note;
        }
    }


