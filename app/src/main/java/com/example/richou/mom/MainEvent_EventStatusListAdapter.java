package com.example.richou.mom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import MomApiPackage.Model.EventStatus;

/**
 * Created by Robin on 30/05/2016.
 */
public class MainEvent_EventStatusListAdapter extends BaseAdapter {
    private List<EventStatus> eventStatuses;
    private LayoutInflater mInflater;

    public MainEvent_EventStatusListAdapter(Context context, List<EventStatus> eventStatuses) {
        super();
        this.eventStatuses = eventStatuses;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return eventStatuses.size();
    }

    @Override
    public Object getItem(int position) {
        return eventStatuses.get(position);
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
            view = mInflater.inflate(R.layout.simple_text, null);

        ((TextView)((ViewGroup)view).findViewById(R.id.textView8)).setText(eventStatuses.get(position).getContent());
        //((TextView)((ViewGroup)view).getChildAt(1)).setText(events.get(position).getDescription());

        return view;
    }

    public void updateData(List<EventStatus> eventStatuses) {
        this.eventStatuses = eventStatuses;
        this.notifyDataSetChanged();
    }
}
