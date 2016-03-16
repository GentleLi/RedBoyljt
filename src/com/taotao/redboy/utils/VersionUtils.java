package com.taotao.redboy.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class VersionUtils {

	private static int versionCode;
	private static String versionName;
	public static int getVersionCode(Context context)
	{
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), 0);
			versionCode = info.versionCode;
			String versionName = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	} 
	public static String getVersionName(Context context)
	{
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo info = packageManager.getPackageInfo(context.getPackageName(), 0);
			versionName = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}
}
