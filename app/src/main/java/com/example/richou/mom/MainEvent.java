package com.example.richou.mom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.ListView;

import java.util.List;

import MomApi.Model.Event;
import MomApi.Model.EventStatus;
import MomApi.MomApi;
import MomApi.RequestCallback;
import MomApi.MomErrors;

public class MainEvent extends AppCompatActivity implements RequestCallback<List<EventStatus>>, View.OnClickListener {
    private Event event;
    private android.widget.ListView lv;
    private Button invitationsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        event = (Event)getIntent().getSerializableExtra("event");
        Log.d("@", "Event in MainEvent: "+event.getId());

        ((TextView)findViewById(R.id.textView3)).setText(event.getName());
        //((TextView)findViewById(R.id.textView3)).setText("topkek");

        lv = (ListView)findViewById(R.id.listView2);
        MomApi m = new MomApi(this);
        m.getEventStatuses(event.getId(), this);

        invitationsButton = (Button) findViewById(R.id.MainEvent_invitations);
        invitationsButton.setOnClickListener(this);
    }

    @Override
    public void onSuccess(List<EventStatus> arg) {
        Log.d("@", "get success");
        ListAdapter adapter = new MainEvent_EventStatusListAdapter(this, arg);
        lv.setAdapter(adapter);
    }

    @Override
    public void onError(MomErrors err) {
        Log.d("@", "Get some errors"+err);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.MainEvent_invitations:
                Intent i = new Intent(this.getBaseContext(), InvitationList.class);
                i.putExtra("event", event);
                startActivity(i);
        }
    }
}
