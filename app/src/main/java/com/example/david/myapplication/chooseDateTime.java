package com.example.david.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * Created by david on 13/11/16.
 */

public class chooseDateTime extends Activity{

    DatePicker dateChosen;
    TimePicker timeChosen;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetime);

        dateChosen=(DatePicker)findViewById(R.id.datePicker);
        timeChosen=(TimePicker)findViewById(R.id.timePicker);




    }

    public void completeDateTime(View v) {
        String chosenDateString =   dateChosen.getYear() + "-" + (dateChosen.getMonth()+1) + "-" + dateChosen.getDayOfMonth();
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

    public void cancelDateTime(View v)
    {

        Intent i = getIntent();
        //get string of the date type the user is choosing for
        String which = i.getStringExtra("forWhatDate");
        //create bundle which will be returned to addingTask/editTask activity
        Bundle returnInfo = new Bundle();
        //create key/value pair for the date
        returnInfo.putString("whichDate",which);
        returnInfo.putString("dateChosen",null);
        returnInfo.putString("timeChosen",null);
        i.putExtras(returnInfo);
        //retrun ok result code
        setResult(RESULT_OK,i);

        finish();
    }
}
