package by.a1.popov.homework5;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyService extends Service {

    public static final String EXTRA_ACTION = "EXTRA_ACTION";
    MyBinder binder = new MyBinder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        doLogFile(intent.getStringExtra(EXTRA_ACTION));
        return super.onStartCommand(intent, flags, startId);
    }

    public void doLogFile(final String action) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // get current time for new log record
                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss", Locale.getDefault());
                String strDate = sdf.format(currentTime);
                // add new log record in file serviceLog.log
                try {
                    FileWriter fw = new FileWriter(getFilesDir() + "/serviceLog.log", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter printWriter = new PrintWriter(bw);
                    printWriter.println(strDate + " - " + action);
                    printWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stopSelf();
            }
        });
        thread.start();
    }

    public String readLogFile() {
        String filename = "serviceLog.log";
        String result = null;
        File file = new File(getFilesDir(), filename);
        if (file.exists()) {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;
            FileReader fileReader = null;
            try {
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            result = "FILE NOT FOUND";
        }
        return result;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (binder == null) {
            binder = new MyBinder();
        }
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
