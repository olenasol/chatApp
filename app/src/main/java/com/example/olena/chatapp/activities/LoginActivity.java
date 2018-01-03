package com.example.olena.chatapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olena.chatapp.R;
import com.example.olena.chatapp.utils.Utils;
import com.example.olena.chatapp.sociallogins.GoogleLogin;
import com.example.olena.chatapp.sociallogins.InstagramLogin;
import com.example.olena.chatapp.sociallogins.TwitterLogin;
import com.example.olena.chatapp.utils.Constants;
import com.google.android.gms.common.SignInButton;
import com.steelkiwi.instagramhelper.InstagramHelperConstants;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class LoginActivity extends AppCompatActivity {

    private TwitterLoginButton twitterLoginButton;
    private TwitterLogin twitterLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        twitterLogin = new TwitterLogin(LoginActivity.this);
        twitterLogin.initializeTwitter();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView aboutTxt = findViewById(R.id.aboutLink);
        TextView signUpTxt = findViewById(R.id.signUpTxt);
        TextView settingsTxt = findViewById(R.id.settingsLink);
        SignInButton googleSignInButton = findViewById(R.id.google_sign_in_button);
        twitterLoginButton = findViewById(R.id.twitter_login_button);
        Button instagramButton = findViewById(R.id.instagramBtn);
        aboutTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, AboutActivity.class));
            }
        });
        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
        settingsTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SettingsActivity.class));
            }
        });
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GoogleLogin().signInWithGoogle(LoginActivity.this);
            }
        });
        signInWithTwitter();
        instagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InstagramLogin().signInWithInstagram(LoginActivity.this);
            }
        });
    }


    private void signInWithTwitter() {
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                twitterLogin.getTwitterSuccessCallback();
                updateUI(2);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(LoginActivity.this, "Error Twitter Sign in", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.RC_SIGN_IN) {
                updateUI(1);
            }
            if (TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE == requestCode) {
                twitterLoginButton.onActivityResult(requestCode, resultCode, data);
            }
            if (requestCode == InstagramHelperConstants.INSTA_LOGIN) {
                updateUI(3);
            }
        }
    }

    private void updateUI(int typeOfLogin) {
        Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
        intent.putExtra(Constants.TYPE_LOGIN, typeOfLogin);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
