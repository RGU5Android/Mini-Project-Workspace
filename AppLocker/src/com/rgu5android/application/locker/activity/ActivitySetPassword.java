package com.rgu5android.application.locker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rgu5android.application.locker.R;
import com.rgu5android.application.locker.common.Constants;
import com.rgu5android.application.locker.common.sharedpref.SharedPrefUtils;

public class ActivitySetPassword extends ActivityBase {

	Button mSetPasswordButton;
	Button mSaveButton;
	Button mCancelButton;
	TextView mPasswordLabel;
	StringBuffer mPasswordStringBuffer;
	boolean mReadPassword = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_password);

		mPasswordStringBuffer = new StringBuffer("");
		mPasswordLabel = (TextView) findViewById(R.id.label_password);

		mSetPasswordButton = (Button) findViewById(R.id.button_set_password);
		mSetPasswordButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mSetPasswordButton.setEnabled(false);
				mReadPassword = true;
			}
		});

		mSaveButton = (Button) findViewById(R.id.button_save);
		mSaveButton.setText(getResources().getString(R.string.reset));
		mSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mPasswordStringBuffer.length() == 4) {
					SharedPrefUtils.setIntValueSharedPref(
							ActivitySetPassword.this,
							Constants.SHARED_PREF_PASSWORD_KEY, Integer
									.parseInt(mPasswordStringBuffer.toString()
											.trim()));
					Toast.makeText(ActivitySetPassword.this,
							"Password has been updated.", Toast.LENGTH_LONG)
							.show();
				} else {
					mPasswordStringBuffer = null;
					mPasswordStringBuffer = new StringBuffer();
					mSetPasswordButton.setEnabled(true);
					mReadPassword = false;
					mPasswordLabel.setText(mPasswordStringBuffer.toString());
				}
			}
		});

		mCancelButton = (Button) findViewById(R.id.button_cancel);
		mCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ActivitySetPassword.this,
						ActivityInstructionManual.class);
				startActivity(intent);
				ActivitySetPassword.this.finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_set_password, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_manage_apps) {
			Intent intent = new Intent(this, ActivityManageApplication.class);
			startActivity(intent);
			this.finish();
			return true;
		}
		if (id == R.id.action_about_us) {
			Intent intent = new Intent(this, ActivityAboutUs.class);
			startActivity(intent);
			this.finish();
			return true;
		}
		if (id == R.id.action_locked_apps) {
			Intent intent = new Intent(this, ActivityLockedApplication.class);
			startActivity(intent);
			this.finish();
			return true;
		}
		if (id == R.id.action_instruction_manual) {
			Intent intent = new Intent(this, ActivityInstructionManual.class);
			startActivity(intent);
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (mReadPassword) {
			if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
				mPasswordStringBuffer.append("1");
				checkPassword();
			}
			return true;
		} else {
			return super.onKeyUp(keyCode, event);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mReadPassword) {
			if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
				mPasswordStringBuffer.append("9");
				checkPassword();
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void onBackPressed() {
	}

	private void checkPassword() {
		if (mPasswordStringBuffer.length() > 0) {
			mPasswordLabel.setText(mPasswordStringBuffer.toString()
					.replace("1", " + ").replace("9", " - "));
		}
		Log.d("mPasswordStringBuffer", mPasswordStringBuffer.toString() + " "
				+ mPasswordStringBuffer.length());
		if (mPasswordStringBuffer.length() == 4) {
			mReadPassword = false;
			mSaveButton.setText(getResources().getString(R.string.save));
		}
	}
}
