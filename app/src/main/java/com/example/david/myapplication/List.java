package com.example.david.myapplication;

/**
 * Created by david on 13/11/16.
 */

public class List {

    private String listTitle;
    private String listDescription;

    public List(String listTitle,String listDescription)
    {
        this.listTitle=listTitle;
        this.listDescription=listDescription;

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
}
