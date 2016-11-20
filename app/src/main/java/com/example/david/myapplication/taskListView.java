package com.example.david.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by david on 14/11/16.
 */

public class taskListView extends ListActivity {

    public static Cursor c;
    public static SimpleCursorAdapter adapt;
    Spinner menuListPage;

    databaseManager dbm;

    String[] listItems;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklistview);

        Resources res = getResources();
        listItems = res.getStringArray(R.array.menuItems);

        menuListPage=(Spinner)findViewById(R.id.menuListPage);


        //information I want to retriev from the db
        String[] columns = new String[]{"listTitle"};
        //where i want to dsiplay the informtion
        int[] to =new int[] {R.id.listTitle};

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

        //ref https://stackoverflow.com/questions/1337424/android-spinner-get-the-selected-item-change-event#1714426

        menuListPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(listItems[position].equals("Tasks")) {
                    // your code here
                    MainActivity.menuTaskPage.setSelection(0);
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        populateListChoice();
    }

//    public void viewTask(View v){
//        finish();
//    }

    public void addList(View v)
    {
        Intent addingTask = new Intent(this,addingList.class);
        startActivity(addingTask);
    }

    //ref https://developer.android.com/guide/topics/ui/controls/spinner.html
    public void populateListChoice()
    {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listItems);
        adapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item,listItems);
        adapter.setDropDownViewResource(R.layout.custom_spinner_drop_item);
        menuListPage.setAdapter(adapter);
        menuListPage.setSelection(1);
    }
}
