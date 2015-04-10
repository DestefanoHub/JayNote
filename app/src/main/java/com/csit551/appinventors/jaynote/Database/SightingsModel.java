package com.csit551.appinventors.jaynote.Database;

/**
 * Created by Andrew on 3/22/2015.
 */
public class SightingsModel
{
    private int id;
    private String name;
    private String size;
    private String type;
    private String color;
    private String date;
    private String time;
    private String location;
    private String misc;

    public SightingsModel(int id, String name, String size, String type, String color, String date, String time, String location, String misc)
    {
        this.id = id;
        this.name = name;
        this.size = size;
        this.type = type;
        this.color = color;
        this.date = date;
        this.time = time;
        this.location = location;
        this.misc = misc;
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

    public String getSize()
    {
        return this.size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public String getType()
    {
        return this.type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getColor()
    {
        return this.color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getDate()
    {
        return this.date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return this.time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getLocation()
    {
        return this.location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getMisc()
    {
        return this.misc;
    }

    public void setMisc(String misc)
    {
        this.misc = misc;
    }
}
