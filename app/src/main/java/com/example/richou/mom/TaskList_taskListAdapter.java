package com.example.richou.mom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import MomApiPackage.Model.EventStatus;
import MomApiPackage.Model.Task;

/**
 * Created by Robin on 01/06/2016.
 */
public class TaskList_taskListAdapter extends BaseAdapter {
    private List<Task> tasks;
    private LayoutInflater mInflater;

    public TaskList_taskListAdapter(Context context, List<Task> tasks) {
        super();
        this.tasks = tasks;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
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

        ((TextView)((ViewGroup)view).getChildAt(0)).setText(tasks.get(position).getName());
        //((TextView)((ViewGroup)view).getChildAt(1)).setText(events.get(position).getDescription());

        return view;
    }

    public void updateData(List<Task> tasks) {
        this.tasks = tasks;
        this.notifyDataSetChanged();
    }
}
