package com.rgu5android.application.locker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class ActivityPasswordChecker extends Activity {

	StringBuffer mPasswordStringBuffer = null;
	StringBuffer mStoredPasswordString = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_checker);

		mStoredPasswordString = new StringBuffer(""
				+ SharedPrefUtils.getIntValueSharedPref(
						ActivityPasswordChecker.this,
						Constants.SHARED_PREF_PASSWORD_KEY));

		mPasswordStringBuffer = new StringBuffer();

		if (SharedPrefUtils.getIntValueSharedPref(ActivityPasswordChecker.this,
				Constants.SHARED_PREF_PASSWORD_KEY) == 0) {
			Intent intent = new Intent(ActivityPasswordChecker.this,
					ActivityInstructionManual.class);
			startActivity(intent);
			ActivityPasswordChecker.this.finish();
		}
	}

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
		Log.e("mPasswordStringBuffer", mPasswordStringBuffer.toString() + " "
				+ mPasswordStringBuffer.length());
		Log.e("mStoredStringBuffer", mStoredPasswordString.toString() + " "
				+ mStoredPasswordString.length());
		if (mPasswordStringBuffer.length() == 4) {
			if (mPasswordStringBuffer.toString().trim()
					.equalsIgnoreCase(mStoredPasswordString.toString().trim())) {
				Intent intent = new Intent(ActivityPasswordChecker.this,
						ActivityInstructionManual.class);
				startActivity(intent);
				ActivityPasswordChecker.this.finish();
			} else {
				mPasswordStringBuffer = null;
				mPasswordStringBuffer = new StringBuffer();
				Toast.makeText(ActivityPasswordChecker.this,
						"Invalid passphrase, hence resetting.",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(
					ActivityPasswordChecker.this,
					"Passphrase: "
							+ mPasswordStringBuffer.toString()
									.replaceAll("1", " + ")
									.replaceAll("9", " - "), Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// android.os.Process.killProcess(android.os.Process.myPid());
	}
}
