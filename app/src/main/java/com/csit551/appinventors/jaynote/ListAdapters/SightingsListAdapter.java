package com.csit551.appinventors.jaynote.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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

    public SightingsListAdapter(ArrayList<SightingsModel> link, Context context)
    {
        this.context = context;
        this.sightings = sightings;
    }

    public int getCount()
    {
        return sightings.size();
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
            view = inflater.inflate(R.layout.activity_link_list, null);
        }

        return view;
    }
}
