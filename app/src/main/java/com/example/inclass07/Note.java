package com.example.inclass07;

import java.util.Date;

/**
 * Created by bhanu on 27-02-2017.
 */

public class Note {
    private long id;
    private long time;
    private String title,completed;
    int priority;
   public Note(){

   }
    public Note(String title, String priority) {
        this.title = title;

        if(priority.equals("High")){
            this.priority=1;
        }
        else if(priority.equals("Medium")){
            this.priority=2;
        }
        else if(priority.equals("Low")){
            this.priority=3;
        }
        completed="pending";
        time=System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        if(priority==1){
            return "High";
        }
        else if(priority==2){
            return "Medium";
        }
        else{
            return "Low";
        }
    }
    public int getPriorityInt(){
        return priority;
    }
    public void setPriority(int priority) {
      this.priority=priority;

    }
    public void setPriority(String priority) {

        if(priority.equals("High")){
            this.priority=1;
        }
        else if(priority.equals("Medium")){
            this.priority=2;
        }
        else if(priority.equals("Low")){
            this.priority=3;
        }
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
}
