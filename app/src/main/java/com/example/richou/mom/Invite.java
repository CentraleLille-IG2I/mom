package com.example.richou.mom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import MomApi.Model.User;
import MomApi.MomApi;
import MomApi.RequestCallback;
import MomApi.MomErrors;

public class Invite extends AppCompatActivity implements View.OnClickListener {
    EditText email;
    Button search;
    TextView name;
    MomApi api;
    User user;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        email = (EditText) findViewById(R.id.Invite_email);
        search = (Button) findViewById(R.id.Invite_search);
        name = (TextView) findViewById(R.id.Invite_name);

        api = new MomApi(this.getBaseContext());

        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.Invite_search:
                UserSearch us = new UserSearch();
                us.getUserByEmail(email.getText().toString());
                break;
        }
    }
}
