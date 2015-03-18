package com.rgu5android.application.locker;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtils {

	public static final boolean getBooleanValueSharedPref(Context context,
			String key) {
		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		return preferences.getBoolean(key, false);
	}

	public static final void setBooleanValueSharedPref(Context context,
			String key, boolean value) {
		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		preferences.edit().putBoolean(key, value).commit();
	}

	public static final int getIntValueSharedPref(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		return preferences.getInt(key, 0);
	}

	public static final void setIntValueSharedPref(Context context, String key,
			int value) {
		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		preferences.edit().putInt(key, value).commit();
	}

	public static final void removeKey(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(
				context.getPackageName(), Context.MODE_PRIVATE);
		preferences.edit().remove(key).commit();
	}

}
