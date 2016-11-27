package com.example.david.myapplication;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by david on 18/11/16.
 * contains code that its subclasses use to perform adding/editing list information
 */

//super class that contains functionality used by the edit/adding list classes
public class listEditor extends ListActivity {

    EditText listTitle;
    EditText listDescription;

    long id;

    //called when the list editor is created
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinglist);

        //get views
        listTitle=(EditText)findViewById(R.id.listTitleEdit);
        listDescription=(EditText)findViewById(R.id.listDescriptionEdit);

        //get the id for the list that is being displayed
        Intent i = getIntent();
        id= i.getLongExtra("id",-1);

        retrieveData();

        //information I want to retriev from the db
        String[] columns = new String[]{"taskTitle"};
        //where i want to dsiplay the informtion
        int[] to =new int[] {R.id.taskTitle};

        //open database
        databaseManager dbm = new databaseManager(this);
        dbm.open();
        //retrieve cursor of all rows from db
        Cursor c = dbm.readListTasks(id);
        //create adapter which displays task title
        customCursorAdapter adapt = new customCursorAdapter(this,c, R.layout.task_row,columns,to);
        //set adapter
        setListAdapter(adapt);
        dbm.close();

        ListView lv = (ListView)findViewById(android.R.id.list);

        //open up screen to allow user to edit task
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Intent editTask = new Intent(listEditor.this,editTask.class);
                editTask.putExtra("id",id);
                startActivity(editTask);
            }
        });

    }

    //gives alert if user tries to create task without name
    public void noTitleAlert() {
        //create alert box
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //alert title
        alert.setTitle("Eh-hem, forget something?")
                //alert message
                .setMessage("You MUST enter a List Name")
                //if user clicks yes
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }

                })
                .show();
    }


    //retrieves data from the db and places into the correct fields so user can edit them
    private void retrieveData(){
        //will hold selected information
        String data="";

        databaseManager dbm = new databaseManager(this);
        dbm.open();

        //retrieve data from row
        Cursor c = dbm.readList(id);

        //move through the cursor
        if (c.moveToFirst()){
            do{
                data = c.getString(c.getColumnIndex("listTitle"));
                listTitle.setText(data);

                data = c.getString(c.getColumnIndex("listDescription"));
                listDescription.setText(data);


            }while(c.moveToNext());//move to next item in cursor
        }
        c.close();

        dbm.close();
    }
}
