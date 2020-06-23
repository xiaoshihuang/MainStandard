package com.xintai.xhao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xintai.xhao.MainActivity;
import com.xintai.xhao.MyApplication;
import com.xintai.xhao.R;
import com.xintai.xhao.bean.AppVersionInfo;
import com.xintai.xhao.bean.SmsErrorBean;
import com.xintai.xhao.mvp.BaseActivity;
import com.xintai.xhao.mvp.BaseView;
import com.xintai.xhao.mvp.Callback;
import com.xintai.xhao.mvp.MvpPresenter;
import com.xintai.xhao.mvp.MvpView;
import com.xintai.xhao.utils.FastjsonUtils;
import com.xintai.xhao.utils.FunctionUtils;
import com.xintai.xhao.utils.GjsonUtils;
import com.xintai.xhao.utils.MyLog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.ResponseBody;

/**
 * Created by xionghao on 2018/9/13 0013.
 * <p/>MVP最大的优势就是解耦，让各个位置各司其职，条例清晰，缺点就是代码量比较大；
 * <p/>而我这里经过一些优化，即实现了MVP的优势，又克服了MVP代码量大的缺点；
 * <P/>改变1，我们用retrofit取代Mode,你有没发现2者本来就很类似，对数据进行请求，然后返回给Presenter;
 * <p/>改变2，对应retrofit返回的数据不解析，保留ResponseBody类型
 * <p/>改变3，通过反射调用retrofit的接口api
 * <p/>这3个改变让我们只需要,在retrofit接口api文件里面配置请求方法，然后在Ui界面接受返回数据即可。
 */

public class RegisterAct extends BaseActivity implements MvpView,View.OnClickListener{
    private EditText user_name_edit,psw1_edit,psw2_edit,phone_edit,code_edit;
    private TextView send_code_txt;
    private LinearLayout phone1_linear,phone2_linear;
    private View phone_line,code_line;
    private Button register_btn;
    private MvpPresenter mvpPresenter;
    private EventHandler eventHandler;
//    private boolean checkSmsCode = false;

    @Override
    protected void initView() {
        View subView = LayoutInflater.from(this).inflate(
                R.layout.register_act, null);
        setContext(subView);
        headLayout.setTitle("注册");

        user_name_edit = (EditText) subView.findViewById(R.id.user_name_edit);
        psw1_edit = (EditText) subView.findViewById(R.id.psw1_edit);
        psw2_edit = (EditText) subView.findViewById(R.id.psw2_edit);
        phone_edit = (EditText) subView.findViewById(R.id.phone_edit);
        code_edit = (EditText) subView.findViewById(R.id.code_edit);

        send_code_txt = (TextView) subView.findViewById(R.id.send_code_txt);
        phone1_linear = (LinearLayout) subView.findViewById(R.id.phone1_linear);
        phone2_linear = (LinearLayout) subView.findViewById(R.id.phone2_linear);
        phone_line =  subView.findViewById(R.id.phone_line);
        code_line = subView.findViewById(R.id.code_line);
        register_btn = (Button) subView.findViewById(R.id.register_btn);

        mvpPresenter = new MvpPresenter();
        mvpPresenter.attachView(this);

        send_code_txt.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        initMobSms();

    }

    @Override
    public void onClick(View v) {
        if(v.equals(send_code_txt)){//发送验证码
            if(phone_edit.getText().toString().length()==11){//只是粗略的判断是否为手机号
                //控制倒计时按钮
                new FunctionUtils().countDown(send_code_txt);
                // 请求发送验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                SMSSDK.getVerificationCode("86", phone_edit.getText().toString());
            }else{
                phone_edit.requestFocus();
                Toast.makeText(getApplication(),"请输入正确的手机号",Toast.LENGTH_SHORT).show();
            }
        }else if(v.equals(register_btn)){//注册
            if(checkInput(user_name_edit,psw1_edit,psw2_edit)){
                //先验证手机号和短信验证码是否匹配，如果验证通过，在结果回调方法里面，进行下一步操作（即请求注册接口）
                SMSSDK.submitVerificationCode("86", phone_edit.getText().toString(), code_edit.getText().toString());
            }
        }
    }

    @Override
    public void callBackData(Object object,String methodMark) {
        //        Toast.makeText(MyApplication.getInstance(),data,Toast.LENGTH_SHORT).show();
        if("register".equals(methodMark)){
            ResponseBody responseBody = (ResponseBody) object;
            AppVersionInfo call= (AppVersionInfo) FastjsonUtils.getInstance().jsonToClass(responseBody,AppVersionInfo.class);
            AppVersionInfo.ResponseObjectBean data = call.getResponseObject();
            Intent intent = new Intent();
            user_name_edit.setText(data.getMinversion());
        }
    }

    /**
     * 无界面短信验证(注册和结果回调)
     */
    public void initMobSms(){
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//对发送短信验证码的结果回调
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            } else {
                                // TODO 处理错误的结果
                                Throwable throwableStr = (Throwable) data;
                                MyLog.i(throwableStr.getMessage());//{"status":603,"detail":"请填写正确的手机号码"}
                                //解析返回的错误信息
                                SmsErrorBean smsErrorBean = GjsonUtils.getInstance().jsonToClass(throwableStr.getMessage().toString(), SmsErrorBean.class);
                                Toast.makeText(getApplication(),smsErrorBean.getDetail(),Toast.LENGTH_LONG).show();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//对短信验证码和手机号是否匹配的结果回调
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理验证码验证通过的结果
                                //调用注册接口（这里是掌上信泰获取信息的接口）
                                HashMap<String,String> params= new HashMap<String,String>();//这里可以完美契合retrofit，入参的HashMap<String,String>
                                params.put("action","checkversion");
                                params.put("plateType","android");
                                try {
                                    //注意：1.这里的register是自定义的，我们需要到retrofit的接口Api里面设置接口方法，方法名称对应这里的register
                                    //2.register还有一个用途是在showData里面判断是哪个接口返回的数据
                                    mvpPresenter.setData(params,"register");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                // TODO 处理错误的结果
                                Throwable throwableStr = (Throwable) data;
                                MyLog.i(throwableStr.getMessage());//{"status":603,"detail":"请填写正确的手机号码"}
                                //解析返回的错误信息
                                SmsErrorBean smsErrorBean = GjsonUtils.getInstance().jsonToClass(throwableStr.getMessage().toString(), SmsErrorBean.class);
                                Toast.makeText(getApplication(),smsErrorBean.getDetail(),Toast.LENGTH_LONG).show();
                            }
                        }
                        // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                        return false;
                    }
                }).sendMessage(msg);
            }
        };
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
    }

    /**
     * 有界面短信验证（备用）；
     * 跳转进入Mob自带的短信验证页面进行短信验证(可以单独完成短信验证，多用于分步注册)
     */
    public void  mobSmsUi(){
        // 跳转去手机号验证页面
        RegisterPage registerPage = new RegisterPage();
        // 使用自定义短信模板(不设置则使用默认模板)
//        String TEMP_CODE = "1319972";
//        registerPage.setTempCode(TEMP_CODE);
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    //返回验证成功的手机号phone
                }else {
                    // TODO 处理错误的结果
                    Throwable throwableStr = (Throwable) data;
                    MyLog.i(throwableStr.getMessage());//{"status":603,"detail":"短信验证码不正确"}
                    //解析返回的错误信息
                    SmsErrorBean smsErrorBean = GjsonUtils.getInstance().jsonToClass(throwableStr.getMessage().toString(), SmsErrorBean.class);
                    Toast.makeText(getApplication(),smsErrorBean.getDetail(),Toast.LENGTH_LONG).show();
                }
            }
        });
        registerPage.show(this);
    }

    /**
     * 检查填写输入是否正常
     * @param user_name_edit
     * @param psw1_edit
     * @param psw2_edit
     * @return
     */
    public boolean checkInput(EditText user_name_edit,EditText psw1_edit,EditText psw2_edit){
        if(TextUtils.isEmpty(user_name_edit.getText().toString())){
            user_name_edit.requestFocus();
            Toast.makeText(getApplication(),"请输入用户名",Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(psw1_edit.getText().toString())){
            psw1_edit.requestFocus();
            Toast.makeText(getApplication(),"请输入密码",Toast.LENGTH_SHORT).show();
            return false;
        }else if(TextUtils.isEmpty(psw2_edit.getText().toString())){
            psw2_edit.requestFocus();
            Toast.makeText(getApplication(),"请确认密码",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!psw1_edit.getText().toString().equals(psw2_edit.getText().toString())){
            Toast.makeText(getApplication(),"两次输入密码不一致",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //断开View引用
        mvpPresenter.detachView();
        // 使用完EventHandler需注销，否则可能出现内存泄漏
        SMSSDK.unregisterEventHandler(eventHandler);
    }

}
