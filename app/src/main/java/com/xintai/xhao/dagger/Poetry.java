package com.xintai.xhao.dagger;

import javax.inject.Inject;

/**
 * Created by xionghao on 2018/5/31 0031.
 */

public class Poetry {
    private String mark;

    @Inject
    public Poetry(){
        mark = "B键已扣，不死不回家，一生飘逸全靠浪";
    }

    public Poetry(String mark){
       this.mark = mark;
    }
}
