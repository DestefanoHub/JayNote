package com.csit551.appinventors.jaynote.Database;

/**
 * Created by Andrew on 3/22/2015.
 */
public class NotesModel
{
    private int id;
    private String text;

    public NotesModel(int id, String text)
    {
        this.id = id;
        this.text = text;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getText()
    {
        return this.text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
