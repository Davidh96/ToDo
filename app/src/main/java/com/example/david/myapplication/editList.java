package com.example.david.myapplication;


import android.view.View;
import android.widget.Toast;


/**
 * Created by david on 14/11/16.
 * contains code for the editing of a list already created
 */

//allows for the saving of edited data
public class editList extends listEditor {

    //is run when a user clicks the 'done' button
    public void complete(View v)
    {
        //if the list title is not empty
        if(!listTitle.getText().toString().equals("")) {
            //create new lst object
            List newList = new List(listTitle.getText().toString(), listDescription.getText().toString());

            //open database
            databaseManager dbm = new databaseManager(this);
            dbm.open();
            //send list off to update the selected list
            dbm.updateList(newList, id);
            dbm.close();

            //notifies user of list changes being saved
            Toast.makeText(this, "List Saved", Toast.LENGTH_SHORT).show();

            finish();
        }
        //if no title was entered
        else{
            noTitleAlert();
        }
    }



}
