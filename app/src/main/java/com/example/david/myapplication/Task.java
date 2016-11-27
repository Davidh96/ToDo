package com.example.david.myapplication;

/**
 * Created by David on 12/11/2016.
 */

//is used to hold data on a task
public class Task {

    private int _id;
    private String taskTitle;
    private String taskDescription;
    private long listID;
    private String startDate;
    private String dueDate;


    public  Task(String taskTitle, String taskDescription, long listID, String startDate, String dueDate)
    {
        this.taskTitle=taskTitle;
        this.taskDescription=taskDescription;
        this.listID=listID;
        this.startDate=startDate;
        this.dueDate=dueDate;
    }

    //returns task id
    public int get_id() {
        return _id;
    }

    //sets task id
    public void set_id(int _id) {
        this._id = _id;
    }

    //returns task title
    public String getTaskTitle() {
        return taskTitle;
    }

    //sets task title
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    //returns task description
    public String getTaskDescription() {
        return taskDescription;
    }

    //sets task description
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    //returns list id of task
    public long getListID() {
        return listID;
    }

    //sets list id of task
    public void setListID(int listID) {
        this.listID = listID;
    }

    //returns task start date
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    //returns task due date
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
