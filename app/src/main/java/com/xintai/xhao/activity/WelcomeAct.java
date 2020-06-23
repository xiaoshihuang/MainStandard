package com.xintai.xhao.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xintai.xhao.MainActivity;
import com.xintai.xhao.R;
import com.xintai.xhao.adapter.WelcomeAdapter;
import com.xintai.xhao.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xionghao on 2018/8/30 0030.
 * <p>引导页面。
 * <p>一般在第一次安装或者有重大更新的时候，起到解释说明的作用；
 * <p>图片多的时候会卡顿，最好使用工具检测下，是否有内存泄漏？
 */

public class WelcomeAct extends AppCompatActivity{
    private View view;
    private LinearLayout linear;
    private ViewPager viewpager;
    private List<View> viewList = new ArrayList<View>();
    private LinearLayout.LayoutParams params;
    private int[] imgs = new int[] { R.mipmap.guide_1,
            R.mipmap.guide_2,R.mipmap.guide_3,R.mipmap.guide_4};//图片太大会卡顿
    private int point_click_width  = 40;//下面被选中指示剂点的直径
    private int point_unclick_width = 22;//下面未被选中指示剂点的直径

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_act);
        viewpager = (ViewPager) findViewById(R.id.vPager);
        initData();
    }

    private void initData() {
        //添加前面的页面
        for (int i = 0; i < imgs.length-1; i++) {
            ImageView img =new ImageView(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            img.setLayoutParams(params);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewList.add(img);
        }

        //单独添加最后的一个页面（由于此页面有跳转）
        LayoutInflater flater = LayoutInflater.from(this);
        View lastPageView = flater.inflate(R.layout.welcome_last, null);
        viewList.add(lastPageView);
        Button enter = (Button) lastPageView.findViewById(R.id.bt_next);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent;//进入到主页面
                intent = new Intent(WelcomeAct.this,MainActivity.class);
//                intent = new Intent();//隐式启动
//                intent.setAction("com.xintai.xhao.action.SET");
                startActivity(intent);
                finish();
            }
        });
        viewpager.setAdapter(new WelcomeAdapter(this, viewList,imgs));
        viewpager.setOnPageChangeListener(new GuidePageChangeListener());

        //初始化点的指示剂
        linear = (LinearLayout) findViewById(R.id.linear);
        for (int i = 0; i < viewpager.getAdapter().getCount(); i++) {
            ImageView img = new ImageView(this);
            if (i == 0) {
                params = new LinearLayout.LayoutParams(point_click_width, point_click_width);
                img.setBackgroundResource(R.mipmap.guide_point_click);
            } else {
                params = new LinearLayout.LayoutParams(point_unclick_width, point_unclick_width);
                img.setBackgroundResource(R.mipmap.guide_point_unclick);
            }
            params.setMargins(20,0,20,0);
            img.setLayoutParams(params);
            linear.addView(img);
        }
    }

    /**
     * 监听ViewPage页面切换的时，来改变对应指示剂的状态（大小和背景）
     * @author Administrator
     *
     */
    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < linear.getChildCount(); i++) {
                if (i == position) {
                    params = new LinearLayout.LayoutParams(point_click_width, point_click_width);
                    linear.getChildAt(i).setBackgroundResource(
                            R.mipmap.guide_point_click);
                } else {
                    params = new LinearLayout.LayoutParams(point_unclick_width, point_unclick_width);
                    linear.getChildAt(i).setBackgroundResource(
                            R.mipmap.guide_point_unclick);
                }
                params.setMargins(20,0,20,0);
                linear.getChildAt(i).setLayoutParams(params);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //经测试viewList里面的ImageView是没有图片资源，不需要手动释放。
    }

}
