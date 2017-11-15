package com.example.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhanu on 27-02-2017.
 */


    public class DatabaseDataManager {
        private Context mContext;
        private DatabaseOpenHelper dbOpenHelper;
        private SQLiteDatabase db;
        private NoteDAO noteDAO;

        public DatabaseDataManager(Context mContext){
            this.mContext=mContext;
            dbOpenHelper= new DatabaseOpenHelper(this.mContext);
            db = dbOpenHelper.getWritableDatabase();
            noteDAO= new NoteDAO(db);
        }
        public void close(){
            if(db!=null){
                db.close();
            }
        }
    public ArrayList<Note> getAllCompletedNotes(){
        ArrayList<Note> currentNotes=this.noteDAO.getAllCompletedNotes();
        ArrayList<Note> originalCopy=new ArrayList<Note>();
        /*
        for(int i=0;i<currentNotes.size();i++){
            if(currentNotes.get(i).getCompleted().equals("completed")){
                originalCopy.add(currentNotes.get(i));
            }
        }*/
        return currentNotes;
    }
    public ArrayList<Note> getAllPendingNotes(){
        ArrayList<Note> currentNotes=this.noteDAO.getAll();
        ArrayList<Note> originalCopy=new ArrayList<Note>();
        for(int i=0;i<currentNotes.size();i++){
            if(currentNotes.get(i).getCompleted().equals("pending")){
                originalCopy.add(currentNotes.get(i));
            }
        }
        return originalCopy;
    }

        public NoteDAO getNoteDAO(){
            return this.noteDAO;
        }
        public long saveNote(Note note){
            return this.noteDAO.save(note);
        }
        public boolean updateNote(Note note){
            return this.noteDAO.update(note);
        }
        public boolean deleteNote(Note note){
            return this.noteDAO.delete(note);
        }
        public Note getNote(long id){
            return this.noteDAO.get(id);
        }
        public ArrayList<Note> getAllNotes(){
            ArrayList<Note> completedNotes=new ArrayList<Note>();
            ArrayList<Note> prevNotes=this.noteDAO.getAll();
            ArrayList<Note> copyofNotes=(ArrayList<Note>)prevNotes.clone();
            for(int i=0;i<prevNotes.size();i++){
                if(prevNotes.get(i).getCompleted().equals("completed")){
                    copyofNotes.remove(prevNotes.get(i));
                    completedNotes.add(prevNotes.get(i));
                }
            }
            copyofNotes.addAll(completedNotes);
            return copyofNotes;
        }
    }

