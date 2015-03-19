package com.rgu5android.application.locker;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastReceiverAppLocker extends BroadcastReceiver {

	private Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		switch (intent.getAction()) {
		case Intent.ACTION_BOOT_COMPLETED:
			context.startService(new Intent(context, ServiceAppLocker.class));
			break;
		case Intent.ACTION_BATTERY_CHANGED:
			if (!isMyServiceRunning(ServiceAppLocker.class)) {
				context.startService(new Intent(context, ServiceAppLocker.class));
			}
			break;
		}
	}

	protected boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
}
