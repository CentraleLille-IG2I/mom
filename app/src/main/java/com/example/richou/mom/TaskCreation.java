package com.example.richou.mom;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import org.w3c.dom.Text;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.Task;
import MomApiPackage.Model.User;
import MomApiPackage.MomApi;
import MomApiPackage.MomErrors;
import MomApiPackage.RequestCallback;

public class TaskCreation extends AppCompatActivity implements View.OnClickListener, RequestCallback<Task> {
    private MomApi m;
    private Button create;
    private EditText fieldName;
    private EditText fieldDescription;

    private Event event;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);

        m = new MomApi(this);

        event = (Event)getIntent().getSerializableExtra("event");
        user = (User)getIntent().getSerializableExtra("user");

        fieldName = (EditText)findViewById(R.id.editText5);
        fieldDescription = (EditText)findViewById(R.id.TaskCreation_editTextDescription);


        create = (Button)findViewById(R.id.button7);
        create.setOnClickListener(this);

        ((TextView)findViewById(R.id.textView3)).setText(event.getName());

    }

    @Override
    public void onClick(View v) {
        String name = fieldName.getText().toString();
        String description = fieldName.getText().toString();

        if (name.length()==0 || description.length()==0) {
            Log.d("@", "event creation need 'empty field' popup");
            return;
        }

        m.createTask(event, name, description, this);
    }

    @Override
    public void onSuccess(Task arg) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("task", arg);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onError(MomErrors err) {
        Log.d("@", "taskCreation get error --> need some popup");
    }
}
