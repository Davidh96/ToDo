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

public class editList extends ListActivity {


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
        //c.close();
        //create adapter which displays task title
        SimpleCursorAdapter adapt = new SimpleCursorAdapter(this, R.layout.task_row, c, columns, to,0);
        //set adapter
        setListAdapter(adapt);
        dbm.close();

        //populateList();

        ListView lv = (ListView)findViewById(android.R.id.list);

        //open up screen to allow user to edit task
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Intent editTask = new Intent(editList.this,editTask.class);
                //editTask.putExtra("id",id);
                Toast.makeText(editList.this, "" + id, Toast.LENGTH_SHORT).show();
                editTask.putExtra("id",id);
                startActivity(editTask);
            }
        });

    }



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

        Toast.makeText(this, "" +id, Toast.LENGTH_SHORT).show();

        dbm.close();
    }

}
