package com.example.olena.chatapp.activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.olena.chatapp.R;
import com.example.olena.chatapp.adapters.MessageListAdapter;
import com.example.olena.chatapp.utils.Utils;
import com.example.olena.chatapp.dataproviders.UserProvider;
import com.example.olena.chatapp.fragments.MessageLogFragment;
import com.example.olena.chatapp.fragments.UserListFragment;
import com.example.olena.chatapp.models.Message;
import com.example.olena.chatapp.services.NotificationService;
import com.example.olena.chatapp.sociallogins.GoogleLogin;
import com.example.olena.chatapp.sociallogins.InstagramLogin;
import com.example.olena.chatapp.utils.Constants;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;

import retrofit2.Call;

public class ChatActivity extends AppCompatActivity {

    private UserListFragment userListFragment;
    private MessageLogFragment messageLogFragment;
    private ArrayList<com.example.olena.chatapp.models.User> listOfUsers = new ArrayList<>();

    public ArrayList<com.example.olena.chatapp.models.User> getListOfUsers() {
        return listOfUsers;
    }


    public void setMessageLogFragment(MessageLogFragment messageLogFragment) {
        this.messageLogFragment = messageLogFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getAccountName();

        if (savedInstanceState != null) {

            Fragment fragment = getSupportFragmentManager().getFragment(savedInstanceState,
                    Constants.LIST_FRAGMENT);
            if (fragment instanceof UserListFragment) {
                userListFragment = (UserListFragment) fragment;

            }
            if (savedInstanceState.getParcelableArrayList(Constants.LIST_OF_USERS) != null) {
                listOfUsers = savedInstanceState.getParcelableArrayList(Constants.LIST_OF_USERS);
            }
        } else {
            startListOfUsersFragment();
            listOfUsers = new UserProvider().getListOfUsers();
            //addMessage();
            startService(new Intent(this, NotificationService.class));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra(Constants.BROADCAST_MESSAGE);
                addMessage(msg);
            }
        };
        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_TEXT);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void getAccountName() {
        int typeLogin = getIntent().getIntExtra(Constants.TYPE_LOGIN, 0);
        String toastText = "";
        switch (typeLogin) {
            case 1:
                toastText = new GoogleLogin().getNameGmail(ChatActivity.this);
                break;
            case 2:
                getNameTwitter();
                break;
            case 3:
                toastText = new InstagramLogin().getNameInstagram(ChatActivity.this);
                break;
        }
        Toast.makeText(this, toastText, Toast.LENGTH_LONG).show();
    }


    private void getNameTwitter() {
        Call<User> user = TwitterCore.getInstance().getApiClient().getAccountService().
                verifyCredentials(false, false, false);
        user.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> userResult) {
                String name = userResult.data.screenName;
                Toast.makeText(ChatActivity.this, "Twitter: " + name, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exc) {

            }
        });

    }

    private void startListOfUsersFragment() {
        userListFragment = new UserListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, userListFragment)
                .commit();
    }

    public void startListOfMessagesFragment(MessageLogFragment messageListFragment) {
        RelativeLayout layout = findViewById(R.id.fragment_container);
        layout.setVisibility(View.GONE);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container2, messageListFragment)
                .commit();
    }


    public void setMessageListVisible() {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        RelativeLayout relativeLayout = findViewById(R.id.fragment_container2);
        relativeLayout.setLayoutParams(param);
        relativeLayout.setVisibility(View.VISIBLE);
    }

    private void setUserFragmentMaxWidth() {
        RelativeLayout relativeLayout = findViewById(R.id.fragment_container);
        relativeLayout.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        relativeLayout.setLayoutParams(param);
    }

    @Override
    public void onBackPressed() {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (messageLogFragment != null && messageLogFragment.isVisible()) {
                getSupportFragmentManager().beginTransaction().addToBackStack(null).hide(messageLogFragment).commit();
            }
            if (userListFragment != null && userListFragment.isHidden()) {
                getSupportFragmentManager().beginTransaction().addToBackStack(null).show(userListFragment).commit();
                setUserFragmentMaxWidth();
            }
            if (userListFragment != null && userListFragment.isVisible()) {
                startActivity(new Intent(ChatActivity.this, LoginActivity.class));
            }
        } else {
            startActivity(new Intent(ChatActivity.this, LoginActivity.class));
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (messageLogFragment != null) {
                getSupportFragmentManager().beginTransaction().show(userListFragment).show(messageLogFragment).commit();
            }

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportFragmentManager().beginTransaction().addToBackStack(null).hide(userListFragment).commit();
        }
    }

    private int getUserInd() {
        com.example.olena.chatapp.models.User searchUser = new UserProvider().getListOfUsers().get(0);
        for (int i = 0; i < listOfUsers.size(); i++) {
            if (listOfUsers.get(i).getUserSurname().equals(searchUser.getUserSurname())) {
                return i;
            }
        }
        return -1;
    }

    private void addMessage(String message) {

        final int index = getUserInd();
        addMessageToView(index, message);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userListFragment.changeListOfUsers(listOfUsers.get(index));
            }
        });


    }

    private void addMessageToView(int index, final String message) {
        if (messageLogFragment != null && messageLogFragment.isVisible()) {
            if (messageLogFragment.getUser() == listOfUsers.get(index)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changeMessagesUI(message);
                    }
                });
            } else {
                listOfUsers.get(index).addMessageToList(new Message(message, false));
            }
        } else {
            listOfUsers.get(index).addMessageToList(new Message(message, false));
        }
    }

    private void changeMessagesUI(String message) {
        RecyclerView recyclerView = messageLogFragment.getRecyclerView();
        MessageListAdapter adapter = (MessageListAdapter) recyclerView.getAdapter();
        adapter.addMessageToList(new Message(message, false));
    }


    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, Constants.LIST_FRAGMENT,
                userListFragment);
        outState.putParcelableArrayList(Constants.LIST_OF_USERS, listOfUsers);
    }

    @Override
    protected void onStop() {
        stopService(new Intent(this, NotificationService.class));
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, NotificationService.class));
        super.onDestroy();
    }
}
