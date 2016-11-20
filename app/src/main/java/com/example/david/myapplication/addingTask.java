package com.example.david.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by david on 03/11/16.
 */

public class addingTask extends taskEditor {

    //is run when a user clicks the 'done' button
    public void complete(View v)
    {

        if(!titleBox.getText().toString().equals("")) {
            databaseManager database = new databaseManager(this);
            database.open();

            listID=listChoice.getItemIdAtPosition(listChoice.getSelectedItemPosition());

            //grab infromation from user input
            Task newTask = new Task(titleBox.getText().toString(), descriptionBox.getText().toString(), (int) listID,
                    chosenStartDate, chosenEndDate);

            //insert into database
            database.addTask(newTask);

            database.close();

            //return to main activity
            finish();
        }
        else{
            noTitleAlert();
        }
    }

}
