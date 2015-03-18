package com.rgu5android.application.locker;

import android.graphics.drawable.Drawable;

public class POJOApplicationInfo implements Comparable<POJOApplicationInfo> {

	String applicationName;
	String applicationPackage;
	Drawable applicationIcon;
	Boolean applicationLocked;

	public POJOApplicationInfo() {
		this.applicationIcon = null;
		this.applicationName = "";
		this.applicationPackage = "";
		this.applicationLocked = false;
	}

	public POJOApplicationInfo(String applicationName,
			String applicationPackage, Drawable applicationIcon,
			Boolean applicationLocked) {
		super();
		this.applicationName = applicationName;
		this.applicationPackage = applicationPackage;
		this.applicationIcon = applicationIcon;
		this.applicationLocked = applicationLocked;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationPackage() {
		return applicationPackage;
	}

	public void setApplicationPackage(String applicationPackage) {
		this.applicationPackage = applicationPackage;
	}

	public Drawable getApplicationIcon() {
		return applicationIcon;
	}

	public void setApplicationIcon(Drawable applicationIcon) {
		this.applicationIcon = applicationIcon;
	}

	public Boolean getApplicationLocked() {
		return applicationLocked;
	}

	public void setApplicationLocked(Boolean applicationLocked) {
		this.applicationLocked = applicationLocked;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((applicationPackage == null) ? 0 : applicationPackage
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		POJOApplicationInfo other = (POJOApplicationInfo) obj;
		if (applicationPackage == null) {
			if (other.applicationPackage != null)
				return false;
		} else if (!applicationPackage.equals(other.applicationPackage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApplicationInfoClass [applicationName=");
		builder.append(applicationName);
		builder.append(", applicationPackage=");
		builder.append(applicationPackage);
		builder.append(", applicationLocked=");
		builder.append(applicationLocked);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(POJOApplicationInfo another) {
		return this.applicationName
				.compareToIgnoreCase(another.applicationName);
	}

}
