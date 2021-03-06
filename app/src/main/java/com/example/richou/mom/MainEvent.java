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

import com.example.richou.mom.global.Context;

import java.util.ArrayList;
import java.util.List;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.EventStatus;
import MomApiPackage.Model.User;
import MomApiPackage.MomApi;
import MomApiPackage.RequestCallback;
import MomApiPackage.MomErrors;

public class MainEvent extends AppCompatActivity implements RequestCallback, View.OnClickListener {
    private Event event;
    //private User user;
    //private MomApi m;

    private android.widget.ListView lv;
    private Button invitationsButton;
    private Button task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        //Context.momApi = new MomApi(this);

        event = (Event)getIntent().getSerializableExtra("event");
        Log.d("@", "Event in MainEvent: "+event.getId());
        //user = (User)getIntent().getSerializableExtra("user");

        task = ((Button) findViewById(R.id.button6));

        setSupportActionBar((Toolbar) findViewById(R.id.MainEvent_toolbar));
        getSupportActionBar().setTitle(event.getName());
        getSupportActionBar().setSubtitle(event.getDate() + " - " + event.getPlace());

        if (!event.canOrganise(Context.loggedUser)) {
            ((MenuItem)findViewById(R.id.MainEvent_writeStatus)).setVisible(false);
            task.setVisibility(View.GONE);
        }
        else {
            task.setOnClickListener(this);
        }


        lv = (ListView)findViewById(R.id.listView2);
        lv.setAdapter(new MainEvent_EventStatusListAdapter(this, new ArrayList<EventStatus>()));

        refresh();
        
        Context.momApi.getEventStatuses(event.getId(), this);

        invitationsButton = (Button) findViewById(R.id.MainEvent_invitations);
        invitationsButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.MainEvent_writeStatus:
                Intent i = new Intent(this, WriteMessage.class);
                i.putExtra("event", event);
                i.putExtra("subTitle", getString(R.string.WriteMessage_eventStatusCreationSubTitle));
                startActivityForResult(i, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refresh() {
        Context.momApi.getEventStatuses(event.getId(), this);
    }

    @Override
    public void onSuccess(Object obj) {
        if (obj instanceof List) {
            Log.d("@", "get success");
            ((MainEvent_EventStatusListAdapter) lv.getAdapter()).updateData((List)obj);
        }
        else if (obj instanceof EventStatus) {

        }
    }

    @Override
    public void onError(MomErrors err) {
        Log.d("@", "Get some errors" + err);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch(v.getId()) {
            case R.id.MainEvent_invitations:
                i = new Intent(this.getBaseContext(), InvitationList.class);
                i.putExtra("event", event);
                //i.putExtra("user", user);
                startActivity(i);
                break;

            case R.id.button6:
                i = new Intent(this, TaskList.class);
                i.putExtra("event", event);
                //i.putExtra("user", user);
                startActivityForResult(i, 2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Context.momApi.createEventStatus(event, data.getExtras().getString("message"), this);
            }
        }
        refresh();
    }
}
