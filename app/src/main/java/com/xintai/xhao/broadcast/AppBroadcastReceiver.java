package com.xintai.xhao.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.xintai.xhao.MyApplication;
import com.xintai.xhao.utils.MyLog;

/**
 * Created by xionghao on 2019/2/11 0011.
 * <p/>注册在MyApplication里面，对网络的监听
 */
public class AppBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String mAction = intent.getAction();
        if("android.net.conn.CONNECTIVITY_CHANGE".equals(mAction)){//当网络状态发生改变
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo == null) {//wifi和流量都关闭的情况,会走这里
                Toast.makeText(context, "网络连接断开！", Toast.LENGTH_LONG).show();
            } else {
                Boolean isAvailable = mNetworkInfo.isAvailable();
                if (isAvailable) {//如果网络可用，可以进一步判断网络的类型
                    String mTypeName = mNetworkInfo.getTypeName();
                    MyLog.i( "网络类型:" + mTypeName);
                    if ("WIFI".equals(mTypeName)) {//如果是wifi可用添加大图和播放视频，音频

                    }else{//使用流量

                    }
                } else {
                    Toast.makeText(context, "网络不可用！", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
