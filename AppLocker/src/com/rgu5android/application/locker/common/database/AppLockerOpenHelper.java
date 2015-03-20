package com.rgu5android.application.locker.common.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rgu5android.application.locker.common.Constants;

class AppLockerOpenHelper extends SQLiteOpenHelper {

	private static AppLockerOpenHelper mAppLockerOpenHelper;

	protected static synchronized AppLockerOpenHelper getAppLockerOpenHelperInstance(
			Context context) {
		if (mAppLockerOpenHelper == null) {
			mAppLockerOpenHelper = new AppLockerOpenHelper(context);
		}
		return mAppLockerOpenHelper;
	}

	private AppLockerOpenHelper(Context context) {
		super(context, Constants.DATABASENAME, null, Constants.DATABASEVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		String createTableQuery = "CREATE TABLE " + Constants.TABLENAME + " ( "
				+ Constants.COLUMN_ID + " "
				+ "INTEGER PRIMARY KEY AUTOINCREMENT , "
				+ Constants.COLUMN_PACKAGE_NAME + " "
				+ "VARCHAR(30) UNIQUE ) ;";
		sqLiteDatabase.execSQL(createTableQuery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion,
			int newVersion) {

	}

}
