package com.urfstats.clgx.Android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceThrower extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        context.startService(new Intent(context, GamesGetter.class));

    }

}
