package com.xintai.xhao.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class A2Bean implements Parcelable {

	String xx1, xx2, xx3;

	public String getXx1() {
		return xx1;
	}

	public void setXx1(String xx1) {
		this.xx1 = xx1;
	}

	public String getXx2() {
		return xx2;
	}

	public void setXx2(String xx2) {
		this.xx2 = xx2;
	}

	public String getXx3() {
		return xx3;
	}

	public void setXx3(String xx3) {
		this.xx3 = xx3;
	}

	// Parcelable序列化实现方法
	public A2Bean() {

	}

	// 系统自动添加，给createFromParcel里面用
	public A2Bean(Parcel in) {
		xx1 = in.readString();
		xx2 = in.readString();
		xx3 = in.readString();
	}

	// 内容接口描述，默认返回0即可。
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		// TODO Auto-generated method stub
		dest.writeString(getXx1());
		dest.writeString(getXx2());
		dest.writeString(getXx3());
	}

	public static final Creator<A2Bean> CREATOR = new Creator<A2Bean>() {

		public A2Bean createFromParcel(Parcel in) {
			return new A2Bean(in); // 在构造函数里面完成了 读取 的工作
		}

		// 供反序列化本类数组时调用的
		public A2Bean[] newArray(int size) {
			return new A2Bean[size];
		}
	};

}
