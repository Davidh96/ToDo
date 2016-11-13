package com.example.david.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by david on 13/11/16.
 */

public class chooseDateTime extends Activity{

    DatePicker dateChosen;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetime);

        dateChosen=(DatePicker)findViewById(R.id.datePicker);


    }

    public void completeDateTime(View v) {
        String chosenDateString =   dateChosen.getYear() + "-" + (dateChosen.getMonth()+1) + "-" + dateChosen.getDayOfMonth();

        Intent i = getIntent();
        //create bundle which will be returned to addingTask/editTask activity
        Bundle returnInfo = new Bundle();
        //create key/value pair for the date
        returnInfo.putString("dateChosen",chosenDateString);
        i.putExtras(returnInfo);
        //retrun ok result code
        setResult(RESULT_OK,i);

        Toast.makeText(this,chosenDateString , Toast.LENGTH_SHORT).show();
        finish();

    }
}
