package com.rgu5android.application.locker;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.widget.Toast;

import com.rgu5android.application.locker.ServiceAppLocker.ServiceBinder;

public class ActivityAppLocker extends Activity {
	private String mStoredPasswordString;
	private StringBuffer mPasswordStringBuffer;
	private ServiceAppLocker mServiceAppLocker;
	private boolean isPasswordMatched = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_locker);

		mStoredPasswordString = new String(""
				+ SharedPrefUtils.getIntValueSharedPref(ActivityAppLocker.this,
						Constants.SHARED_PREF_PASSWORD_KEY));

		if (SharedPrefUtils.getIntValueSharedPref(ActivityAppLocker.this,
				Constants.SHARED_PREF_PASSWORD_KEY) == 0) {
			Intent intent = new Intent(ActivityAppLocker.this,
					ActivitySetPassword.class);
			startActivity(intent);
			ActivityAppLocker.this.finish();
		}
		mPasswordStringBuffer = new StringBuffer();
	}

	@Override
	protected void onStart() {
		super.onStart();
		bindService();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (!isPasswordMatched) {
			if (mServiceAppLocker != null) {
				mServiceAppLocker.setPrevious("");
			}
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		unbindService(serviceConnection);
	}

	private void bindService() {
		Intent intent = new Intent(this, ServiceAppLocker.class);
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			ServiceBinder binder = (ServiceBinder) service;
			mServiceAppLocker = binder.getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {

		}
	};

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			mPasswordStringBuffer.append("1");
			checkPassword();
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			mPasswordStringBuffer.append("9");
			checkPassword();
		}
		return true;
	}

	private void checkPassword() {
		if (mPasswordStringBuffer.length() == 4) {
			if (mPasswordStringBuffer.toString().trim()
					.equalsIgnoreCase(mStoredPasswordString.toString().trim())) {
				isPasswordMatched = true;
				ActivityAppLocker.this.finish();
			} else {
				mPasswordStringBuffer = null;
				mPasswordStringBuffer = new StringBuffer();
				Toast.makeText(ActivityAppLocker.this,
						"Invalid passphrase, hence restarting.",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onBackPressed() {
	}
}
