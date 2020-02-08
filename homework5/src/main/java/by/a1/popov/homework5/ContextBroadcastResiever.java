package by.a1.popov.homework5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import static by.a1.popov.homework5.MyService.EXTRA_ACTION;

public class ContextBroadcastResiever extends BroadcastReceiver {

    private static final String ACTION_AIRPLANE_MODE_CHANGED   = Intent.ACTION_AIRPLANE_MODE_CHANGED;
    private static final String ACTION_TIMEZONE_CHANGED = Intent.ACTION_TIMEZONE_CHANGED;

    @Override
    public void onReceive(Context context, Intent intent) {
        String receivedAction = intent.getAction();
        if (ACTION_AIRPLANE_MODE_CHANGED  .equals(receivedAction)) {
            Toast.makeText(context, "ACTION_AIRPLANE_MODE_CHANGED ", Toast.LENGTH_SHORT).show();
            startServiceAction(context, "ACTION_AIRPLANE_MODE_CHANGED ");
        }

        if (ACTION_TIMEZONE_CHANGED.equals(receivedAction)) {
            Toast.makeText(context, "ACTION_TIMEZONE_CHANGED", Toast.LENGTH_SHORT).show();
            startServiceAction(context, "ACTION_TIMEZONE_CHANGED");
        }
    }

    private void startServiceAction(Context context, String action) {
        Intent serviceIntent = new Intent(context, MyService.class);
        serviceIntent.putExtra(EXTRA_ACTION, action);
        context.startService(serviceIntent);
    }
}
