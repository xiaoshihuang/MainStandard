package com.xintai.xhao.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xintai.xhao.R;
import com.xintai.xhao.widget.HeadLayout;

/**
 * Created by xionghao on 2018/9/18 0018.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private ProgressDialog mProgressDialog;
    protected HeadLayout headLayout;
    private LinearLayout container1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_act);
        headLayout = (HeadLayout) findViewById(R.id.headlayout);
        container1 = (LinearLayout) findViewById(R.id.container1);
        initView();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
    }

    protected abstract void initView();

    /**
     * 将子类的独立布局返回，并填充到父类
     *
     * @param view
     */
    public void setContext(View view) {
        container1.addView(view);
    }

    @Override
    public void showLoading() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, "数据返回异常"+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErr(String msg) {
        //        showToast(getResources().getString(R.string.api_error_msg));
        Toast.makeText(this, "接口请求失败"+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return BaseActivity.this;
    }
}
