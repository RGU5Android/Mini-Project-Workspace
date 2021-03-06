package com.rgu5android.application.locker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.rgu5android.application.locker.R;

public class ActivityAboutUs extends ActivityBase {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_about_us, menu);
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
		if (id == R.id.action_set_password) {
			Intent intent = new Intent(this, ActivitySetPassword.class);
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
	public void onBackPressed() {
	}

}