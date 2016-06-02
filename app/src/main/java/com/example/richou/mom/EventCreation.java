package com.example.richou.mom;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import MomApiPackage.Model.Event;
import MomApiPackage.MomApi;
import MomApiPackage.RequestCallback;
import MomApiPackage.MomErrors;

public class EventCreation extends AppCompatActivity implements View.OnClickListener, RequestCallback<Event> {
    Button bCreate;
    EditText nameField;
    EditText dateField;
    EditText placeField;
    EditText descriptionField;

    private MomApi m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        m = new MomApi(this);

        nameField = (EditText)findViewById(R.id.editText7);
        dateField = (EditText)findViewById(R.id.editText8);
        placeField = (EditText)findViewById(R.id.editText9);
        descriptionField = (EditText)findViewById(R.id.editText10);

        bCreate = (Button)findViewById(R.id.button11);
        bCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name, date, place, description;
        name = nameField.getText().toString();
        date = dateField.getText().toString();
        //date = "2014/12/12 13:37:42:000000";
        place = placeField.getText().toString();
        description = descriptionField.getText().toString();

        if (name.length()==0 || date.length()==0 || place.length()==0 || description.length()==0) {
            Log.d("@", "need warning popup");
            return;
        }

        m.createEvent(name, date, place, description, this);
    }

    @Override
    public void onSuccess(Event arg) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("createdEvent", arg);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onError(MomErrors err) {
        Log.d("@", "event creation returned error --> need warning popup");
    }
}
