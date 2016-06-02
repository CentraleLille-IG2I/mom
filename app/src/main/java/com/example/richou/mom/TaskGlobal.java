package com.example.richou.mom;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.ListView;

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
    private Button bAddSubTask;
    private Button bComment;

    private Event event;
    private User user;
    private Task task;

    private int userInTask=-1; //1: in task, 0: not in task, -1: unknown (data have not been retrieved yet)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_global);

        m = new MomApi(this);

        event = (Event)getIntent().getSerializableExtra("event");
        user = (User)getIntent().getSerializableExtra("user");
        task = (Task)getIntent().getSerializableExtra("task");

        ((TextView)findViewById(R.id.textView3)).setText(event.getName());

        lvSubTask = (ListView)findViewById(R.id.TaskGlobal_listViewSubTask);
        lvComment = (ListView)findViewById(R.id.TaskGLobal_listViewComment);
        bListPeople = (Button)findViewById(R.id.button2);
        bJoinQuit = (Button)findViewById(R.id.button4);
        bAddSubTask = (Button)findViewById(R.id.TaskGlobal_buttonCreateSubTask);
        bComment = (Button)findViewById(R.id.TaskGlobal_buttonComment);

        lvSubTask.setAdapter(new TaskGlobal_subTaskAdapter(this, new ArrayList<TaskItem>()));
        lvComment.setAdapter(new TaskGlobal_commentAdapter(this, new ArrayList<TaskComment>()));



        bListPeople.setOnClickListener(this);
        bJoinQuit.setOnClickListener(this);
        bAddSubTask.setOnClickListener(this);
        bComment.setOnClickListener(this);

        refresh();

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
        Log.d("@", "TaskGlobal get some errors, need popup");
    }

    @Override
    public void onClick(View v) {
        if (v==bAddSubTask) {
            Intent i = new Intent(this, TaskItemCreate.class);
            i.putExtra("event", event);
            i.putExtra("task", task);
            startActivityForResult(i, 2);
        }
        else if (v == bComment) {
            Intent i = new Intent(this, WriteMessage.class);
            i.putExtra("event", event);
            i.putExtra("subTitle", getString(R.string.WriteMessage_taskCommentSubTitle));
            startActivityForResult(i, 1);

        }
        else if (v == bJoinQuit) {
            if (userInTask==1) {
                Log.d("@", "quit functionnality unimplemented yet --> need warning popup");
            }
            else if (userInTask==0) {
                m.addUserInTask(user, task, this);
            }

        }
        else if (v == bListPeople) {

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
