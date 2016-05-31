package com.example.richou.mom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EventCreation extends AppCompatActivity implements View.OnClickListener {
    Button bCreate;
    EditText nameField;
    EditText dateField;
    EditText placeField;
    EditText descriptionField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_creation);

        bCreate = (Button)findViewById(R.id.button11);
        bCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name, date, place, description;
        name = nameField.getText().toString();
        date = dateField.getText().toString();
        place = placeField.getText().toString();
        description = descriptionField.getText().toString();

        if (name.length()==0 || date.length()==0 || place.length()==0 || description.length()==0) {
            Log.d("@", "need warning popup");
            return;
        }

        finish();

    }
}
