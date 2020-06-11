package com.xintai.xhao.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextClock;

import com.xintai.xhao.R;
import com.xintai.xhao.basecontext.HeadLayoutActivity;
import com.xintai.xhao.basecontext.ToolbarActivity;

import java.io.Serializable;

/**
 * Created by xionghao on 2019/3/6 0006.
 * <p>我觉得Toolbar，没有自己写的HeadLayout好用。
 * <p>Toolbar简单使用https://blog.csdn.net/monalisatearr/article/details/78415585
 * <p>关于我们
 */

public class AboutUsAct extends ToolbarActivity {

    @Override
    protected void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View subView = layoutInflater.inflate(R.layout.about_us_act, null);
        setSubContentView(subView);

        mid_title.setText("关于我们");

        System.out.println("提交到远程gthub");
        System.out.println("提交到远程gthub");

    }

}
