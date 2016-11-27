package com.example.david.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by david on 27/11/16.
 */

//ref https://stackoverflow.com/questions/13631075/listview-with-cursoradapter
//ref https://developer.android.com/reference/android/widget/CursorAdapter.html
public class customCursorAdapter extends CursorAdapter {

    int customRow;

    //holds strings of the data that I want to get information abount
    String [] from;
    //holds the id of views that i want to display the information to
    int [] to;



    public customCursorAdapter(Context context, Cursor cursor, int customRow, String [] from, int [] to)
    {
        super(context, cursor, 0);

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

        for(int i=0;i<from.length;i++){
            TextView temp = (TextView)view.findViewById(to[i]);
            temp.setText(cursor.getString(cursor.getColumnIndex(from[i])));
        }


    }
}
