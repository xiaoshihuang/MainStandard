package com.xintai.xhao.basecontext;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xintai.xhao.R;

/**
 * Created by xionghao on 2018/9/7 0007.
 */

public abstract class ToolbarActivity extends AppCompatActivity{
    private LinearLayout container1;
    protected Toolbar toolbar;//Toolbar简单使用https://blog.csdn.net/monalisatearr/article/details/78415585
    protected TextView mid_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.toolbar_act);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        container1 = (LinearLayout) findViewById(R.id.container1);
        mid_title = (TextView) findViewById(R.id.mid_title);

        initView();

        toolbar.setTitle("");//让自己添加的标题居中
        setSupportActionBar(toolbar);

        //android中px与sp,dp之间的转换https://blog.csdn.net/qidingquan/article/details/53714603
        //在mdpi中1dp=1px,在hdpi中1dp=1.5px，在xhdpi中1dp=2px,在xxhpi中1dp=3px。
        //我将42px的图放在xhdpi里面，那么折合成dp就是21.xml里面toolbar的高是42dp，恰好。
        toolbar.setNavigationIcon(R.mipmap.head_back_img);//要使NavigationIcon竖直居中，需设置android:minHeight="42dp"
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish函数只是将最上面的Activity移出了栈，，它的资源并没有被立刻释放.
//                https://blog.csdn.net/mini_snail/article/details/46363979
                finish();
            }
        });
    }

    protected abstract void initView();

    /**
     * 将子类的独立布局返回，并填充到父类
     *
     * @param view
     */
    public void setSubContentView(View view) {
        container1.addView(view);
    }
}
