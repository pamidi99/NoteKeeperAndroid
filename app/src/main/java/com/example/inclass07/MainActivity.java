package com.example.inclass07;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,View.OnLongClickListener{
    DatabaseDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataManager=new DatabaseDataManager(this);
        final ListView listview= (ListView) findViewById(R.id.ListView);
       List<Note> notesList= dataManager.getAllNotes();
        final CustomAdapter adapter=new CustomAdapter(this,R.layout.listview_child,notesList,this,MainActivity.this);
        listview.setAdapter(adapter);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView title= (TextView) findViewById(R.id.editTextTitle);
                if(title.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter title of note",Toast.LENGTH_LONG).show();
                }
                else{
                    Spinner spinner= (Spinner) findViewById(R.id.spinner);

                    Note note=new Note(title.getText().toString(),(String)spinner.getSelectedItem());
                    dataManager.saveNote(note);
                   ArrayList<Note> notes=dataManager.getAllNotes();
                    CustomAdapter customAdapter=new CustomAdapter(MainActivity.this,R.layout.listview_child,notes,MainActivity.this,MainActivity.this);
                   listview.setAdapter(customAdapter);

                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        ListView listView= (ListView) findViewById(R.id.ListView);
        CustomAdapter adapter= (CustomAdapter) listView.getAdapter();
        if(id==R.id.action_show_all||id==R.id.action_sort_by_priority){
            ArrayList<Note> notes=dataManager.getAllNotes();
            CustomAdapter customAdapter=new CustomAdapter(MainActivity.this,R.layout.listview_child,notes,MainActivity.this,MainActivity.this);
            listView.setAdapter(customAdapter);


        }
        else if(id==R.id.action_show_pending){
            ArrayList<Note> notes=dataManager.getAllPendingNotes();
            CustomAdapter customAdapter=new CustomAdapter(MainActivity.this,R.layout.listview_child,notes,MainActivity.this,MainActivity.this);
            listView.setAdapter(customAdapter);
        }
        else if(id==R.id.action_show_completed){
            ArrayList<Note> notes=dataManager.getAllCompletedNotes();
            CustomAdapter customAdapter=new CustomAdapter(MainActivity.this,R.layout.listview_child,notes,MainActivity.this,MainActivity.this);
            listView.setAdapter(customAdapter);

        }
        else if(id==R.id.action_sort_by_time){
            ArrayList<Note>  notes= dataManager.getAllNotes();
            Collections.sort(notes, new Comparator<Note>() {
                @Override
                public int compare(Note note, Note t1) {
                    int diff=(int)(note.getTime()-t1.getTime());
                    return  diff;
                }
            });
            CustomAdapter customAdapter=new CustomAdapter(MainActivity.this,R.layout.listview_child,notes,MainActivity.this,MainActivity.this);
            listView.setAdapter(customAdapter);
        }



        return  true;

    }

    @Override
    public void onCheckedChanged(final CompoundButton compoundButton, boolean b) {
        if(!b){

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.diolog_message_negative)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Note note=dataManager.getNote((Long)compoundButton.getTag());
                            note.setCompleted("pending");
                            dataManager.updateNote(note);
                            ArrayList<Note> notes=dataManager.getAllNotes();
                            CustomAdapter customAdapter=new CustomAdapter(MainActivity.this,R.layout.listview_child,notes,MainActivity.this,MainActivity.this);
                            ListView listView= (ListView) findViewById(R.id.ListView);
                            listView.setAdapter(customAdapter);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create().show();
        }
        else{
            Note note=dataManager.getNote((Long)compoundButton.getTag());
            note.setCompleted("completed");
            dataManager.updateNote(note);
        }
    }

    
    @Override
    public boolean onLongClick(View view) {
        long id=(Long)view.getTag();
        final Note note=new Note();
        note.setId(id);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.diolog_message_positive)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dataManager.deleteNote(note);
                        ArrayList<Note> notes=dataManager.getAllNotes();
                        CustomAdapter customAdapter=new CustomAdapter(MainActivity.this,R.layout.listview_child,notes,MainActivity.this,MainActivity.this);
                        ListView listView= (ListView) findViewById(R.id.ListView);
                        listView.setAdapter(customAdapter);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
        return false;
    }
}
