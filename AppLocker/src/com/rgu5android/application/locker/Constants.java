package com.rgu5android.application.locker;

import java.text.DateFormat;
import java.util.Date;

public class Constants {

	public static final String LOGGING_TAG = ":::ApplicationLock:::";
	public static final String SHARED_PREFERENCES_PASSWORD_FILE = "com_rgu5android_shared_preferences_password";
	public static final String SHARED_PREFERENCES_LOCKED_APPLICATION_FILE = "com_rgu5android_shared_preferences_locked_application";

	/**
	 * @return Used this for logging in application as key
	 */
	public static final String getLoggingTag() {
		return (DateFormat.getDateTimeInstance().format(new Date())
				+ LOGGING_TAG + "-:-");
	}

}
