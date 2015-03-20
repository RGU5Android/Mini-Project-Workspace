package com.rgu5android.application.locker.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Binder;
import android.os.IBinder;

import com.rgu5android.application.locker.activity.ActivityAppLocker;
import com.rgu5android.application.locker.common.database.AppLockerDatabaseController;

public class ServiceAppLocker extends Service {

	public static final String PACKAGE = "PACKAGE";
	private String previousPackage = "";
	private String currentPackage = "";

	private final IBinder mBinder = new ServiceBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public class ServiceBinder extends Binder {
		public ServiceAppLocker getService() {
			return ServiceAppLocker.this;
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {

				List<String> lockedApplications = null;

				try {
					lockedApplications = (new AppLockerDatabaseController())
							.getLockedPackages(getApplicationContext());
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				ActivityManager activityManager = (ActivityManager) ServiceAppLocker.this
						.getSystemService(Context.ACTIVITY_SERVICE);

				currentPackage = activityManager.getRunningAppProcesses()
						.get(0).processName;

				if (lockedApplications != null
						&& lockedApplications.contains(currentPackage)
						&& !currentPackage.equalsIgnoreCase(previousPackage)) {
					Intent intent = new Intent(getApplicationContext(),
							ActivityAppLocker.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra(PACKAGE, currentPackage);
					startActivity(intent);
					previousPackage = currentPackage;
				}

				if (currentPackage.contains("launcher")
						|| currentPackage
								.contains("com.google.android.googlequicksearchbox")
						|| currentPackage.contains("com.htc.opensense.social")
						|| currentPackage.contains("com.sonyericsson.home")) {
					previousPackage = "";
				}
				if (lockedApplications != null) {
					lockedApplications.clear();
					lockedApplications = null;
				}
			}
		}, 0, 200);
		return super.onStartCommand(intent, flags, startId);
	}

	public void setPrevious(String previous) {
		previousPackage = previous;
	}

}
