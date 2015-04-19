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
    private String dateTime;
    private String audio;
    private String image;
    private String location;
    private String misc;

    public SightingsModel(int id, String name, String size, String type, String color, String dateTime, String audio, String image, String location, String misc)
    {
        this.id = id;
        this.name = name;
        this.size = size;
        this.type = type;
        this.color = color;
        this.dateTime = dateTime;
        this.audio = audio;
        this.image = image;
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

    public String getDateTime()
    {
        return this.dateTime;
    }

    public void setDateTime(String dateTime)
    {
        this.dateTime = dateTime;
    }

    public String getAudio()
    {
        return this.audio;
    }

    public void setAudio(String audio)
    {
        this.audio = audio;
    }

    public String getImage()
    {
        return this.image;
    }

    public void setImage(String image)
    {
        this.image = image;
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
