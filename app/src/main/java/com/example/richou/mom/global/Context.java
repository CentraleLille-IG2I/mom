package com.example.richou.mom.global;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import MomApiPackage.Model.User;
import MomApiPackage.MomApi;

/**
 * Created by Robin on 03/06/2016.
 */
public class Context {
    public static MomApi momApi;

    public static GoogleApiClient mGoogleApiClient;
    public static GoogleSignInOptions gso;

    public static User loggedUser;

    public static SyncSources sync=null; //at some point we could need to have more than one external source at a time



}
