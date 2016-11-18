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

    Task newTask;



    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addingtask);

        completeButton=(Button)findViewById(R.id.completeButton);

        titleBox=(EditText)findViewById((R.id.taskTitleEdit));
        descriptionBox=(EditText)findViewById((R.id.taskDescriptionEdit));

        stDateBtn = (Button)findViewById(R.id.startDateEdit);
        enDateBtn=(Button)findViewById(R.id.endDateEdit);

        listChoice=(Spinner)findViewById(R.id.listChoice);

        populateListChoice();

    }

    //is run when a user clicks the 'done' button
    public void complete(View v)
    {

        if(!titleBox.getText().toString().equals("")) {
            databaseManager database = new databaseManager(this);
            database.open();

            //grab infromation from user input
            newTask = new Task(titleBox.getText().toString(), descriptionBox.getText().toString(), (int) listID,
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
