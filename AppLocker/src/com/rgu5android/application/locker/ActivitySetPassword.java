package com.rgu5android.application.locker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ActivitySetPassword extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_reset_password);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
	public void onBackPressed() {
	}

}
