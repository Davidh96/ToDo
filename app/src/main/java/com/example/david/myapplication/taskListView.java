package com.example.david.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by david on 14/11/16.
 */

public class taskListView extends ListActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklistview);

    }

    public void viewTask(View v){
        finish();
    }

    public void addList(View v)
    {
        Intent addingTask = new Intent(this,addingList.class);
        startActivity(addingTask);
    }
}
