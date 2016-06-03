package com.example.richou.mom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import android.widget.ListView;

import java.util.List;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.Invitation;
import MomApiPackage.MomApi;
import MomApiPackage.RequestCallback;
import MomApiPackage.MomErrors;

public class InvitationList extends AppCompatActivity implements RequestCallback<List<Invitation>>, View.OnClickListener {

    private ListView listView;
    private MomApi api;
    private Event event;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listView = (ListView) findViewById(R.id.InvitationList_list);
        event = (Event) getIntent().getSerializableExtra("event");
        api = new MomApi(this.getBaseContext());

        toolbar.setSubtitle(event.getName());
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.InvitationList_Fab);
        fab.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        api.getEventInvitations(event, this);
    }

    @Override
    public void onSuccess(List<Invitation> arg) {
        ListAdapter adapter = new InvitationList_Adapter(this, arg);
        listView.setAdapter(adapter);
    }

    @Override
    public void onError(MomErrors err) {
        Toast.makeText(this.getBaseContext(), R.string.InvitationList_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.InvitationList_Fab:
                Intent i = new Intent(this.getBaseContext(), Invite.class);
                i.putExtra("event", event);
                startActivityForResult(i, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        api.getEventInvitations(event, this);
    }
}
