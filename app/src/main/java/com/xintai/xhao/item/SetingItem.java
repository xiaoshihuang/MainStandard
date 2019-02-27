package com.xintai.xhao.item;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xintai.xhao.R;
import com.xintai.xhao.utils.MyLog;

/**
 * Created by xionghao on 2018/9/5 0005.
 * 设置里面的Item，条目展示，经常会被用到；
 * 我想写成一个控件，可以在xml里面调用的，这样会节省很多代码。
 *  <p>1drawable里面xml,如果设置背景可以使用图片或者shape，不能直接使用color；如果给字体设置颜色，可以直接在Item里面使用color;
 *  <p>几乎可以包括所有情况btn_img_click,login_color_click,seting_item_click,text_color;
 *  <p>2.对应TextView和LinearLayout要设置android:clickable="true"，否则变色器没有效果
 *  <p>3.问题：如果在一个页面，引用多个此控件，那么我们怎么实现它的点击事件？
 *  如果直接在Activity里面根据控件id来判断，点击了谁，是无效的。你打印发现在点击控件的时候，
 *  view的id是不变的，而且不同于你点击的任何一个SetingItem的id,测试发现它其实是SetingItem里面的linear，
 *  原因是，事件的传递，最后被默认消费的是linear，所以要解决这个问题，我们需要对事件进行拦截。
 */

public class SetingItem extends LinearLayout {
    private String info;

    public SetingItem(Context context) {
        super(context);
        initView();
    }

    //1.如果希望可以被引入到xml，至少要写2个参数的构造方法；
    // 2.这里可以自定义属性，让属性可以直接在xml里面设置；
    public SetingItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private LinearLayout linear;
    private TextView txt1,txt2;
    private ImageView img1;

    private void initView() {
        //设置变色器
        setBackgroundResource(R.drawable.seting_item_click);
        setClickable(true);
        //添加布局
        LayoutInflater layoutInflater  = LayoutInflater.from(this.getContext());
        View view = layoutInflater.inflate(R.layout.seting_item,this);
        linear = (LinearLayout) view.findViewById(R.id.linear);
        txt1 = (TextView) view.findViewById(R.id.txt1);
        txt2 = (TextView) view.findViewById(R.id.txt2);
        img1 = (ImageView) view.findViewById(R.id.img1);
    }

    /**
     * 设置条目状态
     * @param title  主标题
     * @param info  附加信息
     * @param isHide  是否隐藏-->更多的图标
     */
    public void setState(String title,String info,boolean isHide){
        txt1.setText(title);
        txt2.setText(info);
        this.info = info;
        if(isHide==true){
            img1.setVisibility(View.INVISIBLE);
        }else{
            img1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * 设置附加信息
     * @param info
     */
    public void setInfo(String info){
        txt2.setText(info);
    }

    /**
     * 获取附加信息
     * @return
     */
    public String getInfo(){
        return info;
    }

//    public void setTitle(String title){
//        txt1.setText(title);
//    }

//    public void hideImg(){
//        img1.setVisibility(View.INVISIBLE);
//    }
}
