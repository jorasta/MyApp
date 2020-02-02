package by.a1.popov.homework5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import static by.a1.popov.homework5.MyService.EXTRA_ACTION;

public class ManifestBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_LOCALE_CHANGED = Intent.ACTION_LOCALE_CHANGED;
    private static final String WIFI_STATE_CHANGED_ACTION = WifiManager.WIFI_STATE_CHANGED_ACTION;

    @Override
    public void onReceive(Context context, Intent intent) {
        String receivedAction = intent.getAction();
        if (ACTION_LOCALE_CHANGED.equals(receivedAction)) {
            Toast.makeText(context, "ACTION_LOCALE_CHANGED", Toast.LENGTH_SHORT).show();
            startServiceAction(context, "ACTION_LOCALE_CHANGED");
        }

        if (WIFI_STATE_CHANGED_ACTION.equals(receivedAction)) {
            Toast.makeText(context, "WIFI_STATE_CHANGED_ACTION", Toast.LENGTH_SHORT).show();
            startServiceAction(context, "WIFI_STATE_CHANGED_ACTION");
        }
    }

    private void startServiceAction(Context context, String action) {
        Intent serviceIntent = new Intent(context, MyService.class);
        serviceIntent.putExtra(EXTRA_ACTION, action);
        context.startService(serviceIntent);
    }
}
