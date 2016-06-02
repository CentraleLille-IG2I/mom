package com.example.richou.mom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import MomApiPackage.Model.User;
import MomApiPackage.MomApi;
import MomApiPackage.MomErrors;
import MomApiPackage.RequestCallback;

public class Login extends AppCompatActivity implements RequestCallback, View.OnClickListener {
    private MomApi m;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m = new MomApi(this);

        Button b = (Button)findViewById(R.id.button);
        b.setOnClickListener(this);

        Button register = (Button)findViewById(R.id.button_register);
        register.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                Log.d("@", "Connect");

                String user = ((EditText)findViewById(R.id.editText)).getText().toString();
                String pass = ((EditText)findViewById(R.id.editText2)).getText().toString();

                if (user.length() == 0 || pass.length() == 0)
                    setErrorMessage(getString(R.string.Login_Err_emptyField));
                else {
                    m.login(user, pass, this);
                }
                break;
            case R.id.button_register:
                Log.d("@", "Register");
                Intent i = new Intent(this, Register.class);
                startActivityForResult(i, 1);
                break;
            default:
                Log.e("@", "Unknown onClick view");
        }
    }

    private void setErrorMessage(String t)
    {
        TextView terr = (TextView)findViewById(R.id.textViewErr);
        terr.setText(t);
        terr.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(Object obj) {
        //m.getUser(arg, this);
        if (obj instanceof User) {
            Intent i = new Intent(this, MainScreen.class);
            i.putExtra("user", (User)obj);
            startActivity(i);
        }
        else
        {
            m.getUser((int)obj, this);
        }

    }

    @Override
    public void onError(MomErrors err) {
        switch (err) {
            case HTTP_401:
                setErrorMessage(getString(R.string.Login_Err_wrongCredential));
                break;
            case MALFORMED_DATA:
                setErrorMessage(getString(R.string.Global_Err_unknown));
                break;
            case NETWORK_UNAVAILABLE:
                setErrorMessage(getString(R.string.Global_Err_networkUnavailable));
                break;
            default:
                setErrorMessage(getString(R.string.Global_Err_unknown));
                break;
        }
    }
}
