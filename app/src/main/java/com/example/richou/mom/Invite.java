package com.example.richou.mom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.richou.mom.global.Context;

import java.util.List;

import MomApiPackage.Model.Event;
import MomApiPackage.Model.Invitation;
import MomApiPackage.Model.Rank;
import MomApiPackage.Model.User;
import MomApiPackage.MomApi;
import MomApiPackage.RequestCallback;
import MomApiPackage.MomErrors;

public class Invite extends AppCompatActivity implements View.OnClickListener, RequestCallback<Invitation>{
    private Event event;

    private EditText email;
    private Button search, submit;
    private TextView name, message;
    private Spinner spinner;
    //private MomApi api;
    private User user;
    private UserSearch us;
    private RankGetter rg;

    class UserSearch implements RequestCallback<User> {
        public void getUserByEmail() {
            Context.momApi.getUserByEmail(email.getText().toString(), this);
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
            Context.momApi.getEventRanks(event, this);
        }

        public void onSuccess(List<Rank> ranks) {
            Invite_SpinnerAdapter adapter = new Invite_SpinnerAdapter(getBaseContext(), ranks);
            spinner.setAdapter(adapter);
            spinner.setSelection(0);
        }

        @Override
        public void onError(MomErrors err) {
            Toast.makeText(getBaseContext(), R.string.Invite_rank_error, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        email = (EditText) findViewById(R.id.Invite_email);
        search = (Button) findViewById(R.id.Invite_search);
        submit = (Button) findViewById(R.id.Invite_submit);
        name = (TextView) findViewById(R.id.Invite_name);
        message = (TextView) findViewById(R.id.Invite_message);
        spinner = (Spinner) findViewById(R.id.Invite_rank_spinner);
        event = (Event) getIntent().getSerializableExtra("event");

        //Context.momApi = new MomApi(this.getBaseContext());
        us = new UserSearch();
        rg = new RankGetter();

        rg.getRanks();
        search.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.Invite_search:
                us.getUserByEmail();
                break;
            case R.id.Invite_submit:
                us.getUserByEmail();
                if(user == null) {
                    Toast.makeText(getBaseContext(), R.string.Invite_errorSelectValidEmail, Toast.LENGTH_SHORT).show();
                    return;
                }
                Rank rank = (Rank) spinner.getSelectedItem();
                if(rank == null) {
                    Toast.makeText(getBaseContext(), R.string.Invite_errorSelectRank, Toast.LENGTH_SHORT).show();
                    return;
                }
                Context.momApi.createInvitation(event, user, rank, message.getText().toString(), this);
        }
    }

    @Override
    public void onSuccess(Invitation arg) {
        Toast.makeText(getBaseContext(), getString(R.string.Invite_sent, user.getFullName()), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onError(MomErrors err) {
        Toast.makeText(getBaseContext(), R.string.Invite_error, Toast.LENGTH_SHORT).show();
    }
}
