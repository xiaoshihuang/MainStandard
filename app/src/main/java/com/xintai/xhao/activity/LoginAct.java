package com.xintai.xhao.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xintai.xhao.R;

/**
 * Created by xionghao on 2018/9/3 0003.
 * 登录页面；
 * 1.一般App都有登录的需求，那么开始就强制登录，挺省事的，逻辑也清晰。
 * 对登录很依赖的领域有社交理财等（QQ，微信），对登录需求弱化的有资讯（今日头条--前提你不是话痨）；
 * 2.现在主流软件（支付宝，京东，微信，百度）都是默认登录的。
 * <p>登录的方式：
 * 现在登录的方式有很多种，比如账号，短信验证码，刷脸，第三方登录。
 * 感觉几乎所有的App支持短信验证码登录。
 * 短信验证码，优点：是方便安全，手机号--容易记，密码--动态的短信安全。
 * 缺点：需要交钱给技术支持方(当然也有免费的)，还有手机号更换的时候要及时修改绑定的手机。
 * 账号登陆：可以是对短信验证码登录的补充，比如更换了手机号，而没来得及变更绑定手机号；
 * <p>登录界面设计：
 * 越是大公司的，登录方式越多，因为用户的信息更加全面。
 * 如果支持多账号切换登录，一般是头像，下面接账号；如果不支持，是公司图标，下面接账号。
 * <p/>如果是账号密码登录，需要对密码进行加密，
 * 如果高度涉及到财产安全的，建议使用md5加密，这样即是是后台人员也不会知道你的密码。
 * 这样的往往会有重置密码的功能，通过绑定手机/邮箱重新设置密码。
 * 一般情况，整个对称加密就好，防范别人恶意窃取密码。
 * <p/>网络数据传输安全，上传数据post请求，https,加密，传输不使用代理。
 * <p/>如何保持登录状态:看完以下三篇，你就懂很多以前想不通的问题。--
 * App保持登录状态的常用方法，https://blog.csdn.net/mpegfour/article/details/78362593
 * Token和SessionId的区别，https://segmentfault.com/a/1190000015881055
 * Session和Tokend相关区别，https://www.cnblogs.com/xiaozhang2014/p/7750200.html
 */


public class LoginAct extends AppCompatActivity implements View.OnClickListener{
    private EditText login_edt1,login_edt2;
    private Button login_btn;
    private RelativeLayout rel_layout;
    private TextView sms_login_txt, register_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);
        initView();

        System.out.println("提交到远程github");
        System.out.println("提交到远程github");
        System.out.println("提交到远程github");
        System.out.println("提交到远程github");
        System.out.println("提交到远程github");
        System.out.println("项目一起提交");
    }

    @SuppressLint("WrongViewCast")
    public  void initView(){
        login_edt1 = (EditText) findViewById(R.id.login_edit1);
        login_edt2 = (EditText) findViewById(R.id.login_edit2);
        login_btn = (Button) findViewById(R.id.login_btn);
        rel_layout = (RelativeLayout) findViewById(R.id.rel_layout);
        sms_login_txt = (TextView) findViewById(R.id.sms_login);
        register_txt = (TextView) findViewById(R.id.new_register);

        login_btn.setOnClickListener(this);
        sms_login_txt.setOnClickListener(this);
        register_txt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(login_btn)){
            //登录
            Intent intent=new Intent(LoginAct.this,MainFragementAct.class);
            startActivity(intent);
        }else if(v.equals(sms_login_txt)){
            //用来切换账号登录和短信验证登录
            Intent intent=new Intent(LoginAct.this,RecyclerViewAct.class);
            startActivity(intent);
        }else if(v.equals(register_txt)){
            //跳转新用户注册
            Intent intent=new Intent(LoginAct.this,RegisterAct.class);
            startActivity(intent);
        }
    }
}
