package com.example.olena.chatapp.sociallogins;


import android.app.Activity;
import android.util.Log;

import com.example.olena.chatapp.R;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

public class TwitterLogin {

    private Activity activity;

    public TwitterLogin(Activity activity) {
        this.activity = activity;
    }

    public void initializeTwitter() {
        TwitterConfig config = new TwitterConfig.Builder(activity)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(activity.getString(R.string.CONSUMER_KEY),
                        activity.getString(R.string.CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }

    public void getTwitterSuccessCallback() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;

    }
}
