package com.xintai.xhao.bean;

/**
 * Created by xionghao on 2018/9/28 0028.
 */

public class SmsErrorBean {
    //    {"status":603,"detail":"请填写正确的手机号码"}
    /**
     * status : 603
     * detail : 请填写正确的手机号码
     */
    private int status;
    private String detail;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
