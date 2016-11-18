package com.example.david.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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

        finish();
    }



}
