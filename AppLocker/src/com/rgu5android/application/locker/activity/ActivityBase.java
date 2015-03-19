package com.rgu5android.application.locker.activity;

import com.rgu5android.application.locker.service.ServiceAppLocker;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ActivityBase extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!isMyServiceRunning(ServiceAppLocker.class)) {
			ActivityBase.this.startService(new Intent(ActivityBase.this,
					ServiceAppLocker.class));
		}

	}

	protected boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) ActivityBase.this
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
