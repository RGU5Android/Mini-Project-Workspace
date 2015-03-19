package com.rgu5android.application.locker.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.rgu5android.application.locker.R;
import com.rgu5android.application.locker.common.ListViewManageApplicationAdapter;
import com.rgu5android.application.locker.common.POJOApplicationInfo;
import com.rgu5android.application.locker.common.sharedpref.SharedPrefUtils;

public class ActivityLockedApplication extends ActivityBase {

	ProgressDialog mProgressDialog;
	List<POJOApplicationInfo> mApplicationInfoClasses;
	SwipeListView mSwipelistview;
	ListViewManageApplicationAdapter mListViewCellAdaptor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locked_application);

		mSwipelistview = (SwipeListView) findViewById(R.id.example_swipe_lv_list_locked);
		mApplicationInfoClasses = new ArrayList<POJOApplicationInfo>();
		mListViewCellAdaptor = new ListViewManageApplicationAdapter(this,
				mApplicationInfoClasses);

		mSwipelistview
				.setSwipeListViewListener(new BaseSwipeListViewListener() {
					@Override
					public void onOpened(int position, boolean toRight) {
						Log.d(":::SwipeList:::", "onOpened -> " + position);
					}

					@Override
					public void onClosed(int position, boolean fromRight) {
						Log.d(":::SwipeList:::", "onClosed -> " + position);
					}

					@Override
					public void onListChanged() {
						Log.d(":::SwipeList:::", "onListChanged");
					}

					@Override
					public void onMove(int position, float x) {
						Log.d(":::SwipeList:::", "onMove -> " + position);
					}

					@Override
					public void onStartOpen(int position, int action,
							boolean right) {
						Log.d(":::SwipeList:::", "onStartOpen -> " + position);
					}

					@Override
					public void onStartClose(int position, boolean right) {
						Log.d(":::SwipeList:::", "onStartClose -> " + position);
					}

					@Override
					public void onClickFrontView(int position) {
						Log.d(":::SwipeList:::", "onClickFrontView-> "
								+ position);
						mSwipelistview.closeOpenedItems();
						mSwipelistview.openAnimate(position);
					}

					@Override
					public void onClickBackView(int position) {
						mSwipelistview.closeAnimate(position);
						Log.d(":::SwipeList:::", "onClickBackView -> "
								+ position);
					}

					@Override
					public void onDismiss(int[] reverseSortedPositions) {
						Log.d(":::SwipeList:::", "onDismiss-> ");
					}
				});

		mSwipelistview.setSwipeMode(SwipeListView.SWIPE_ACTION_NONE);
		mSwipelistview.setOffsetLeft(convertDpToPixel(0f));
		mSwipelistview.setOffsetRight(convertDpToPixel(80f));
		mSwipelistview.setAnimationTime(1000);

		mSwipelistview.setAdapter(mListViewCellAdaptor);

		mProgressDialog = new ProgressDialog(ActivityLockedApplication.this);
		mProgressDialog.setTitle("Please Wait..");
		mProgressDialog.setMessage("Fetching List Of Locked Applications...");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		new ApplicationListAsyncTask().execute();
	}

	public int convertDpToPixel(float dp) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_locked_application, menu);
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
		if (id == R.id.action_instruction_manual) {
			Intent intent = new Intent(this, ActivityInstructionManual.class);
			startActivity(intent);
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class ApplicationListAsyncTask extends
			AsyncTask<Void, Void, List<POJOApplicationInfo>> {

		@Override
		protected List<POJOApplicationInfo> doInBackground(Void... params) {
			List<POJOApplicationInfo> data = new ArrayList<POJOApplicationInfo>();
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
						ActivityLockedApplication.this,
						applicationInfo.packageName)) {
					data.add(new POJOApplicationInfo(getPackageManager()
							.getApplicationLabel(applicationInfo).toString(),
							applicationInfo.packageName, getPackageManager()
									.getDrawable(applicationInfo.packageName,
											applicationInfo.icon,
											applicationInfo), true));
				}
			}
			Collections.sort(data);
			return data;
		}

		@Override
		protected void onPostExecute(List<POJOApplicationInfo> result) {
			mApplicationInfoClasses.clear();
			mApplicationInfoClasses.addAll(result);
			mListViewCellAdaptor.notifyDataSetChanged();
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, ActivityInstructionManual.class);
		startActivity(intent);
		this.finish();
	}
}
