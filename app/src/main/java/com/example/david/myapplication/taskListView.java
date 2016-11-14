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

    public static Cursor c;
    public static SimpleCursorAdapter adapt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklistview);
        //information I want to retriev from the db
        String[] columns = new String[]{"listTitle"};
        //where i want to dsiplay the informtion
        int[] to =new int[] {R.id.listTitle1};

        databaseManager dbm = new databaseManager(this);
        dbm.open();
        //retrieve cursor of all rows from db
        c = dbm.readLists();
        //create adapter which displays task title
        adapt = new SimpleCursorAdapter(this, R.layout.list_row, c, columns, to,0);
        //set adapter
        setListAdapter(adapt);
        dbm.close();
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
