package com.rgu5android.application.locker;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.SwipeListView;

public class ListViewManageApplicationAdapter extends BaseAdapter {

	private List<POJOApplicationInfo> data;
	private Context context;

	public ListViewManageApplicationAdapter(Context context,
			List<POJOApplicationInfo> data) {
		this.data = data;
		this.context = context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final POJOApplicationInfo applicationInfoClass = (POJOApplicationInfo) getItem(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutInflater li = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(
					R.layout.list_view_cell_manage_application, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.cellImageView = (ImageView) convertView
					.findViewById(R.id.example_row_iv_image);
			viewHolder.cellTextView = (TextView) convertView
					.findViewById(R.id.example_row_tv_title);
			viewHolder.cellLockAppButton = (Button) convertView
					.findViewById(R.id.example_row_b_action_1);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		((SwipeListView) parent).recycle(convertView, position);

		viewHolder.cellImageView.setImageDrawable(applicationInfoClass
				.getApplicationIcon());
		viewHolder.cellTextView.setText(applicationInfoClass
				.getApplicationName());

		if (applicationInfoClass.getApplicationLocked()) {
			viewHolder.cellLockAppButton.setText("Unlock Application");
		}

		viewHolder.cellLockAppButton
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						applicationInfoClass
								.setApplicationLocked(!applicationInfoClass
										.getApplicationLocked());
						SharedPrefUtils.setBooleanValueSharedPref(context,
								applicationInfoClass.getApplicationPackage(),
								(applicationInfoClass.getApplicationLocked()));

						if (applicationInfoClass.getApplicationLocked()) {
							Toast.makeText(
									context,
									applicationInfoClass.getApplicationName()
											+ " has been locked.",
									Toast.LENGTH_LONG).show();
						} else {
							SharedPrefUtils.removeKey(context,
									applicationInfoClass
											.getApplicationPackage());
							Toast.makeText(
									context,
									applicationInfoClass.getApplicationName()
											+ " has been unlocked.",
									Toast.LENGTH_LONG).show();
						}

					}
				});

		return convertView;
	}

	static class ViewHolder {
		ImageView cellImageView;
		TextView cellTextView;
		Button cellLockAppButton;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
