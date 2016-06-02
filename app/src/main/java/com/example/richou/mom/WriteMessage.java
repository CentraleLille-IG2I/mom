package com.example.richou.mom;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.EventStatus;
import MomApiPackage.MomApi;
import MomApiPackage.MomErrors;
import MomApiPackage.RequestCallback;

public class WriteMessage extends AppCompatActivity implements View.OnClickListener {
    /*public static enum Type {
        EVENT_STATUS;
    }*/

    private Event event;
    //private Type type;
    private Button postButton;
    private EditText editText;
    private MomApi m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);

        m = new MomApi(this);

        event = (Event)getIntent().getSerializableExtra("event");
        //type = getIntent().getSerializableExtra("subTitle");

        TextView subTitle = (TextView)findViewById(R.id.writeMessage_subTitle);
        subTitle.setText(getIntent().getExtras().getString("subTitle"));

        editText = (EditText)findViewById(R.id.editText6);
        postButton = (Button)findViewById(R.id.button9);
        postButton.setOnClickListener(this);

        ((TextView)findViewById(R.id.textView3)).setText(event.getName());



        /*if (type == Type.EVENT_STATUS)
            subTitle.setText(getString(R.string.WriteMessage_subTitle_eventStatus));
        else
            subTitle.setText("Well, actually, ..., this isn't suppose to append :)");*/
    }

    @Override
    public void onClick(View v) {
        String message = editText.getText().toString();
        if (message.length()==0) {
            Log.d("@", "writeMessage need emptyField Popup");
            return;
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra("message", message);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
