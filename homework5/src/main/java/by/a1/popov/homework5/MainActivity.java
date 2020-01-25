package by.a1.popov.homework5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String LocalReceiverAction = "LocalReceiverAction";
    private ContextBroadcastResiever contextBroadcastResiever;
    private MyService myService;
    private TextView textViewLog;
    private Intent intent;
    private boolean myBound = false;

    private BroadcastReceiver localBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (LocalReceiverAction.equals(intent.getAction())) {
                textViewLog.setText(myService.readLogFile());
                Log.d("MYDEBUG", "new read");
            }
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            myService = ((MyService.MyBinder) binder).getService();
            textViewLog.setText(myService.readLogFile());
            myBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewLog = findViewById(R.id.textViewLog);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        intent = new Intent(this, MyService.class);
        contextBroadcastResiever = new ContextBroadcastResiever();
        registerReceiver(contextBroadcastResiever, intentFilter);

        IntentFilter lclIntentFilter = new IntentFilter();
        intentFilter.addAction(LocalReceiverAction);
        LocalBroadcastManager.getInstance(this).registerReceiver(localBroadcastReceiver, lclIntentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!myBound) return;
        unbindService(serviceConnection);

        myBound = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (contextBroadcastResiever != null) {
            unregisterReceiver(contextBroadcastResiever);
        }
        if (!myBound) return;
        unbindService(serviceConnection);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localBroadcastReceiver);
    }
}
