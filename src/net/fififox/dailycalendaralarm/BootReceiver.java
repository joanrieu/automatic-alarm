package net.fififox.dailycalendaralarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Receives broadcast {@link Intent}s from the system
 * @author Joan Rieu
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() == Intent.ACTION_BOOT_COMPLETED) {
            intent = new Intent(context, AlarmService.class);
            intent.setAction(Intent.ACTION_CONFIGURATION_CHANGED);
            context.startService(intent);
        }

    }

}
