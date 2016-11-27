package com.example.david.myapplication;


import android.view.View;
import android.widget.Toast;


/**
 * Created by david on 13/11/16.
 */

public class editTask extends taskEditor {

    //is run when a user clicks the 'done' button
    public void complete(View v)
    {
        if(!titleBox.getText().toString().equals("")) {
            databaseManager database = new databaseManager(this);
            database.open();
            listID=listIDs.get(listChoice.getSelectedItemPosition());


            //grab infromation from user input
            Task updateTask = new Task(titleBox.getText().toString(), descriptionBox.getText().toString(), listID,
                    chosenStartDate, chosenEndDate);
            //update row in db
            database.updateTask(updateTask, id);

            database.close();

            //notifies user of task changes being saved
            Toast.makeText(this,"Task Saved",Toast.LENGTH_SHORT).show();

            //return to main activity
            finish();
        }
        else{
            noTitleAlert();
        }
    }







}
