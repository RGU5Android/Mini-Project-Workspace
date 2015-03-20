package com.rgu5android.application.locker.common.database;

import java.util.ArrayList;
import java.util.List;

import com.rgu5android.application.locker.common.Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AppLockerDatabaseController {

	private AppLockerOpenHelper appLockerOpenHelper;
	private SQLiteDatabase sqLiteDatabase;

	public void openDatabase(Context context) throws SQLException, Exception {
		appLockerOpenHelper = AppLockerOpenHelper
				.getAppLockerOpenHelperInstance(context);
		sqLiteDatabase = appLockerOpenHelper.getWritableDatabase();
	}

	public void closeDatabase() throws SQLException, Exception {
		if (sqLiteDatabase != null) {
			sqLiteDatabase = null;
		}
		if (appLockerOpenHelper != null) {
			appLockerOpenHelper = null;
		}
	}

	public void insertPackage(Context context, String packageName)
			throws SQLException, Exception {
		this.openDatabase(context);
		ContentValues contentValues = new ContentValues();
		contentValues.put(Constants.COLUMN_PACKAGE_NAME, packageName);
		sqLiteDatabase.insert(Constants.TABLENAME, null, contentValues);
		this.closeDatabase();
	}

	public void deletePackage(Context context, String packageName)
			throws SQLException, Exception {
		this.openDatabase(context);
		String whereClause = Constants.COLUMN_PACKAGE_NAME + "=?";
		String[] whereArgs = new String[] { packageName };
		sqLiteDatabase.delete(Constants.TABLENAME, whereClause, whereArgs);
		this.closeDatabase();
	}

	public List<String> getLockedPackages(Context context) throws SQLException,
			Exception {
		this.openDatabase(context);
		List<String> lockedApplicationList = null;
		Cursor cursor = sqLiteDatabase.rawQuery("select * from "
				+ Constants.TABLENAME, null);
		if (cursor.moveToFirst()) {
			lockedApplicationList = new ArrayList<String>();
			while (cursor.isAfterLast() == false) {
				lockedApplicationList.add(cursor.getString(1));
				cursor.moveToNext();
			}
		}
		cursor.close();
		this.closeDatabase();
		return lockedApplicationList;
	}

}
