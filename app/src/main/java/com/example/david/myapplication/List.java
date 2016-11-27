package com.example.david.myapplication;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by david on 13/11/16.
 */

public class List {

    private String listTitle;
    private String listDescription;

    private int colour;

    public List(String listTitle,String listDescription)
    {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        this.listTitle=listTitle;
        this.listDescription=listDescription;
        this.colour=color;

    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }
}
