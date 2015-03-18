package com.rgu5android.application.locker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;

public class ActivityManageApplication extends Activity {

	SwipeListView mSwipelistview;
	ListViewManageApplicationAdapter mListViewCellAdaptor;
	List<POJOApplicationInfo> mApplicationInfoClasses;
	HashMap<String, Boolean> mLockedApplicationList;
	ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_application);

		mSwipelistview = (SwipeListView) findViewById(R.id.example_swipe_lv_list);
		mApplicationInfoClasses = new ArrayList<POJOApplicationInfo>();
		mLockedApplicationList = new HashMap<String, Boolean>();
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

		mSwipelistview.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT);
		mSwipelistview.setOffsetLeft(convertDpToPixel(0f));
		mSwipelistview.setOffsetRight(convertDpToPixel(80f));
		mSwipelistview.setAnimationTime(1000);

		mSwipelistview.setAdapter(mListViewCellAdaptor);

		mProgressDialog = new ProgressDialog(ActivityManageApplication.this);
		mProgressDialog.setTitle("Please Wait..");
		mProgressDialog.setMessage("Fetching List Of Applications...");
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
		getMenuInflater().inflate(R.menu.activity_readme_manual, menu);
		return true;
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
				String packageName = applicationInfo.packageName;

				Boolean appLocked = SharedPrefUtils.getBooleanValueSharedPref(
						ActivityManageApplication.this, packageName);

				String appName = getPackageManager().getApplicationLabel(
						applicationInfo).toString();

				Drawable appIcon = getPackageManager().getDrawable(
						applicationInfo.packageName, applicationInfo.icon,
						applicationInfo);

				data.add(new POJOApplicationInfo(appName, packageName, appIcon,
						appLocked));
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

}
