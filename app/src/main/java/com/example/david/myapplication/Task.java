package com.example.david.myapplication;

/**
 * Created by David on 12/11/2016.
 */

//is used to hold data on a task
public class Task {

    private int _id;
    private String taskTitle;
    private String taskDescription;
    private int listID;
    private String startDate;
    private String dueDate;


    public  Task(String taskTitle, String taskDescription, int listID, String startDate, String dueDate)
    {
        this.taskTitle=taskTitle;
        this.taskDescription=taskDescription;
        this.listID=listID;
        this.startDate=startDate;
        this.dueDate=dueDate;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
