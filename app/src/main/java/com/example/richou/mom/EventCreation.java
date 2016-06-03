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
import android.widget.EditText;
import android.widget.Toast;

import MomApiPackage.Model.Event;
import MomApiPackage.MomApi;
import MomApiPackage.RequestCallback;
import MomApiPackage.MomErrors;

public class EventCreation extends AppCompatActivity implements RequestCallback<Event> {
    EditText nameField;
    EditText dateField;
    EditText placeField;
    EditText descriptionField;

    private MomApi m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);
        setSupportActionBar((Toolbar) findViewById(R.id.EventCreation_toolbar));
        getSupportActionBar().setTitle(R.string.title_activity_event_creation);

        m = new MomApi(this);

        nameField = (EditText)findViewById(R.id.EventCreation_name);
        dateField = (EditText)findViewById(R.id.EventCreation_date);
        placeField = (EditText)findViewById(R.id.EventCreation_place);
        descriptionField = (EditText)findViewById(R.id.EventCreation_description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_creation, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.EventCreation_submit:
                String name, date, place, description;
                name = nameField.getText().toString();
                date = dateField.getText().toString();
                //date = "2014/12/12 13:37:42:000000";
                place = placeField.getText().toString();
                description = descriptionField.getText().toString();

                if (name.length()==0) {
                    Toast.makeText(getBaseContext(), R.string.EventCreation_empty, Toast.LENGTH_SHORT).show();
                    return true;
                }
                m.createEvent(name, date, place, description, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        Toast.makeText(getBaseContext(), R.string.EventCreation_error, Toast.LENGTH_SHORT).show();
    }
}
