package com.example.david.myapplication;


import android.view.View;
import android.widget.Toast;


/**
 * Created by david on 13/11/16.
 */

//will be used to create new list
public class addingList extends listEditor {

    //is called when user clicks 'done' button
    public void complete(View V)
    {
        if(!listTitle.getText().toString().equals("")) {
            List newList = new List(listTitle.getText().toString(), listDescription.getText().toString());

            databaseManager dbm = new databaseManager(this);
            dbm.open();
            dbm.addList(newList);
            dbm.close();

            //notifies user of task changes being saved
            Toast.makeText(this,"List Saved",Toast.LENGTH_SHORT).show();

            finish();
        }
        else{

        }
    }


}
