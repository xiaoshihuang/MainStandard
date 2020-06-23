package com.xintai.xhao.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.xintai.xhao.MyApplication;
import com.xintai.xhao.R;
import com.xintai.xhao.adapter.DividerGridViewItemDecoration;
import com.xintai.xhao.adapter.DividerItemDecoration;
import com.xintai.xhao.adapter.MyRecyclerViewAdapter;
import com.xintai.xhao.basecontext.HeadLayoutActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xionghao on 2019/3/19 0019.
 * <P/>关于RecyclerView的使用，https://blog.csdn.net/tuike/article/details/79064750
 */

public class RecyclerViewAct extends HeadLayoutActivity{
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void initView() {
        View subView = LayoutInflater.from(this).inflate(
                R.layout.recycle_view_act, null);
        setSubContentView(subView);
        setTitle("产品列表");

        for(int i=1;i<=26;i++){
            list.add("Iteam"+i);
        }
        //通过findViewById拿到RecyclerView实例
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //设置RecyclerView管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));//列表竖向展示
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));//列表横向展示
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));//网格展示
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));//瀑布流展示

        //利用ItemDecoration实现条目分割线，ItemDecoration是谷歌定义的可用于画分割线的类， 是抽象的，需要我们自己去实现
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));//适合列表的分割线
//        mRecyclerView.addItemDecoration(new DividerGridViewItemDecoration(this));
        //上面添加的都是系统默认的分割线，如果需要改变分割线的颜色，可以在主题里面添加 <item name="android:listDivider">@drawable/bg_recyclerview_divider</item>

        //初始化适配器
        mAdapter = new MyRecyclerViewAdapter(list);
        //设置添加或删除item时的动画，这里使用默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position, String data) {
                Toast.makeText(MyApplication.getInstance(),"你点击了第"+position+"Item"+":"+data,Toast.LENGTH_LONG).show();
//                mAdapter.deleteItem(position);
            }
        });


    }
}
