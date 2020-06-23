package com.xintai.xhao.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xintai.xhao.R;
import com.xintai.xhao.activity.LoginAct;
import com.xintai.xhao.activity.RegisterAct;
import com.xintai.xhao.activity.SetingAct;
import com.xintai.xhao.widget.HeadLayout;

/**
 * Created by xionghao on 2019/3/5 0005.
 */

public class Fragment3 extends Fragment{
    private Activity mActivity;
    private View view_fragment3;
    private HeadLayout headLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_fragment3 = inflater.inflate(R.layout.view_fragment3, null);
        return view_fragment3;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        headLayout = (HeadLayout) view_fragment3.findViewById(R.id.headlayout);
        initView();
    }

    private void initView(){
        mActivity = getActivity();
        headLayout.setGoBackNone();
        headLayout.setTitle("我的");
        headLayout.showRightText("设置", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity,SetingAct.class);
                startActivity(intent);
            }
        });
    }

}
