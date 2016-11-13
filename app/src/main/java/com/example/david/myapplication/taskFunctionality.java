package com.example.david.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by david on 13/11/16.
 */

public abstract class taskFunctionality extends Activity{
    Button completeButton;

    EditText titleBox;
    EditText descriptionBox;
    EditText listBox;
    EditText endBox;

    DatePicker startDate;

    public String chosenDate;
    Task newTask;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addingtask);

        completeButton=(Button)findViewById(R.id.completeButton);

        titleBox=(EditText)findViewById((R.id.taskTitleEdit));
        descriptionBox=(EditText)findViewById((R.id.taskDescriptionEdit));
        listBox=(EditText)findViewById((R.id.listIDEdit));
        endBox=(EditText)findViewById((R.id.endDateEdit));

        startDate=(DatePicker)findViewById(R.id.datePicker);
    }

    //is run when a user clicks the 'done' button
    public void complete(View v)
    {
        //get the date chosen by the user
        //String chosenDate =   startDate.getYear() + "-" + (startDate.getMonth()+1) + "-" + startDate.getDayOfMonth();


        databaseManager database = new databaseManager(this);
        database.open();
        //grab infromation from user input
        newTask = new Task(titleBox.getText().toString(), descriptionBox.getText().toString(), 0,
                chosenDate, endBox.getText().toString());
        //insert into database
        database.addTask(newTask);

        database.close();

        //return to main activity
        finish();
    }

    public void chooseDate(View v)
    {

        Intent chooseDate = new Intent(this,chooseDateTime.class);

        //ref https://developer.android.com/reference/android/app/Activity.html#startActivityForResult(android.content.Intent,%20int,%20android.os.Bundle)
        startActivityForResult(chooseDate,0);

    }

    //retrieves date and time chosen by the user in the chooseDateTime activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK)
        {
            //places bundle from intent into a new bundle
            Bundle returnInfo = data.getExtras();
            //gets the date from the bundle
            chosenDate=returnInfo.getString("dateChosen");
        }
    }
}
