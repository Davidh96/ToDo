package com.example.david.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by david on 13/11/16.
 */

public class editTask extends Activity {
    Button completeButton;

    EditText titleBox;
    EditText descriptionBox;
    EditText listBox;
    EditText startBox;
    EditText endBox;
    long id;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addingtask);

        completeButton=(Button)findViewById(R.id.completeButton);

        titleBox=(EditText)findViewById((R.id.taskTitleEdit));
        descriptionBox=(EditText)findViewById((R.id.taskDescriptionEdit));
        listBox=(EditText)findViewById((R.id.listIDEdit));
        startBox=(EditText)findViewById((R.id.startDateEdit));
        endBox=(EditText)findViewById((R.id.endDateEdit));

        Intent i = getIntent();

        id= i.getLongExtra("id",-1);



        retrieveData();;


    }

    //is run when a user clicks the 'done' button
    public void complete(View v)
    {
        databaseManager database = new databaseManager(this);
        database.open();
        //grab infromation from user input
//        Task newTask = new Task(titleBox.getText().toString(), descriptionBox.getText().toString(), 0,
//                startBox.getText().toString(), endBox.getText().toString());
//        //insert into database
//        database.addTask(newTask);

        database.close();

        //return to main activity
        finish();
    }

    private void retrieveData(){
        String data="";
        databaseManager dbm = new databaseManager(this);
        dbm.open();
        Cursor c = dbm.readTask(id);
        if(c!=null) {

        }

        if (c.moveToFirst()){
            do{
                data = c.getString(c.getColumnIndex("taskTitle"));
                titleBox.setText(data);

                data = c.getString(c.getColumnIndex("taskDescription"));
                descriptionBox.setText(data);

                data = c.getString(c.getColumnIndex("listID"));
                listBox.setText(data);

                data = c.getString(c.getColumnIndex("startDate"));
                startBox.setText(data);

                data = c.getString(c.getColumnIndex("dueDate"));
                endBox.setText(data);

            }while(c.moveToNext());
        }
        c.close();

        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();

        dbm.close();
    }
}
