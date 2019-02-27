package com.xintai.xhao.mvp;

import android.os.Handler;

import com.xintai.xhao.bean.AppVersionInfo;
import com.xintai.xhao.network.RequestApi;
import com.xintai.xhao.utils.MyLog;

import java.util.HashMap;

import rx.Observer;

/**
 * Created by xionghao on 2018/9/20 0020.
 *<p/> 我用retrofit取代了Mode在mvp中的位置（仅限网络请求，不算其他耗时操作）
 */

public class MvpModel {
    /**
     * 获取网络接口数据
     * @param params 请求参数
     * @param callback 数据回调接口
     */
    public static void getNetData(final HashMap<String,String> params, final Callback<String> callback){

        // 利用postDelayed方法模拟网络请求数据的耗时操作
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                switch (param){
//                    case "normal":
//                        callback.onSuccess("根据参数"+param+"的请求网络数据成功");
//                        break;
//                    case "failure":
//                        callback.onFailure("请求失败：参数有误");
//                        break;
//                    case "error":
//                        callback.onError();
//                        break;
//                }
//                callback.onComplete();
//            }
//        },2000);

    }
}
