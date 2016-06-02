package com.example.richou.mom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.TaskComment;

/**
 * Created by Robin on 02/06/2016.
 */
public class TaskGlobal_commentAdapter extends BaseAdapter {
    private List<TaskComment> taskComments;
    private LayoutInflater mInflater;

    public TaskGlobal_commentAdapter(Context context, List<TaskComment> taskComments) {
        super();
        this.taskComments = taskComments;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return taskComments.size();
    }

    @Override
    public Object getItem(int position) {
        return taskComments.get(position);
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

        ((TextView)((ViewGroup)view).getChildAt(0)).setText(taskComments.get(position).getContent());
        //((TextView)((ViewGroup)view).getChildAt(1)).setText(events.get(position).getDescription());

        return view;
    }

    public void updateData(List<TaskComment> taskComments) {
        this.taskComments = taskComments;
        this.notifyDataSetChanged();
    }
}
