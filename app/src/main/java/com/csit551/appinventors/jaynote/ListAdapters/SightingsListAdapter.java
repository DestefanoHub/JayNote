package com.csit551.appinventors.jaynote.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csit551.appinventors.jaynote.Database.SightingsModel;
import com.csit551.appinventors.jaynote.R;

import java.util.ArrayList;

/**
 * Created by Andrew on 3/22/2015.
 */
public class SightingsListAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<SightingsModel> sightings;
    private TextView name;
    private TextView size;
    private TextView type;
    private TextView color;
    /*private Something date;
    private Something time;*/
    private TextView location;

    public SightingsListAdapter(ArrayList<SightingsModel> link, Context context)
    {
        this.context = context;
        this.sightings = sightings;
    }

    public int getCount()
    {

            return sightings.size();        /*nullpointerexception-cina*/
    }

    public Object getItem(int position)
    {
        return sightings.get(position);
    }

    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.sighting_adapter, null);
        }

        name = (TextView) view.findViewById(R.id.name);
        /*size = (TextView) view.findViewById(R.id.size);
        type = (TextView) view.findViewById(R.id.type);
        color = (TextView) view.findViewById(R.id.color);
        date = (Something) view.findViewById(R.id.date);
        time = (Something) view.findViewById(R.id.stime);
        location = (TextView) view.findViewById(R.id.location);*/
        SightingsModel sighting = sightings.get(position);
        name.setText(sighting.getName());

        return view;
    }
}
