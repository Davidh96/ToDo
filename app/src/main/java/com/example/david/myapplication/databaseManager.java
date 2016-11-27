package com.example.david.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by David on 12/11/2016.
 * is the controller of database access and manipulating the data within the database
 */

//manager of the database
public class databaseManager extends SQLiteOpenHelper {

    //database information
    private static final int dbVersion=1;
    private static final String dbName="ToDoDB";
    private static final String taskTableName="task";
    private static final String listTableName="list";

    private static SQLiteDatabase db;

    //create table sql code
    //task table sql
    String createTaskTable = "Create Table " + taskTableName +"(" +
            "_id integer primary key autoincrement," +
            "taskTitle text," +
            "taskDescription text,"+
            "listID integer," +
            "startDate text," +
            "dueDate text" +
            ");";

    //list table sql
    String createListTable = "Create Table " + listTableName +"(" +
            "_id integer primary key autoincrement," +
            "listTitle text," +
            "listDescription text,"+
            "listColour integer"+
            ");";

    //constructor
    public databaseManager(Context context) {
        super(context, dbName, null, dbVersion);
    }

    //called when the database is to be created first
    public void onCreate(SQLiteDatabase db) {
        //create table called task
        try {
            db.execSQL(createTaskTable);
            db.execSQL(createListTable);
        }
        catch(SQLException e)
        {
            Log.e("Error executing SQL: ", e.toString());
        }
    }

    //is called to upgrade the datbase
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //opens up the database for writing to it
    public databaseManager open()
    {
        //open up db for writing to
        db=this.getWritableDatabase();
        return this;
    }

    //opens up the database to read from it
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

        //insert new values into db
        db.insert(taskTableName,null,newInsert);

        requeryTask();
    }

    //insert new list
    public void addList(List newList){
        //puts user input into content values
        ContentValues newInsert = new ContentValues();
        newInsert.put("listTitle",newList.getListTitle());
        newInsert.put("listDescription",newList.getListDescription());
        newInsert.put("listColour",newList.getColour());

        //insert new values into db
        db.insert(listTableName,null,newInsert);

        requeryList();

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
        requeryTask();
        return updated;
    }

    //updates a selected list row
    public boolean updateList(List updatedList,long id){
        //puts user input into content values
        ContentValues update = new ContentValues();
        update.put("listTitle",updatedList.getListTitle());
        update.put("listDescription",updatedList.getListDescription());


        //update row
        boolean updated=db.update(listTableName,update,"_id" + "=" + id,null)>0;
        requeryList();
        return updated;
    }

    //delete a task chosen by the user
    public boolean  deleteTask(long rowID){
        boolean deleted =db.delete(taskTableName,"_id" + "=" + rowID,null)>0;
        requeryTask();
        return deleted;
    }

    //delete a list chosen by the user
    public boolean  deleteList(long rowID){
        boolean deleted =db.delete(listTableName,"_id" + "=" + rowID,null)>0;
        db.delete(taskTableName,"listID" + "=" + rowID,null);
        requeryList();
        requeryTask();
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

    //returns a cursor of all rows
    public Cursor readListTasks(long listid)
    {
        return   db.query(taskTableName,new String[]{
                "_id",
                "taskTitle",
                "taskDescription",
                "listID",
                "startDate",
                "dueDate"
        },"listID" + "=" + listid,null,null,null,null);


    }

    //returns a cursor of all rows
    public Cursor readLists()
    {
        return   db.query(listTableName,new String[]{
                "_id",
                "listTitle",
                "listDescription",
                "listColour"
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

    //returns cursor of chosen row
    public Cursor readList(long rowID) {
        return   db.query(listTableName,new String[]{
                "_id",
                "listTitle",
                "listDescription",
                "listColour"
        },"_id" + "=" +rowID,null,null,null,null);


    }


    //updates and notifies the cursor adapter of a change in the db
    public void requeryTask() {

        this.open();
        //updates cursor
        MainActivity.c = this.readTasks();
        //notifies adapter of update
        MainActivity.listAdapter.changeCursor(MainActivity.c);

        this.close();

    }

    //updates and notifies the cursor adapter of a change in the db
    public void requeryList() {

        //checks if the adapter in tasklistview has actually been created, prevents crashing when user creates new list from within taskEditor
        if(taskListView.adapt!=null) {
            this.open();
            //updates cursor
            taskListView.c = this.readLists();
            //notifies adapter of update
            taskListView.adapt.changeCursor(taskListView.c);

            this.close();
        }

    }


}
