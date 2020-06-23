package com.xintai.xhao.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xintai.xhao.R;
import com.xintai.xhao.fragment.Fragment1;
import com.xintai.xhao.fragment.Fragment2;
import com.xintai.xhao.fragment.Fragment3;

/**
 * Created by xionghao on 2019/3/5 0005.
 * <p/>这样的结构是没有预加载的，也没必要预先加载。
 * <p/>比较有名的App一般不会把主页面的结构搞的很复杂，页面切换也没必要添加。
 */

public class MainFragementAct extends FragmentActivity implements View.OnClickListener {
    private android.support.v4.app.FragmentManager m_frgMager;
    private android.support.v4.app.FragmentTransaction m_frgTras;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private int checked = 1;// 切换动画游标

    private LinearLayout linear1, linear2, linear3;
    private TextView txt1, txt2, txt3;
    private ImageView img1, img2, img3;
    private int position = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        //系统内存不足的时候，Activity可能被销毁。在Activity进入到后台前，我们可以在onSaveInstanceState里面保持一些数据，
        //在Activity被重建的时候，对先前数据的还原。
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
        }
        setContentView(R.layout.act_main_fragment);
        initView();
        System.out.println("项目一起提交");
    }

    private void initView() {
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);

        m_frgMager = getSupportFragmentManager();
        setSelection(position);

        linear1.setOnClickListener(this);
        linear2.setOnClickListener(this);
        linear3.setOnClickListener(this);
    }

    private void setSelection(int position2) {
        // TODO Auto-generated method stub
        m_frgTras = m_frgMager.beginTransaction();// Transaction事务，交易，处理
        if (1 == position2) {
            position = 1;
            setItem(img1, img2, img3);
            setItem(txt1, txt2, txt3);
            if (fragment1 == null) {
                fragment1 = new Fragment1();
                m_frgTras.add(R.id.framelayout, fragment1);
            }
            showFragment(fragment1, 1);
        } else if (2 == position2) {
            position = 2;
            setItem(img2, img1, img3);
            setItem(txt2, txt1, txt3);
            if (fragment2 == null) {
                fragment2 = new Fragment2();
                m_frgTras.add(R.id.framelayout, fragment2);
            }
            showFragment(fragment2, 2);
        } else if (3 == position2) {
            position = 3;
            setItem(img3, img2, img1);
            setItem(txt3, txt2, txt1);
            if (fragment3 == null) {
                fragment3 = new Fragment3();
                m_frgTras.add(R.id.framelayout, fragment3);
            }
            showFragment(fragment3, 3);
        }
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        if (view.equals(linear1)) {
            setSelection(1);
        } else if (view.equals(linear2)) {
            setSelection(2);
        } else if (view.equals(linear3)) {
            setSelection(3);
        }

    }

    /**
     * 控制Fragment的隐显
     *
     * @param fragment
     * @param checknumber
     */
    private void showFragment(Fragment fragment, int checknumber) {// 被选中的RadioButton
        // if (checknumber < checked) {// 从左向右
        // m_frgTras.setCustomAnimations(R.anim.zoom_right_in,
        // R.anim.zoom_right_out);// (进，出)
        // } else if (checknumber == checked) {
        // m_frgTras.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        // } else if (checknumber > checked) {// 从右向左
        // m_frgTras.setCustomAnimations(R.anim.zoom_left_in,
        // R.anim.zoom_left_out);
        // }
        checked = checknumber;

        if (fragment1 != null) {
            m_frgTras.hide(fragment1);
        }
        if (fragment2 != null) {
            m_frgTras.hide(fragment2);
        }
        if (fragment3 != null) {
            m_frgTras.hide(fragment3);
        }
        m_frgTras.show(fragment);
        // m_frgTras.commit();
        m_frgTras.commitAllowingStateLoss();
    }

    /**
     * 被选中改变背景，颜色
     * @param cur 被选中
     * @param nocur1 未被选中
     * @param nocur2 未被选中
     */
    public void setItem(View cur, View nocur1, View nocur2) {
        cur.setSelected(true);
        nocur1.setSelected(false);
        nocur2.setSelected(false);
    }

    /**
     * 一般在onStop之前调用，如果是finish或者back键主观意图关闭一个Activity，此方法是不会调用的。
     * 在activity可能被回收的时候，会调用onSaveInstanceState来保存一些东西，例如：
     * 1.当用户按下HOME键时。
     * 2.长按HOME键，选择运行其他的程序时。
     * 3.按下电源按键（关闭屏幕显示）时。
     * 4.从activity A中启动一个新的activity时。
     * 5.屏幕方向切换时，例如从竖屏切换到横屏时。
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //		super.onRestoreInstanceState(outState);  //避免存储Fragment实例，去重叠
        // 记录当前的position
        outState.putInt("position", position);//保证在activity被回收后，在onCreate里面重新加载的时候，指向是正确的
    }
}
