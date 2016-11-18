package com.example.david.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
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

        //ref https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
        //ref https://developer.android.com/reference/android/app/AlertDialog.html
        //ref https://developer.android.com/guide/topics/ui/dialogs.html
        //long press to delete list
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long _id) {
                // TODO Auto-generated method stub
                final long id=_id;

                //create alert box
                AlertDialog.Builder alert = new AlertDialog.Builder(taskListView.this);
                        //alert title
                        alert.setTitle("Are you sure?")
                        .setMessage("By deleting this list you will lose all of the tasks contained in it")
                                //if user clicks yes
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Delete list
                                dbm.open();
                                boolean deleted = dbm.deleteList((id));
                                if(deleted==true) {
                                    Toast.makeText(taskListView.this, "Deleted List", Toast.LENGTH_SHORT).show();
                                }
                                dbm.close();
                            }
                        })
                                //if user clicks cancel
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Do not delete list
                                Toast.makeText(taskListView.this, "Deletion Canceled", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();

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
