package com.xintai.xhao.utils;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by xionghao on 2018/9/25 0025.
 * Gson工具类
 */

//Person mPerson =gson.fromJson（personStr，Person.class）是将json串转化成对象，
//String personStr=gson.toJson(mPerson);是将对象等转化成json串，

public class GjsonUtils {
    private final static GjsonUtils instance = new GjsonUtils();
    private GjsonUtils(){}
    //单例模式--饿汉模式--优势：天生的线程安全；弊端：消耗资源（感觉一个程序里面不在乎多一个静态常量的所占用的资源）
    public static GjsonUtils getInstance() {
        return instance;
    }

    /**
     * 将json格式的responseBody转换成javad对象
     * @param responseBody json格式的responseBody
     * @param clazz 目标对象类
     * @return
     */
    public <T>T  jsonToClass(ResponseBody responseBody, Class<T> clazz) {
        String jsonStr = null;
        try {
            jsonStr = new String(responseBody.bytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonToClass(jsonStr, clazz);
    }

    /**
     * 将json格式的字符串转换成javad对象
     * @param jsonStr Json字符串
     * @param clazz 目标对象类
     * @return
     */
    public <T>T jsonToClass(String jsonStr, Class<T> clazz) {//返回Object也可以，但是泛型更好
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, clazz);
    }

    /**
     * 将Json对象转换成Json字符串
     * @param object
     * @return
     */
    public String classToJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * Android studio根据Json字段的自动生成Bean类；
     * json解析快捷方法(fastJson和Gson的解析类，编写方式一样的)
     * 1. 打开 Settings，选择菜单栏 File -> Settings...
     * 2. 左边选择 Plugins 选项，右边进入之后选择 Browse repositories...
     * 3. 搜索 GsonFormat，选中之后选择右边 Install 安装插件
     * 4. 安装完成后， 重启 Android Studio
     * 5. 首先创建一个 bean, 然后使用快捷键 Alt + S, 打开插件 GUI 之后， 在里面粘贴 json 格式字符串, 选择 ok
     * 6. 在弹出的窗口中， 选择需要生成的字段， 再选择 ok， 即可自动生成需要的 Gson bean
     */
}
