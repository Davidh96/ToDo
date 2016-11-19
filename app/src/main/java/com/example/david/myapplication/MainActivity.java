package com.example.david.myapplication;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.david.myapplication.R.styleable.Spinner;

public class MainActivity extends ListActivity {

    public static SimpleCursorAdapter adapt;
    public static ArrayAdapter<String> adapter;

    static databaseManager dbm;
    static Cursor c;


    Button addTask;

    public static Spinner menuTaskPage;

    String[] listItems;


    //String[] listItems = new String[]{"Tasks","Lists"};

    //ref https://developer.android.com/guide/topics/ui/notifiers/notifications.html
    //
    NotificationCompat.Builder notif;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        listItems = res.getStringArray(R.array.menuItems);

        menuTaskPage=(Spinner)findViewById(R.id.menuTaskPage);

        //
        notif = new NotificationCompat.Builder(this);
        //close notification when in app
        notif.setAutoCancel(true);




        //information I want to retriev from the db
        String[] columns = new String[]{"taskTitle"};
        //where i want to dsiplay the informtion
        int[] to =new int[] {R.id.taskTitle};


        dbm = new databaseManager(this);
        dbm.open();
        //retrieve cursor of all rows from db
        c = dbm.readTasks();
        //c.close();
        //create adapter which displays task title
        adapt = new SimpleCursorAdapter(this, R.layout.task_row, c, columns, to,0);
        //set adapter
        setListAdapter(adapt);
        dbm.close();

        addTask=(Button)findViewById(R.id.addTask);

        //display screen to create a new task
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addingTask = new Intent(MainActivity.this,addingTask.class);
                startActivity(addingTask);

            }
        });

        ListView lv = (ListView)findViewById(android.R.id.list);

        //open up screen to allow user to edit task
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Intent editTask = new Intent(MainActivity.this,editTask.class);
                //editTask.putExtra("id",id);
                Toast.makeText(MainActivity.this, "" + id, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, "Deleted Task", Toast.LENGTH_SHORT).show();
                }
                dbm.close();


                return true;
            }
        });

        //ref https://stackoverflow.com/questions/1337424/android-spinner-get-the-selected-item-change-event#1714426

        menuTaskPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(listItems[position].equals("Lists")) {
                    // your code here
                    Intent viewsLists = new Intent(MainActivity.this, taskListView.class);
                    startActivity(viewsLists);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        populateListChoice();

    }

    //called when add list button is clicked
    public void viewList(View v)
    {
        //create intent to switch to create list screen
        //Intent addingList = new Intent(this,addingList.class);
        Intent viewsLists = new Intent(this,taskListView.class);
        startActivity(viewsLists);
    }

    //called when notification is to be sent to user
    public void notify(View v)
    {
        //sets notification details
        notif.setSmallIcon(R.drawable.ic_launcher);
        notif.setTicker("Yo");
        notif.setWhen(System.currentTimeMillis());
        notif.setContentTitle("Title");
        notif.setContentText("This is the text");

        //create intent to bring user to the app
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        notif.setContentIntent(pIntent);

        //get notification manager
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1222222,notif.build());
    }

    //ref https://developer.android.com/guide/topics/ui/controls/spinner.html
    public void populateListChoice()
    {
        adapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item, listItems);
        menuTaskPage.setAdapter(adapter);
    }


}
