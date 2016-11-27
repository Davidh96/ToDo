package com.example.david.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * Created by david on 13/11/16.
 * contains code for receiving information from the datepicker/timepicker and sending them back to the task editor
 */

//controls the retrieval of date/time chosen by user
public class chooseDateTime extends Activity{

    DatePicker dateChosen;
    TimePicker timeChosen;

    //when the chooseDateTime activity begins
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetime);

        //retrieve views
        dateChosen=(DatePicker)findViewById(R.id.datePicker);
        timeChosen=(TimePicker)findViewById(R.id.timePicker);

    }

    //when the user presses the save button
    public void completeDateTime(View v) {
        //place chosen date into a string
        String chosenDateString =   dateChosen.getYear() + "-" + (dateChosen.getMonth()+1) + "-" + dateChosen.getDayOfMonth();
        //place chosen time into a string
        String chosenTimeString =   timeChosen.getHour() + ":" + timeChosen.getMinute();


        Intent i = getIntent();
        //get string of the date type the user is choosing for
        String which = i.getStringExtra("forWhatDate");
        //create bundle which will be returned to addingTask/editTask activity
        Bundle returnInfo = new Bundle();
        //create key/value pair for the date
        returnInfo.putString("whichDate",which);
        returnInfo.putString("dateChosen",chosenDateString);
        returnInfo.putString("timeChosen",chosenTimeString);
        i.putExtras(returnInfo);
        //retrun ok result code
        setResult(RESULT_OK,i);

        finish();

    }
}
