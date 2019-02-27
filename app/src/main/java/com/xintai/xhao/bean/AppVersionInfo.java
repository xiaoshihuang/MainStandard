package com.xintai.xhao.bean;

/**
 * Created by xionghao on 2018/6/15 0015.
 */

public class AppVersionInfo {

    /**
     * errorMessage :
     * responseObject : {"downloadurl":"http://imtt.dd.qq.com/16891/3FEBB86BBC631BD61C9B9F7E3B2555A5.apk?fsname=com.xintai.appbasicframework_2.2.2_25.apk&csr=1bbd","maxversion":"2.1.8","minversion":"2.1.8","rfeshen":"0"}
     * resultCode : 1
     */

    private String errorMessage;
    private ResponseObjectBean responseObject;
    private String resultCode;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ResponseObjectBean getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(ResponseObjectBean responseObject) {
        this.responseObject = responseObject;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public static class ResponseObjectBean {
        /**
         * downloadurl : http://imtt.dd.qq.com/16891/3FEBB86BBC631BD61C9B9F7E3B2555A5.apk?fsname=com.xintai.appbasicframework_2.2.2_25.apk&csr=1bbd
         * maxversion : 2.1.8
         * minversion : 2.1.8
         * rfeshen : 0
         */

        private String downloadurl;
        private String maxversion;
        private String minversion;
        private String rfeshen;

        public String getDownloadurl() {
            return downloadurl;
        }

        public void setDownloadurl(String downloadurl) {
            this.downloadurl = downloadurl;
        }

        public String getMaxversion() {
            return maxversion;
        }

        public void setMaxversion(String maxversion) {
            this.maxversion = maxversion;
        }

        public String getMinversion() {
            return minversion;
        }

        public void setMinversion(String minversion) {
            this.minversion = minversion;
        }

        public String getRfeshen() {
            return rfeshen;
        }

        public void setRfeshen(String rfeshen) {
            this.rfeshen = rfeshen;
        }
    }
}
