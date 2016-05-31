package com.example.richou.mom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.widget.ListView;

import java.util.List;

import MomApi.Model.Event;
import MomApi.Model.EventStatus;
import MomApi.MomApi;
import MomApi.RequestCallback;
import MomApi.MomErrors;

public class MainEvent extends AppCompatActivity implements RequestCallback<List<EventStatus>> {
    private Event event;
    private android.widget.ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_event);

        event = (Event)getIntent().getSerializableExtra("event");

        ((TextView)findViewById(R.id.textView3)).setText(event.getName());
        ((TextView)findViewById(R.id.textView3)).setText("topkek");

        lv = (ListView)findViewById(R.id.listView2);
        MomApi m = new MomApi(this);
        m.getEventStatuses(event.getId(), this);
    }

    @Override
    public void onSuccess(List<EventStatus> arg) {
        Log.d("@", "get success");
        ListAdapter adapter = new MainEvent_EventStatusListAdapter(this, arg);
        lv.setAdapter(adapter);
    }

    @Override
    public void onError(MomErrors err) {
        Log.d("@", "Get some errors");
    }
}
