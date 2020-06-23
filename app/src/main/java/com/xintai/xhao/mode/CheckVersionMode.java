package com.xintai.xhao.mode;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.xintai.xhao.F;
import com.xintai.xhao.MyApplication;
import com.xintai.xhao.activity.LoadingAct;
import com.xintai.xhao.bean.AppVersionInfo;
import com.xintai.xhao.dialog.NewDialog;
import com.xintai.xhao.network.RequestApi;
import com.xintai.xhao.utils.MyLog;
import com.xintai.xhao.utils.SystemUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observer;

/**
 * 验证版本信息。如果小于最低版本强制更新；大于最低版本，用户可选择性更新；
 * Created by xionghao on 2018/8/28 0028.
 */
public class CheckVersionMode {
    private LoadingAct loadingAct;
    private String versionName;
    private int odlVerCode;
    private boolean isForcedUpdate;
    private Handler handler = new Handler();

    public CheckVersionMode(LoadingAct loadingAct) {
        this.loadingAct = loadingAct;
        try {
            //            odlVerCode = new SystemUtils(context).getVersionCode();
            versionName = new SystemUtils(MyApplication.getInstance())
                    .getVersionName();
            if(!TextUtils.isEmpty(versionName)){
                F.versionName = versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取版本信息
     *
     * @param versionUrl 请求版本信息的url
     */
    public void getVersionInfo(String versionUrl) {
        RequestApi.getVersionInfo(versionUrl, new Observer<AppVersionInfo>() {
            @Override
            public void onCompleted() {
                //运行onNext后会运行onCompleted
//                MyLog.i("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                //1解析类不对的时候，程序是不会直接蹦掉的，而是走onError，抛出异常，空指针
                MyLog.e("RequestApi.getVersionInfo返回onError:" + e.toString());
            }

            @Override
            public void onNext(AppVersionInfo adverindex) {
                if (adverindex != null && adverindex.getResultCode().equals("1")) {
                    analysisData(adverindex.getResponseObject());//分析数据
                } else {
                    MyLog.e("RequestApi.getVersionInfo返回onNext:获取到数据状态不正确");
                }
            }
        });
    }

    private String downloadurl;
    /**
     * 分析数据:比较版本，判断是否需要更新。
     *
     * @param responseObject
     */
    public void analysisData(AppVersionInfo.ResponseObjectBean responseObject) {
        String minversion = responseObject.getMinversion();
        String maxversion = responseObject.getMaxversion();
        downloadurl = responseObject.getDownloadurl();
        MyLog.i("系统允许最小版本是"+minversion);

        int compareResult = versionName.compareTo(minversion);
        String msg;
        if (compareResult >= 0) {// 当前版本>=最小版本， 不需要强制更新
            isForcedUpdate = false;
            compareResult = versionName.compareTo(maxversion);
            if (compareResult < 0) { // 比最高版本低，选择更新，但非强制更新，
                msg = "发现新版本：" + maxversion + ",是否需要更新？";
                chooseDialog(msg, downloadurl);
            } else { // 大于或等于最新版本，不予处理
                MyLog.i("版本很高，不需要更新");
                loadingAct.goNextActivity();
            }
        } else { // 小于最低版本需要强制更新
            isForcedUpdate = true;
            msg = "当前版本不可用，必须更新至版本：" + maxversion;
            chooseDialog(msg, downloadurl);
        }
    }

    private NewDialog newdialog;
    // apk下载地址
    private String apkUrl = "";

    // 文件存储
    private File updateDir = null;
    private File downLoadApkFile = null;

    public static String downloadDir = "app/download/";
    // 下载状态
    private final  int DOWNLOAD_COMPLETE = 0;
    private final  int DOWNLOAD_FAIL = 1;
    private final  int DOWNLOAD_ING = 2;
    // 下载的百分比
    private int percentage = 0;
    /**
     * 弹出选择对话框，更新或者不更新。
     */
    private void chooseDialog(String message, final String downloadurl) {
//        调用自定义的Dialog
        newdialog = new NewDialog(loadingAct);
        newdialog.setTitle("提示");
        newdialog.setContent(message);
        newdialog.setBackgroundColor(Color.BLACK);// 这里的color指的是Color这个类，而不是R.color.XX
        newdialog.setTitleBackgroundColor(Color.parseColor("#373737"));
        newdialog.setYesText("更新");
        newdialog.setNoText("取消");
        newdialog.setCancelable(false);// 调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        newdialog.show();

        newdialog.setOnYClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // 去后台Service下载最新版本，需要在Manifest注册
//                Intent updateIntent = new Intent(context, UpdateService.class);
//                updateIntent.putExtra("apkUrl", downloadurl);
//                context.startService(updateIntent);
//                newdialog.dismiss();

                //因为是LoadingAct没必要使用Service,onCreate()里面只干了这一件事情，贯穿整个LoadingAct。
                newdialog.setButUnClickable(false);
                newdialog.showProgressbar();
                // 开启一个新的线程下载,其实这里使用AsyncTask比Handler-Thread更好，因为只有一个后台任务，并且需要更新UI。
                new Thread(new updateRunnable(),"upDataApk").start();// 这个是下载的重点，是下载的过程
            }
        });
        newdialog.setOnNClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                newdialog.dismiss();
                if (isForcedUpdate) {// 需要强制更新的时候，点击取消按钮，会退出程序
                    loadingAct.finish();
                } else {
                    loadingAct.goNextActivity();
                }
            }
        });
    }

    //开启线程进行下载最新的Apk
    private class updateRunnable implements Runnable {
        Message message = updateHandler.obtainMessage();

        public void run() {
            //			message.what = DOWNLOAD_COMPLETE;
            // 创建文件
            String downLoadApkPath = F.allCachePath + F.getAppName() +".apk";
            downLoadApkFile = new File(downLoadApkPath);
            MyLog.i("存放下载文件（apk）地址:"+downLoadApkPath);
            try {
                if (downLoadApkFile.exists()) {
                    downLoadApkFile.delete();
                    downLoadApkFile.createNewFile();
                }else{
                    downLoadApkFile.createNewFile();
                }
//                MyLog.i("updateRunnable所在的线程名称："+Thread.currentThread().getName());
                // 下载apk
                downloaddownLoadApkFile(downloadurl, downLoadApkFile);
            } catch (Exception ex) {
                ex.printStackTrace();
                message.what = DOWNLOAD_FAIL;
                // 下载失败
                updateHandler.sendMessage(message);//线程间通讯Handler
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
    public long downloaddownLoadApkFile(String downloadUrl, File saveFile)
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
                int readsize = 0;
                int num = 0;
                while ((readsize = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, readsize);
                    downloadCount += readsize;
                    if (downloadCount * 10 / updateTotalSize > num) {//downloadCount逢十，则num加一
                        num++;
//                        num = downloadCount * 100 / updateTotalSize;//如果想加载动画连贯，那么进度条没走完，就会弹出选择安装的界面。
//                        MyLog.i("进度："+num);
                        Message message = updateHandler.obtainMessage();
                        message.what = DOWNLOAD_ING;
//                        message.arg1 = num;
                        updateHandler.sendMessage(message);
                    }
                }
                Message message = updateHandler.obtainMessage();
                message.what = DOWNLOAD_COMPLETE;
                MyLog.i("downloaddownLoadApkFile所在的线程名称："+Thread.currentThread().getName());
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
//            MyLog.i("Handler所在的线程名称："+Thread.currentThread().getName());//main主线程
            switch (msg.what) {
                case DOWNLOAD_COMPLETE:
                    // 点击安装PendingIntent
                    //				Uri uri = Uri.fromFile(downLoadApkFile);
                    newdialog.dismiss();
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setAction(Intent.ACTION_VIEW);
                    String type = "application/vnd.android.package-archive";
                    intent.setDataAndType(Uri.fromFile(downLoadApkFile), type);// 设置intent的file类型
                    loadingAct.startActivity(intent);// 启动安装程序
                    loadingAct.finish();
                    break;
                case DOWNLOAD_FAIL:
                    // 下载失败
                    Toast.makeText(MyApplication.getInstance(),"下载失败",Toast.LENGTH_SHORT).show();
                    break;
                case DOWNLOAD_ING:
                    percentage += 10;
                    newdialog.setProgress(percentage);
                    break;
                default:
                    break;
            }
        }
    };

    //释放对activity的持有
    public void detachContext(){
        loadingAct = null;
    }

    /**
     * 获取下载地址上的文件名称
     * */
//    private String SubAppName(String appurl) {
//        String[] splitStr = appurl.split("/");
//        String result = splitStr[splitStr.length - 1];
//        MyLog.i(result);
//        return result;
//    }

}
