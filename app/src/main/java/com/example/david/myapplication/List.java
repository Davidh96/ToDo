package com.example.david.myapplication;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by david on 13/11/16.
 * contains code for making a list object
 */

//is used to store list information
public class List {

    private String listTitle;
    private String listDescription;

    private int colour;

    //constructor, places information passed into the correct variables
    public List(String listTitle,String listDescription)
    {
        //get a random colour
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        this.listTitle=listTitle;
        this.listDescription=listDescription;
        this.colour=color;

    }

    //returns list title
    public String getListTitle() {
        return listTitle;
    }

    //sets list title
    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }
    //returns list description
    public String getListDescription() {
        return listDescription;
    }

    //sets list description
    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    //returns list colour
    public int getColour() {
        return colour;
    }

    //sets list colour
    public void setColour(int colour) {
        this.colour = colour;
    }
}
