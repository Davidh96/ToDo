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

    Button stDateBtn;
    Button enDateBtn;


    int listID;
    DatePicker startDate;

    String chosenStartDate;
    String chosenEndDate;

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
//            //places bundle from intent into a new bundle
//            Bundle returnInfo = data.getExtras();
//            //gets the date from the bundle
//            chosenDate=returnInfo.getString("dateChosen");
//            chosenDate =chosenDate + " " + returnInfo.getString("timeChosen");

            //places bundle from intent into a new bundle
            Bundle returnInfo = data.getExtras();
            String dateType = returnInfo.getString("whichDate");

            //if the choice is for the start date
            if(dateType.equals("Start")) {
                //gets the date from the bundle
                chosenStartDate = returnInfo.getString("dateChosen");
                chosenStartDate += " " + returnInfo.getString("timeChosen");
                Toast.makeText(this,chosenStartDate , Toast.LENGTH_SHORT).show();
                if(chosenStartDate.equals("null null"))
                {
                    chosenStartDate="Pick Date";
                }
                stDateBtn.setText(chosenStartDate);

            }

            // if the choice is for the due date
            if(dateType.equals("End")) {
                //gets the date from the bundle
                chosenEndDate = returnInfo.getString("dateChosen");
                chosenEndDate += " " + returnInfo.getString("timeChosen");
                Toast.makeText(this, chosenEndDate, Toast.LENGTH_SHORT).show();
                if(chosenEndDate.equals("null null"))
                {
                    chosenEndDate="Pick Date";
                }
                enDateBtn.setText(chosenEndDate);
            }
        }
    }
}
