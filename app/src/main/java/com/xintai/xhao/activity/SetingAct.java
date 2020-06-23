package com.xintai.xhao.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xintai.xhao.F;
import com.xintai.xhao.R;
import com.xintai.xhao.basecontext.HeadLayoutActivity;
import com.xintai.xhao.basecontext.ToolbarActivity;
import com.xintai.xhao.item.SetingItem;
import com.xintai.xhao.utils.FileUtils;

/**
 * Created by xionghao on 2018/9/5 0005.
 * <p>设置页面；
 * <p>条目和继承HeadLayoutActivity的使用
 */

public class SetingAct extends HeadLayoutActivity implements View.OnClickListener {
    private SetingItem seting_item1, seting_item2, seting_item3,seting_item4;
    private TextView exit_txt;

    protected void initView() {
        View subView = LayoutInflater.from(this).inflate(
                R.layout.seting_act, null);
        setSubContentView(subView);
        setTitle("设置");

        seting_item1 = (SetingItem) subView.findViewById(R.id.seting_item1);
        seting_item2 = (SetingItem) subView.findViewById(R.id.seting_item2);
        seting_item3 = (SetingItem) subView.findViewById(R.id.seting_item3);
        seting_item4 = (SetingItem) subView.findViewById(R.id.seting_item4);
        exit_txt = (TextView) subView.findViewById(R.id.exit_txt);

        seting_item1.setState("账号与安全", "", false);
        if(FileUtils.sdDir==null){
            FileUtils.getFilePath("");
        }
        if (FileUtils.sdDir.exists()) {
            Long dataSize = FileUtils.getFolderSize(FileUtils.sdDir);
            String dataFormatSize = FileUtils.getFormatSize(dataSize);
            seting_item2.setState("清除缓存", dataFormatSize, true);
        } else {
            seting_item2.setState("清除缓存", "0M", false);
        }
        seting_item3.setState("当前版本", "V " + F.versionName, true);
        seting_item4.setState("关于我们", "", true);

        seting_item1.setOnClickListener(this);
        seting_item2.setOnClickListener(this);
        seting_item3.setOnClickListener(this);
        seting_item4.setOnClickListener(this);
        exit_txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(seting_item1)) {//账号和安全==显示账号，绑定手机，绑定邮箱，修改密码

        } else if (v.equals(seting_item2)) {
            //清除缓存
            Long dataSize = FileUtils.getFolderSize(FileUtils.sdDir);
            if(dataSize>0){
                if (F.clearCache()) {
                    seting_item2.setState("清除缓存", "0M", true);
                    Toast.makeText(getApplication(),"缓存清理完毕",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplication(),"没有缓存需要清理",Toast.LENGTH_SHORT).show();
            }
        } else if (v.equals(seting_item3)) {
            //当前版本
        }else if (v.equals(seting_item4)) {//关于我们
            Intent intent = new Intent(SetingAct.this,AboutUsAct.class);
            startActivity(intent);
        }else if (v.equals(exit_txt)) {
            //退出登录
        }
    }


}
