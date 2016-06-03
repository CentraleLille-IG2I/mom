package com.example.richou.mom;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.User;
import MomApiPackage.RequestCallback;
import MomApiPackage.MomApi;
import MomApiPackage.MomErrors;

public class MainScreen extends AppCompatActivity implements RequestCallback<List<Event>>, AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView lv;
    private Button bSettings;
    private FloatingActionButton bCreate;

    private User user;

    private MomApi m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        user = (User)getIntent().getSerializableExtra("user");

        m = new MomApi(this);

        lv = (ListView)findViewById(R.id.listView);
        bCreate = (FloatingActionButton)findViewById(R.id.MainScreen_Fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.MainScreen_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.title_activity_main_screen);
        toolbar.setSubtitle(user.getFullName());

        lv.setOnItemClickListener(this);
        bCreate.setOnClickListener(this);

        lv.setAdapter(new MainScreen_eventListAdapter(this, new ArrayList<Event>()));
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.MainScreen_settings:
                Log.d("@", "Settings button.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refresh() {
        m.getUserEvents(user, this);
    }

    @Override
    public void onSuccess(List<Event> arg) {
        Log.d("@", "received success");

        ((MainScreen_eventListAdapter)lv.getAdapter()).updateData(arg);
    }

    @Override
    public void onError(MomErrors err) {
        Log.d("@", "received error");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Event event = (Event)parent.getItemAtPosition(position);
        Log.d("@", "Event: "+event.getId());
        startMainEventActivity(event);
    }

    private void startMainEventActivity(Event e) {
        Intent i = new Intent(this, MainEvent.class);
        i.putExtra("event", e);
        i.putExtra("user", user);
        startActivityForResult(i, 2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.MainScreen_Fab:
                //Log.d("@", "Create Button unimplemented !");
                Intent i = new Intent(this, EventCreation.class);
                startActivityForResult(i, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK) {
                Log.d("@", "activity finished ok");

                Event event = (Event)data.getSerializableExtra("createdEvent");
                startMainEventActivity(event);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("@", "activity canceled");
                refresh();
            }
        }
        else
            refresh();
    }
}
