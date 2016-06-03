package com.example.richou.mom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.richou.mom.global.Context;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.model.people.PersonBuffer;

import MomApiPackage.Model.User;
import MomApiPackage.MomApi;
import MomApiPackage.MomErrors;
import MomApiPackage.RequestCallback;



public class Login extends AppCompatActivity implements RequestCallback, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, ResultCallback<People.LoadPeopleResult> {
    //private MomApi m;

    //private GoogleApiClient mGoogleApiClient;
    //private GoogleSignInOptions gso;

    //private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Context.momApi = new MomApi(this);

        Context.gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("360253169459-lj9ncsqf3chgi4gjcbjq1kfe2ksqeo7v.apps.googleusercontent.com")
                        .requestScopes(Plus.SCOPE_PLUS_PROFILE, Plus.SCOPE_PLUS_LOGIN)
                        .requestEmail()
                        .build();

        Context.mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, Context.gso)
                        .addOnConnectionFailedListener(this)
                        .addApi(Plus.API)
                        .build();

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        Button b = (Button)findViewById(R.id.button);
        b.setOnClickListener(this);

        Button register = (Button)findViewById(R.id.button_register);
        register.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                //connection button
                Log.d("@", "Connect");

                String user = ((EditText)findViewById(R.id.editText)).getText().toString();
                String pass = ((EditText)findViewById(R.id.editText2)).getText().toString();

                if (user.length() == 0 || pass.length() == 0)
                    Toast.makeText(this, getString(R.string.Login_Err_emptyField), Toast.LENGTH_LONG).show();  //setErrorMessage(getString(R.string.Login_Err_emptyField));
                else {
                    Context.momApi.login(user, pass, this);
                }
                break;

            case R.id.button_register:
                //creation of a new account
                Log.d("@", "Register");
                Intent i = new Intent(this, Register.class);
                startActivityForResult(i, 1);
                break;

            case R.id.sign_in_button:
                //google button
                signIn();
                break;

            default:
                Log.e("@", "Unknown onClick view");
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(Context.mGoogleApiClient);
        startActivityForResult(signInIntent, 9001);
    }

    private void setErrorMessage(String t)
    {
        /*TextView terr = (TextView)findViewById(R.id.textViewErr);
        terr.setText(t);
        terr.setVisibility(View.VISIBLE);*/
        Toast.makeText(this, t, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(Object obj) {
        //m.getUser(arg, this);
        if (obj instanceof User) {
            Context.loggedUser = (User)obj;

            Intent i = new Intent(this, MainScreen.class);
            startActivity(i);
        }
        else //userId from login
        {
            Context.momApi.getUser((int)obj, this);
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        setErrorMessage(getString(R.string.Login_googleConnectionError));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 9001) {
            //google sign In result
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("@", "handleSignInResult:" + result);
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d("@", acct.getId() + " " + acct.getIdToken() + " " + acct.getServerAuthCode());

            Log.d("@", "Need validation from the server to get fully auhtenticated");

            /*Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
                    .setResultCallback(this);*/

            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
        } else {
            setErrorMessage(getString(R.string.Login_Err_wrongCredential));
        }
    }

    @Override
    public void onResult(People.LoadPeopleResult peopleData) {
        /*if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = peopleData.getPersonBuffer();
            try {
                int count = personBuffer.getCount();
                for (int i = 0; i < count; i++) {
                    Log.d("@", "Display name: " + personBuffer.get(i).getDisplayName() + personBuffer.get(i).);
                }
            } finally {
                personBuffer.release();
            }
        } else {
            Log.e("@", "Error requesting visible circles: " + peopleData.getStatus());
        }*/

    }
}
