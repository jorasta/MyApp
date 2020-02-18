package by.a1.popov.homework8app;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static by.a1.popov.homework8app.NotificatorForPlayer.CHANNEL_ID;
import static by.a1.popov.homework8app.NotificatorForPlayer.CHANNEL_NAME;


public class MainActivity extends AppCompatActivity implements MediaController.MediaPlayerControl {

    @BindView(R.id.recyclerSongs)
    public RecyclerView recyclerSongs;
    @BindView(R.id.textViewSongTitle)
    public TextView textViewSongTitle;

    public static final String KEY_ACTION = "KEY_ACTION";
    public static final String KEY_ACTION_FILTER = "TRACK";
    public static final String KEY_SONG = "SONG_TITLE";
    private Unbinder unbinder;

    private List<String> songList;
    private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound = false;

    private MusicController controller;
    private NotificationManager notificationManager;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString(KEY_ACTION);
            if (action != null) {
                switch (action) {
                    case NotificatorForPlayer.ACTION_PREV:
                        musicSrv.playPrev();
                        break;
                    case NotificatorForPlayer.ACTION_PLAY:
                        musicSrv.go();
                        controller.show(0);
                        break;
                    case NotificatorForPlayer.ACTION_PAUSE:
                        musicSrv.pausePlayer();
                        controller.show(0);
                        break;
                    case NotificatorForPlayer.ACTION_NEXT:
                        musicSrv.playNext();
                        break;
                    case NotificatorForPlayer.ACTION_CHANGE_SONG_TTL:
                        textViewSongTitle.setText(intent.getStringExtra(KEY_SONG));
                        controller.show(0);
                        break;
                }
            }
        }
    };

    private ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            musicSrv = binder.getService();
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        songList = new ArrayList<>();
        getSongList();
        recyclerSongs.setAdapter(new SongsAdapter(listAssetFiles()));
        registerReceiver(broadcastReceiver, new IntentFilter(KEY_ACTION_FILTER));
        createNotificationChannel();
        setController();
    }


    public void songPicked(int id) {
        musicSrv.setSong(id);
        musicSrv.playSong();
    }

    public void getSongList() {
        songList = listAssetFiles();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        if (musicSrv != null && musicBound && musicSrv.isPlaying())
            return musicSrv.getPosition();
        else return 0;
    }

    @Override
    public int getDuration() {
        if (musicSrv != null && musicBound && musicSrv.isPlaying())
            return musicSrv.getDur();
        else return 0;
    }

    @Override
    public boolean isPlaying() {
        if (musicSrv != null && musicBound )
            return musicSrv.isPlaying();
        return false;
    }

    @Override
    public void pause() {
        musicSrv.pausePlayer();
    }

    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);
    }

    @Override
    public void start() {
        musicSrv.go();
    }

    private void setController() {
        controller = new MusicController(this);
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSrv.playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSrv.playPrev();
            }
        });
        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.viewMediaController));
        controller.setEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv = null;
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
        notificationManager.cancelAll();
        unregisterReceiver(broadcastReceiver);
    }

    private List<String> listAssetFiles() {
        List<String> files = new ArrayList<>();
        try {
            String[] list = getAssets().list("");
            if (list != null) {
                for (String file : list) {
                    if (file.contains(".mp3")) {
                        files.add(file);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }

    public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {

        private List<String> itemList;

        SongsAdapter(List<String> songList) {
            this.itemList = songList;
        }

        @NonNull
        @Override
        public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_list, parent, false);
            return new SongViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SongViewHolder holder, final int position) {
            final String song = itemList.get(position);
            holder.itemView.setTag(position);
            holder.trackName.setText(song);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    songPicked(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemList != null ? itemList.size() : 0;
        }

        public class SongViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.textViewSongName)
            public TextView trackName;

            SongViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}