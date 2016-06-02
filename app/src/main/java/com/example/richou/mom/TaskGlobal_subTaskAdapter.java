package com.example.richou.mom;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.TaskItem;
import MomApiPackage.MomApi;
import MomApiPackage.MomErrors;
import MomApiPackage.RequestCallback;

/**
 * Created by Robin on 02/06/2016.
 */
public class TaskGlobal_subTaskAdapter extends BaseAdapter {
    private List<TaskItem> taskItems;
    private LayoutInflater mInflater;
    private Context context;

    public TaskGlobal_subTaskAdapter(Context context, List<TaskItem> taskItems) {
        super();
        this.taskItems = taskItems;
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return taskItems.size();
    }

    @Override
    public Object getItem(int position) {
        return taskItems.get(position);
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
            view = mInflater.inflate(R.layout.simple_checkline, null);

        final TaskItem taskItem = taskItems.get(position);

        ((TextView)((ViewGroup)view).getChildAt(0)).setText(taskItem.getName());
        ((CheckBox)((ViewGroup)view).getChildAt(1)).setChecked(taskItem.isCompleted());
        ((CheckBox)((ViewGroup)view).getChildAt(1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskItem.setCompleted(((CheckBox) v).isChecked());
                MomApi m  = new MomApi(context);
                m.editTaskItem(taskItem, new RequestCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer arg) {
                        Log.d("@", "taskItem adapter is fine");
                    }

                    @Override
                    public void onError(MomErrors err) {
                        Log.d("@", "taskItem adapter get error --> need popup");
                    }
                });
            }
        });

        return view;
    }

    public void updateData(List<TaskItem> taskItems) {
        this.taskItems = taskItems;
        this.notifyDataSetChanged();
    }
}
