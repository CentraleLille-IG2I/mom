package com.example.richou.mom;

import android.app.Activity;
import android.content.Intent;
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
import MomApiPackage.Model.EventStatus;
import MomApiPackage.Model.User;
import MomApiPackage.MomApi;
import MomApiPackage.RequestCallback;
import MomApiPackage.MomErrors;

public class MainEvent extends AppCompatActivity implements RequestCallback, View.OnClickListener {
    private Event event;
    private User user;
    private MomApi m;

    private android.widget.ListView lv;
    private Button invitationsButton;
    private Button writeStatus;
    private Button task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        m = new MomApi(this);

        event = (Event)getIntent().getSerializableExtra("event");
        Log.d("@", "Event in MainEvent: "+event.getId());
        user = (User)getIntent().getSerializableExtra("user");

        ((TextView)findViewById(R.id.textView3)).setText(event.getName());
        ((TextView)findViewById(R.id.textViewEventDate)).setText(String.format(getString(R.string.MainEvent_date), event.getDate()));
        ((TextView)findViewById(R.id.textViewEventPlace)).setText(String.format(getString(R.string.MainEvent_place), event.getPlace()));

        writeStatus = ((Button) findViewById(R.id.MainEvent_writeStatus));
        task = ((Button) findViewById(R.id.button6));
        if (!event.canOrganise(user)) {
            writeStatus.setVisibility(View.GONE);
            task.setVisibility(View.GONE);
        }
        else {
            writeStatus.setOnClickListener(this);
            task.setOnClickListener(this);
        }


        lv = (ListView)findViewById(R.id.listView2);
        lv.setAdapter(new MainEvent_EventStatusListAdapter(this, new ArrayList<EventStatus>()));

        refresh();
        
        m.getEventStatuses(event.getId(), this);

        invitationsButton = (Button) findViewById(R.id.MainEvent_invitations);
        invitationsButton.setOnClickListener(this);
    }

    private void refresh() {
        m.getEventStatuses(event.getId(), this);
        /*EfficientAdapter adp = (EfficientAdapter) QuickList.getAdapter();
        adp.UpdateDataList(EfficientAdapter.MY_DATA);
        adp.notifyDataSetChanged();
        QuickList.invalidateViews();
        QuickList.scrollBy(0, 0);*/
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
                startActivity(i);
                break;

            case R.id.button6:
                i = new Intent(this, TaskList.class);
                i.putExtra("event", event);
                i.putExtra("user", user);
                startActivityForResult(i, 2);
                break;

            case R.id.MainEvent_writeStatus:
                i = new Intent(this, WriteMessage.class);
                i.putExtra("event", event);
                i.putExtra("subTitle", getString(R.string.WriteMessage_eventStatusCreationSubTitle));
                startActivityForResult(i, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                m.createEventStatus(event, data.getExtras().getString("message"), this);
            }
        }
        refresh();
    }
}
