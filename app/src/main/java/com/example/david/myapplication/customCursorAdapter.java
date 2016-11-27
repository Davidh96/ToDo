package com.example.david.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/**
 * Created by david on 27/11/16.
 * contains code to adapt information sent in, to the custom row
 */

//Reference: In order to create a custom cursor adapter I had to research what was neeeded. This code has been ADAPTED from the following links
// https://stackoverflow.com/questions/13631075/listview-with-cursoradapter
//https://developer.android.com/reference/android/widget/CursorAdapter.html

//controls the retrieval of information and placing it into the custom row
public class customCursorAdapter extends CursorAdapter {

    int customRow;

    //holds strings of the data that I want to get information abount
    String [] from;
    //holds the id of views that i want to display the information to
    int [] to;


    //constructor
    public customCursorAdapter(Context context, Cursor cursor, int customRow, String [] from, int [] to)
    {
        super(context, cursor, 0);

        //place the row id into customRow
        this.customRow=customRow;

        //place from array into this classes from array
        this.from = from;
        //place to array into this classes to array
        this.to = to;


    }

    //inflates a new view
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(customRow, parent, false);
    }

    //binds data to my views
    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView temp=null;

        for(int i=0;i<from.length;i++){

            temp= (TextView)view.findViewById(to[i]);

            //if in task list view
            if(from[i].contains("task")) {
                //find the list id
                long listId =cursor.getLong(cursor.getColumnIndex("listID"));

                //if not part of default list
                if(listId>=0) {

                    databaseManager dbm = new databaseManager(context);
                    dbm.open();

                    //retrieve data from row
                    Cursor c = dbm.readList(listId);

                    if (c.moveToFirst()) {
                        do {
                            //get the colour associated with that list
                            int listColour = c.getInt(c.getColumnIndex("listColour"));
                            //set the text colour to that list colour
                            temp.setTextColor(listColour);
                        } while (c.moveToNext());
                    }
                    c.close();

                    dbm.close();
                }
                //if part of default list
                else{
                    //set default colour
                    temp.setTextColor(ContextCompat.getColor(context, R.color.defualtColour));
                }
            }
            //if in list list view
            else{
                //get the colour associated with the list
                int listColour=cursor.getInt(cursor.getColumnIndex("listColour"));
                //set text colour to list colur
                temp.setTextColor(listColour);

            }

            //set text
            temp.setText(cursor.getString(cursor.getColumnIndex(from[i])));
        }

    }
}

//End Reference
