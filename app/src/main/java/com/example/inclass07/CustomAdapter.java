package com.example.inclass07;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;
import java.util.Date;

import java.util.List;

/**
 * Created by bhanu on 27-02-2017.
 */

public class CustomAdapter extends ArrayAdapter<Note>{
    Context activity;
    int resource;
    List<Note> notesList;
    PrettyTime prettyTime;

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    View.OnLongClickListener listenerLong;
    public CustomAdapter(Context context, int resource, List<Note> objects, CompoundButton.OnCheckedChangeListener onCheckedChangeListener, View.OnLongClickListener listenerLong) {
        super(context, resource, objects);
        this.activity=context;
        this.resource=resource;
        this.notesList=objects;
        prettyTime=new PrettyTime();
        this.onCheckedChangeListener=onCheckedChangeListener;
        this.listenerLong=listenerLong;
    }
    public void updateList(List<Note> notesList){
        this.notesList=notesList;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(resource, parent, false);
            }
            TextView txtviewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            txtviewTitle.setText(notesList.get(position).getTitle());
            TextView txtviewPriority = (TextView) convertView.findViewById(R.id.TextViewPriority);
            txtviewPriority.setText(notesList.get(position).getPriority() + " Priority");
            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBoxCompleted);
            if (notesList.get(position).getCompleted().equals("completed")) {
                checkbox.setChecked(true);
            }
            TextView txtviewTime = (TextView) convertView.findViewById(R.id.textViewTime);
            checkbox.setTag(notesList.get(position).getId());
            checkbox.setOnCheckedChangeListener(onCheckedChangeListener);
            txtviewTime.setText(prettyTime.format(new Date(notesList.get(position).getTime())));
            convertView.setTag(notesList.get(position).getId());
            convertView.setOnLongClickListener(listenerLong);
            return convertView;

    }
}
