package com.xintai.xhao.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.xintai.xhao.F;
import com.xintai.xhao.MainActivity;
import com.xintai.xhao.MyApplication;
import com.xintai.xhao.R;
import com.xintai.xhao.mode.CheckVersionMode;
import com.xintai.xhao.service.UpdateService;
import com.xintai.xhao.utils.MyLog;
import com.xintai.xhao.utils.SharedPreferencesHelp;
import com.xintai.xhao.utils.SystemUtils;

import java.io.InputStream;

/**
 * Created by xionghao on 2018/8/24 0024.
 * <p>查看各大知名企业的App，载入页面都是第一页面，具有标志性和稳定性；
 * <p>后面跟着的是引导页（在第一次安装和版本有重大更新时，对新功能进行说明），
 * <p>或者广告页面（前提是有公司愿意在你的App上打广告），然后就是进入主页面，当然也有部分App是强制登录才能访问主页的。
 * <p>载入页面 ：检测更新，定位，获取准备数据等;
 */

public class LoadingAct extends AppCompatActivity {//AppCompatActivity是ActionBarActivity的父类,Compat兼容的意思

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1.继承Activity实现去标题栏和全屏
        //android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
        //去title
        //        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏 ，覆盖状态栏 ；这里不建议全屏，因为有最新版本下载的时候，需要在状态栏显示；
        //        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //这两行代码还必须写在setContentView()前面

        //2.继承AppCompatActivity实现去标题栏和全屏，一般通过设置自定义主题
        //parent="Theme.AppCompat.DayNight.NoActionBar"
        //<item name="android:windowFullscreen">true</item><!-- 设置全屏-->

        setContentView(R.layout.loading_act);
        //解决进入Loading页面先白或者黑一下后，再显示Loading的图片，其实不应该在onCreate里面设置图片，而是在activity的主题里面设置背景。

        //        float scale=this.getResources().getDisplayMetrics().density;
        //        MyLog.i("density======"+scale);

        // 1.一张图片要适应不同长宽比的手机屏幕是不可能的(要么不全，要么变形，只需要适应主流手机)
        // 2.安卓手机屏幕的长宽变化趋势4:3-->5:3-->16:9,现在主流的就是16:9
        // 3.一般情况适合主流的手机屏幕16:9即可，720*1280，640*1136
        // 4.桌面图标m48*48,h72*72,xh96*96,xxh144*144,xxxh192*192不同分辨率的分辨率的手机24*（2-3-4-6-8)
        // 5.决定屏幕清晰度的是ppi（屏幕像素密度）;分辨率和屏幕尺寸，共同决定了ppi;分辨率相同，尺寸越大越不清晰；ppi越大给系统带来的压力就越大。
        // 6.高分辨率的图片显示在低分辨率的手机上，是粗略图；低分辨率的图显示在高分辨率的手机上是模糊的。

        //1.ScaleType.CENTER_CROP(不留白)：将图片等比例缩放，让图像的短边与ImageView的边长度相同，即不能留有空白，缩放后截取中间部分进行显示。
        //（比如16:9的图片放到4:3的控件里面，那么图片长度是会被截取的）
        //2.ScaleType.CENTER：：图片大小为原始大小，如果图片大小大于ImageView控件，则截取图片中间部分，若小于，则直接将图片居中显示。
        //3.ScaleType.CENTER_INSIDE（全图）：将图片大小大于ImageView的图片进行等比例缩小，直到整幅图能够居中显示在ImageView中，小于ImageView的图片不变，直接居中显示。
        //4.ScaleType.FIT_CENTER：ImageView的默认状态，大图等比例缩小，使整幅图能够居中显示在ImageView中，小图等比例放大，同样要整体居中显示在ImageView中。

        //将activity作为参数传递进去，然后实现回调是不可取的，可能造成内存泄漏，但是模块里面存在展示Dialog，这样写会让Activity看着简洁。
        CheckVersionMode checkVersionMode = new CheckVersionMode(LoadingAct.this);
        //下面url是掌上信泰的验证版本的地址
        String versionUrl = "https://mobile.xintai.com/agentwap/wap/app/userlogin.do?action=checkversion&plateType=android";
        checkVersionMode.getVersionInfo(versionUrl);

        //        String s1 = "abc";
        //        String s2 =new String("abc");
        //        String s3  = "abc";
        //        if(s1==s2){
        //            MyLog.i("s1==s2");
        //        }else{
        //            MyLog.i("s1!=s2");
        //        }
        //        MyLog.i("获取s1地址"+s1.hashCode());
        //        MyLog.i("获取s2地址"+s2.hashCode());
        //        MyLog.i("获取s3地址"+s3.hashCode());

    }

    /**
     * 模拟版本验证后,判断下一步跳转页面
     */
    public void goNextActivity() {
        F.userName = SharedPreferencesHelp.getSharedPreferences().getString("userName", "");
        F.userId = SharedPreferencesHelp.getSharedPreferences().getString("userId", "");
        F.session = SharedPreferencesHelp.getSharedPreferences().getString("userId", "");
        boolean isfrist = SharedPreferencesHelp.getSharedPreferences().getBoolean("isfrist", true);
        if (isfrist) {//如果是首次安装，去引导页面
            SharedPreferencesHelp.getSharedPreferencesEdit().putBoolean("isfrist", false).commit();
            Intent intent = new Intent(this, WelcomeAct.class);
            startActivity(intent);
        } else {
            if (TextUtils.isEmpty(F.userName) || TextUtils.isEmpty(F.userId)) {//强制登录
                //                        SharedPreferencesHelp.myEdit.putString("UserName", "xhao");
                //                        SharedPreferencesHelp.myEdit.putString("UserId", "001").commit();
                Intent intent = new Intent(LoadingAct.this, LoginAct.class);
                startActivity(intent);
            } else {
                //                        //1.有些页面是必须要登录，比如查询自己的购物车，自己的收藏，自己的订单，而我们不可能每次进入这样的页面前都进行一次登录，
                //                        //这个时候就会用到session和cookie，作为一个标记，让服务器知道是谁在请求，这样就不必每次都进行登录
                //                        //2.session是客户端在请求服务端的时候随机生成的唯一标识码，在客户端再次请求服务器的时候，将sessionId作为cookie
                //                        //传递到服务器，就可以保证客户端和服务端的可持续性。--这样可以避免反复的登录！通过sessionId知道是谁在请求服务器，
                //                        //3.同样需求的还有图形验证码的实现。
                //                        //在请求获取图形验证码接口的时候，www服务端就会生成一个session,将图形码和唯一的sessionId返回给客户端，
                //                        //在客户端请求验证图形码接口的时候带上session，服务端才会根据session才会知道刚才给谁一个什么样的图形码，
                //                        //从而来比对图形码是否正确。--掌上信泰上面有用到。
                //                        //4.okhttp上面可以查看Header获取session.
                //
                Intent intent = new Intent(LoadingAct.this, MainActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            //                    Intent intent = new Intent(LoadingAct.this, MainActivity.class);
            //                    //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //                    startActivity(intent);
        }
        finish();
    }

}
