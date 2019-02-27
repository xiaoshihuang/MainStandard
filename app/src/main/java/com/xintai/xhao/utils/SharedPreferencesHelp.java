package com.xintai.xhao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xintai.xhao.MyApplication;

/**
 * SharedPreferences类是Android一个轻量级的存储类；
 * SharedPreferences最终使用xml文件存放数据，文件存放在/data/data/<package name>/shared_prefs/目录下，需要root后才能查看；
 * MODE_PRIVATE 只有本程序才能读取；MODE_WORLD_READABLE其他程序有读取的权限；MODE_WORLD_WRITEABLE其他程序有修改的权限；
 * 可以存取5种基本类型和set集合
 *
 * @author Administrator
 */
public class SharedPreferencesHelp {
    //封装为了方便简洁，却有失灵活

    public static SharedPreferences getSharedPreferences() {
            return SingletonHolder.mySharedPreferences;
    }

    public static SharedPreferences.Editor getSharedPreferencesEdit() {
        return SingletonHolder.myEdit;
    }

    /**
     * 静态内部类单例,只有在装载该内部类时才会去创建单例对象；特点：延迟加载，线程安全。
     */
    private static class SingletonHolder {
        private static String fileName = "myInfo";
        private static int mode = Context.MODE_PRIVATE;
        private final static SharedPreferences mySharedPreferences = MyApplication.getInstance().getSharedPreferences(fileName, mode);
        private final  static SharedPreferences.Editor myEdit=mySharedPreferences.edit();
    }
}
