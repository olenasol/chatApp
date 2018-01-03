package com.example.olena.chatapp.sociallogins;

import android.app.Activity;

import com.example.olena.chatapp.utils.Constants;
import com.steelkiwi.instagramhelper.InstagramHelper;
import com.steelkiwi.instagramhelper.model.InstagramUser;

public class InstagramLogin {
    private static InstagramHelper instagramHelper;


    public void signInWithInstagram(Activity activity) {
        String scope = "basic+public_content+follower_list+comments+relationships+likes";
        instagramHelper = new InstagramHelper.Builder()
                .withClientId(Constants.TWITTER_CLIENT_ID)
                .withRedirectUrl(Constants.TWITTER_CALLBACK)
                .withScope(scope)
                .build();
        instagramHelper.loginFromActivity(activity);
    }

    public String getNameInstagram(Activity activity) {
        if (instagramHelper != null) {
            InstagramUser user = instagramHelper.getInstagramUser(activity);
            return "Instagram: " + user.getData().getUsername();
        } else {
            return "";
        }
    }
}
