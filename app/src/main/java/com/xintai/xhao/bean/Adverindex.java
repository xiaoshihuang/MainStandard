package com.xintai.xhao.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */
public class Adverindex implements Parcelable {
    /**
     * adverup : [{"pic":"http://img.025nj.com/img/wehome/upload/adverindex/20170103103345332.png","url":"http://e8119f2537e2.ih5.cn/idea/KcWHbkt","ttype":"1","tid":"0","ptype":"0","ptitle":"我们家年度风云人物","info":"啊晓得啊，2016我们家出名人了！","shareurl":"http://e8119f2537e2.ih5.cn/idea/i3-ZWUO"},{"pic":"http://img.025nj.com/img/wehome/upload/adverindex/20161205125836306.png","url":"http://www.025nj.com/SheQuApi3.0/shequ/index.php?m=pintuan&c=index&a=index&paytype=2&uid=","ttype":"31","tid":"0","ptype":"0","ptitle":"靠谱团购-团团家","info":"\u201c我们家\u201dAPP年末倾情推出本地靠谱团购业务\u201c团团家\u201d严选商品 性价比之王 包邮到家 一起买更便宜哦。","shareurl":"http://www.025nj.com/SheQuApi3.0/shequ/index.php?m=pintuan&c=index&a=index"},{"pic":"http://img.025nj.com/img/wehome/upload/adverindex/20160624135411449.png","url":"","ttype":"28","tid":"0","ptype":"0","ptitle":"公共自行车信息查询","info":"","shareurl":""},{"pic":"http://img.025nj.com/img/wehome/upload/adverindex/20170107112708635.jpg","url":"http://www.025nj.com/SheQuApi3.0/shequ/index.php?m=goods&c=index&a=myshow&gid=933&pc_hash=cFEwmI","ttype":"7","tid":"933","ptype":"0","ptitle":"老山洋槐蜂蜜特价","info":"","shareurl":"http://www.025nj.com/SheQuApi3.0/shequ/index.php?m=goods&c=index&a=myshow&gid=933&pc_hash=cFEwmI"},{"pic":"http://img.025nj.com/img/wehome/upload/adverindex/20170111105713356.jpg","url":"http://www.025nj.com/SheQuApi3.0/public/adveract/adveract","ttype":"30","tid":"0","ptype":"0","ptitle":"亲子拾光","info":"","shareurl":"http://www.025nj.com/SheQuApi3.0/public/adveract/adveract"}]
     * adverdown : [{"pic":"http://img.025nj.com/img/wehome/upload/adverindex/20161226090328196.png","url":"http://www.025nj.com/SheQuApi3.0/shequ/index.php?m=toolsapi&c=index&a=cityair","ttype":"1","tid":"0","ptype":"0","ptitle":"空气质量","info":"","shareurl":"http://www.025nj.com/SheQuApi3.0/shequ/index.php?m=toolsapi&c=index&a=disobey&type=1"},{"pic":"http://img.025nj.com/img/wehome/upload/adverindex/20161226090358652.png","url":"http://www.025nj.com/SheQuApi3.0/shequ/index.php?m=toolsapi&c=index&a=park","ttype":"1","tid":"0","ptype":"0","ptitle":"停车","info":"","shareurl":"http://m.meishij.net/html5/"},{"pic":"http://img.025nj.com/img/wehome/upload/adverindex/20161226090348602.png","url":"http://www.025nj.com/SheQuApi3.0/shequ/index.php?m=toolsapi&c=index&a=express","ttype":"1","tid":"0","ptype":"0","ptitle":"快递","info":"","shareurl":"http://www.025nj.com/SheQuApi3.0/shequ/index.php?m=toolsapi&c=index&a=disobey&type=1"},{"pic":"http://img.025nj.com/img/wehome/upload/adverindex/20161226090339920.png","url":"http://www.025nj.com/SheQuApi3.0/shequ/index.php?m=user&c=usercheck&a=zz_init&checkuid=","ttype":"21","tid":"0","ptype":"0","ptitle":"站长招募","info":"","shareurl":"http://zt.longhoo.net/html/myhomeshare/myhomedownload/"},{"pic":"http://img.025nj.com/img/wehome/upload/adverindex/20170108092902961.jpg","url":"https://www.025nj.com/SheQuApi3.0/shequ/index.php?m=goods&c=index&a=myshow&gid=931&pc_hash=42I45W","ttype":"7","tid":"931","ptype":"0","ptitle":"进口","info":"进口日用","shareurl":"https://www.025nj.com/SheQuApi3.0/shequ/index.php?m=admin&c=index&pc_hash=cQbGEc"}]
     * errorCode : 0
     */

    private int errorCode;
    private List<AdverupBean> adverup;
    private List<AdverdownBean> adverdown;

    public static Adverindex objectFromData(String str) {
        return new Gson().fromJson(str, Adverindex.class);
    }

    public static Adverindex objectFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            return new Gson().fromJson(jsonObject.getString(str), Adverindex.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Adverindex> arrayAdverindexFromData(String str) {
        Type listType = new TypeToken<ArrayList<Adverindex>>() {
        }.getType();
        return new Gson().fromJson(str, listType);
    }

    public static List<Adverindex> arrayAdverindexFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<Adverindex>>() {
            }.getType();
            return new Gson().fromJson(jsonObject.getString(str), listType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<AdverupBean> getAdverup() {
        return adverup;
    }

    public void setAdverup(List<AdverupBean> adverup) {
        this.adverup = adverup;
    }

    public List<AdverdownBean> getAdverdown() {
        return adverdown;
    }

    public void setAdverdown(List<AdverdownBean> adverdown) {
        this.adverdown = adverdown;
    }

    public static class AdverupBean {
        /**
         * pic : http://img.025nj.com/img/wehome/upload/adverindex/20170103103345332.png
         * url : http://e8119f2537e2.ih5.cn/idea/KcWHbkt
         * ttype : 1
         * tid : 0
         * ptype : 0
         * ptitle : 我们家年度风云人物
         * info : 啊晓得啊，2016我们家出名人了！
         * shareurl : http://e8119f2537e2.ih5.cn/idea/i3-ZWUO
         */
        private String pic;
        private String url;
        private String ttype;
        private String tid;
        private String ptype;
        private String ptitle;
        private String info;
        private String shareurl;

        public static AdverupBean objectFromData(String str) {
            return new Gson().fromJson(str, AdverupBean.class);
        }

        public static AdverupBean objectFromData(String str, String key) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                return new Gson().fromJson(jsonObject.getString(str), AdverupBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static List<AdverupBean> arrayAdverupBeanFromData(String str) {
            Type listType = new TypeToken<ArrayList<AdverupBean>>() {
            }.getType();
            return new Gson().fromJson(str, listType);
        }

        public static List<AdverupBean> arrayAdverupBeanFromData(String str, String key) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<AdverupBean>>() {
                }.getType();
                return new Gson().fromJson(jsonObject.getString(str), listType);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return new ArrayList();
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTtype() {
            return ttype;
        }

        public void setTtype(String ttype) {
            this.ttype = ttype;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getPtype() {
            return ptype;
        }

        public void setPtype(String ptype) {
            this.ptype = ptype;
        }

        public String getPtitle() {
            return ptitle;
        }

        public void setPtitle(String ptitle) {
            this.ptitle = ptitle;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getShareurl() {
            return shareurl;
        }

        public void setShareurl(String shareurl) {
            this.shareurl = shareurl;
        }
    }

    public static class AdverdownBean {
        /**
         * pic : http://img.025nj.com/img/wehome/upload/adverindex/20161226090328196.png
         * url : http://www.025nj.com/SheQuApi3.0/shequ/index.php?m=toolsapi&c=index&a=cityair
         * ttype : 1
         * tid : 0
         * ptype : 0
         * ptitle : 空气质量
         * info :
         * shareurl : http://www.025nj.com/SheQuApi3.0/shequ/index.php?m=toolsapi&c=index&a=disobey&type=1
         */
        private String pic;
        private String url;
        private String ttype;
        private String tid;
        private String ptype;
        private String ptitle;
        private String info;
        private String shareurl;

        public static AdverdownBean objectFromData(String str) {
            return new Gson().fromJson(str, AdverdownBean.class);
        }

        public static AdverdownBean objectFromData(String str, String key) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                return new Gson().fromJson(jsonObject.getString(str), AdverdownBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static List<AdverdownBean> arrayAdverdownBeanFromData(String str) {
            Type listType = new TypeToken<ArrayList<AdverdownBean>>() {
            }.getType();
            return new Gson().fromJson(str, listType);
        }

        public static List<AdverdownBean> arrayAdverdownBeanFromData(String str, String key) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<AdverdownBean>>() {
                }.getType();
                return new Gson().fromJson(jsonObject.getString(str), listType);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return new ArrayList();
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTtype() {
            return ttype;
        }

        public void setTtype(String ttype) {
            this.ttype = ttype;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getPtype() {
            return ptype;
        }

        public void setPtype(String ptype) {
            this.ptype = ptype;
        }

        public String getPtitle() {
            return ptitle;
        }

        public void setPtitle(String ptitle) {
            this.ptitle = ptitle;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getShareurl() {
            return shareurl;
        }

        public void setShareurl(String shareurl) {
            this.shareurl = shareurl;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.errorCode);
        dest.writeList(this.adverup);
        dest.writeList(this.adverdown);
    }

    public Adverindex() {
    }

    protected Adverindex(Parcel in) {
        this.errorCode = in.readInt();
        this.adverup = new ArrayList<AdverupBean>();
        in.readList(this.adverup, AdverupBean.class.getClassLoader());
        this.adverdown = new ArrayList<AdverdownBean>();
        in.readList(this.adverdown, AdverdownBean.class.getClassLoader());
    }

    public static final Creator<Adverindex> CREATOR = new Creator<Adverindex>() {
        @Override
        public Adverindex createFromParcel(Parcel source) {
            return new Adverindex(source);
        }

        @Override
        public Adverindex[] newArray(int size) {
            return new Adverindex[size];
        }
    };
}
