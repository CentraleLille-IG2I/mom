package com.example.richou.mom;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import MomApiPackage.MomApi;
import MomApiPackage.RequestCallback;
import MomApiPackage.MomErrors;

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
                    Toast.makeText(getBaseContext(), R.string.Register_empty_email, Toast.LENGTH_SHORT).show();
                }

                if(!pass.getText().toString().equals(passBis.getText().toString())) {
                    Toast.makeText(getBaseContext(), R.string.Register_different_pass, Toast.LENGTH_SHORT).show();
                }

                if(pass.getText().length() == 0) {
                    Toast.makeText(getBaseContext(), R.string.Register_empty_password, Toast.LENGTH_SHORT).show();
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
        Intent returnIntent = new Intent();
        returnIntent.putExtra("userId", arg);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onError(MomErrors err) {
        Toast.makeText(getBaseContext(), R.string.Register_error, Toast.LENGTH_SHORT).show();
    }
}
