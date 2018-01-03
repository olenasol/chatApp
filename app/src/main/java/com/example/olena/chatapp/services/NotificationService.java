package com.example.olena.chatapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.olena.chatapp.R;
import com.example.olena.chatapp.dataproviders.UserProvider;
import com.example.olena.chatapp.utils.Constants;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    private static int countAddedMessages = 0;
    private NotificationManager mNotificationManager;

    public NotificationService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        addMessage();
        return super.onStartCommand(intent, flags, startId);
    }

    private void addMessage() {
        if (countAddedMessages <= 9) {
            Timer timer = new Timer();
            TimerTask hourlyTask = new TimerTask() {
                @Override
                public void run() {
                    final String message = "Hello" + countAddedMessages;
                    Intent intent = new Intent(Constants.BROADCAST_TEXT);
                    intent.putExtra(Constants.BROADCAST_MESSAGE, message);
                    sendBroadcast(intent);
                    com.example.olena.chatapp.models.User searchUser = new UserProvider().getListOfUsers().get(0);

                    countAddedMessages++;
                    showNotification(searchUser.getUserName(), searchUser.getUserSurname(),
                            message);
                    if (countAddedMessages > Constants.NUMBER_OF_NOTIFICATIONS) {
                        this.cancel();
                    }
                }

            };
            timer.schedule(hourlyTask, 0L, 5000);
        }
    }

    private void showNotification(String nam, String surname, String message) {

        String id = "my_channel_01";
        CharSequence name = "new channel";
        String description = "new channel";
        if (Build.VERSION.SDK_INT >= 26) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.setLightColor(Color.RED);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, id)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(nam + " " + surname)
                        .setContentText(message)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSound(uri);
        Notification notification = mBuilder.build();

        mNotificationManager.notify(Constants.NOTIFICATION_ID, notification);

    }

}
