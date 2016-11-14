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

public class editTask extends Activity {
    Button completeButton;

    EditText titleBox;
    EditText descriptionBox;
    Spinner listChoice;


    public String chosenDate;
    int listID;
    DatePicker startDate;

    long id;

    ArrayList<String> lists= new ArrayList<String>();
    ArrayList<String> listItemID= new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addingtask);

        completeButton=(Button)findViewById(R.id.completeButton);

        titleBox=(EditText)findViewById((R.id.taskTitleEdit));
        descriptionBox=(EditText)findViewById((R.id.taskDescriptionEdit));
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
             chosenDate , "");
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


            }while(c.moveToNext());
        }
        c.close();

        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();

        dbm.close();
    }

    //ref https://developer.android.com/guide/topics/ui/controls/spinner.html
    public void populateListChoice()
    {

        //will hold selected information
        String data="";

        databaseManager dbm = new databaseManager(this);
        dbm.open();

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
            chosenDate =chosenDate + " " + returnInfo.getString("timeChosen");
        }
    }
}
