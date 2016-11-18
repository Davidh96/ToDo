package com.example.david.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by david on 15/11/16.
 */

public class taskEditor extends Activity {

    Button completeButton;

    EditText titleBox;
    EditText descriptionBox;
    Spinner listChoice;

    Button stDateBtn;
    Button enDateBtn;


    int listID;
    DatePicker startDate;

    String chosenStartDate;
    String chosenEndDate;

    long id;

    ArrayList<String> lists= new ArrayList<String>();
    ArrayList<String> listItemID= new ArrayList<String>();

    public void cancelCreation(View v)
    {
        finish();
    }

    //ref https://developer.android.com/guide/topics/ui/controls/spinner.html
    public void populateListChoice()
    {

        //will hold selected information
        String data="";

        databaseManager dbm = new databaseManager(this);
        dbm.open();
        lists.add("Default");
        //retrieve data from row
        Cursor c = dbm.readLists();

        if (c.moveToFirst()){
            do{
                data = c.getString(c.getColumnIndex("listTitle"));
                lists.add(data);

                data = c.getString(c.getColumnIndex("_id"));
                listItemID.add(data);
                listID=Integer.parseInt(data);


            }while(c.moveToNext());
        }
        c.close();

        //Toast.makeText(this, "" +id, Toast.LENGTH_SHORT).show();

        dbm.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lists);
        listChoice.setAdapter(adapter);
    }

    public void chooseStartDate(View v)
    {

        Intent chooseDate = new Intent(this,chooseDateTime.class);
        //the user is choosing the start date
        chooseDate.putExtra("forWhatDate","Start");

        //ref https://developer.android.com/reference/android/app/Activity.html#startActivityForResult(android.content.Intent,%20int,%20android.os.Bundle)
        startActivityForResult(chooseDate,0);

    }

    public void chooseEndDate(View v)
    {

        Intent chooseDate = new Intent(this,chooseDateTime.class);
        //the user is choosing the end date
        chooseDate.putExtra("forWhatDate","End");

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
            String dateType = returnInfo.getString("whichDate");

            //if the choice is for the start date
            if(dateType.equals("Start")) {
                //gets the date from the bundle
                chosenStartDate = returnInfo.getString("dateChosen");
                chosenStartDate += " " + returnInfo.getString("timeChosen");
                Toast.makeText(this,chosenStartDate , Toast.LENGTH_SHORT).show();
                //if user canceled selection
                if(chosenStartDate.equals("null null"))
                {
                    chosenStartDate=null;
                    stDateBtn.setText("Pick Date");
                }
                else {
                    stDateBtn.setText(chosenStartDate);
                }

            }

            // if the choice is for the due date
            if(dateType.equals("End")) {
                //gets the date from the bundle
                chosenEndDate = returnInfo.getString("dateChosen");
                chosenEndDate += " " + returnInfo.getString("timeChosen");
                Toast.makeText(this, chosenEndDate, Toast.LENGTH_SHORT).show();
                //if user canceled selection
                if(chosenEndDate.equals("null null"))
                {
                    chosenEndDate=null;
                    enDateBtn.setText("Pick Date");
                }
                else {
                    enDateBtn.setText(chosenEndDate);
                }
            }
        }
    }

    public void noTitleAlert()
    {
        //create alert box
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //alert title
        alert.setTitle("Eh-hem, forget something?")
                //alert message
                .setMessage("You MUST enter a Task Name")
                //if user clicks yes
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }

                })
                .show();
    }
}
