package ru.crksoft.jyswc.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import ru.crksoft.jyswc.core.mediacontroller;

public class jyswcservice extends Service {

    private Boolean serviceAlive = false;

    private BroadcastReceiver rcvr;
    private IntentFilter fltr;

    private Context mContext;

    private static final boolean DEBUG_AS_TOAST = false;
    private static final String TAG = "jyswcservice";

    mediacontroller mMediaController;

    private void debug(String message) {

        if (DEBUG_AS_TOAST)
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        else
            Log.d(TAG, message);

    }

    private void processKeyPress(int key) {

        debug("Pressed key code: " + key);

        switch (key) {
            case 300 : { // Next
                mMediaController.keypressNext();
                break;
            }
            case 299 : { // Previous
                mMediaController.keypressPrevious();
                break;
            }
            case 258 : { // MUTE
            }
            case 331 : { // Media
                //mMediaController.keypressPrevious();
            }
        }


    }

    private void processBootcheckEvent(String className) {

        switch (className) {
            case "com.microntek.radio": {
                mMediaController.keypressPause();
                break;
            }
            case "com.microntek.music": {
                mMediaController.keypressPause();
                break;
            }
            case "com.microntek.dvd": {
                mMediaController.keypressPause();
                break;
            }
            case "com.microntek.ipod": {
                mMediaController.keypressPause();
                break;
            }
            case "com.microntek.media": {
                mMediaController.keypressPause();
                break;
            }
            case "com.microntek.avin": {
                mMediaController.keypressPause();
                break;
            }
            case "com.microntek.bluetooth": {
                mMediaController.keypressPause();
                break;
            }
            default: {
                debug("Unknown class name: " + className);
            }
        }

    }

    private void setUpMediaController() {

        mMediaController = mediacontroller.getInstance(mContext);

    }

    private void setUpBroadcastReceiver() {
        rcvr = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                mContext = context;

                if (action.equals("com.microntek.irkeyDown")) {

                    int keyCode = intent.getIntExtra("keyCode", -1);
                    processKeyPress(keyCode);

                } else if (action.equals("com.microntek.bootcheck")) {

                    String className = intent.getStringExtra("class");
                    processBootcheckEvent(className);

                }

            }
        };

        fltr = new IntentFilter();
        fltr.addAction("com.microntek.bootcheck");
        fltr.addAction("com.microntek.irkeyDown");

        registerReceiver(rcvr, fltr);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!serviceAlive) {

            debug("Service is starting ...");

            setUpMediaController();
            setUpBroadcastReceiver();

            serviceAlive = true;

        }
        else
        {
            debug("Service is already running ...");
        }

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        debug("Service is stopping ...");
        serviceAlive = false;
        unregisterReceiver(rcvr);

    }
}
