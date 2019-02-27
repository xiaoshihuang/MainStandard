package com.xintai.xhao.utils;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.xintai.xhao.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by xionghao on 2018/6/25 0025.
 */

public class NetWorkUtils {
    // 更详细的可见 https://www.cnblogs.com/fnlingnzb-learner/p/7531811.html

    private boolean isNetWorkSeted =true;

    /**
     * 判断网络是否可用；
     * 注意：如果连接一个没有连接外网的局域网，目前Android SDK还不能识别这种情况，一般的解决办法就是ping一个外网；
     * @param context Context对象
     */
    public static Boolean isNetworkConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo == null) {
            //wifi和流量都关闭的情况,会走这里
                        Log.i("xhao","网络信息:NetworkInfo==null");
//            Toast.makeText(context,"网络连接没有开启，请去设置",Toast.LENGTH_LONG).show();
            setNetwork(context);//如果一个页面有多个请求，岂不是要弹出多个提示框
            return false;
        } else {
            Boolean isAvailable = mNetworkInfo.isAvailable();
            if (isAvailable) {
                //网络可用，可以进一步判断网络的类型，比如在wifi的时候软件更新下载，视频播放等
                Log.i("xhao", "网络类型:" + mNetworkInfo.getTypeName());//可以判断是wifi还是流量上网的。
                if (mNetworkInfo.getTypeName().equals("WIFI")) {

                }
            } else {

            }
            return isAvailable;
        }
    }

    /**
     * 判断WIFI网络是否可用，zai软件更新下载，视频播放等比较耗流量的操作前需要判断wifi是否可用
     *
     * @param context
     * @return
     */
    public boolean isWifiConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);//判断是否为流量使用ConnectivityManager.TYPE_MOBILE
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isAvailable();
        } else {
            return false;
        }
    }

    /**
     * 网络未连接时，弹出对话框，询问是否需要跳转到设置页面
     */
    private static void setNetwork(final Context context) {
        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "网络未连接！", Toast.LENGTH_LONG);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("提示");
        builder.setMessage("网络不可用，去设置网络！");

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                /**
                 * 判断手机系统的版本！如果API大于10 就是3.0+ 因为3.0以上的版本的设置和3.0以下的设置不一样，调用的方法不同
                 */
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                    //                    intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName(
                            "com.android.settings",
                            "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                context.startActivity(intent);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }

    /* @author suncat
    * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
    * @return
    */
    public static final boolean ping() {

        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            Log.d("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            Log.d("----result---", "result = " + result);
        }
        return false;
    }

}
