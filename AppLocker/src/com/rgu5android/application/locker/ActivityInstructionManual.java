package com.rgu5android.application.locker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ActivityInstructionManual extends Activity {

	ListView instructionManualListView;
	Button buttonManageApplication;
	Button buttonResetPassword;
	SharedPreferences sharedPreferencesPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_readme_manual);

		/**
		 * Unit testing code.
		 */

		SharedPrefUtils.setIntValueSharedPref(this,
				Constants.SHARED_PREF_PASSWORD_KEY, 1919);

		/**
		 * Unit testing code.
		 */

		instructionManualListView = (ListView) findViewById(R.id.list_view_instruction);
		instructionManualListView.setClickable(false);

		ArrayAdapter<String> instructionArrayAdapter = new ArrayAdapter<String>(
				ActivityInstructionManual.this,
				android.R.layout.simple_list_item_1, getResources()
						.getStringArray(R.array.instruction_strings));

		instructionManualListView.setAdapter(instructionArrayAdapter);

		buttonManageApplication = (Button) findViewById(R.id.btn_manage_apps);
		buttonManageApplication.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityInstructionManual.this,
						ActivityManageApplication.class);
				startActivity(intent);
				ActivityInstructionManual.this.finish();
			}
		});

		buttonResetPassword = (Button) findViewById(R.id.btn_setup_password);
		buttonResetPassword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityInstructionManual.this,
						ActivitySetPassword.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_instruction_manual, menu);
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
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
	}
}
