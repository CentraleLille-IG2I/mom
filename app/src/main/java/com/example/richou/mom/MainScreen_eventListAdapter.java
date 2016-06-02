package com.example.richou.mom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.EventStatus;

/**
 * Created by Robin on 29/05/2016.
 */
public class MainScreen_eventListAdapter extends BaseAdapter {
    private List<Event> events;
    private LayoutInflater mInflater;

    public MainScreen_eventListAdapter(Context context, List<Event> events) {
        super();
        this.events = events;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(convertView != null)
            view  = convertView;
        else
            view = mInflater.inflate(R.layout.simple_line, null);

        ((TextView)((ViewGroup)view).getChildAt(0)).setText(events.get(position).getName());
        ((TextView)((ViewGroup)view).getChildAt(1)).setText(events.get(position).getDescription());

        return view;
    }

    public void updateData(List<Event> events) {
        this.events = events;
        this.notifyDataSetChanged();
    }
}
