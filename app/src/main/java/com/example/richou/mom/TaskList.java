package com.example.richou.mom;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.Task;
import MomApiPackage.Model.User;
import MomApiPackage.MomApi;
import MomApiPackage.MomErrors;
import MomApiPackage.RequestCallback;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TaskList extends AppCompatActivity implements RequestCallback<List<Task>>, AdapterView.OnItemClickListener, View.OnClickListener {
    private MomApi m;
    private ListView lv;
    private Button newTask;

    private Event event;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        m = new MomApi(this);

        event = (Event)getIntent().getSerializableExtra("event");
        user = (User)getIntent().getSerializableExtra("user");
        ((TextView)findViewById(R.id.textView3)).setText(event.getName());

        newTask = (Button)findViewById(R.id.TaskList_buttonCreateTask);
        newTask.setOnClickListener(this);

        lv = (ListView)findViewById(R.id.TaskList_listView);
        lv.setOnItemClickListener(this);
        lv.setAdapter(new TaskList_taskListAdapter(this, new ArrayList<Task>()));

        refresh();
    }

    private void refresh() {
        m.getEventTask(event, this);
    }

    @Override
    public void onSuccess(List<Task> arg) {
        ((TaskList_taskListAdapter)lv.getAdapter()).updateData(arg);
    }

    @Override
    public void onError(MomErrors err) {
        Log.d("@", "Task list got an error, need popup");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("@", "clicked on : "+((Task)parent.getItemAtPosition(position)).getName());
        startTaskGlobalActivity((Task)parent.getItemAtPosition(position));
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, TaskCreation.class);
        i.putExtra("event", event);
        i.putExtra("user", user);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1) {
            if (resultCode==Activity.RESULT_OK) {
                Task t = (Task)data.getSerializableExtra("task");
                startTaskGlobalActivity(t);
            }
            else
                refresh();
        }
        else
            refresh();
    }

    private void startTaskGlobalActivity (Task task) {
        Intent i = new Intent(this, TaskGlobal.class);
        i.putExtra("event", event);
        i.putExtra("user", user);
        i.putExtra("task", task);
        startActivityForResult(i, 2);
    }
}
