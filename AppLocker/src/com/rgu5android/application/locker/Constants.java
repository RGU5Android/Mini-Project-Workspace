package com.rgu5android.application.locker;

import java.text.DateFormat;
import java.util.Date;

public class Constants {

	public static final String LOGGING_TAG = ":::ApplicationLock:::";

	public static final String SHARED_PREF_PASSWORD_KEY = "lock_password_key";

	/**
	 * @return Used this for logging in application as key
	 */
	public static final String getLoggingTag() {
		return (DateFormat.getDateTimeInstance().format(new Date())
				+ LOGGING_TAG + "-:-");
	}

}
