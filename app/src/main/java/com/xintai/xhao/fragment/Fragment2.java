package com.xintai.xhao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xintai.xhao.R;
import com.xintai.xhao.widget.HeadLayout;

/**
 * Created by xionghao on 2019/3/5 0005.
 */

public class Fragment2 extends Fragment {

    private View view_fragment2;
    private HeadLayout headLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view_fragment2 = inflater.inflate(R.layout.view_fragment2, null);
        return view_fragment2;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        headLayout = (HeadLayout) view_fragment2.findViewById(R.id.headlayout);
        initView();
    }

    private void initView() {
        headLayout.setGoBackNone();
        headLayout.setTitle("通讯录");
    }

}
