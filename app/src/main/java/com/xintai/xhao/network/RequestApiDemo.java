package com.xintai.xhao.network;

import com.xintai.xhao.F;
import com.xintai.xhao.bean.Adverindex;
import com.xintai.xhao.bean.AppVersionInfo;
import com.xintai.xhao.bean.CakeBean;
import com.xintai.xhao.bean.MenuBean;
import com.xintai.xhao.bean.Verification;

import java.util.Map;

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
public class RequestApiDemo extends RetrofitUtils {

    protected static final NetService service = getRetrofit().create(NetService.class);
    //设置Cache-Control状态，此处表示使用缓存，F.cache_time表示缓存的时长。
    public static final String cache_state_1 = "max-age=" + F.okHttp3_cache_time;

    /**
     * 定义数据请求的接口
     */
    private interface NetService {
        //POST请求，请求参数比较少
        @FormUrlEncoded
        @POST("bjws/app.user/login")
        Observable<Verification> getVerfcationCodePost(@Field("tel") String tel, @Field("password") String pass);

        //POST请求，请求参数比较多
        @FormUrlEncoded
        @POST("bjws/app.user/login")
        Observable<Verification> getVerfcationCodePostMap(@FieldMap Map<String, String> map);

        //如果Post请求参数有多个，除了使用@FieldMap，还可以使用@Body。那么统一封装到类中应该会更好，这样维护起来会非常方便
        //@Body Reviews reviews
        //        Reviews是一个由参数组成的类
        //        public class Reviews {
        //            public String tel;
        //            public String password;
        //        }

        //POST请求--不使用公共的baseUrl
        @FormUrlEncoded
        @POST
        Observable<AppVersionInfo> postVersionInfoParam(@Url String url, @Field("action") String action, @Field("plateType") String plateType);

        //GET请求
        @GET("bjws/app.user/login")
        Observable<Verification> getVerfcationGet(@Query("tel") String tel, @Query("password") String pass);

        //可以在拦截器里面设置固定Header，也可以在接口方法里面单独设置Header，接口方法+拦截器可以设置动态的Header;
        /**
         * 动态设置Header；
         * 如果设置 @Headers("Cache-Control: public," + CACHE_CONTROL_CACHE)就是使用缓存机制；
         * 如果没有设置，就是实时请求；
         * post请求一般不使用缓存，post请求获取到的数据是经常变动的，对其进行缓存的意义不大；
         */
        //GET请求--带缓存
        @Headers("Cache-Control: public," + cache_state_1)
        @GET("public/adverindex/adverindextestjson")
        Observable<Adverindex> getAdverGet(@Query("checkuid") String tel);

        //GET请求---不使用公共的baseUrl
        @GET
        Observable<Adverindex> getVersionInfo(@Url String url);

        @GET
        Observable<Adverindex> getVersionInfoParam(@Url String url, @Query("action") String action, @Query("plateType") String plateType);

        @GET
        Observable<AppVersionInfo> getVersionInfoParam1(@Url String url, @QueryMap Map<String, String> options);

        //如果请求的相对地址也是需要调用方传递，那么可以使用@Path注解，示例代码如下
        //        @GET("book/{id}")
        //        Call<BookResponse> getBook(@Path("id") String id);

        //一对多(比如查找name=张三，李四，王五的)，可以写多个Query("q") String name，也可以写一个Query("q") List<String> names


        //GET请求，设置缓存
        @Headers("Cache-Control: public," + cache_state_1)
        @GET("shequ/index.php")
        //m=cake&c=index&a=cake_list&page=1&limit=5
        Observable<CakeBean> getCakeGet(@Query("m") String m,@Query("c") String c,@Query("a") String a,@Query("page") String page,@Query("limit") String limit);

        //GET请求，设置缓存
        @Headers("Cache-Control: public," + cache_state_1)
        @GET("bjws/app.user/login")
        Observable<Verification> getVerfcationGetCache(@Query("tel") String tel, @Query("password") String pass);

        @GET("bjws/app.menu/getMenu")
        Observable<MenuBean> getMainMenu();

        //wap/app/userlogin.do?action=getValidatecode
        @GET("wap/app/userlogin.do")
        Observable<okhttp3.ResponseBody> getPic(@Query("action") String action);
    }


    //POST请求
    public static void verfacationCodePost(String tel, String pass,Observer<Verification> observer){
        setSubscribe(service.getVerfcationCodePost(tel, pass),observer);
    }

    //POST请求参数以map传入
    public static void verfacationCodePostMap(Map<String, String> map,Observer<Verification> observer) {
        setSubscribe(service.getVerfcationCodePostMap(map),observer);
    }

    //Get请求设置缓存
    public static void verfacationCodeGetCache(String tel, String pass,Observer<Verification> observer) {
        setSubscribe(service.getVerfcationGetCache(tel, pass),observer);
    }

    //Get请求
    public static void verfacationCodeGet(String tel, String pass,Observer<Verification> observer) {
        setSubscribe(service.getVerfcationGet(tel, pass),observer);
    }

    //Get请求广告
    public static void AdvGet(String uid,Observer<Adverindex> observer) {
        setSubscribe(service.getAdverGet(uid),observer);
    }

    //Get请求广告m=cake&c=index&a=cake_list&page=1&limit=5
    public static void getCakeCodeGet(Observer<CakeBean> observer) {
        setSubscribe(service.getCakeGet("cake","index","cake_list","1","4"),observer);
    }

    //Get获取掌上信泰的版本信息
    public static void GetVersion(String url,Observer<Adverindex> observer) {
        setSubscribe(service.getVersionInfo(url),observer);
    }

    //Get获取掌上信泰的版本信息
    public static void GetVersionParam(String url,String param1,String param2,Observer<Adverindex> observer) {
        setSubscribe(service.getVersionInfoParam(url,param1,param2),observer);
    }

    //Get获取掌上信泰的版本信息
    public static void GetVersionParam1(String url,Map<String, String> map,Observer<AppVersionInfo> observer) {
        setSubscribe(service.getVersionInfoParam1(url,map),observer);
    }

    //Post获取掌上信泰的版本信息
    public static void PostVersionParam(String url,String param1,String param2,Observer<AppVersionInfo> observer) {
        setSubscribe(service.postVersionInfoParam(url,param1,param2),observer);
    }

    //Get请求验证码
    public static void AdvGetPic(String action,Observer<okhttp3.ResponseBody> observer) {
        setSubscribe(service.getPic(action),observer);
    }

    //Get请求
    public static void verfacationCodeGetsub(String tel, String pass, Observer<Verification> observer) {
        setSubscribe(service.getVerfcationGet(tel, pass),observer);
    }

    //Get请求
    public static void Getcache( Observer<MenuBean> observer) {
        setSubscribe(service.getMainMenu(),observer);
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
