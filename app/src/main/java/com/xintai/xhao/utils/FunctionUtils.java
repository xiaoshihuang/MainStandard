package com.xintai.xhao.utils;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by xionghao on 2018/9/18 0018.
 * <p>封装一些常用的功能
 */
public class FunctionUtils {

    private int times;
    private Handler handle = new Handler();
    private Runnable runnable;

    /**
     * 设置短信验证码倒计时。
     * <p/>1.假如发短信服务是要收费的，肯定不允许不断发送。
     * <p/>2.接受短信，也许会有延迟，给用户一个心理上的准备。
     */
    public void countDown(final TextView send_code_txt) {
        times = 60;
        send_code_txt.setText("发送验证码（" + times + "s）");
        send_code_txt.setTextColor(Color.parseColor("#898989"));
        send_code_txt.setClickable(false);
        //注意，这里并没有开启新的子线程.所以就不是在非UI线程更改UI界面。
        runnable = new Runnable() {
            @Override
            public void run() {
                --times;
                if (times > 0) {
                    send_code_txt.setText("发送验证码（" + times + "s）");
                    handle.postDelayed(runnable, 1000);
                } else if (times == 0) {
                    send_code_txt.setTextColor(Color.parseColor("#333333"));
                    send_code_txt.setText("发送验证码");
                    send_code_txt.setClickable(true);
                }
            }
        };
        handle.postDelayed(runnable, 1000);
    }

}
