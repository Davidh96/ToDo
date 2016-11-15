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



    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addingtask);

        completeButton=(Button)findViewById(R.id.completeButton);

        titleBox=(EditText)findViewById((R.id.taskTitleEdit));
        descriptionBox=(EditText)findViewById((R.id.taskDescriptionEdit));
        stDateBtn=(Button)findViewById(R.id.startDateEdit);
        enDateBtn=(Button)findViewById(R.id.endDateEdit);
//

        startDate=(DatePicker)findViewById(R.id.datePicker);

        listChoice = (Spinner)findViewById(R.id.listChoice);

        Intent i = getIntent();

        id= i.getLongExtra("id",-1);


        populateListChoice();
        retrieveData();



    }


    //is run when a user clicks the 'done' button
    public void complete(View v)
    {

        databaseManager database = new databaseManager(this);
        database.open();
        //grab infromation from user input
        Task updateTask = new Task(titleBox.getText().toString(), descriptionBox.getText().toString(), listID,
                chosenStartDate , chosenEndDate);
        //iupdate row in db
        database.updateTask(updateTask,id);

        database.close();

        //return to main activity
        finish();
    }

    //retrieves data from the db and places into the correct fields so user can edit them
    private void retrieveData(){
        //will hold selected information
        String data="";

        databaseManager dbm = new databaseManager(this);
        dbm.open();

        //retrieve data from row
        Cursor c = dbm.readTask(id);

        if (c.moveToFirst()){
            do{
                data = c.getString(c.getColumnIndex("taskTitle"));
                titleBox.setText(data);

                data = c.getString(c.getColumnIndex("taskDescription"));
                descriptionBox.setText(data);

                data = c.getString(c.getColumnIndex("listID"));


                data = c.getString(c.getColumnIndex("startDate"));
                if(data==null)
                {
                    data= "Pick Date";
                }
                stDateBtn.setText(data);

                data = c.getString(c.getColumnIndex("dueDate"));
                if(data==null)
                {
                    data= "Pick Date";
                }
                enDateBtn.setText(data);


            }while(c.moveToNext());
        }
        c.close();

        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();

        dbm.close();
    }





}
