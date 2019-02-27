package com.xintai.xhao.utils;

import com.xintai.xhao.F;

/**
 *  Created by xionghao on 2018/8/28 0028.
 * 自定义Log输出
 * 感觉没啥用，唯一的作用是可以统一管理是否打印；
 * 设置F.isPrint是否打印MyLog日志；
 * 设置F.loggingTag作为日志MyLog的共用tag;
 */

public class MyLog {

    public static void i(String msg) {
            if (F.isPrint)
                android.util.Log.i(F.loggingTag, msg);
    }
    public static void v(String msg) {
        if (F.isPrint)
            android.util.Log.v(F.loggingTag, msg);
    }
    public static void d(String msg) {
        if (F.isPrint)
            android.util.Log.d(F.loggingTag, msg);
    }
    public static void e(String msg) {
        if (F.isPrint)
            android.util.Log.e(F.loggingTag, msg);
    }
    public static void w(String msg) {
        if (F.isPrint)
            android.util.Log.w(F.loggingTag, msg);
    }

}
