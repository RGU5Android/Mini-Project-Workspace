package com.rgu5android.application.locker.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rgu5android.application.locker.R;

public class ActivityInstructionManual extends ActivityBase {

	ListView instructionManualListView;
	Button buttonManageApplication;
	Button buttonResetPassword;
	SharedPreferences sharedPreferencesPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_readme_manual);

		instructionManualListView = (ListView) findViewById(R.id.list_view_instruction);
		instructionManualListView.setClickable(false);

		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> instruction = new HashMap<String, String>(2);

		instruction.put("header", "Lock Applications");
		instruction.put("content",
				"\tSwipe the application from unlocked list to right.");
		data.add(instruction);

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2, new String[] { "header",
						"content" }, new int[] { android.R.id.text1,
						android.R.id.text2 });

		instructionManualListView.setAdapter(adapter);

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
