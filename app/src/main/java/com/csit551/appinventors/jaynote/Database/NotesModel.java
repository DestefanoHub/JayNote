package com.csit551.appinventors.jaynote.Database;

/**
 * Created by Andrew on 3/22/2015.
 */
public class NotesModel
{
    private int id;
    private String name;
    private String body;

    public NotesModel(int id, String name, String body)
    {
        this.id = id;
        this.name = name;
        this.body = body;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getBody()
    {
        return this.body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }
}
