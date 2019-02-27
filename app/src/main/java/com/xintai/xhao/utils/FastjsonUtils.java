package com.xintai.xhao.utils;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import javax.inject.Singleton;

import okhttp3.ResponseBody;

/**
 * Created by xionghao on 2018/9/28 0028.
 */

public class FastjsonUtils {
//    Gson和fastJson对比：
//    Gson谷歌开发的，功能无可挑剔。
//    fastJson阿里出品，性能更快，但是在遇到复杂的Bean对象转json字符串，可能出错。
//    使用：在复杂的Bean对象转json字符串使用Gson,其他的情况使用fastJson。

    private FastjsonUtils(){}
    public static FastjsonUtils getInstance(){
        return SingletonHolder.instance;
    }
    /**
     * 静态内部类单例,只有在装载该内部类时才会去创建单例对象；特点：延迟加载，线程安全。
     */
    private static class SingletonHolder {
        private final static  FastjsonUtils instance = new FastjsonUtils();
    }

    /**
     * 把Java对象转换为JSON数据格式字符串
     *
     * @param object
     * @return
     */
    public String classToJson(Object object) {
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 把JSON格式的ResponseBody转换为JAVA对象
     *
     * @param <T>
     * @param responseBody
     * @param clz
     * @return
     */
    public <T> T jsonToClass(ResponseBody responseBody, Class<T> clz) {
        String jsonStr = null;
        try {
            jsonStr = new String(responseBody.bytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonToClass(jsonStr, clz);
    }

    /**
     * 把JSON数据格式字符串转换为JAVA对象
     *
     * @param <T>
     * @param jsonStr
     * @param clz
     * @return
     */
    public <T> T jsonToClass(String jsonStr, Class<T> clz) {
        try {
            return JSON.parseObject(jsonStr,clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
