package com.example.david.myapplication;

import android.view.View;
import android.widget.Toast;


/**
 * Created by david on 03/11/16.
 * contains code for creating a new task and sending it off to be inserted it into the database
 */

//will be used to create new task
public class addingTask extends taskEditor {

    //is run when a user clicks the 'done' button
    public void complete(View v)
    {
        //if not task title has been entered
        if(!titleBox.getText().toString().equals("")) {
            //open database
            databaseManager database = new databaseManager(this);
            database.open();

            //gets the id of the chosen list
            listID=listIDs.get(listChoice.getSelectedItemPosition());

            //grab information from user input and create a new list
            Task newTask = new Task(titleBox.getText().toString(), descriptionBox.getText().toString(), (int) listID,
                    chosenStartDate, chosenEndDate);

            //insert into database
            database.addTask(newTask);

            database.close();

            //notifies user of task changes being saved
            Toast.makeText(this,"Task Saved",Toast.LENGTH_SHORT).show();

            //return to main activity
            finish();
        }
        //if no title was entered
        else{
            //give alert
            noTitleAlert();
        }
    }

}
