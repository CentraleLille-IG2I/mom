package com.example.richou.mom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import MomApi.Model.Event;
import MomApi.RequestCallback;
import MomApi.MomApi;
import MomApi.MomErrors;

public class MainScreen extends AppCompatActivity implements RequestCallback<List<Event>>, AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView lv;
    private Button bSettings;
    private Button bCreate;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        userId = getIntent().getExtras().getInt("userId");

        MomApi m = new MomApi(this);

        m.getUserEvents(userId, this);

        lv = (ListView)findViewById(R.id.listView);
        bSettings = (Button)findViewById(R.id.button2);
        bCreate = (Button)findViewById(R.id.button4);

        lv.setOnItemClickListener(this);
        bSettings.setOnClickListener(this);
        bCreate.setOnClickListener(this);

    }

    @Override
    public void onSuccess(List<Event> arg) {
        Log.d("@", "received success");


        //List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        //HashMap<String, String> elem;

        /*for (Event e : arg) {
            elem = new HashMap<>();
            elem.put("txt1", e.getName());
            elem.put("txt2", e.getDescription());
            list.add(elem);
        }*/

        //Log.d("@", list.toString());

        //ListAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, new String[] {"txt1","txt2"}, new int[] {android.R.id.text1, android.R.id.text2});
        ListAdapter adapter = new MainScreen_eventListAdapter(this, arg);
        lv.setAdapter(adapter);
    }

    @Override
    public void onError(MomErrors err) {
        Log.d("@", "received error");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Event event = (Event)parent.getItemAtPosition(position);
        Intent i = new Intent(this, MainEvent.class);
        i.putExtra("event", event);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Log.d("@", "Settings Button unimplemented !");
                break;
            case R.id.button4:
                //Log.d("@", "Create Button unimplemented !");
                Intent i = new Intent(this, EventCreation.class);
                startActivity(i);
                break;
        }
    }
}
