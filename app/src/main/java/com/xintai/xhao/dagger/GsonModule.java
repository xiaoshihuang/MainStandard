package com.xintai.xhao.dagger;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xionghao on 2018/5/25 0025.
 */
//什么时候使用Module：1.第三方jar里面的实例，你不能对其构造方法进行@Inject；
//    2.一个类的构造方法有多个的时候，Module可以指定注入那一个构造方法
@Module
public class GsonModule {

    /**
     * @Provides 注解表示这个方法是用来创建某个实例对象的，这里我们创建返回Gson对象；
     * 方法名随便，一般用provideXXX结构；
     * @return 返回注入对象
     */
    @Provides
    public Gson provideGson() {
        return new Gson();
    }
}