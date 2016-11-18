package com.example.david.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
            //grab infromation from user input
            Task updateTask = new Task(titleBox.getText().toString(), descriptionBox.getText().toString(), listID,
                    chosenStartDate, chosenEndDate);
            //iupdate row in db
            database.updateTask(updateTask, id);

            database.close();

            //return to main activity
            finish();
        }
        else{
            noTitleAlert();
        }
    }







}
