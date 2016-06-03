package com.example.richou.mom;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.Task;
import MomApiPackage.Model.User;
import MomApiPackage.MomApi;
import MomApiPackage.MomErrors;
import MomApiPackage.RequestCallback;

public class TaskCreation extends AppCompatActivity implements RequestCallback<Task> {
    private MomApi m;
    private EditText fieldName;
    private EditText fieldDescription;

    private Event event;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creation);
        setSupportActionBar((Toolbar) findViewById(R.id.TaskCreation_toolbar));

        m = new MomApi(this);

        event = (Event)getIntent().getSerializableExtra("event");
        user = (User)getIntent().getSerializableExtra("user");

        getSupportActionBar().setTitle(event.getName());
        getSupportActionBar().setSubtitle(getString(R.string.title_activity_task_creation));

        fieldName = (EditText)findViewById(R.id.editText5);
        fieldDescription = (EditText)findViewById(R.id.TaskCreation_editTextDescription);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_creation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.TaskCreation_submit:
                String name = fieldName.getText().toString();
                String description = fieldName.getText().toString();

                if (name.length()==0) {
                    Toast.makeText(TaskCreation.this, R.string.TaskCreation_empty, Toast.LENGTH_SHORT).show();
                    return true;
                }
                m.createTask(event, name, description, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        Toast.makeText(getBaseContext(), R.string.TaskCreation_error, Toast.LENGTH_SHORT).show();
    }
}
