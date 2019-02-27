package com.xintai.xhao.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xintai.xhao.MyApplication;
import com.xintai.xhao.R;
import com.xintai.xhao.safe.AesCrypt;
import com.xintai.xhao.safe.MD5Crypt;
import com.xintai.xhao.safe.RsaCrypt;
import com.xintai.xhao.utils.MyLog;

/**
 * Created by xionghao on 2018/9/3 0003.
 * 登录页面；
 * 1.一般App都有登录的需求，那么开始就强制登录，挺省事的，逻辑也清晰。
 * 对登录很依赖的领域是社交理财等（QQ，微信），对登录需求弱化的是资讯（今日头条--前提你不是话痨）；
 * 2.现在主流软件（支付宝，京东，微信，百度）都是默认登录的。
 * <p>登录的方式：
 * 现在登录的方式有很多种，比如账号，短信验证码，刷脸，第三方登录。
 * 感觉几乎所有的App支持短信验证码登录。
 * 优点：是方便安全，手机号--容易记，密码--动态的短信安全。
 * 缺点：需要交钱给技术支持方(当然也有免费的)，还有手机号更换的时候要及时修改绑定的手机。
 * 账号登陆：可以是对短信验证码登录的补充，比如更换了手机号，而没来得及变更绑定手机号；
 * <p>登录界面设计：
 * 越是大公司的，登录方式越多，因为用户的信息更加全面。
 * 如果支持多账号切换登录，一般是头像，下面接账号；如果不支持，是公司图标，下面接账号。
 *
 * <p/>如何保持长连接:
 *
 */


public class LoginAct extends AppCompatActivity implements View.OnClickListener{
    private EditText login_edt1,login_edt2;
    private Button login_btn;
    private RelativeLayout rel_layout;
    private TextView sms_login,new_register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);
        initView();
        String xhao = "xionghao2026";
    }

    @SuppressLint("WrongViewCast")
    public  void initView(){
        login_edt1 = (EditText) findViewById(R.id.login_edit1);
        login_edt2 = (EditText) findViewById(R.id.login_edit2);
        login_btn = (Button) findViewById(R.id.login_btn);
        rel_layout = (RelativeLayout) findViewById(R.id.rel_layout);
        sms_login = (TextView) findViewById(R.id.sms_login);
        new_register = (TextView) findViewById(R.id.new_register);
        login_btn.setOnClickListener(this);
        sms_login.setOnClickListener(this);
        new_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(login_btn)){
            //登录
//            MyLog.i(MD5Crypt.md5("xhao2018"));
//            AesCrypt.testAes();
//            RsaCrypt.TestRsa();
        }else if(v.equals(sms_login)){
            //用来切换账号登录和短信验证登录

        }else if(v.equals(new_register)){
            //跳转新用户注册
            Intent intent=new Intent(LoginAct.this,RegisterAct.class);
            startActivity(intent);
        }
    }
}
