package com.example.david.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by David on 12/11/2016.
 */

public class databaseManager extends SQLiteOpenHelper {

    //database information
    private static final int dbVersion=1;
    private static final String dbName="ToDoDB";
    private static final String taskTableName="task";

    private static SQLiteDatabase db;

    //create table sql code
    String createTaskTable = "Create Table " + taskTableName +"(" +
            "_id integer primary key autoincrement," +
            "taskTitle text," +
            "taskDescription text,"+
            "listID integer," +
            "startDate text," +
            "dueDate text" +
            ");";

    public databaseManager(Context context) {
        super(context, dbName, null, dbVersion);
    }

    public void onCreate(SQLiteDatabase db) {
        //create table called task
        db.execSQL(createTaskTable);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public databaseManager open()
    {
        //open up db for writing to
        db=this.getWritableDatabase();
        return this;
    }

    public databaseManager read()
    {
        //opens up db for reading from
        db=this.getReadableDatabase();
        return this;
    }

    //insert new task
    public void addTask(Task newTask){
        //puts user input into content values
        ContentValues newInsert = new ContentValues();
        newInsert.put("taskTitle",newTask.getTaskTitle());
        newInsert.put("taskDescription",newTask.getTaskDescription());
        newInsert.put("listID",newTask.getListID());
        newInsert.put("startDate",newTask.getStartDate());
        newInsert.put("dueDate",newTask.getDueDate());

        //inswrt new values into db
        db.insert(taskTableName,null,newInsert);

        requery();

    }

    //updates a selected task row
    public boolean updateTask(Task updatedTask,long id){
        //puts user input into content values
        ContentValues update = new ContentValues();
        update.put("taskTitle",updatedTask.getTaskTitle());
        update.put("taskDescription",updatedTask.getTaskDescription());
        update.put("listID",updatedTask.getListID());
        update.put("startDate",updatedTask.getStartDate());
        update.put("dueDate",updatedTask.getDueDate());

        //update row
        boolean updated=db.update(taskTableName,update,"_id" + "=" + id,null)>0;
        requery();
        return updated;
    }

    //delete a task chosen by the user
    public boolean  deleteTask(long rowID){
        boolean deleted =db.delete(taskTableName,"_id" + "=" + rowID,null)>0;
        requery();
        return deleted;
    }

    //returns a cursor of all rows
    public Cursor readTasks()
    {
        return   db.query(taskTableName,new String[]{
                "_id",
                "taskTitle",
                "taskDescription",
                "listID",
                "startDate",
                "dueDate"
        },null,null,null,null,null);


    }

    //returns cursor of chosen row
    public Cursor readTask(long rowID) {
        return   db.query(taskTableName,new String[]{
                "_id",
                "taskTitle",
                "taskDescription",
                "listID",
                "startDate",
                "dueDate"
        },"_id" + "=" +rowID,null,null,null,null);


    }


    //updates and notifies the cursor adapter of a change in the db
    public void requery() {

        this.open();
        //updates cursor
        MainActivity.c = this.readTasks();
        //notifies adapter of update
        MainActivity.adapt.changeCursor(MainActivity.c);

        this.close();

    }


}
