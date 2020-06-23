package com.xintai.xhao.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.xintai.xhao.F;
import com.xintai.xhao.MyApplication;
import com.xintai.xhao.utils.FileUtils;
import com.xintai.xhao.configuration.GlideConfig;
import com.xintai.xhao.utils.MyLog;
import com.xintai.xhao.utils.PersistentCookieStore;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 类描述：封装一个OkHttp3的获取类
 */
public class OkHttp3Utils {

    private static OkHttpClient mOkHttpClient;
    //设置缓存目录
    //    创建图片文本使用createNewFile，创建文件夹使用mkdir();mkdirs()可以包含不存在的目录
    private static File okHttp3_cache_file = new File(F.okHttp3_cache_path);
    private static Cache cache = new Cache(okHttp3_cache_file, F.okHttp3_cache_size);

    //如果有些手机没有SD卡或者系统自身没有分配外部存储空间时,那就需要用到内部存储了
    //    内部存储路径---手机需要root才可以查看的
    // 1.context.getFilesDir()路径是:/data/data/< package name >/files/…
    // 2.context.getCacheDir()路径是:/data/data/< package name >/cach/…
    //    外部储存路径
    // 1.Environment.getExternalStorageDirectory()SD根目录:/mnt/sdcard/ (6.0后写入需要用户授权)
    // 2.context.getExternalFilesDir(dir)路径为:/mnt/sdcard/Android/data/< package name >/files/…
    // 3.context.getExternalCacheDir()路径为:/mnt/sdcard/Android/data/< package name >/cach/…

    //缓存处理还是很有必要的，它有效的减少服务器负荷，降低延迟,提升用户体验;
    //卸载App的时候,对应包名下的缓存也会被删除，比如getFilesDir(),getCacheDir(),getExternalCacheDir(),getExternalFilesDir(dir)
    //但是getExternalStorageDirectory()下面的缓存不会自动删除

    //之前一直有一个疑惑，既然Retrofit已经是对OkHttp的一个封装了，为什么还一直说Retrofit+OkHttp要一起搭配使用，
    //后来才知道其实OkHttp很重要的一个作用，就是对一些网络请求的配置，例如连接超时，读取超时，以及一些缓存配置等。

    /**
     * 获取OkHttpClient对象；
     * 并设置缓存，Logging，连接超时等；
     * @return
     */
    public static OkHttpClient getOkHttpClient() {

        if (null == mOkHttpClient) {
//          HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    MyLog.i( "拦截器拦截 >>> " + message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//None,Basic(请求/返回),Headers（请求/返回/头）,Body（请求/返回/头/体）

            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()
                    //设置一个自动管理cookies的管理器
                    .cookieJar(new CookiesManager())
//                    .proxy(Proxy.NO_PROXY)//设置不走代理，避免被抓包
                    //添加自定义的拦截器
                    //                    .addInterceptor(new MyIntercepter())
                    //添加log打印拦截器
                    .addInterceptor(loggingInterceptor)
                    //添加网络连接器
                    .addNetworkInterceptor(netInterceptor)//netInterceptor这样缓存有效
                    //                    .addInterceptor(netInterceptor)//缓存无效，每次都是重新请求
                    //设置请求读写的超时时间
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .cache(cache)
                    .build();
        }
        return mOkHttpClient;
    }

    /**
     * 目前的情况是我们这个要addNetworkInterceptor这样才有效。
     * 1.有网环境下我们请求数据时，如果没有缓存或者缓存过期了，就去服务器拿数据，并且将新缓存保存下来，如果有缓存而且没有过期，则直接使用缓存。
     * 2.无网环境下我们请求数据时，缓存没过期则直接使用缓存，缓存过期了则无法使用，需要重新联网获取服务器数据。
     *
     *  可以通过配置retrofit的Headers来动态设置缓存；
     * 1.如果retrofit配置接口调用方法，没有在Headers里面设置Cache-Control，那么String cacheControl则为空，即为实时请求。
     * 2.retrofit配置接口调用方法，通过设置Headers里面的Cache-Control，设置缓存时长。
     */
    static Interceptor netInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            String cacheControl =request.cacheControl().toString();

            // 添加header参数Request提供了两个方法，一个是header(key, value)，另一个是.addHeader(key, value)，
            // 两者的区别是，header()如果有重名的将会覆盖，而addHeader()允许相同key值的header存在
            return response.newBuilder()
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control", cacheControl)
//                    .header("Cache-Control", "public, max-age=  60 * 60 * 24" )  //设置缓存为1天
                    .build();
        }
    };

    /**
     * 自动管理Cookies
     */
    private static class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(MyApplication.getInstance().getApplicationContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo == null) {//表示， 设置里面 wifi和流量都没打开
            return false;
        }
        return mNetworkInfo.isAvailable();
        // 更详细的可见com.xintai.xhao.utils.NetWorkUtils，https://www.cnblogs.com/fnlingnzb-learner/p/7531811.html
    }
}
