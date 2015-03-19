package com.rgu5android.application.locker.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.rgu5android.application.locker.activity.ActivityAppLocker;
import com.rgu5android.application.locker.common.sharedpref.SharedPrefUtils;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.IBinder;

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

				Set<String> lockedApplications = new HashSet<String>();

				PackageManager manager = getPackageManager();
				Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
				mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

				List<ResolveInfo> resolveInfos = manager.queryIntentActivities(
						mainIntent, 0);
				Collections.sort(resolveInfos,
						new ResolveInfo.DisplayNameComparator(manager));

				for (ResolveInfo info : resolveInfos) {
					ApplicationInfo applicationInfo = info.activityInfo.applicationInfo;
					if (SharedPrefUtils.getBooleanValueSharedPref(
							getApplicationContext(),
							applicationInfo.packageName)) {
						lockedApplications.add(applicationInfo.packageName);
					}
				}

				ActivityManager activityManager = (ActivityManager) ServiceAppLocker.this
						.getSystemService(Context.ACTIVITY_SERVICE);

				currentPackage = activityManager.getRunningAppProcesses()
						.get(0).processName;

				if (lockedApplications.contains(currentPackage)
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
				lockedApplications.clear();
				lockedApplications = null;
			}
		}, 0, 1000);
		return super.onStartCommand(intent, flags, startId);
	}

	public void setPrevious(String previous) {
		previousPackage = previous;
	}

}
