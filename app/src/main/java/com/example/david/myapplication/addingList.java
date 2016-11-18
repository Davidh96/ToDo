package com.example.david.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

            finish();
        }
        else{

        }
    }


}
