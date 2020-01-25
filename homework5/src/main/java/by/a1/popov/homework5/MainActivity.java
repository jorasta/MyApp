package by.a1.popov.homework5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ContextBroadcastResiever contextBroadcastResiever;
    private MyService myService;
    private TextView textViewLog;
    private Intent intent;
    private boolean myBound = false;

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
    }
}
