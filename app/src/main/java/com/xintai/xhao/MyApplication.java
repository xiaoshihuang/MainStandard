package com.xintai.xhao;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.xintai.xhao.broadcast.AppBroadcastReceiver;
import com.xintai.xhao.service.MyIntentService;
import com.xintai.xhao.utils.MyLog;

/**
 *
 */
public class MyApplication extends Application {

    //getApplication()只能用于Activity和Service里面，
    //如果是一些Util里面呢。所以这里是有必要的。
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        this.myApplication = this;

        //动态注册一个监听网络状态的广播,因为静态注册监听网络状态改变的广播，在7.0以上是无效的
//        IntentFilter mIntentFilter = new IntentFilter();
//        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        AppBroadcastReceiver mBroadcastReceiver  = new AppBroadcastReceiver();
//        this.registerReceiver(mBroadcastReceiver,mIntentFilter);

        //可以在后台完成地图定位，推送，分享，图片显示等第三方sdk的初始化
        Intent intent1 = new Intent(this, MyIntentService.class);
        intent1.putExtra("action","init");
        this.startService(intent1);
    }

    public static MyApplication getInstance() {
        return myApplication;
    }


}

