package com.example.olena.chatapp.activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.renderscript.RenderScript;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.olena.chatapp.R;
import com.example.olena.chatapp.adapters.MessageListAdapter;
import com.example.olena.chatapp.additional_classes.Utils;
import com.example.olena.chatapp.dataproviders.UserProvider;
import com.example.olena.chatapp.fragments.MessageLogFragment;
import com.example.olena.chatapp.fragments.UserListFragment;
import com.example.olena.chatapp.models.Message;
import com.example.olena.chatapp.utils.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;

public class ChatActivity extends AppCompatActivity {

    private UserListFragment userListFragment;
    private MessageLogFragment messageLogFragment;
    private static int countAddedMessages = 0;
    private NotificationManager mNotificationManager;
    private ArrayList<com.example.olena.chatapp.models.User> listOfUsers = new ArrayList<>();

    public ArrayList<com.example.olena.chatapp.models.User> getListOfUsers() {
        return listOfUsers;
    }

    public MessageLogFragment getMessageLogFragment() {

        return messageLogFragment;
    }

    public static int getCountAddedMessages() {
        return countAddedMessages;
    }

    public void setMessageLogFragment(MessageLogFragment messageLogFragment) {
        this.messageLogFragment = messageLogFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){

        }
        //getNameGmail();
        getNameTwitter();
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        startListOfUsersFragment();
        listOfUsers = new UserProvider().getListOfUsers();
        addMessage();


    }
    private void getNameGmail(){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            Toast.makeText(this,"Gmail: " + personName,Toast.LENGTH_LONG).show();
        }
    }
    private void getNameTwitter(){
        Call<User> user = TwitterCore.getInstance().getApiClient().getAccountService().verifyCredentials(false, false,false);
        user.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> userResult) {
                String name = userResult.data.screenName;
                Toast.makeText(ChatActivity.this,"Twitter: "+name,Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exc) {
                Log.d("TwitterKit", "Verify Credentials Failure", exc);
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
    public void startListOfMessagesFragment(MessageLogFragment messageListFragment){
        RelativeLayout layout = findViewById(R.id.fragment_container);
        layout.setVisibility(View.GONE);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container2, messageListFragment)
                .commit();
    }


    public void setMessageListVisible(){
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        RelativeLayout relativeLayout = findViewById(R.id.fragment_container2);
        relativeLayout.setLayoutParams(param);
        relativeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        RelativeLayout relativeLayout = findViewById(R.id.fragment_container);
        relativeLayout.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ) {
            if(messageLogFragment!=null&&messageLogFragment.isVisible()) {
                getSupportFragmentManager().beginTransaction().addToBackStack(null).hide(messageLogFragment).commit();
            }
            if(userListFragment!=null&&userListFragment.isHidden()){
                getSupportFragmentManager().beginTransaction().addToBackStack(null).show(userListFragment).commit();
                relativeLayout.setLayoutParams(param);
            }
            if(userListFragment!=null&&userListFragment.isVisible()){
                startActivity(new Intent(ChatActivity.this,LoginActivity.class));
            }
        }
        else {
            startActivity(new Intent(ChatActivity.this,LoginActivity.class));
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            getSupportFragmentManager().beginTransaction().addToBackStack(null).hide(userListFragment).commit();

        }
    }
    private int getUserInd(){
        com.example.olena.chatapp.models.User searchUser = new UserProvider().getListOfUsers().get(0);
        for (int i=0;i<listOfUsers.size();i++){
            if(listOfUsers.get(i).getUserSurname().equals(searchUser.getUserSurname())){
                return i;
            }
        }
        return  -1;
    }
    private void addMessage() {
        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {
                final String message = "Hello";

                final int index = getUserInd();
                addMessageToView(index,message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userListFragment.changeListOfUsers(listOfUsers.get(index));
                    }
                });


                countAddedMessages++;
                showNotification(listOfUsers.get(index).getUserName(),listOfUsers.get(index).getUserSurname(),
                        message);
                if(countAddedMessages>9) {
                    this.cancel();
                }
            }

        };

        timer.schedule(hourlyTask, 0l, 5000);
    }
    private void addMessageToView(int index,final String message){
        if(messageLogFragment!=null&&messageLogFragment.isVisible()){
            if (messageLogFragment.getUser() == listOfUsers.get(index)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changeMessagesUI(message);
                    }
                });
            }
            else {
                listOfUsers.get(index).addMessageToList(new Message(message, false));
            }
        }
        else {
            listOfUsers.get(index).addMessageToList(new Message(message, false));
        }
    }
    private void changeMessagesUI(String message){
        RecyclerView recyclerView = messageLogFragment.getRecyclerView();
        MessageListAdapter adapter = (MessageListAdapter)recyclerView.getAdapter();
        adapter.addMessageToList(new Message(message, false));
    }
    private void showNotification(String nam,String surname,String message){

        String id = "my_channel_01";
        CharSequence name = "new channel";
        String description = "new channel";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.setLightColor(Color.RED);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this,id)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(nam+" "+surname)
                        .setContentText(message+" "+countAddedMessages)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSound(uri);
        Notification notification=mBuilder.build();

        //mNotificationManager.notify(Constants.NOTIFICATION_ID,notification);

    }

}
