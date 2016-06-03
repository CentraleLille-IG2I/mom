package com.example.richou.mom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.richou.mom.global.Context;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.Task;
import MomApiPackage.MomApi;
import MomApiPackage.MomErrors;
import MomApiPackage.RequestCallback;

public class TaskItemCreate extends AppCompatActivity implements View.OnClickListener, RequestCallback<Integer> {
    //private MomApi m;
    private EditText fieldName;

    private Event event;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_item_create);

        setSupportActionBar((Toolbar) findViewById(R.id.TaskItemCreate_toolbar));

        //m = new MomApi(this);

        event = (Event)getIntent().getSerializableExtra("event");
        task = (Task)getIntent().getSerializableExtra("task");

        getSupportActionBar().setTitle(event.getName());
        getSupportActionBar().setSubtitle(task.getName()+" - "+getString(R.string.title_activity_task_item_create));

        fieldName = (EditText)findViewById(R.id.TaskItemCreate_editText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_item_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.TaskItemCreate_send:
                String name = fieldName.getText().toString();
                if (name.length()==0) {
                    Toast.makeText(getBaseContext(), R.string.TaskItemCreate_empty, Toast.LENGTH_SHORT).show();
                    return true;
                }
                Context.momApi.addTaskItem(task, name, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onSuccess(Integer arg) {
        finish();
    }

    @Override
    public void onError(MomErrors err) {
        Toast.makeText(getBaseContext(), R.string.TaskItemCreate_error, Toast.LENGTH_SHORT).show();
    }
}
