package com.example.david.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by David on 12/11/2016.
 */

public class databaseManager extends SQLiteOpenHelper {

    private static final int dbVersion=1;
    private static final String dbName="ToDoDB";
    private static final String taskTableName="task";

    private SQLiteDatabase db;

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

    public void addTask(Task newTask){
        ContentValues newInsert = new ContentValues();
        newInsert.put("taskTitle",newTask.getTaskTitle());
        newInsert.put("taskDescription",newTask.getTaskDescription());
        newInsert.put("listID",newTask.getListID());
        newInsert.put("startDate",newTask.getStartDate());
        newInsert.put("dueDate",newTask.getDueDate());
        //newInsert.put();
        db=this.getWritableDatabase();
        db.insert(taskTableName,null,newInsert);
        this.close();
    }



}
