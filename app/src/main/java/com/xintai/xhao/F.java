package com.xintai.xhao;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.xintai.xhao.configuration.GlideConfig;
import com.xintai.xhao.network.OkHttp3Utils;
import com.xintai.xhao.utils.FileUtils;
import com.xintai.xhao.utils.MyLog;

import java.io.File;

/**
 * 2549242548
 * 放在这里的方法或者常量，
 * 要么是经常引用的，要么是很关键的。
 */

public class F {
    //全局变量=成员变量（包含类变量+实例变量），储存在堆内存；局部变量指的是方法，模块，语句里面的变量，储存在栈内存。
    //类变量（static修饰）优先加载，类实例化后加载实例变量，局部变量在方法调用的时候加载。
    //版本号
    public static String versionName="";//静态变量，static修饰的变量或者常量，都是随着类的字节码存在而存在，优先加载
    //用于记录登录状态，以及方便接口调用
    public static String userName="",psw="",userId="",nickName="",verify="",headImg="";
    public static String deviceid="",code="",pushId="",session = "";
    public static String lat = "", log = "", location = "";
    public static Double latitude, longitude;
    //关于打印
    public static boolean isPrint = true;
    public static String loggingTag = "xhao";
    //服务器路径
    public static final String baseUrl = "https://mobile.xintai.com/";//静态常量，final修饰的为常量，赋值后不能更改
    //   private static final String baseUrl = "https://mobile.xintai.com/agentwap/";

    //所有缓存的上一层目录
    public static final String allCachePath = FileUtils.getFilePath("");
    //okHttp3缓存
    public static final String okHttp3_cache_path = allCachePath+"okHttp3Cache";//= FileUtils.getFilePath("okHttp3Cache");
    public static final long okHttp3_cache_time = 60 * 60 * 24 * 1;//okHttp3缓存时长，此处为1天
    public static final long okHttp3_cache_size = 30 * 1024 * 1024;//okHttp3缓存大小，此处为30M
    //glade缓存
    public static final String glade_cache_path = allCachePath+"glideCache"; //= FileUtils.getFilePath("glideCache");
    public static final int glade_cache_size = 70 * 1024 * 1024;

    /**
     * 清除项目缓存目录下的文件
     * @return
     */
    public static boolean clearCache(){
        FileUtils.deleteFolder(FileUtils.sdDir);//删除okHttp3数据请求缓存
//        File okHttp3Cache = new File(okHttp3_cache_path);
//        FileUtils.deleteFolder(okHttp3Cache);//删除okHttp3数据请求缓存
//        File glideCache = new File(glade_cache_path);
//        FileUtils.deleteFolder(glideCache);//删除glide图片缓存
        return true;
    }

    /**
     * 获取AndroidManifest里面的属性
     * https://www.jb51.net/article/125221.htm
     * @return App的名称
     */
    public static  String getAppName(){
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = MyApplication.getInstance().getPackageManager().getApplicationInfo(MyApplication.getInstance().getPackageName(),
            0 );//0代表PackageManager.GET_UNINSTALLED_PACKAGES
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String appName =applicationInfo.loadLabel(MyApplication.getInstance().getPackageManager()).toString();
        MyLog.i("App名称"+appName);
        return appName;
    }
}
