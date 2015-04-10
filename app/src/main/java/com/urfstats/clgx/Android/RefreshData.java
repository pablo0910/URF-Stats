package com.urfstats.clgx.Android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RefreshData extends BroadcastReceiver {
    public RefreshData() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (MainActivity.ACTIVITYALIVE) context.getApplicationContext().startActivity(new Intent(context, MainActivity.class));

    }
}
