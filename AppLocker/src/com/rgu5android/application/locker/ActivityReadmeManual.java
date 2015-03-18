package com.rgu5android.application.locker;

import android.app.Activity;
import android.content.Context;
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

public class ActivityReadmeManual extends Activity {

	ListView instructionManualListView;
	Button buttonManageApplication;
	Button buttonResetPassword;
	SharedPreferences sharedPreferencesPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_readme_manual);

		sharedPreferencesPassword = this.getSharedPreferences(
				Constants.SHARED_PREFERENCES_PASSWORD_FILE,
				Context.MODE_PRIVATE);

		instructionManualListView = (ListView) findViewById(R.id.list_view_instruction);
		instructionManualListView.setClickable(false);

		String[] instructionsStrings = new String[] { "Android List View",
				"Adapter implementation", "Simple List View In Android",
				"Create List View Android", "Android Example",
				"List View Source Code", "List View Array Adapter",
				"Android List View", "Adapter implementation",
				"Simple List View In Android", "Create List View Android",
				"Android Example", "List View Source Code",
				"List View Array Adapter", "Android Example List View",
				"Android Example List View" };

		ArrayAdapter<String> instructionArrayAdapter = new ArrayAdapter<String>(
				ActivityReadmeManual.this, android.R.layout.simple_list_item_1,
				instructionsStrings);

		instructionManualListView.setAdapter(instructionArrayAdapter);

		buttonManageApplication = (Button) findViewById(R.id.btn_manage_apps);
		buttonManageApplication.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityReadmeManual.this,
						ActivityManageApplication.class);
				startActivity(intent);
				ActivityReadmeManual.this.finish();
			}
		});

		buttonResetPassword = (Button) findViewById(R.id.btn_setup_password);
		buttonResetPassword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ActivityReadmeManual.this,
						ActivityResetPassword.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_readme_manual, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_manage_apps) {
			Intent intent = new Intent(ActivityReadmeManual.this,
					ActivityManageApplication.class);
			startActivity(intent);
			ActivityReadmeManual.this.finish();
			return true;
		}
		if (id == R.id.action_reset_password) {
			Intent intent = new Intent(ActivityReadmeManual.this,
					ActivityResetPassword.class);
			startActivity(intent);
			return true;
		}
		if (id == R.id.action_about_us) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
