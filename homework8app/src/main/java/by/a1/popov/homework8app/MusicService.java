package by.a1.popov.homework8app;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import static by.a1.popov.homework8app.MainActivity.KEY_ACTION;
import static by.a1.popov.homework8app.MainActivity.KEY_SONG;
import static by.a1.popov.homework8app.NotificatorForPlayer.ACTION_CHANGE_SONG_TTL;


public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    private MediaPlayer player;
    private List<String> songs;
    private int songPosition;
    private final IBinder musicBind = new MusicBinder();
    private String songTitle = "";

    public void onCreate() {
        super.onCreate();
        songPosition = 0;
        player = new MediaPlayer();
        initMusicPlayer();
    }

    public void initMusicPlayer() {
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public void setList(List<String> theSongs) {
        songs = theSongs;
    }

    class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        player.stop();
        player.release();
        return false;
    }

    public void playSong() {
        player.reset();
        String playSong = songs.get(songPosition);
        songTitle = playSong;
        try {
            AssetFileDescriptor afd = getAssets().openFd(playSong);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (Exception e) {
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        player.prepareAsync();
    }

    public void setSong(int songIndex) {
        songPosition = songIndex;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (player.getCurrentPosition() > 0) {
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        getApplicationContext().sendBroadcast(new Intent("TRACK")
                .putExtra(KEY_ACTION, ACTION_CHANGE_SONG_TTL)
                .putExtra(KEY_SONG, songTitle));
        NotificatorForPlayer.createNotification(getApplicationContext(), songTitle, R.drawable.ic_pause_circle, true);
    }

    public int getPosition() {
        return player.getCurrentPosition();
    }

    public int getDur() {
        return player.getDuration();
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }

    public void pausePlayer() {
        player.pause();
        NotificatorForPlayer.createNotification(getApplicationContext(), songTitle, R.drawable.ic_play_circle, false);
    }

    public void seek(int position) {
        player.seekTo(position);
    }

    public void go() {
        player.start();
        NotificatorForPlayer.createNotification(getApplicationContext(), songTitle, R.drawable.ic_pause_circle, true);
    }

    public void playPrev() {
        songPosition--;
        if (songPosition < 0) songPosition = songs.size() - 1;
        playSong();
    }

    public void playNext() {
        songPosition++;
        if (songPosition >= songs.size()) songPosition = 0;
        playSong();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

}