package com.example.richou.mom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import MomApi.Model.Event;
import MomApi.Model.Rank;
import MomApi.Model.User;
import MomApi.MomApi;
import MomApi.RequestCallback;
import MomApi.MomErrors;

public class Invite extends AppCompatActivity implements View.OnClickListener {
    private Event event;

    private EditText email;
    private Button search;
    private TextView name;
    private Spinner spinner;
    private MomApi api;
    private User user;
    private UserSearch us;
    private RankGetter rg;

    class UserSearch implements RequestCallback<User> {
        public void getUserByEmail(String email) {
            api.getUserByEmail(email, this);
        }

        @Override
        public void onSuccess(User arg) {
            user = arg;
            if(user == null) {
                Toast.makeText(getBaseContext(), R.string.Invite_email_not_found, Toast.LENGTH_SHORT).show();
                name.setText("-");
            }
            else {
                name.setText(user.getFullName());
            }
        }

        @Override
        public void onError(MomErrors err) {
            Toast.makeText(getBaseContext(), R.string.Invite_error, Toast.LENGTH_SHORT).show();
        }
    }

    class RankGetter implements RequestCallback<List<Rank>> {
        public void getRanks() {
            api.getEventRanks(event, this);
        }

        public void onSuccess(List<Rank> ranks) {
            Invite_SpinnerAdapter adapter = new Invite_SpinnerAdapter(getBaseContext(), ranks);
            spinner.setAdapter(adapter);
            spinner.setSelection(0);
        }

        @Override
        public void onError(MomErrors err) {
            Toast.makeText(getBaseContext(), "Couldn't retrieve ranks", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        email = (EditText) findViewById(R.id.Invite_email);
        search = (Button) findViewById(R.id.Invite_search);
        name = (TextView) findViewById(R.id.Invite_name);
        spinner = (Spinner) findViewById(R.id.Invite_rank_spinner);
        event = (Event) getIntent().getSerializableExtra("event");

        api = new MomApi(this.getBaseContext());
        us = new UserSearch();
        rg = new RankGetter();

        rg.getRanks();
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.Invite_search:
                us.getUserByEmail(email.getText().toString());
                break;
        }
    }
}
