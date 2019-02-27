package com.xintai.xhao.mvp;

/**
 * Created by xionghao on 2018/9/20 0020.
 */

public interface MvpView extends BaseView{//接口和接口之间，只能是继承
    /**
     * 当数据请求成功后，调用此接口显示数据
     * @param data 数据源
     */
    void callBackData(Object data,String methodMark );
}