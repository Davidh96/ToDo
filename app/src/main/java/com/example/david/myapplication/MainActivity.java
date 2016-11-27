package com.example.david.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
/*
contains code for the first screen seen by the user, a listview of all tasks
 */

//displays the list of tasks created by the user
public class MainActivity extends ListActivity {

    public static customCursorAdapter listAdapter;
    public static ArrayAdapter<String> menuAdapter;

    static databaseManager dbm;
    static Cursor c;


    ImageButton addTaskBtn;


    public static Spinner menuTaskPage;

    String[] listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the menu items from resources
        Resources res = getResources();
        listItems = res.getStringArray(R.array.menuItems);

        //get views
        menuTaskPage=(Spinner)findViewById(R.id.menuTaskPage);
        addTaskBtn=(ImageButton)findViewById(R.id.addNew);
        ListView lv = (ListView)findViewById(android.R.id.list);


        //information I want to retrieve from the db
        String[] from = new String[]{"taskTitle","taskDescription"};
        //where i want to display the information
        int[] to =new int[] {R.id.taskTitle,R.id.taskDescriptionView};

        //open database
        dbm = new databaseManager(this);
        dbm.open();
        //retrieve cursor of all rows from db
        c = dbm.readTasks();
        //c.close();

        listAdapter=new customCursorAdapter(this,c, R.layout.task_row,from,to);
        //set adapter
        setListAdapter(listAdapter);

        dbm.close();

        //open up screen to allow user to edit task
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Intent editTask = new Intent(MainActivity.this,editTask.class);
                editTask.putExtra("id",id);
                startActivity(editTask);
            }
        });


        //long press to delete task
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                // delete task
                dbm.open();
                boolean deleted = dbm.deleteTask((id));
                if(deleted==true) {
                    Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                }
                dbm.close();


                return true;
            }
        });

        //Reference: The following code was ADAPTED from the follwoing link
        // https://stackoverflow.com/questions/1337424/android-spinner-get-the-selected-item-change-event#1714426

        //when the user has chosen an item from the drop down menu
        menuTaskPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(listItems[position].equals("Lists")) {
                    // switch to list view
                    Intent viewsLists = new Intent(MainActivity.this, taskListView.class);
                    startActivity(viewsLists);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //do nothing
            }

        });
        //End Reference


        populateListChoice();

    }


    //populates menu with menu choices
    public void populateListChoice()
    {
        menuAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item,listItems);
        menuAdapter.setDropDownViewResource(R.layout.custom_spinner_drop_item);
        menuTaskPage.setAdapter(menuAdapter);
    }

    //open up screen to rate a new task
    public void addTask(View v)
    {
        Intent addingTask = new Intent(MainActivity.this,addingTask.class);
        startActivity(addingTask);
    }


}
