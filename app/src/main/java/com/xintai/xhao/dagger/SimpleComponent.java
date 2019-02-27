package com.xintai.xhao.dagger;

import com.xintai.xhao.MainActivity;
import com.xintai.xhao.TestAct;

import dagger.Component;

/**
 * Created by xionghao on 2018/5/25 0025.
 */

//用@Component表示这个接口是一个连接器，能用@Component注解的只能是interface或者抽象类；
//一般情况下注入的实例不是同一实例；
//Component可以依赖多个Module对象；
//@Component(modules = GsonModule.class)
@Component(modules = {GsonModule.class,PoetryModule.class})
public interface SimpleComponent {

    /**
     * 这里inject表示注入的意思，这个方法名可以随意更改，但建议就用inject即可。
     * 将Dagger2在编译时候生成的实例注入到请求注入的Activity和Fragment;
     *
     * @param activity 被注入的对象
     */
    void inject(MainActivity activity);

    void inject(TestAct activity);

}
