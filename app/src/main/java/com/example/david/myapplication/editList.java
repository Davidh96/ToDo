package com.example.david.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by david on 14/11/16.
 */

public class editList extends Activity {


    EditText listTitle;
    EditText listDescription;

    long id;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinglist);

        listTitle=(EditText)findViewById(R.id.listTitleEdit);
        listDescription=(EditText)findViewById(R.id.listDescriptionEdit);

        Intent i = getIntent();

        id= i.getLongExtra("id",-1);



        retrieveData();


    }


    //is run when a user clicks the 'done' button
    public void complete(View v)
    {

        Toast.makeText(this,"Testing!",Toast.LENGTH_SHORT).show();

        List newList = new List(listTitle.getText().toString(),listDescription.getText().toString());

        databaseManager dbm = new databaseManager(this);
        dbm.open();
        dbm.updateList(newList,id);
        dbm.close();

        finish();
    }

    //retrieves data from the db and places into the correct fields so user can edit them
    private void retrieveData(){
        //will hold selected information
        String data="";

        databaseManager dbm = new databaseManager(this);
        dbm.open();

        //retrieve data from row
        Cursor c = dbm.readList(id);

        if (c.moveToFirst()){
            do{
                data = c.getString(c.getColumnIndex("listTitle"));
                listTitle.setText(data);

                data = c.getString(c.getColumnIndex("listDescription"));
                listDescription.setText(data);


            }while(c.moveToNext());
        }
        c.close();

        Toast.makeText(this, "" +id, Toast.LENGTH_SHORT).show();

        dbm.close();
    }

}
