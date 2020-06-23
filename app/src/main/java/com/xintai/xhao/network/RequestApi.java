package com.xintai.xhao.network;

import com.xintai.xhao.F;
import com.xintai.xhao.bean.Adverindex;
import com.xintai.xhao.bean.AppVersionInfo;
import com.xintai.xhao.bean.CakeBean;
import com.xintai.xhao.bean.MenuBean;
import com.xintai.xhao.bean.Verification;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 类描述：网络请求的操作类
 */
public class RequestApi extends RetrofitUtils {

    protected static final NetService service = getRetrofit().create(NetService.class);
    //设置Cache-Control状态，此处表示使用缓存，F.cache_time表示缓存的时长。
    public static final String cache_state_1 = "max-age=" + F.okHttp3_cache_time;

    /**
     * 定义数据请求的接口
     */
    private interface NetService {
        //GET请求---不使用公共的baseUrl
        @GET
        Observable<AppVersionInfo> getVersionInfo(@Url String url);

        //POST请求，请求参数比较多
        @FormUrlEncoded
        @POST("/agentwap/wap/app/userlogin.do")
        Observable<ResponseBody> register(@FieldMap HashMap<String, String> map);

        @GET("/agentwap/wap/app/userlogin.do")
        Observable<ResponseBody> getRegisterInfo(@QueryMap HashMap<String, String> map);
    }

    /**
     * 作用在Activity直接调用;
     * Get获取掌上信泰的版本信息;
     * @param url
     * @param observer
     */
    public static void getVersionInfo(String url, Observer<AppVersionInfo> observer) {
        setSubscribe(service.getVersionInfo(url), observer);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////自己尝试反射，不需要写Mode(非常规写法)
    /**
     * 作用在mvp模式里面presenter，取代mode;
     * 调用NetService里面具体的请求方法;
     * @param methodName 方法名称
     * @param params 参数
     * @param observer 返回类型
     * @throws Exception
     */
    public static void Request(String methodName, HashMap<String, String> params, Observer<ResponseBody> observer) throws Exception {
//        setSubscribe(service.postRegisterInfo(params), observer);
        Object[] args = new Object[]{params};
        setSubscribe((Observable<ResponseBody>) nvokeMethod(service,methodName,args), observer);
    }

    /**
     * 利用反射，调用一个实例的指定方法
     * @param owner  类
     * @param methodName 方法名
     * @param args  方法参数
     * @return
     * @throws Exception
     */
    public static Object nvokeMethod(Object owner, String methodName, Object[] args) throws Exception {
        // 反射机制和反射调用方法 https://blog.csdn.net/haima1998/article/details/38126567
        Class ownerClass = owner.getClass();
        Class[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        Method method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(owner, args);
    }

    /**
     * 插入观察者
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())//指定网络请求在io后台线程中进行
                //                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//指定observer回调在UI主线程中进行
                .subscribe(observer);//事件起于被观察者，处理在观察者。其实是观察者订阅被观察者，但是为了流式API调用风格才这样书写的，
    }
}
