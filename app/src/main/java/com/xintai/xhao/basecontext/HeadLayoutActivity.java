package com.xintai.xhao.basecontext;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xintai.xhao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xionghao on 2019/3/6 0006.
 * 自带Headlayout的Activity基类。
 */

public abstract class HeadLayoutActivity extends AppCompatActivity {//implements View.OnClickListener
    public View include_headlayout;
    public ImageView goback, leftmenu, rightmenu1, rightmenu2;
    public TextView title, righttxt;
    public LinearLayout content_layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headlayout_activity);

        include_headlayout = findViewById(R.id.include_headlayout);
        goback = (ImageView) findViewById(R.id.goback);// 专门负责返回
        leftmenu = (ImageView) findViewById(R.id.leftmenu);// 左边的菜单
        title = (TextView) findViewById(R.id.title);//中间标题
        rightmenu1 = (ImageView) findViewById(R.id.rightmenu1);//
        rightmenu2 = (ImageView) findViewById(R.id.rightmenu2);// 最右边菜单
        righttxt = (TextView) findViewById(R.id.righttxt);

        content_layout = (LinearLayout) findViewById(R.id.content_layout);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setTitlebarEvent();

        initView();
    }

    protected abstract void initView();

    /**
     * 将子类的独立布局返回，并填充到父类
     *
     * @param view
     */
    public void setSubContentView(View view) {
        content_layout.addView(view);
    }

    public void setTitlebarEvent() {
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) (HeadLayoutActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE));
                imm.hideSoftInputFromWindow(goback.getWindowToken(), 0);
                finish();
            }
        });
        leftmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLeftmenuClick();
            }
        });
        rightmenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightmenu1Click();
            }
        });
        rightmenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRightmenu2Click();
            }
        });
        righttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRighttxtClick();
            }
        });
    }

    /**
     * 重写父类onLeftmenuClick，对左边menu进行响应
     */
    public void onLeftmenuClick() {

    }

    /**
     * onRightmenu1Click，对右边menu1进行响应
     */
    public void onRightmenu1Click() {

    }

    /**
     * onRightmenu2Click，对右边menu2进行响应
     */
    public void onRightmenu2Click() {

    }

    /**
     * onRighttxtClick，对右边righttxt进行响应
     */
    public void onRighttxtClick() {

    }

    /**
     * 设置头部显示的控件元素
     *
     * @param goBack     默认返回
     * @param leftMenu   默认menu图标
     * @param rightMenu1 默认地图图标
     * @param rightMenu2 最右边，默认设置图标
     * @param rightTxt   有时候文字提示而不是图标
     */
    public void setHeadVisibility(Boolean goBack, Boolean leftMenu,
                                  Boolean rightMenu1, Boolean rightMenu2, Boolean rightTxt) {
        visibFromBoolean(goback, goBack);
        visibFromBoolean(leftmenu, leftMenu);
        visibFromBoolean(rightmenu1, rightMenu1);
        visibFromBoolean(rightmenu2, rightMenu2);
        visibFromBoolean(righttxt, rightTxt);
    }

    public void visibFromBoolean(View view, Boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 指定HeadFragment子类的背景颜色。
     * 因为有时候（比如布局没有被控件填充满）在子类布局里面设置背景色是无效的；
     *
     * @param colorString 格式：#ededed
     */
    public void setContentBg(String colorString) {
        content_layout.setBackgroundColor(Color.parseColor(colorString));
    }

    /**
     * 给头部设置背景颜色
     *
     * @param color
     */
    public void setHeadBg(int color) {
        // head_relative.setBackgroundColor(Color.parseColor("#32b67a"));
        include_headlayout.setBackgroundColor(color);
    }


    /**
     * 设置标题
     *
     * @param str
     */
    public void setTitle(String str) {
        title.setText(str);
    }

    /**
     * 设置标题颜色
     *
     * @param color
     */
    public void setTitleColor(int color) {
        title.setTextColor(color);
    }

    /**
     * 设置右边文字
     *
     * @param str
     */
    public void setRightTitle(String str) {
        righttxt.setText(str);
    }

    /**
     * 设置标题颜色
     *
     * @param color
     */
    public void setRightTitleColor(int color) {
        righttxt.setTextColor(color);
    }

    public void setGobackImg(int i) {
        goback.setImageResource(i);// 修改的是src而不是背景
    }

    public void setLeftMenuImg(int i) {
        leftmenu.setImageResource(i);
    }

    public void setRightMenu1Img(int i) {
        rightmenu1.setImageResource(i);
    }

    public void setRightMenu2Img(int i) {
        rightmenu2.setImageResource(i);
    }
}
