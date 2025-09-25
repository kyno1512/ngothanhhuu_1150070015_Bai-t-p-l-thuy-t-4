package com.example.lab4th;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PowerStateChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null || intent.getAction() == null) return;

        String action = intent.getAction();

        if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
            // Cắm sạc
            Toast.makeText(context, R.string.power_connected, Toast.LENGTH_LONG).show();
        } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            // Rút sạc
            Toast.makeText(context, R.string.power_disconnected, Toast.LENGTH_LONG).show();
        }
    }
}
