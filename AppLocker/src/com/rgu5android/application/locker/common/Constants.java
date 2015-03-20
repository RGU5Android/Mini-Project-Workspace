package com.rgu5android.application.locker.common;

import java.text.DateFormat;
import java.util.Date;

public class Constants {

	public static final String LOGGING_TAG = ":::ApplicationLock:::";

	public static final String SHARED_PREF_PASSWORD_KEY = "lock_password_key";
	public static final String DATABASENAME = "AppLocker";
	public static final String TABLENAME = "locked_application";
	public static final int DATABASEVERSION = 1;

	public static final String COLUMN_ID = "id";
	public static final String COLUMN_PACKAGE_NAME = "package_name";

	/**
	 * @return Used this for logging in application as key
	 */
	public static final String getLoggingTag() {
		return (DateFormat.getDateTimeInstance().format(new Date())
				+ LOGGING_TAG + "-:-");
	}

}
