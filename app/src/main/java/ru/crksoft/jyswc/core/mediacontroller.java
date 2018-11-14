package ru.crksoft.jyswc.core;

import android.content.Context;
import android.media.AudioManager;
import android.os.SystemClock;
import android.view.KeyEvent;

public class mediacontroller {

    private static mediacontroller instance;
    private static Context mContext;

    private Boolean mMusicPlaying = false;

    private AudioManager audioManager;
    private KeyEvent event;

    private mediacontroller() {
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    public static mediacontroller getInstance(Context context) {

        mContext = context;

        if (instance == null)
        {
            instance = new mediacontroller();

        }

        return instance;
    }

    private void setMusicPlaying() {
        mMusicPlaying = true;
    }

    private void setMusicStopped() {
        mMusicPlaying = false;
    }

    public Boolean isMusicPlaying() {
        return mMusicPlaying;
    }

    public void keypressNext() {

        long eventtime = SystemClock.uptimeMillis() - 1;

        event = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT, 0);
        audioManager.dispatchMediaKeyEvent(event);
        event = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_NEXT, 0);
        audioManager.dispatchMediaKeyEvent(event);

        setMusicPlaying();

    }

    public void keypressPrevious() {

        long eventtime = SystemClock.uptimeMillis() - 1;

        event = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
        audioManager.dispatchMediaKeyEvent(event);
        event = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS, 0);
        audioManager.dispatchMediaKeyEvent(event);

        setMusicPlaying();

    }

    public void keypressPlay() {

        long eventtime = SystemClock.uptimeMillis() - 1;

        event = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY, 0);
        audioManager.dispatchMediaKeyEvent(event);
        event = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY, 0);
        audioManager.dispatchMediaKeyEvent(event);

        setMusicPlaying();

    }

    public void keypressStop() {

        long eventtime = SystemClock.uptimeMillis() - 1;

        event = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_STOP, 0);
        audioManager.dispatchMediaKeyEvent(event);
        event = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_STOP, 0);
        audioManager.dispatchMediaKeyEvent(event);

        setMusicStopped();

    }

    public void keypressPause() {

        long eventtime = SystemClock.uptimeMillis() - 1;

        event = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PAUSE, 0);
        audioManager.dispatchMediaKeyEvent(event);
        event = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PAUSE, 0);
        audioManager.dispatchMediaKeyEvent(event);

        setMusicStopped();

    }


}
