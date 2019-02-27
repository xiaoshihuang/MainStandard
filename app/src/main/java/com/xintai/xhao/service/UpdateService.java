package com.xintai.xhao.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.xintai.xhao.MainActivity;
import com.xintai.xhao.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

/**
 * 在后台下载最新版本，注意需要注册
 * 1.Service运行在后台处理一些耗时的操作，为了避免阻塞主线程，需要开启一个线程。
 * 2.Service有2种启动方式，startService()和bindService()，bindService()是绑定调用者的，如果调用者退出Service也会退出。
 * 3.startService(),Service生命周期-onCreate()-onStartCommand()，如果Service存活期间，再次调用StartService，只会运行onSartCommand();
 * stopService(),Service生命周期onDestroy();
 * 4.bindService(),Service生命周期-onCreate() -->onBind()--> 调用者结束时，服务会自动运行onUnbind-->onDestroy来退出。
 * unbindService()-->onUnbind-->onDestroy;
 * 5.如果多个Activity绑定bindService同一个Service,当其中一个Activity销毁或者解绑，Service依旧会存在，
 * 只有当所有绑定的Activity都结束的时候，Service才会销毁。
 * @author Administrator
 * 
 */
public class UpdateService extends Service {
	// apk下载地址
	private String apkUrl = "";

	// 文件存储
	private File updateDir = null;
	private File updateFile = null;

	public static String downloadDir = "app/download/";
	// 下载状态
	private final static int DOWNLOAD_COMPLETE = 0;
	private final static int DOWNLOAD_FAIL = 1;
	private final static int DOWNLOAD = 2;
	// 下载的百分比
	private int percentage = 0;
	// 通知栏
	private NotificationManager updateNotificationManager = null;
	private Notification updateNotification = null;
	// 通知栏跳转Intent
	private Intent updateIntent = null;
	private PendingIntent updatePendingIntent = null;
	private RemoteViews remoteViews = null;
	private MyBinder mBinder = new MyBinder();
	private UpdateService updateService;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	public class MyBinder extends Binder {//内部类持有对外部类的引用
		UpdateService getService() {
			return updateService;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		updateService = this;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		// 获取下载Apk地址
				apkUrl = intent.getStringExtra("apkUrl");
				// 创建文件
				if (Environment.MEDIA_MOUNTED.equals(Environment
						.getExternalStorageState())) {// 如果存在sd卡，并且状态可用,mounted：安装好的
					updateDir = new File(Environment.getExternalStorageDirectory(),// storage/sdcard0
							downloadDir);
					updateFile = new File(updateDir.getPath(), SubAppName(apkUrl));
				}

				updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				// this.updateNotification = new Notification();
				updateNotification = new Notification(R.mipmap.ic_launcher, "下载更新",
						System.currentTimeMillis());
				
				// 设置下载过程中，点击通知栏，回到主界面
				updateIntent = new Intent(this, MainActivity.class);
				// 设置通知栏显示内容
				updatePendingIntent = PendingIntent.getActivity(this, 0, updateIntent,0);
				remoteViews = new RemoteViews(getPackageName(), R.layout.jindu);
				remoteViews.setTextViewText(R.id.notificationTitle, "应用下载");
				remoteViews.setTextViewText(R.id.notificationPercent, percentage + "%");
				remoteViews.setProgressBar(R.id.notificationProgress, 100, percentage,false);//设置进度条

				//将RemoteViews和PendingIntent赋值给Notification;
				updateNotification.contentView = remoteViews;
				updateNotification.contentIntent = updatePendingIntent;
				// 更新通知(Notification)界面
				updateNotificationManager.notify(0, updateNotification);

				// 开启一个新的线程下载，如果直接在Service所在线程下载，会导致ANR问题，Service本身也会阻塞
				new Thread(new updateRunnable()).start();// 这个是下载的重点，是下载的过程
				
		return super.onStartCommand(intent, flags, startId);
	}

	private class updateRunnable implements Runnable {
		Message message = updateHandler.obtainMessage();

		public void run() {
//			message.what = DOWNLOAD_COMPLETE;
			try {
				if (!updateDir.exists()) {
					updateDir.mkdirs();
				}
				if (!updateFile.exists()) {
					updateFile.createNewFile();
				}
				// 下载apk
				downloadUpdateFile(apkUrl, updateFile);
			} catch (Exception ex) {
				ex.printStackTrace();
				message.what = DOWNLOAD_FAIL;
				// 下载失败
				updateHandler.sendMessage(message);
			}
		}
	}
	
	/**
	 * 下载apk
	 * @param downloadUrl  下载apk的url
	 * @param saveFile apk存放目录
	 * @return
	 * @throws Exception
	 */
	public long downloadUpdateFile(String downloadUrl, File saveFile)
			throws Exception {
		
		int downloadCount = 0;//已经下载的长度
		int currentSize = 0;
		long totalSize = 0;
		int updateTotalSize = 0;//apk总长

		HttpURLConnection httpConnection = null;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			URL url = new URL(downloadUrl);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection
					.setRequestProperty("User-Agent", "PacificHttpClient");
			if (currentSize > 0) {
				httpConnection.setRequestProperty("RANGE", "bytes="
						+ currentSize + "-");
			}
			httpConnection.setConnectTimeout(10000);
			httpConnection.setReadTimeout(20000);
			updateTotalSize = httpConnection.getContentLength();
			if (httpConnection.getResponseCode() == 200) {
				is = httpConnection.getInputStream();
				fos = new FileOutputStream(saveFile, false);
				byte buffer[] = new byte[2048];
				// if(updateTotalSize%1000<0){
				// buffer = new byte[1000];
				// }else{
				// buffer = new byte[updateTotalSize/1000];
				// }
				int readsize = 0;
				int num = 0;
				while ((readsize = is.read(buffer)) > 0) {
					fos.write(buffer, 0, readsize);
					downloadCount += readsize;
					if (downloadCount * 10 / updateTotalSize > num) {
						num = num + 1;
						Message message = updateHandler.obtainMessage();
						message.what = DOWNLOAD;
						updateHandler.sendMessage(message);
					}
				}
				Message message = updateHandler.obtainMessage();
				message.what = DOWNLOAD_COMPLETE;
				updateHandler.sendMessage(message);
			} else {
				throw new Exception("fail!");
			}
		} finally {
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
			if (is != null) {
				is.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
		return totalSize;
	}
	
	private Handler updateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD_COMPLETE:
				// 点击安装PendingIntent
//				Uri uri = Uri.fromFile(updateFile);
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setAction(Intent.ACTION_VIEW);
				String type = "application/vnd.android.package-archive";
				intent.setDataAndType(Uri.fromFile(updateFile), type);// 设置intent的file类型
				startActivity(intent);// 启动安装程序
				updateNotificationManager.cancel(0);
				stopSelf();
				break;
			case DOWNLOAD_FAIL:
				// 下载失败
				stopSelf();
				break;
			case DOWNLOAD:
				percentage += 10;
				remoteViews.setTextViewText(R.id.notificationPercent,
						percentage + "%");
				remoteViews.setProgressBar(R.id.notificationProgress, 100,
						percentage, false);
				updateNotificationManager.notify(0, updateNotification);
				break;
			default:
				break;
			}
		}
	};


	/**
	 * 获取下载地址上的文件名称
	 * */
	private String SubAppName(String appurl) {
		String[] splitStr = appurl.split("/");
		String result = splitStr[splitStr.length - 1];
		return result;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
