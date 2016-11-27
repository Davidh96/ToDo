package com.example.david.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by david on 18/11/16.
 */

public class listEditor extends ListActivity {

    EditText listTitle;
    EditText listDescription;

    long id;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinglist);

        listTitle=(EditText)findViewById(R.id.listTitleEdit);
        listDescription=(EditText)findViewById(R.id.listDescriptionEdit);

        Intent i = getIntent();

        id= i.getLongExtra("id",-1);

        retrieveData();

        //information I want to retriev from the db
        String[] columns = new String[]{"taskTitle"};
        //where i want to dsiplay the informtion
        int[] to =new int[] {R.id.taskTitle};

        databaseManager dbm = new databaseManager(this);
        dbm.open();
        //retrieve cursor of all rows from db
        Cursor c = dbm.readListTasks(id);
        //create adapter which displays task title
        customCursorAdapter adapt = new customCursorAdapter(this,c, R.layout.task_row,columns,to);
        //set adapter
        setListAdapter(adapt);
        dbm.close();

        //populateList();

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

    //retrieves data from the db and places into the correct fields so user can edit them
    private void retrieveData(){
        //will hold selected information
        String data="";

        databaseManager dbm = new databaseManager(this);
        dbm.open();

        //retrieve data from row
        Cursor c = dbm.readList(id);

        if (c.moveToFirst()){
            do{
                data = c.getString(c.getColumnIndex("listTitle"));
                listTitle.setText(data);

                data = c.getString(c.getColumnIndex("listDescription"));
                listDescription.setText(data);


            }while(c.moveToNext());
        }
        c.close();

        dbm.close();
    }
}
