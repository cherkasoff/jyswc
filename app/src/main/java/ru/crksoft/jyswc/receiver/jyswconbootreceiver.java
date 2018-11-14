package ru.crksoft.jyswc.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.crksoft.jyswc.service.jyswcservice;

public class jyswconbootreceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent srvc = new Intent(context, jyswcservice.class);
        context.startService(srvc);

    }

}
