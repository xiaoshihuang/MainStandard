package com.xintai.xhao.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.xintai.xhao.F;
import com.xintai.xhao.utils.FileUtils;
import com.xintai.xhao.utils.MyLog;

/**
 * Created by xionghao on 2018/11/28 0028.
 * 1.IntentService相比Service，自带一个工作线程。
 * 2.需要给出构造方法并实现onHandleIntent方法，在此方法里面完成耗时操作。
 * 3.如果有多个任务需要处理，排队，一个个的处理，处理完成所有的任务，会自行销毁。
 */

public class MyIntentService extends IntentService {

    //必须实现父类的构造方法
    public MyIntentService() {
        super("MyIntentService");
    }

    //看IntentService的源码就知道，为什么只需要复写这个方法
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        MyLog.i("MyIntentService运行onHandleIntent");
        String action = intent.getStringExtra("action");
        if("init".equals(action)){

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLog.i("MyIntentService运行完所有任务自行销毁onDestroy");
    }
}
