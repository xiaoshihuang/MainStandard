package com.xintai.xhao.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.xintai.xhao.MyApplication;

/**
 * 常见系统资源和功能
 * @author Administrator
 *
 */
public class SystemUtils {

	private Context context;

	public SystemUtils(Context context) {
		this.context = context;
	}

	/**
	 * 获取设备唯一标识符
	 * @return
	 */
	public String getDeviceId() {
		StringBuilder deviceId = new StringBuilder();
		// IMEI（imei）,需要权限android.permission.READ_PHONE_STATE
		TelephonyManager telehponyManager = (TelephonyManager) MyApplication.getInstance().getApplicationContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(MyApplication.getInstance().getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return "";
		}
		String imei = telehponyManager.getDeviceId();//对手机而已，IMEI是唯一的，它类似355653050548491，但是水货手机是IMEI是无效的
		if (!TextUtils.isEmpty(imei)) {
			deviceId.append("imei");
			deviceId.append(imei);
//			return deviceId.toString();
		}
		// WLAN mac地址
		WifiManager wifiManager = (WifiManager) MyApplication.getInstance().getApplicationContext()
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifiManager.getConnectionInfo();
		String wifiMac = info.getMacAddress();//android.permission.ACCESS_WIFI_STATE 权限，否则这个地址会为null
		if (!TextUtils.isEmpty(wifiMac)) {
			deviceId.append("wifi");
			deviceId.append(wifiMac);
//			return deviceId.toString();
		}
		// 蓝牙 mac地址
		BluetoothAdapter bluetoothAdapter = null; // 只在有蓝牙的设备上运行。
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();      
		String blueToothMac = bluetoothAdapter.getAddress();//android.permission.BLUETOOTH权限      
		if (!TextUtils.isEmpty(blueToothMac)) {
			deviceId.append("blueTooth");
			deviceId.append(blueToothMac);
//			return deviceId.toString();
		}
		// 序列号（sn）,装有SIM卡的Android 2.3设备，可以通过下面的方法获取到Sim Serial Number：
		String simNumber = telehponyManager.getSimSerialNumber();
		if (!TextUtils.isEmpty(simNumber)) {
			deviceId.append("sim");
			deviceId.append(simNumber);
//			return deviceId.toString();
		}
//		deviceId =imei355653050548491wifi84:7a:88:6d:9e:9ablueTooth98:0D:2E:98:D4:2A
		//有些返回的是null,有些是无效的，有可能和其他人的相同。但是多个组合在一起，就算某一个相同，也不影响整体的唯一性，最好加密一次
		MyLog.i("设备唯一标识符deviceId ="+deviceId.toString());
		return deviceId.toString();
	}

	/**
	 * 获取版本号
	 * 
	 * @return
	 * @throws NameNotFoundException
	 */
	public int getVersionCode() throws NameNotFoundException {
		String packageName = MyApplication.getInstance().getPackageName();
		int versionCode;
		versionCode = MyApplication.getInstance().getPackageManager()
				.getPackageInfo(packageName, 0).versionCode;
		return versionCode;
	}

	/**
	 * 获取版本名称
	 * 
	 * @return
	 * @throws NameNotFoundException
	 */
	public String getVersionName() throws NameNotFoundException {
		String packageName = MyApplication.getInstance().getPackageName();
		String versionName = MyApplication.getInstance().getPackageManager().getPackageInfo(
				packageName, 0).versionName;
		return versionName;
	}
	
	/**
	 * 获取apk签名对应的sha1，高德地图注册地图的key会需要
	 * 发布的sha1码，瞎几把填，不会影响你测试的使用，但是你打包apk后对高德地图的调用是无效的
	 * @param context
	 * @return
	 */
	public static String sHA1(Context context) {
	    try {
	        PackageInfo info = context.getPackageManager().getPackageInfo(
	            context.getPackageName(), PackageManager.GET_SIGNATURES);
	        byte[] cert = info.signatures[0].toByteArray();
	        MessageDigest md = MessageDigest.getInstance("SHA1");
	        byte[] publicKey = md.digest(cert);
	        StringBuffer hexString = new StringBuffer();
	        for (int i = 0; i < publicKey.length; i++) {
	            String appendString = Integer.toHexString(0xFF & publicKey[i])
	                .toUpperCase(Locale.US);
	            if (appendString.length() == 1)
	                hexString.append("0");
	                hexString.append(appendString);
	                hexString.append(":");
	        }
	        String result = hexString.toString();
	        return result.substring(0, result.length()-1);
	    } catch (NameNotFoundException e) {
	        e.printStackTrace();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
