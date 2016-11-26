package com.example.david.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    ArrayAdapter<String> adapter;
    EditText titleBox;
    EditText descriptionBox;
    Spinner listChoice;

    Button stDateBtn;
    Button enDateBtn;


    long listID;
    DatePicker startDate;

    String chosenStartDate;
    String chosenEndDate;

    long id;

    ArrayList<String> lists = new ArrayList<String>();
    ArrayList<Long> listIDs = new ArrayList<Long>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addingtask);

        titleBox = (EditText) findViewById((R.id.taskTitleEdit));
        descriptionBox = (EditText) findViewById((R.id.taskDescriptionEdit));
        stDateBtn = (Button) findViewById(R.id.startDateEdit);
        enDateBtn = (Button) findViewById(R.id.endDateEdit);


        startDate = (DatePicker) findViewById(R.id.datePicker);

        listChoice = (Spinner) findViewById(R.id.listChoice);

        Intent i = getIntent();

        id = i.getLongExtra("id", -1);

        populateListChoice();
        retrieveData();


    }


    //retrieves data from the db and places into the correct fields so user can edit them
    private void retrieveData() {
        //will hold selected information
        String data = "";

        databaseManager dbm = new databaseManager(this);
        dbm.open();

        //retrieve data from row
        Cursor c = dbm.readTask(id);

        if (c.moveToFirst()) {
            do {
                //gets entered title
                data = c.getString(c.getColumnIndex("taskTitle"));
                titleBox.setText(data);

                //gets entered description
                data = c.getString(c.getColumnIndex("taskDescription"));
                descriptionBox.setText(data);

                //gets chosen listid
                data = c.getString(c.getColumnIndex("listID"));
                listID = Long.parseLong(data);

                //goes trough arraylist to find the list chosen by the user
                for(int i=0;i<listIDs.size();i++)
                {
                    if(listID==listIDs.get(i))
                    {
                       listChoice.setSelection(i);
                    }
                }

                data = c.getString(c.getColumnIndex("startDate"));
                if (data == null) {
                    data = "Pick Date";
                }
                stDateBtn.setText(data);

                data = c.getString(c.getColumnIndex("dueDate"));
                if (data == null) {
                    data = "Pick Date";
                }
                enDateBtn.setText(data);


            } while (c.moveToNext());
        }
        c.close();


        dbm.close();
    }

    //ref https://developer.android.com/guide/topics/ui/controls/spinner.html
    public void populateListChoice() {

        //will hold selected information
        String data = "";

        databaseManager dbm = new databaseManager(this);
        dbm.open();
        lists.add("Default");
        listIDs.add((long)-1);
        //retrieve data from row
        Cursor c = dbm.readLists();

        if (c.moveToFirst()) {
            do {

                data = c.getString(c.getColumnIndex("listTitle"));
                lists.add(data);

                long id = c.getLong(c.getColumnIndex("_id"));
                listIDs.add(id);


            } while (c.moveToNext());
        }
        c.close();

        dbm.close();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, lists);
        listChoice.setAdapter(adapter);
    }

    public void chooseStartDate(View v) {

        Intent chooseDate = new Intent(this, chooseDateTime.class);
        //the user is choosing the start date
        chooseDate.putExtra("forWhatDate", "Start");

        //ref https://developer.android.com/reference/android/app/Activity.html#startActivityForResult(android.content.Intent,%20int,%20android.os.Bundle)
        startActivityForResult(chooseDate, 0);

    }

    public void chooseEndDate(View v) {

        Intent chooseDate = new Intent(this, chooseDateTime.class);
        //the user is choosing the end date
        chooseDate.putExtra("forWhatDate", "End");

        //ref https://developer.android.com/reference/android/app/Activity.html#startActivityForResult(android.content.Intent,%20int,%20android.os.Bundle)
        startActivityForResult(chooseDate, 0);

    }

    //retrieves date and time chosen by the user in the chooseDateTime activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            //places bundle from intent into a new bundle
            Bundle returnInfo = data.getExtras();
            String dateType = returnInfo.getString("whichDate");

            //if the choice is for the start date
            if (dateType.equals("Start")) {
                //gets the date from the bundle
                chosenStartDate = returnInfo.getString("dateChosen");
                chosenStartDate += " " + returnInfo.getString("timeChosen");
                //if user canceled selection
                if (chosenStartDate.equals("null null")) {
                    chosenStartDate = null;
                    stDateBtn.setText("Pick Date");
                } else {
                    stDateBtn.setText(chosenStartDate);
                }

            }

            // if the choice is for the due date
            if (dateType.equals("End")) {
                //gets the date from the bundle
                chosenEndDate = returnInfo.getString("dateChosen");
                chosenEndDate += " " + returnInfo.getString("timeChosen");
                //if user canceled selection
                if (chosenEndDate.equals("null null")) {
                    chosenEndDate = null;
                    enDateBtn.setText("Pick Date");
                } else {
                    enDateBtn.setText(chosenEndDate);
                }
            }
        }
    }

    //gives alert if user tries to create task without name
    public void noTitleAlert() {
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

    //opens up intent to allow user to create new list
    public void TEcreateList(View view){
        Intent createList = new Intent(this,addingList.class);
        startActivity(createList);
    }

    //will repopulate spinner list when the user returns to the intent after creating a new list
    protected void onRestart()
    {
        super.onRestart();
        populateListChoice();
    }


}
