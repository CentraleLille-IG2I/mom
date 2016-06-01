package com.example.richou.mom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import MomApi.Model.User;
import MomApi.MomApi;
import MomApi.RequestCallback;
import MomApi.MomErrors;

public class Register extends AppCompatActivity implements View.OnClickListener, RequestCallback<Integer> {
    EditText firstName, lastName, email, phone, pass, passBis;
    Button submit;

    MomApi api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = (EditText) findViewById(R.id.register_first_name);
        lastName = (EditText) findViewById(R.id.register_last_name);
        email = (EditText) findViewById(R.id.register_email);
        phone = (EditText) findViewById(R.id.register_phone);
        pass = (EditText) findViewById(R.id.register_password);
        passBis = (EditText) findViewById(R.id.register_password_bis);
        submit = (Button) findViewById(R.id.register_submit);

        submit.setOnClickListener(this);
        api = new MomApi(getBaseContext());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_submit:
                Log.d("@", "Register - Submit");

                if(email.getText().length() == 0) {
                    Toast.makeText(getBaseContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                }

                if(!pass.getText().toString().equals(passBis.getText().toString())) {
                    Toast.makeText(getBaseContext(), "Passwords are different", Toast.LENGTH_SHORT).show();
                }

                if(pass.getText().length() == 0) {
                    Toast.makeText(getBaseContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                }
                
                Log.d("@", firstName.getText().toString()+
                        lastName.getText().toString()+
                        email.getText().toString()+
                        phone.getText().toString()+
                        pass.getText().toString());
                api.register(firstName.getText().toString(),
                        lastName.getText().toString(),
                        email.getText().toString(),
                        phone.getText().toString(),
                        pass.getText().toString(),
                        this);
        }
    }

    @Override
    public void onSuccess(Integer arg) {
        Intent i = new Intent(this, MainScreen.class);
        i.putExtra("userId", arg);
        startActivity(i);
    }

    @Override
    public void onError(MomErrors err) {
        Toast.makeText(getBaseContext(), "Something wrong happened.", Toast.LENGTH_SHORT).show();
    }
}
