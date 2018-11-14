package ru.crksoft.jyswc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import ru.crksoft.jyswc.core.mediacontroller;
import ru.crksoft.jyswc.service.jyswcservice;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    AudioManager audioManager;
    Button btPlay, btPause, btStop, btNext, btPrevious, btServiceStart;
    BroadcastReceiver rcvr;
    IntentFilter fltr;
    private mediacontroller mMediaController;

    Button btPreviousRead, btNextRead;
    Switch swServiceEnabled;

    SharedPreferences _preferences;

    private void startJYSWCService() {
        Intent srvc = new Intent(this, jyswcservice.class);
        startService(srvc);
    }

    private void setUpControls() {

        btServiceStart = (Button) findViewById(R.id.btServiceStart);
        btServiceStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent srvc = new Intent(v.getContext(), jyswcservice.class);
                v.getContext().startService(srvc);
            }
        });


        btPlay = (Button) findViewById(R.id.btPlay);
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaController.keypressPlay();
            }
        });

        btStop = (Button) findViewById(R.id.btStop);
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaController.keypressStop();
            }
        });

        btPause = (Button) findViewById(R.id.btPause);
        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaController.keypressPause();
            }
        });

        btNext = (Button) findViewById(R.id.btNext);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaController.keypressNext();
            }
        });

        btPrevious = (Button) findViewById(R.id.btPrevious);
        btPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaController.keypressPrevious();
            }
        });

        swServiceEnabled = (Switch) findViewById(R.id.swEnableService);
        swServiceEnabled.setChecked(isServiceEnabled());
        swServiceEnabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setServiceState(swServiceEnabled.isChecked());

                if (isServiceEnabled()) {
                    startJYSWCService();
                }

            }
        });

    }


    private void setUpBroadcastReceiver() {

        rcvr = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                mContext = context;

                if (action.equals("com.microntek.irkeyDown")) {

                    int keyCode = intent.getIntExtra("keyCode", -1);
                    Toast.makeText(context, "keycode: " + keyCode, Toast.LENGTH_SHORT).show();


                } else if (action.equals("com.microntek.bootcheck")) {

                    String className = intent.getStringExtra("class");
                    Toast.makeText(context, "bootcheck classname: " + className, Toast.LENGTH_SHORT).show();


                }

            }
        };

        fltr = new IntentFilter();
        fltr.addAction("com.microntek.bootcheck");
        fltr.addAction("com.microntek.irkeyDown");

    }

    private int getPreviousButtonCode() {
        return _preferences.getInt("previousButtonCode", 0);
    }

    private int getNextButtonCode() {
        return _preferences.getInt("nextButtonCode", 0);
    }

    private Boolean isServiceEnabled() {
        return _preferences.getBoolean("serviceEnabled", false);
    }

    private void setServiceState(Boolean state) {
        SharedPreferences.Editor _editor = _preferences.edit();
        _editor.putBoolean("serviceEnabled", state);
        _editor.apply();
    }

    private void setPreviosButtonCode(int code) {
        SharedPreferences.Editor _editor = _preferences.edit();
        _editor.putInt("previousButtonCode", code);
        _editor.apply();
    }

    private void setNextButtonCode(int code) {
        SharedPreferences.Editor _editor = _preferences.edit();
        _editor.putInt("nextButtonCode", code);
        _editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _preferences = getSharedPreferences("jyswc", MODE_APPEND);

        setUpControls();
        setUpBroadcastReceiver();

        mMediaController = mediacontroller.getInstance(this);

    }

}
