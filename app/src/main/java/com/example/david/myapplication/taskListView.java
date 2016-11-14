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

    databaseManager dbm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklistview);
        //information I want to retriev from the db
        String[] columns = new String[]{"listTitle"};
        //where i want to dsiplay the informtion
        int[] to =new int[] {R.id.listTitle1};

        dbm = new databaseManager(this);
        dbm.open();
        //retrieve cursor of all rows from db
        c = dbm.readLists();
        //create adapter which displays task title
        adapt = new SimpleCursorAdapter(this, R.layout.list_row, c, columns, to,0);
        //set adapter
        setListAdapter(adapt);
        dbm.close();

        ListView lv = (ListView)findViewById(android.R.id.list);

        //open up screen to allow user to edit task
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Intent editList = new Intent(taskListView.this,editList.class);
                //editTask.putExtra("id",id);
                Toast.makeText(taskListView.this, "" + id, Toast.LENGTH_SHORT).show();
                editList.putExtra("id",id);
                startActivity(editList);
            }
        });

        //long press to delete list
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                dbm.open();
                boolean deleted = dbm.deleteList((id));
                if(deleted==true) {
                    Toast.makeText(taskListView.this, "Deleted List", Toast.LENGTH_SHORT).show();
                }
                dbm.close();

                return true;
            }
        });
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
