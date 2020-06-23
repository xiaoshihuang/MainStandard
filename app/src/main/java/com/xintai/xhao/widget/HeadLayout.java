package com.xintai.xhao.widget;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xintai.xhao.R;

/**
 * 组合控件--公共的头部。
 * <p/>依次是：返回ImageView，左ImageView,Title,右ImageView1,右ImageView2(右TextView);
 * <p/>可以作为控件添加到xml；也可以引用此布局，写成带头部的公共Fragment
 *
 * @author xh
 */
public class HeadLayout extends LinearLayout{
    public ImageView goback, leftmenu, rightmenu1, rightmenu2;
    public TextView title, righttxt;
    private Context context;

    public HeadLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    // 注意：能在xml被引用的自定义控件，需要2个参数的构造方法
    public HeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    public void init() {
        LayoutInflater f = LayoutInflater.from(getContext());
        View view = f.inflate(R.layout.wgt_headlayout, this);

        goback = (ImageView) findViewById(R.id.goback);// 专门负责返回
        leftmenu = (ImageView) findViewById(R.id.leftmenu);// 左边的菜单
        title = (TextView) findViewById(R.id.title);//中间标题
        rightmenu1 = (ImageView) findViewById(R.id.rightmenu1);//
        rightmenu2 = (ImageView) findViewById(R.id.rightmenu2);// 最右边菜单
        righttxt = (TextView) findViewById(R.id.righttxt);

        context = this.getContext();

        goback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)(getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                imm.hideSoftInputFromWindow(goback.getWindowToken(), 0);
                if(context instanceof  Activity){
                    ((Activity) context).finish();
                }
            }
        });


    }

    /**
     * 设置标签名称
     *
     * @param s 标签名称
     */
    public void setTitle(String s) {
        title.setText(s);
    }

    /**
     * 隐藏goback
     */
    public void setGoBackNone() {
        goback.setVisibility(View.GONE);
    }

    /**
     * 修改goback的src
     *
     * @param i src资源
     */
    public void changeGobackImg(int i) {
        goback.setImageResource(i);// 修改的是src而不是背景
    }

    /**
     * 隐藏返回，显示左边的LeftMenu,传递事件
     *
     * @param c LeftMenu的点击事件
     */
    public void showLeftMenu(OnClickListener c) {
        goback.setVisibility(View.GONE);
        leftmenu.setVisibility(View.VISIBLE);
        leftmenu.setOnClickListener(c);
    }

    /**
     * 修改leftMenu的src
     *
     * @param i src资源
     */
    public void changeLeftMenuImg(int i) {
        leftmenu.setImageResource(i);
    }


    /**
     * 显示右边的rightMenu1,传递事件
     *
     * @param c LeftMenu的点击事件
     */
    public void showRightMenu1(OnClickListener c) {
        rightmenu1.setVisibility(View.VISIBLE);
        rightmenu1.setOnClickListener(c);
    }

    /**
     * 显示右边的rightMenu2,传递事件
     *
     * @param c LeftMenu的点击事件
     */
    public void showRightMenu2(OnClickListener c) {
        rightmenu2.setVisibility(View.VISIBLE);
        rightmenu2.setOnClickListener(c);
    }

    /**
     * 显示右边的TextView,传递事件
     *
     * @param c LeftMenu的点击事件
     */
    public void showRightText(String str, OnClickListener c) {
        if (!TextUtils.isEmpty(str)) {
            righttxt.setText(str);
        }
        righttxt.setVisibility(View.VISIBLE);
        righttxt.setOnClickListener(c);
    }

    /**
     * 修改rightMenu1的src
     *
     * @param i src资源
     */
    public void changeRightMenu1Img(int i) {
        rightmenu1.setImageResource(i);
    }

    /**
     * 修改rightMenu2的src
     *
     * @param i src资源
     */
    public void changeRightMenu2Img(int i) {
        rightmenu2.setImageResource(i);
    }

    /**
     * 点击标题。传递事件
     *
     * @param c
     */
    public void titleClick(OnClickListener c) {
        title.setOnClickListener(c);
    }

}
