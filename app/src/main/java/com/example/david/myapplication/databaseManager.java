package com.example.david.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by David on 12/11/2016.
 */

public class databaseManager extends SQLiteOpenHelper {

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
        db.execSQL(createTaskTable);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public databaseManager open()
    {
        db=this.getWritableDatabase();
        return this;
    }

    public databaseManager read()
    {
        db=this.getReadableDatabase();
        return this;
    }

    public void addTask(Task newTask){
        ContentValues newInsert = new ContentValues();
        newInsert.put("taskTitle",newTask.getTaskTitle());
        newInsert.put("taskDescription",newTask.getTaskDescription());
        newInsert.put("listID",newTask.getListID());
        newInsert.put("startDate",newTask.getStartDate());
        newInsert.put("dueDate",newTask.getDueDate());

        db.insert(taskTableName,null,newInsert);


    }

    public void deleteTask(long rowID){
        db.delete(taskTableName,"_id" + "=" + rowID,null);
    }

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


}
