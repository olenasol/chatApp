package com.example.olena.chatapp.sociallogins;

import android.app.Activity;
import android.content.Intent;

import com.example.olena.chatapp.R;
import com.example.olena.chatapp.utils.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleLogin {

    public void signInWithGoogle(Activity activity) {
        String clientId = "584701609197-9227kj4vafsoabb6qserp7aekh1s9oc5.apps.googleusercontent.com";
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleApiClient = GoogleSignIn.getClient(activity, gso);

        Intent signInIntent = mGoogleApiClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

    public String getNameGmail(Activity activity) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(activity);
        assert acct != null;
        String authCode = acct.getIdToken();
        if (acct != null) {
            return "Google: " + acct.getDisplayName();
        } else {
            return "";
        }

    }
}
