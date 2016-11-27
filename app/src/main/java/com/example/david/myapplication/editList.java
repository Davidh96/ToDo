package com.example.david.myapplication;


import android.view.View;
import android.widget.Toast;


/**
 * Created by david on 14/11/16.
 */

public class editList extends listEditor {

    //is run when a user clicks the 'done' button
    public void complete(View v)
    {
        List newList = new List(listTitle.getText().toString(),listDescription.getText().toString());

        databaseManager dbm = new databaseManager(this);
        dbm.open();
        dbm.updateList(newList,id);
        dbm.close();

        //notifies user of list changes being saved
        Toast.makeText(this,"List Saved",Toast.LENGTH_SHORT).show();

        finish();
    }



}
