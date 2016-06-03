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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.Task;
import MomApiPackage.Model.TaskComment;
import MomApiPackage.Model.TaskItem;
import MomApiPackage.Model.User;
import MomApiPackage.MomApi;
import MomApiPackage.MomErrors;
import MomApiPackage.RequestCallback;


public class TaskGlobal extends AppCompatActivity implements RequestCallback, View.OnClickListener {
    private MomApi m;
    private ListView lvSubTask;
    private ListView lvComment;
    private Button bListPeople;
    private Button bJoinQuit;

    private Event event;
    private User user;
    private Task task;

    private int userInTask=-1; //1: in task, 0: not in task, -1: unknown (data have not been retrieved yet)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_global);
        setSupportActionBar((Toolbar) findViewById(R.id.TaskGlobal_toolbar));

        m = new MomApi(this);

        event = (Event)getIntent().getSerializableExtra("event");
        user = (User)getIntent().getSerializableExtra("user");
        task = (Task)getIntent().getSerializableExtra("task");

        getSupportActionBar().setTitle(event.getName());
        getSupportActionBar().setSubtitle(task.getName());

        lvSubTask = (ListView)findViewById(R.id.TaskGlobal_listViewSubTask);
        lvComment = (ListView)findViewById(R.id.TaskGLobal_listViewComment);
        bListPeople = (Button)findViewById(R.id.button2);
        bJoinQuit = (Button)findViewById(R.id.button4);

        lvSubTask.setAdapter(new TaskGlobal_subTaskAdapter(this, new ArrayList<TaskItem>()));
        lvComment.setAdapter(new TaskGlobal_commentAdapter(this, new ArrayList<TaskComment>()));

        bListPeople.setOnClickListener(this);
        bJoinQuit.setOnClickListener(this);

        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.TaskGlobal_addTaskItem: {
                Intent i = new Intent(this, TaskItemCreate.class);
                i.putExtra("event", event);
                i.putExtra("task", task);
                startActivityForResult(i, 2);
            }
                return true;
            case R.id.TaskGlobal_addComment: {
                Intent i = new Intent(this, WriteMessage.class);
                i.putExtra("event", event);
                i.putExtra("subTitle", getString(R.string.WriteMessage_taskCommentSubTitle));
                startActivityForResult(i, 1);
            }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void userInTask(Task t) {
        m.getTaskUsers(task, this);
    }

    private void refresh() {
        m.getTaskItems(task, this);
        m.getTaskComments(task, this);
        m.getTaskUsers(task, this);
    }

    @Override
    public void onSuccess(Object arg) {
        if (arg instanceof TaskItem) {
            Log.d("@", "taskItem");
        }
        else if (arg instanceof TaskComment) {

        }
        else if (arg instanceof List) {
            //Warning uglyest part of the code !!!!!
            Log.d("@", "list");
            for (Object o : ((List)arg)) {
                if (o instanceof TaskItem)
                    ((TaskGlobal_subTaskAdapter)lvSubTask.getAdapter()).updateData((List<TaskItem>)arg);

                else if (o instanceof TaskComment)
                    ((TaskGlobal_commentAdapter)lvComment.getAdapter()).updateData((List<TaskComment>)arg);

                else if (o instanceof User) {
                    if (isUserContained((List<User>) arg)) {
                        bJoinQuit.setText(getString(R.string.TaskGlobal_quit));
                        userInTask = 1;
                    } else {
                        bJoinQuit.setText(getString(R.string.TaskGlobal_join));
                        userInTask = 0;
                    }
                }
                break;
            }

        }
        else if (arg instanceof Integer) {
            Log.d("@", "OKOK");
            refresh();
        }
    }

    private boolean isUserContained(List<User> users) {
        for (User u : users) {
            if (u.getId() == user.getId())
                return true;
        }
        return false;
    }

    @Override
    public void onError(MomErrors err) {
        Toast.makeText(getBaseContext(), R.string.TaskGlobal_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button4:
            if (userInTask==1) {
                Toast.makeText(getBaseContext(), R.string.global_NIY, Toast.LENGTH_SHORT).show();

            }
            else if (userInTask==0) {
                m.addUserInTask(user, task, this);
            }
            break;
            case R.id.button2:
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                m.addTaskComment(task, data.getExtras().getString("message"), this);
            }
        }
        refresh();
    }
}
