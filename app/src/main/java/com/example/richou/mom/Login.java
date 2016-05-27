package com.example.richou.mom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import MomApi.RequestCallback;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

import MomApi.MomApi;

public class Login extends AppCompatActivity implements View.OnClickListener, RequestCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button b = (Button)findViewById(R.id.button);
        b.setOnClickListener(this);
    }

    public void onClick(View v) {
        Log.d("@", "test2");
        MomApi m = new MomApi(this);
        String user = ((EditText)findViewById(R.id.editText)).getText().toString();
        String pass = ((EditText)findViewById(R.id.editText2)).getText().toString();

        if (user.length() == 0 || pass.length() == 0)
            setErrorMessage(getString(R.string.Login_Err_emptyField));
        else
            m.login(user, pass, this);
    }

    private void setErrorMessage(String t)
    {
        TextView terr = (TextView)findViewById(R.id.textViewErr);
        terr.setText(t);
        terr.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorResponse(VolleyError error) {


        if (error.networkResponse != null && error.networkResponse.statusCode == HttpsURLConnection.HTTP_UNAUTHORIZED)
            setErrorMessage(getString(R.string.Login_Err_wrongCredential));
        else if (error instanceof NoConnectionError || error instanceof TimeoutError)
            setErrorMessage(getString(R.string.Global_Err_networkUnavailable));
        else
            setErrorMessage(getString(R.string.Login_Err_unknown));
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("@", response.toString());


        try {
            Log.d("@", response.getString("status"));
            if (response.getString("status").equals("success")) {
                Log.d("@", "logged in");
                response.getString("user_pk");
                startActivity(new Intent(this, MainScreen.class));
            }
        } catch (JSONException e) {
            Log.d("@", "MalformedJSON");
            setErrorMessage(getString(R.string.Login_Err_unknown));
        }
    }
}
