package by.a1.popov.homework8app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

class NotificatorForPlayer {

    static final String CHANNEL_ID = "CHANNEL_ID";
    static final String CHANNEL_NAME = "MUSIC_CHANEL";
    private static final int NOTIFICATION_ID = 1;
    static final String ACTION_PREV = "ACTION_PREV";
    static final String ACTION_PLAY = "ACTION_PLAY";
    static final String ACTION_PAUSE = "ACTION_PAUSE";
    static final String ACTION_NEXT = "ACTION_NEXT";
    static final String ACTION_CHANGE_SONG_TTL = "ACTION_CHANGE_SONG_TTL";

    static void createNotification(Context context, String audioFile, int playButton, boolean playFlag) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

            PendingIntent notifyPrev, notifyPlayPause, notifyNext;

            notifyNext = PendingIntent.getBroadcast(context, 0,
                    createPendingIntent(context, ACTION_NEXT),
                    PendingIntent.FLAG_UPDATE_CURRENT);


            notifyPrev = PendingIntent.getBroadcast(context, 0,
                    createPendingIntent(context, ACTION_PREV),
                    PendingIntent.FLAG_UPDATE_CURRENT);

            notifyPlayPause = PendingIntent.getBroadcast(context, 0,
                    createPendingIntent(context, (playFlag ? ACTION_PAUSE : ACTION_PLAY)),
                    PendingIntent.FLAG_UPDATE_CURRENT);


            Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_music_note)
                    .setContentTitle(audioFile)
                    .setOnlyAlertOnce(true)
                    .setShowWhen(false)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(R.drawable.ic_skip_previous, "Previous", notifyPrev)
                    .addAction(playButton, "Pause", notifyPlayPause)
                    .addAction(R.drawable.ic_skip_next, "Next", notifyNext)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2)
                            .setMediaSession(mediaSessionCompat.getSessionToken()))
                    .build();
            notificationManagerCompat.notify(NOTIFICATION_ID, notification);
        }
    }

    private static Intent createPendingIntent(Context context, String action) {
        return new Intent(context, NotificationBroadcastReceiver.class)
                .setAction(action);
    }
}
