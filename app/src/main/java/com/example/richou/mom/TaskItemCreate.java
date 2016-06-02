package com.example.richou.mom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.Task;
import MomApiPackage.Model.User;
import MomApiPackage.MomApi;
import MomApiPackage.MomErrors;
import MomApiPackage.RequestCallback;

public class TaskItemCreate extends AppCompatActivity implements View.OnClickListener, RequestCallback<Integer> {
    private MomApi m;
    private Button bcreate;
    private EditText fieldName;

    private Event event;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_item_create);

        m = new MomApi(this);

        event = (Event)getIntent().getSerializableExtra("event");
        task = (Task)getIntent().getSerializableExtra("task");

        ((TextView)findViewById(R.id.textView3)).setText(event.getName());

        fieldName = (EditText)findViewById(R.id.TaskItemCreate_Name);
        bcreate = (Button)findViewById(R.id.TaskItemCreate_buttonCreate);

        bcreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = fieldName.getText().toString();
        if (name.length()==0) {
            Log.d("@", "need empty field popup");
            return;
        }
        m.addTaskItem(task, name, this);
    }

    @Override
    public void onSuccess(Integer arg) {
        finish();
    }

    @Override
    public void onError(MomErrors err) {
        Log.d("@", "taskItemCreate get errors");
    }
}
