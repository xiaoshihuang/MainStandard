package com.xintai.xhao.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/23.
 */
public class CakeBean {

    /**
     * error : 0
     * msg :
     * data : {"list":[{"id":"8","name":"花样妈妈","title":"安易客蛋糕\u2014\u2014花样妈妈 15cm*15cm","thumb":"https://img.025nj.com/img/shequ/2016/0226/20160226013836772.jpg","price":"198.00","unit":"15cm","sales":"42"},{"id":"7","name":"海盐乳酪慕斯","title":"安易客蛋糕\u2014\u2014海盐乳酪慕斯 15cm*15cm","thumb":"https://img.025nj.com/img/shequ/2016/0225/20160225051228568.jpg","price":"198.00","unit":"15cm","sales":"49"},{"id":"6","name":"君度栗蓉","title":"安易客蛋糕\u2014\u2014君度栗蓉 15cm*15cm","thumb":"https://img.025nj.com/img/shequ/2016/0225/20160225045629871.jpg","price":"198.00","unit":"15cm","sales":"41"},{"id":"5","name":"良辰莓景","title":"安易客蛋糕\u2014\u2014良辰莓景 15cm*15cm","thumb":"https://img.025nj.com/img/shequ/2016/0225/20160225041521293.jpg","price":"178.00","unit":"15cm","sales":"50"},{"id":"4","name":"法式经典拿破仑","title":"安易客蛋糕\u2014\u2014法式经典拿破仑 15cm*15cm","thumb":"https://img.025nj.com/img/shequ/2016/0225/20160225024517990.jpg","price":"198.00","unit":"15cm","sales":"46"}],"tel":"025-84696783"}
     */

    private String error;
    private String msg;
    private DataBean data;

    public static CakeBean objectFromData(String str) {

        return new Gson().fromJson(str, CakeBean.class);
    }

    public static CakeBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), CakeBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<CakeBean> arrayCakeBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<CakeBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<CakeBean> arrayCakeBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<CakeBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * list : [{"id":"8","name":"花样妈妈","title":"安易客蛋糕\u2014\u2014花样妈妈 15cm*15cm","thumb":"https://img.025nj.com/img/shequ/2016/0226/20160226013836772.jpg","price":"198.00","unit":"15cm","sales":"42"},{"id":"7","name":"海盐乳酪慕斯","title":"安易客蛋糕\u2014\u2014海盐乳酪慕斯 15cm*15cm","thumb":"https://img.025nj.com/img/shequ/2016/0225/20160225051228568.jpg","price":"198.00","unit":"15cm","sales":"49"},{"id":"6","name":"君度栗蓉","title":"安易客蛋糕\u2014\u2014君度栗蓉 15cm*15cm","thumb":"https://img.025nj.com/img/shequ/2016/0225/20160225045629871.jpg","price":"198.00","unit":"15cm","sales":"41"},{"id":"5","name":"良辰莓景","title":"安易客蛋糕\u2014\u2014良辰莓景 15cm*15cm","thumb":"https://img.025nj.com/img/shequ/2016/0225/20160225041521293.jpg","price":"178.00","unit":"15cm","sales":"50"},{"id":"4","name":"法式经典拿破仑","title":"安易客蛋糕\u2014\u2014法式经典拿破仑 15cm*15cm","thumb":"https://img.025nj.com/img/shequ/2016/0225/20160225024517990.jpg","price":"198.00","unit":"15cm","sales":"46"}]
         * tel : 025-84696783
         */

        private String tel;
        private List<ListBean> list;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static DataBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), DataBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<DataBean> arrayDataBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<DataBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 8
             * name : 花样妈妈
             * title : 安易客蛋糕——花样妈妈 15cm*15cm
             * thumb : https://img.025nj.com/img/shequ/2016/0226/20160226013836772.jpg
             * price : 198.00
             * unit : 15cm
             * sales : 42
             */

            private String id;
            private String name;
            private String title;
            private String thumb;
            private String price;
            private String unit;
            private String sales;

            public static ListBean objectFromData(String str) {

                return new Gson().fromJson(str, ListBean.class);
            }

            public static ListBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), ListBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<ListBean> arrayListBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<ListBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<ListBean> arrayListBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<ListBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }
        }
    }
}
