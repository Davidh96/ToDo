package com.example.david.myapplication;


import android.view.View;
import android.widget.Toast;

/**
 * Created by david on 13/11/16.
 * contains code for adding list details into a new list, and then sending it off to be inserted into the database
 */

//will be used to create new list
public class addingList extends listEditor {

    //is called when user clicks 'done' button
    public void complete(View V)
    {
        //if the list title is not empty
        if(!listTitle.getText().toString().equals("")) {

            //create new list object
            List newList = new List(listTitle.getText().toString(), listDescription.getText().toString());

            //open up database
            databaseManager dbm = new databaseManager(this);
            dbm.open();
            //send list off to be added into the database
            dbm.addList(newList);
            dbm.close();

            //notifies user of task changes being saved
            Toast.makeText(this,"List Saved",Toast.LENGTH_SHORT).show();

            finish();
        }
        //if no title was entered
        else{
            //give an alert
            noTitleAlert();
        }
    }


}
