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
import android.widget.TextView;
import android.widget.Toast;

import MomApiPackage.Model.Event;
import MomApiPackage.MomApi;

public class WriteMessage extends AppCompatActivity  {

    private Event event;
    private EditText editText;
    //private MomApi m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);

        setSupportActionBar((Toolbar) findViewById(R.id.WriteMessage_toolbar));

        //m = new MomApi(this);

        event = (Event)getIntent().getSerializableExtra("event");

        getSupportActionBar().setTitle(event.getName());
        getSupportActionBar().setSubtitle(getIntent().getExtras().getString("subTitle"));

        editText = (EditText)findViewById(R.id.editText6);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.WriteMessage_send:
                String message = editText.getText().toString();
                if (message.length()==0) {
                    Toast.makeText(getBaseContext(), R.string.WriteMessage_errorEmptyMessage, Toast.LENGTH_SHORT);
                    return true;
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("message", message);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
