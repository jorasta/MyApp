package by.a1.popov.homework8app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static by.a1.popov.homework8app.MainActivity.KEY_ACTION;
import static by.a1.popov.homework8app.MainActivity.KEY_ACTION_FILTER;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent(KEY_ACTION_FILTER)
                .putExtra(KEY_ACTION, intent.getAction()));

    }
}
