package com.xintai.xhao.mvp;

import com.xintai.xhao.bean.AppVersionInfo;
import com.xintai.xhao.network.RequestApi;
import com.xintai.xhao.utils.MyLog;

import java.util.HashMap;

import okhttp3.ResponseBody;
import rx.Observer;

/**
 * Created by xionghao on 2018/9/20 0020.
 * <p/>MvpPresenter,MvpModel,MvpView定义好了m-p-v返回的类型,这里的返回类型是String
 */

public class MvpPresenter extends BasePresenter<MvpView> {
    //Mode完成对数据的请求，然后将获取到的数据通过callback回调，显示在presenter里面，
    //感觉Mode的功能和retrofit很相似，我们这里可以直接使用retrofit；
    //将retrofit返回的数据类型统一为ResponseBody，这样我们在通过view回调数据的时候不需要定义多个方法，
    //通过请求时候传入的methodName来判断，将数据返回给那个请求；
    //在retrofit里面调用请求的时候，我们可以使用反射机制，这样就只需要一个presenter；

    /**
     * 获取网络数据
     * @param params 入参
     * @param methedMark 标记是哪个方法请求的
     */
    public void setData(HashMap<String, String> params, final String methedMark) throws Exception {
        //显示正在加载进度条
        getView().showLoading();

        RequestApi.Request(methedMark,params, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {
                if (isViewAttached()) {
                    getView().hideLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                MyLog.i("MvpPresenter.onError:" + e.toString());
                //1.解析类不对的时候，程序是不会直接蹦掉的，而是走onError，抛出异常，空指针
                //2.没有网络的时候，也会被这里捕获
                if (isViewAttached()) {
                    getView().showErr(e.toString());
                    getView().hideLoading();
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                //将retrofit获取到的数据，以ResponseBody的形式直接返回，不要在presenter里面解析（在view里面解析），这样一个Presenter就可以共用
                if (isViewAttached()) {
                    getView().callBackData(responseBody,methedMark);
                }
            }
        });

        // 调用Model请求数据
//                MvpModel.getNetData(params, new Callback<String>() {
//
//                    @Override
//                    public void onSuccess(String data) {
//                        //调用view接口显示数据
//                        if (isViewAttached()) {
//                            getView().showData(data);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        //调用view接口提示失败信息
//                        if (isViewAttached()) {
//                            getView().showToast(msg);
//                        }
//                    }
//
//                    @Override
//                    public void onError() {
//                        //调用view接口提示请求异常
//                        if (isViewAttached()) {
//                            getView().showErr();
//                        }
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        // 隐藏正在加载进度条
//                        if (isViewAttached()) {
//                            getView().hideLoading();
//                        }
//                    }
//                });
    }
}
