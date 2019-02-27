package com.xintai.xhao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xintai.xhao.bean.Adverindex;
import com.xintai.xhao.bean.AppVersionInfo;
import com.xintai.xhao.dagger.DaggerSimpleComponent;
import com.xintai.xhao.dagger.Poetry;
import com.xintai.xhao.dagger.Student;
import com.xintai.xhao.network.RequestApi;
import com.xintai.xhao.network.RequestApiDemo;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Observer;

/**
 * Created by xionghao on 2018/5/30 0030.
 */

public class TestAct extends AppCompatActivity implements View.OnClickListener{
    TextView text1;
    Button btn,btn1;

    @Inject
    Student student ;
    @Inject
    Gson gson;
    @Inject
    Poetry poetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test);

        btn = (Button) findViewById(R.id.btn);
        btn1 = (Button) findViewById(R.id.btn1);
        text1 = (TextView) findViewById(R.id.text1);

        // 将Dagger2在编译时候生成的实例注入到请求注入的Activity和Fragment;
        DaggerSimpleComponent.builder()
                //                .simpleModule(new SimpleModule(this))
                .build()
                .inject(this);

        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
    }

    /**
     json解析快捷方法--fastJson和Gson的解析类，编写方式一样的
     1. 打开 Settings，选择菜单栏 File -> Settings...
     2. 左边选择 Plugins 选项，右边进入之后选择 Browse repositories...
     3. 搜索 GsonFormat，选中之后选择右边 Install 安装插件
     4. 安装完成后， 重启 Android Studio
     5. 首先创建一个 bean, 然后使用快捷键 Alt + S, 打开插件 GUI 之后， 在里面粘贴 json 格式字符串, 选择 ok
     6. 在弹出的窗口中， 选择需要生成的字段， 再选择 ok， 即可自动生成需要的 Gson bean
     */

    @Override
    public void onClick(View v) {
        if(v.equals(btn)){
            text1.setText(gson.toJson(student)+"\n"+gson.toJson(poetry)+"\n"+student);
        }else  if(v.equals(btn1)){
            //解析类不对的时候，程序是不会直接蹦掉的，而是走onError，抛出异常，空指针。
            //get请求一般是baseUrl+path+Query，比如MainActivity里面的请求。
            //关于retrofit请求url和param设置http://duanyytop.github.io/2016/08/06/Retrofit%E7%94%A8%E6%B3%95%E8%AF%A6%E8%A7%A3/
            //
            getVersionUrl();
            getVersionParam();
            getVersionParam1();
              //Post请求需要把请求参数放置在请求体中，而非拼接在url后面
            postVersionParam();
        }
    }

    /**
     * 为某个请求设置完整的URL；
     * 假如说你的某一个请求不是以base_url开头该怎么办呢？别着急，办法很简单，看下面这个例子你就懂了；
     */
    private void getVersionUrl() {
        //https://mobile-dat.sinatay.com/agentwap/wap/app/userlogin.do?action=checkversion&plateType=android
        String allUrl = "https://mobile-dat.sinatay.com/agentwap/wap/app/userlogin.do?action=checkversion&plateType=android";
        RequestApiDemo.GetVersion(allUrl, new Observer<Adverindex>() {
            @Override
            public void onCompleted() {
                Log.i("xhao","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                //1解析类不对的时候，程序是不会直接蹦掉的，而是走onError，抛出异常，空指针
                Log.i("xhao","onError:"+e.toString());
            }

            @Override
            public void onNext(Adverindex adverindex) {
                int lens = adverindex.getAdverup().size();
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < lens; i++) {
                    buffer.append(adverindex.getAdverup().get(i).getPtitle());
                    buffer.append("\n");
                }
                text1.setText(buffer.toString());
            }
        });
    }

    /**
     * url=非base_url+ @Query；
     * 假如说你的某一个请求不是以base_url开头该怎么办呢？别着急，办法很简单，看下面这个例子你就懂了；
     */
    private void getVersionParam() {
        //https://mobile-dat.sinatay.com/agentwap/wap/app/userlogin.do?action=checkversion&plateType=android
        String allUrl = "https://mobile-dat.sinatay.com/agentwap/wap/app/userlogin.do";
        String param1 = "checkversion";
        String param2 = "android";
        RequestApiDemo.GetVersionParam(allUrl,param1,param2, new Observer<Adverindex>() {
            @Override
            public void onCompleted() {
                Log.i("xhao","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                //1解析类不对的时候，程序是不会直接蹦掉的，而是走onError，抛出异常，空指针
                Log.i("xhao","onError:"+e.toString());
            }

            @Override
            public void onNext(Adverindex adverindex) {
                int lens = adverindex.getAdverup().size();
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < lens; i++) {
                    buffer.append(adverindex.getAdverup().get(i).getPtitle());
                    buffer.append("\n");
                }
                text1.setText(buffer.toString());
            }
        });
    }

    /**
     * url=非base_url+@QueryMap；
     * 假如说你的某一个请求不是以base_url开头该怎么办呢？别着急，办法很简单，看下面这个例子你就懂了；
     */
    private void getVersionParam1() {
        //https://mobile-dat.sinatay.com/agentwap/wap/app/userlogin.do?action=checkversion&plateType=android
        String allUrl = "https://mobile-dat.sinatay.com/agentwap/wap/app/userlogin.do";
        Map<String, String> map = new HashMap<>();
        map.put("action", "checkversion");
        map.put("plateType", "android");
        RequestApiDemo.GetVersionParam1(allUrl,map, new Observer<AppVersionInfo>() {
            @Override
            public void onCompleted() {
                Log.i("xhao","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                //1解析类不对的时候，程序是不会直接蹦掉的，而是走onError，抛出异常，空指针
                Log.i("xhao","onError:"+e.toString());
            }

            @Override
            public void onNext(AppVersionInfo adverindex) {
                text1.setText("低于"+adverindex.getResponseObject().getMinversion()+"需要强制更新");
            }
        });
    }

    /**
     * Post请求；
     * url=非base_url+ @Filed；
     * @FormUrlEncoded不可用于get请求；
     * @FormUrlEncoded将会自动将请求参数的类型调整为application/x-www-form-urlencoded；
     * 如果有多个参数可以使用@FieldMap或者@Body
     * 1.(@FieldMap Map<String, String> fields);
     * 2.(@Body Reviews reviews);
     * 假如说你的某一个请求不是以base_url开头该怎么办呢？别着急，办法很简单，看下面这个例子你就懂了；
     */
    private void postVersionParam() {
        //https://mobile-dat.sinatay.com/agentwap/wap/app/userlogin.do?action=checkversion&plateType=android
        String allUrl = "https://mobile-dat.sinatay.com/agentwap/wap/app/userlogin.do";
        String param1 = "checkversion";
        String param2 = "android";
        RequestApiDemo.PostVersionParam(allUrl,param1,param2, new Observer<AppVersionInfo>() {
            @Override
            public void onCompleted() {
                Log.i("xhao","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                //1解析类不对的时候，程序是不会直接蹦掉的，而是走onError，抛出异常，空指针
                Log.i("xhao","onError:"+e.toString());
            }

            @Override
            public void onNext(AppVersionInfo adverindex) {
                text1.setText("低于"+adverindex.getResponseObject().getMinversion()+"需要强制更新");
            }
        });
    }


}
