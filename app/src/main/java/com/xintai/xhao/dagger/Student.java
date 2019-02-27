package com.xintai.xhao.dagger;

import javax.inject.Inject;

/**
 * Created by xionghao on 2018/5/25 0025.
 */

public class Student {
    String name = "张无忌";

//    用Inject标记构造函数,表示用它来注入到目标对象中去
    @Inject
    public Student() {
        //通过Inject会自动在app\build\generated\source\apt\debug\XXXXX包名下生成\Student_Factory.java
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}